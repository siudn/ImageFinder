package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imagefinder.WebCrawler;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<WebCrawler> bots = new ArrayList<>();
        bots.add(new WebCrawler("https://en.wikipedia.org", 1));
        bots.add(new WebCrawler("https://www.npr.org", 2));
        bots.add(new WebCrawler("https://www.nytimes.com", 3));

        for (WebCrawler w : bots) {
            try {
                w.getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
