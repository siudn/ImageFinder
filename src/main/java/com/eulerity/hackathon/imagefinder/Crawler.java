package com.eulerity.hackathon.imagefinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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
		if (level <= 4) {
			Document doc = request(url, visitedUrls, imageList);

			if (doc != null) {
				for (Element link : doc.select("a[href]")) {
					String hrefValue = link.absUrl("href");
					if (!visitedUrls.contains(hrefValue)) {
						return crawl(++level, hrefValue, visitedUrls, imageList);
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
			Elements images = doc.select("img");

			if (con.response().statusCode() == 200) {
				for (Element image : images) {
					if (image.attr("alt") != "") {
						ImageData newImage = new ImageData(image.attr("alt"), image.attr("abs:src"));
						imageList.add(newImage);
					} else {
						ImageData newImage = new ImageData(image.attr("abs:src"));
						imageList.add(newImage);
					}
				}
				visitedUrls.add(url);
				return doc;
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.WARNING, "Error");
		}
		return null;
	}
}
