package se.goteborg.retursidan.model;

import se.goteborg.retursidan.model.entity.Photo;

import java.sql.Blob;

/**
 * @author Patrik Bergström
 */
public class PhotoHolder {
    private Blob image;
    private long imageLength;
    private String mimeType;

    public void setImage(Blob image) {
        this.image = image;
    }

    public Blob getImage() {
        return image;
    }

    public void setImageLength(long imageLength) {
        this.imageLength = imageLength;
    }

    public long getImageLength() {
        return imageLength;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
