package net.immocrm.domain.geo;

import java.util.Objects;

public class Coordinates {

	private final Double longitude;
	private final Double latitude;
	

	public Coordinates() {
		this.longitude = null;
		this.latitude = null;
	}

	public Coordinates(Double longitude, Double latitude) {
		Objects.requireNonNull(longitude);
		Objects.requireNonNull(latitude);
		this.longitude = longitude;
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public boolean isEmpty() {
		return longitude == null;
	}

}
