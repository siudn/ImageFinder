package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imagefinder.ImageData;

import com.google.gson.Gson;

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
		String json = crawl(1, url, new ArrayList<String>(), new ArrayList<ImageData>(), new String());
		System.out.println(json);
	}

    protected static String crawl(int level, String url, ArrayList<String> visitedUrls, ArrayList<ImageData> imageList, String json) {
		if (level == 5) {
			json = new Gson().toJson(imageList);
			return json;
		}
		else if (level <= 4) {
			Document doc = request(url, visitedUrls, imageList);

			if (doc != null) {
				for (Element link : doc.select("a[href]")) {
					String hrefValue = link.absUrl("href");
					if (!visitedUrls.contains(hrefValue)) {
						return crawl(++level, hrefValue, visitedUrls, imageList, json);
					}
				}
			}
		}
		return "Data unavailable.";
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
						// System.out.println(newImage.title);
						// System.out.println(newImage.imageUrl);
						// System.out.println(newImage.logoOrIcon);
					} else {
						ImageData newImage = new ImageData(image.attr("abs:src"));
						imageList.add(newImage);
						// System.out.println(newImage.title);
						// System.out.println(newImage.imageUrl);
						// System.out.println(newImage.logoOrIcon);
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
