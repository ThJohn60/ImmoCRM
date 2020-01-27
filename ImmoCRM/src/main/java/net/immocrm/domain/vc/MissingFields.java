package net.immocrm.domain.vc;

import java.util.ArrayList;
import java.util.List;

public class MissingFields {

	private final List<String> fields;


	public MissingFields() {
		this.fields = new ArrayList<>();
	}


	public void addMissingField(String fieldname) {
		fields.add(fieldname);
	}


	public List<String> getFields() {
		return fields;
	}

	public void addAll(MissingFields missingFields) {
		fields.addAll(missingFields.fields);
	}

	public boolean isEmpty() {
		return fields.isEmpty();
	}

	public String getErrorMsg() {
		StringBuilder result = new StringBuilder();
		if (!isEmpty()) {
			result.append("Die folgenden Felder dürfen nicht leer sein:\n");
			for (String missing : fields) {
				result.append("\t").append(missing).append("\n");
			}
			result.append("\n");
		}
		return result.toString();
	}

}
