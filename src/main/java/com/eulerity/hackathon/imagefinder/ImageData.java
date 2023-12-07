package com.eulerity.hackathon.imagefinder;

public class ImageData {
    public String title;
    public String imageUrl;
    public boolean logoOrIcon;

    public ImageData(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        if (endsWithSvgOrPng(imageUrl)) {
            this.logoOrIcon = true;
        }
    }

    public ImageData(String imageUrl) {
        this("N/A", imageUrl);
    }

    public boolean endsWithSvgOrPng(String url) {
        String lowercaseUrl = url.toLowerCase();
        return lowercaseUrl.endsWith(".svg") || lowercaseUrl.endsWith(".png");
    }
}
