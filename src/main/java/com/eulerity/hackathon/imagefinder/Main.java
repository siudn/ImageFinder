package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main extends HttpServlet {
	protected static final Gson gson = new GsonBuilder().create();

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		JsonElement jsonElement = new JsonParser().parse(req.getReader());
		JsonObject jsonObject = jsonElement.getAsJsonObject();

		String url = jsonObject.get("url").getAsString();
		ArrayList<ImageData> images = new ArrayList<ImageData>();

		Crawler test = new Crawler(url, images);

		try {
			test.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		res.setContentType("application/json");
		res.getWriter().write(gson.toJson(images)); // convert ArrayList to JSON
	}
}
