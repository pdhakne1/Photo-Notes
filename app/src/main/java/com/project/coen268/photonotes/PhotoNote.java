package com.project.coen268.photonotes;

import java.io.Serializable;

/**
 * Created by pallavi on 2/12/16.
 */
public class PhotoNote implements Serializable{

    private String caption;
    private String imagePath;

    public PhotoNote() {
    }

    public PhotoNote(String caption, String imagePath) {
        this.caption = caption;
        this.imagePath = imagePath;
    }

    public String getCaption() {
        return caption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
