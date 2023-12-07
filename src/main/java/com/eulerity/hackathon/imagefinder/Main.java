package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imagefinder.Crawler;
import com.eulerity.hackathon.imagefinder.ImageData;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res ) throws IOException {
		String url = req.getParameter("url");
		Crawler.crawl(1, url, new ArrayList<String>(), new ArrayList<ImageData>(), new String());
	}
}
