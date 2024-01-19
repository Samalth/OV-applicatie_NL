package com.ovapp;

import java.util.*;

public class City {

	String name;
	List<String> amenities;

	City(String name, List<String> amenities) {
		this.name = name;
		this.amenities = amenities;
	}

	public String getName() {
		return name;
	}

	public List<String> getAmenities() {
		return amenities;
	}
}
