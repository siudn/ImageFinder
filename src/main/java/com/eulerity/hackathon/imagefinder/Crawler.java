package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler implements Runnable {
    private static final int MAX_DEPTH = 3; // change this value if you want more/less pages
    private ArrayList<String> visitedLinks = new ArrayList<String>();
    private ArrayList<ImageData> imageList = new ArrayList<ImageData>();
    private String first_link;
    private Thread thread;

    public Crawler(String link, ArrayList<ImageData> images) {
        // crawler constructor
        first_link = link;
        imageList = images;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        crawl(1, first_link, first_link);
    }

    protected ArrayList<ImageData> crawl(int level, String url, String origUrl) {
        if (level <= MAX_DEPTH) {
            Document doc = request(url);

            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    // selects all links on the page
                    String next_link = link.absUrl("href");
                    if (validLink(next_link) && next_link.contains(origUrl)) {
                        // calls validLink function (below) and stays within domain
                        System.out.println("Crawling " + next_link + "...");
                        return crawl(level++, next_link, origUrl);
                        // recusively crawls on next webpage
                    }
                }
            }
        }
        return imageList;
    }

    private Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                Elements images = doc.select("img");
                // selects all img elements on webpage
                for (Element image : images) {
					// if alt is provided, construct with alt, otherwise construct only with url
					if (!image.attr("alt").isEmpty() && !image.attr("abs:src").isEmpty()) { 
						ImageData newImage = new ImageData(image.attr("alt"), image.attr("abs:src"));
						imageList.add(newImage); // add image to ArrayList of images
					} else if (image.attr("alt").isEmpty() && !image.attr("abs:src").isEmpty()) {
						ImageData newImage = new ImageData(image.attr("abs:src"));
						imageList.add(newImage);
					}
				}
                visitedLinks.add(url); // add url to visitedLinks arrayList
				return doc;
            }
            return null;
        } 
        catch (IOException e) {
            return null;
        }
    }

    private boolean validLink(String url) {
        // checks that link is not a section of the same page & it hasn't been visited
        return !url.contains("#") && !visitedLinks.contains(url);
    }

    public Thread getThread() {
        return thread;
    }
}
