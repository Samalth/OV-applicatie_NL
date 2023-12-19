package com.ovapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {

	public void listCity() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Routes.json")) {
			ObjectMapper mapper = new ObjectMapper();
			Routes Routes = mapper.readValue(inputStream, Routes.class);

			List<String> cities = Routes.getCities();

			for (String city : cities) {
				System.out.println(city);
			}
		}
	}

	public List<String> returnCity() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Routes.json")) {
			ObjectMapper mapper = new ObjectMapper();
			Routes Routes = mapper.readValue(inputStream, Routes.class);

			return Routes.getCities();
		}
	}
}