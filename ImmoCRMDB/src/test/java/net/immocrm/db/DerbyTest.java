package net.immocrm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DerbyTest {

	
	private static final String FS = System.getProperty("file.separator");
	@SuppressWarnings("unused")
	private static final String IMMO_DB_URL = "jdbc:derby:/ImmoCRMDerbyDB/db/ImmoDB";

	public DerbyTest() throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
	}

	
	private void checkTypeTable() throws Exception {
		try (Connection con = DriverManager.getConnection(getUrl())) {
			long startMillis = System.currentTimeMillis();
			try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM property_type")) {
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						System.out.println(rs.getString("id") + ", " + rs.getString("name") + ", " + rs.getTimestamp("created_on"));		
					}
				}
			}
			System.out.println("Runtime: " + (System.currentTimeMillis() - startMillis));
		}
	}


	public static void main(String[] args) throws Exception {
		DerbyTest dao = new DerbyTest();
		dao.checkTypeTable();
	}
	
	private static String getUrl() {
		String userDir = System.getProperty("user.dir");
		int projectNameStart = userDir.indexOf("ImmoCRMDB");
		if (projectNameStart > 0) {
			String url = "jdbc:derby:" + userDir.substring(0, projectNameStart - 1) + FS + "ImmoCRMDerbyDB" + FS + "db" + FS + "ImmoDB";
			return url;
		}
		return null;
	}
	
}
