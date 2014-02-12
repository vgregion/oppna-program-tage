package se.goteborg.retursidan.model.entity;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.*;

import se.goteborg.retursidan.model.GeneralEntityBean;

/**
 * Model bean representing a photo
 *
 */
@Entity
@Table(name="vgr_tage_photo")
public class Photo extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 5475984046751168694L;

	@Column(nullable=false)
	private String creatorUid;
	
	private String title;
	private String mimeType;
	
	/**
	 * Store the image as a LOB
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(length=4000000, nullable=false)
	private Blob image;

	/**
	 * Store the thumbnail as a LOB
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(length=100000, nullable=false)
	private Blob thumbnail;
	
	private int width;
	private int height;
	
	public String getCreatorUid() {
		return creatorUid;
	}
	public void setCreatorUid(String creatorUid) {
		this.creatorUid = creatorUid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public Blob getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Blob thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
