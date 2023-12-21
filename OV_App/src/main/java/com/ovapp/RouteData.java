package com.ovapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteData {
	private String station;
	private String transportType;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTransport() {
		return transportType;
	}

	public void setTransport(String transportType) {
		this.transportType = transportType;
	}
}