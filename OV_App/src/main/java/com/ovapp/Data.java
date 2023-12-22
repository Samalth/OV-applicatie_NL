package com.ovapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {
	public List<RouteData> getRouteData() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Routes.json")) {
			ObjectMapper mapper = new ObjectMapper();
			Routes routes = mapper.readValue(inputStream, Routes.class);

			return routes.getRouteData();
		}
	}
}