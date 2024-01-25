package com.ovapp;

import java.util.*;

abstract class meansOfTransport {
	String name = "";

	List<Integer> schedule;

	meansOfTransport() {
	}

	meansOfTransport(String name, List<Integer> schedule) {
		this.name = name;
		this.schedule = schedule;
	}

	String getTransportName() {
		return "";
	}

	List<Integer> getTransportSchedule() {
		return List.of(0);
	}

	ArrayList<String> getDepartureTime(List<Integer> schedule, int departureHours, int departureMinutes) {
		return new ArrayList<String>();
	}
}
