package com.ovapp;

import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Data {
	public void getCities() throws IOException {

		BufferedWriter writer = Files.newBufferedWriter(Paths.get("Routes.json"));
		JSONObject Route = new JSONObject();
		Route.put("DepartureCity", "Amsterdam");
	}
}

