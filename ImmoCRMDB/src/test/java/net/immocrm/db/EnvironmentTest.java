package net.immocrm.db;

import java.util.Enumeration;
import java.util.Properties;

public class EnvironmentTest {

	public static void main(String[] args) throws Exception {
		new EnvironmentTest().listEnvironment();
	}

	public void listEnvironment() {
		Properties props = System.getProperties();
		Enumeration<?> names = props.propertyNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement().toString(); 
			System.out.println(key + "=" + props.getProperty(key));
		}
	}

}
