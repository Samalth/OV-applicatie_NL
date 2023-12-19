package com.ovapp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {

	public void listCities() throws IOException {
		File file = new File("Routes.json");

		ObjectMapper mapper = new ObjectMapper();
		CityList cityList = mapper.readValue(file, CityList.class);

		List<String> cities = cityList.getCities();

		for (String city : cities) {
			System.out.println(city);
		}
	}
}
