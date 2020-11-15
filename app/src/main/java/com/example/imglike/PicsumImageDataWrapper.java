package com.example.imglike;

import java.io.Serializable;
@Deprecated
public class PicsumImageDataWrapper implements Serializable {
    private PicsumImageData picsumImageData;
    private SaveLikedState saveLikedState;

    public PicsumImageDataWrapper() {

    }

    public PicsumImageDataWrapper(PicsumImageData picsumImageData, SaveLikedState saveLikedState) {
        this.picsumImageData = picsumImageData;
        this.saveLikedState = saveLikedState;
    }

    public void setPicsumImageData(PicsumImageData picsumImageData) {
        this.picsumImageData = picsumImageData;
    }

    public void setSaveLikedState(SaveLikedState saveLikedState) {
        this.saveLikedState = saveLikedState;
    }

    public PicsumImageData getPicsumImageData() {
        return picsumImageData;
    }

    public SaveLikedState getSaveLikedState() {
        return saveLikedState;
    }
}
