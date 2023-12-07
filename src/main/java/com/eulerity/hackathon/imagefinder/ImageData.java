package com.eulerity.hackathon.imagefinder;

public class ImageData {
    public String title;
    public String imageUrl;
    public boolean logoOrIcon;

    public ImageData(String title, String imageUrl) { // primary constructor
        this.title = title;
        this.imageUrl = imageUrl;
        if (endsWithSvgOrPng(title, imageUrl)) {
            this.logoOrIcon = true;
        }
    }

    public ImageData(String imageUrl) { // constructor if no "alt" available
        this("N/A", imageUrl);
    }

    public boolean endsWithSvgOrPng(String alt, String url) { // checks if image is a logo
        String lowercaseUrl = url.toLowerCase();
        return lowercaseUrl.endsWith(".svg") || lowercaseUrl.endsWith(".png") || alt.contains("logo");
    }
}
