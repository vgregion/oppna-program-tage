package se.goteborg.retursidan.portlet.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.goteborg.retursidan.model.entity.Photo;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Property editor that will be called for the photo list when advertisements are submitted 
 *
 */
public class PhotoListPropertyEditor extends PropertyEditorSupport {
	private static Logger logger = LoggerFactory.getLogger(PhotoListPropertyEditor.class.getName());
	
	/**
	 * Convert the incoming comma separated list of photo id's to a list of actual Photo objects 
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		logger.trace("Converting incoming list [" + text + "] of photo id's to a set of photo objects");
		List<Photo> photos = new ArrayList<Photo>();
		if (text != null) {
			StringTokenizer st = new StringTokenizer(text, ",");
			while(st.hasMoreTokens()) {
				try {
					String id = st.nextToken();
					Photo photo = new Photo();
					photo.setId(Integer.parseInt(id));
					photos.add(photo);
				} catch(Exception e) {
					logger.trace("Could not convert value in input string \"" + text + "\" to a photo id", e);
				}
			}
		}
		setValue(photos);
	}
	
	/**
	 * Convert the list of photos to a comma separated list of photo id's
	 */
	@Override
	public String getAsText() {
		@SuppressWarnings("unchecked")
		List<Photo> photos = (List<Photo>)getValue();
		StringBuffer sb = new StringBuffer();
		if (photos != null) {
			Iterator<Photo> i = photos.iterator();
			while(i.hasNext()) {
				Photo photo = i.next();
				sb.append(photo.getId());
				if (i.hasNext()) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
}
