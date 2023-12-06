package com.eulerity.hackathon.imagefinder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crawler {
    public static void main(String[] args) {
        String url = "https://en.wikipedia.org";
        crawl(1, url, new ArrayList<String>());
    }

    private static void crawl(int level, String url, ArrayList<String> visitedUrls) {
        if (level <= 5) {
            Document doc = request(url, visitedUrls);

            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String hrefValue = link.absUrl("href");
                    if (!visitedUrls.contains(hrefValue)) {
                        crawl(++level, hrefValue, visitedUrls);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> visited) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            Elements images = doc.select("img");

            if (con.response().statusCode() == 200) {
                for (Element image : images) {
                    if (image.attr("alt") != "") {
                        System.out.println("Image Title: " + image.attr("alt"));
                    } else {
                        System.out.println("Image Title: N/A");
                    }
                    System.out.println("Link: " + image.attr("src"));
                }
                visited.add(url);
                return doc;
            }
        } catch (IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Error");
        }

        return null;
    }
}
