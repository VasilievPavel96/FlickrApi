package com.vasilievpavel.flickrapi;

import java.util.List;

/**
 * Created by pavel on 06.05.2017.
 */

class PhotoManager {
    private static final PhotoManager ourInstance = new PhotoManager();
    private List<Photo> photoList;
    static PhotoManager getInstance() {
        return ourInstance;
    }

    public static PhotoManager getOurInstance() {
        return ourInstance;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    private PhotoManager() {

    }
}
