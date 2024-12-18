package com.example.bingebook;

public class Items_Views  {
    private String imageUrl;
    private int sampleId;

    public Items_Views(String imageUrl, int sampleId) {
        this.imageUrl = imageUrl;
        this.sampleId = sampleId;
    }

    public String getImage() {
        return imageUrl;
    }

    public int getId() {
        return sampleId;
    }
}
