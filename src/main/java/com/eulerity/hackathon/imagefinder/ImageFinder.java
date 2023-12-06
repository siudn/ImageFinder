package com.eulerity.hackathon.imagefinder;

import com.eulerity.hackathon.imagefinder.Crawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageFinder extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res ) throws IOException {
		String url = req.getParameter("url");
		PrintWriter out = res.getWriter();
		Crawler.crawl(1, url, new ArrayList<String>(), out);
	}
}
