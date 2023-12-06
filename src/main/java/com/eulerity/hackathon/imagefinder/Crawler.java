package com.eulerity.hackathon.imagefinder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crawler {
    protected static void crawl(int level, String url, ArrayList<String> visitedUrls, PrintWriter out) {
		if (level <= 5) {
			Document doc = request(url, visitedUrls, out);

			if (doc != null) {
				for (Element link : doc.select("a[href]")) {
					String hrefValue = link.absUrl("href");
					if (!visitedUrls.contains(hrefValue)) {
						crawl(++level, hrefValue, visitedUrls, out);
					}
				}
			}
		}
	}

	protected static Document request(String url, ArrayList<String> visited, PrintWriter out) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			Elements images = doc.select("img");

			if (con.response().statusCode() == 200) {
				for (Element image : images) {
					if (image.attr("alt") != "") {
						out.println("Image Title: " + image.attr("alt"));
					} else {
						out.println("Image Title: N/A");
					}
					out.println("Link: " + image.attr("src"));
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
