package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageFinder extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res ) throws IOException {
		String url = req.getParameter("url");

		PrintWriter out = res.getWriter();

		out.println("URL:" + url);
	}
}
