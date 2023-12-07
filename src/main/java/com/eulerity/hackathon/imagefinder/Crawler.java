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
    protected static ArrayList<ImageData> crawl(int level, String url, ArrayList<String> visitedUrls, ArrayList<ImageData> imageList) {
		if (level <= 4) { // limited to 4 levels deep
			Document doc = request(url, visitedUrls, imageList);

			if (doc != null) {
				for (Element link : doc.select("a[href]")) { // selects all links on the page
					String hrefValue = link.absUrl("href");
					if (!visitedUrls.contains(hrefValue)) { // checks that link was not yet visited
						return crawl(++level, hrefValue, visitedUrls, imageList); 
						// recursively runs the crawl on this link
					}
				}
			}
		}
		return imageList;
	}

	protected static Document request(String url, ArrayList<String> visitedUrls, ArrayList<ImageData> imageList) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			Elements images = doc.select("img"); // selects all images on the page

			if (con.response().statusCode() == 200) {
				for (Element image : images) {
					// if alt is provided, construct with alt, otherwise construct only with url
					if (image.attr("alt") != "") { 
						ImageData newImage = new ImageData(image.attr("alt"), image.attr("abs:src"));
						imageList.add(newImage); // add image to ArrayList of images
					} else {
						ImageData newImage = new ImageData(image.attr("abs:src"));
						imageList.add(newImage);
					}
				}
				visitedUrls.add(url); // add url to list of visited URLs
				return doc;
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.WARNING, "Error");
		}
		return null;
	}
}
