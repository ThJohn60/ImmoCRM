package net.immocrm.gui.geo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.immocrm.domain.Address;

public class MapViewController {

	@FXML WebView browserView;

	public void showAddressInMap(Address address) {
		String encodedUrl = getUrl(address);
		WebEngine webEngine = browserView.getEngine();
		webEngine.load(encodedUrl);
	}

	private String getUrl(Address address) {
		StringBuilder url = new StringBuilder();
		url.append("https://www.google.de/maps/place/")
			.append(address.getStreet().trim().replaceAll(" ", "+").replaceAll("_", "+")).append("+")
			.append(address.getPostalCode()).append("+")
			.append(address.getCity().replaceAll(" ", "+"));
		return url.toString();
	}

	@SuppressWarnings("unused")
	private String encode(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str.trim(), "ISO-8859-1");
	}

}
