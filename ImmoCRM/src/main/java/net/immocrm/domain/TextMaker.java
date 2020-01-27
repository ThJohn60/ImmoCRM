package net.immocrm.domain;

import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.termin.Termin;
import net.immocrm.domain.vc.DomainTool;


public class TextMaker {

	public static final TextMaker INSTANCE = new TextMaker();

	private TextMaker() {
		super();
	}


	public String getStreetAndCity(Address address) {
		if (address == null || address.isEmpty()) {
			return "";
		}
		return address.getStreet() + ", " + address.getPostalCodeAndCity();
	}

	public String getTreeNodeText(Address address) {
		return getStreetAndCity(address);
	}

	public String getDetailsHeader(Person p) {
		return getDialogHeader(p);
	}

	public String getDetailsHeader(Order order) {
		return order.getOrderType().name() + " " + order.getImmobilie().getImmobilieTypeName() + " in " + getStreetAndCity(order.getImmobilie().getAddress());
	}

	public String getDetailsHeader(Immobilie immobilie) {
		StringBuilder sb = new StringBuilder().append(getSectionHaeder(immobilie));
		if (!immobilie.getWohnflaeche().isEmpty()) {
			String areaName = areaName(immobilie);
			if (areaName != null) {
				sb.append(", ").append(immobilie.getWohnflaeche()).append(" ").append(areaName);
			}
		}
		return sb.toString();
	}

	public String getSectionHaeder(Immobilie immobilie) {
		StringBuilder sb = new StringBuilder();
		if (!immobilie.getRoomCnt().isEmpty()
				&& (immobilie.getImmobilieCategory() == ImmobilieCategoryEnum.Haus || immobilie.getImmobilieCategory() == ImmobilieCategoryEnum.Wohnung)) {
			sb.append(immobilie.getRoomCnt()).append(" Zimmer-");
		}
		sb.append(immobilie.getImmobilieTypeName());
		return sb.toString();
	}

	public String areaName(Immobilie immobilie) {
		switch (immobilie.getImmobilieCategory()) {
		case Gewerbegebäude:
			return "Gewerbefläche";
		case Haus:
		case Wohnung:
			return "Wohnläche";
		case Sonstiges:
		case Stellplatz:
		case Grundstück:
			return "Fläche";
		default:
			break;
		}
		return null;
	}

	public String getDialogHeader(Immobilie immobilie) {
		if (immobilie.getImmobilieType() != null) {
			return immobilie.getImmobilieTypeName() + " in " + getTreeNodeText(immobilie);
		}
		return getTreeNodeText(immobilie);
	}

	public String getTreeNodeText(Immobilie immobilie) {
		if (immobilie.isNew()) {
			if (immobilie.getImmobilieCategory() == null) {
				return "Neue Immobilie";
			}
			return getNewImmobilieTypeExpression(immobilie);
		}
		return getAddressTextForTreeNodes(immobilie);
	}

	private String getNewImmobilieTypeExpression(Immobilie immobilie) {
		ImmobilieCategoryEnum type = immobilie.getImmobilieCategory();
		switch(type) {
		case Gewerbegebäude:
		case Grundstück:
		case Haus:
			return "Neues " + type.name();
		case Wohnung:
			return "Neue " + type.name();
		case Stellplatz:
			return "Neue Garage/" + type.name();
		default:
			break;
		}
		return "";
	}



	public String getDialogHeader(Merkmal merkmal) {
		return merkmal.toString();
	}

	public String getTreeNodeText(Merkmal merkmal) {
		return merkmal.toString();
	}



	public String getDialogHeader(MerkmalType type) {
		return type.getText();
	}

	public String getTreeNodeText(MerkmalType type) {
		return type.getText();
	}



	public String getDialogHeader(Notar notar) {
		if (DomainTool.isEmpty(notar)) {
			return "notar";
		}
		return notar.getName() + " in " +  notar.getCity();
	}

	public String getTreeNodeText(Notar notar) {
		return getDialogHeader(notar);
	}


	public String getTabHeader(Order order) {
		return order.getOrderType().name() + ": " + order.getImmobilie().getAddress().getStreet();
	}


	public String getDialogHeader(Order order) {
		return getTreeNodePersonParentText(order);
	}

	public String getTreeNodeImmobileParentText(Order order) {
		return order.getOrderType().name() + " für " + order.getCustomer().getName();
	}

	public String getTreeNodePersonParentText(Order order) {
		return order.getOrderType().name() + ": " + order.getImmobilie().getImmobilieTypeName() + " in " +  order.getImmobilie().getAddress().getCity();
	}

	private String getAddressTextForTreeNodes(Immobilie immobilie) {
		return immobilie.getAddress().getCity() + ", " + immobilie.getAddress().getStreet();
	}

	public String getDialogHeader(OrderHistory history) {
		return getTreeNodeText(history);
	}

	public String getTreeNodeText(OrderHistory history) {
		return String.format(history.getCreateTimestamp() + " - " +  history.getDescription());
	}



	public String getDialogHeader(Person person) {
		if (DomainTool.isEmpty(person.getCity())) {
			return person.getName();
		}
		return person.getName() + ", " + person.getCity();
	}

	public String getTreeNodeText(Person person) {
		return getDialogHeader(person);
	}



	public String getDialogHeader(Preference pref) {
		return pref.toString();
	}

	public String getTreeNodeText(Preference pref) {
		return pref.toString();
	}




	public String getDialogHeader(Request request) {
		Immobilie immobilie = request.getOrder().getImmobilie();
		String prefix = request.isEmpty() ? "Neue Anfrage für " : "Anfrage für ";
		return prefix + immobilie.getImmobilieType() + " in " + immobilie.getAddress().getCity();
	}

	public String getTreeNodeText(Request request) {
		if (request.isEmpty()) {
			return "";
		}
		if (request.getRequestPrice().isEmpty()) {
			return String.format("Anfrage von %s", request.getPurchaser().getName());
		}
		return String.format("%s bietet %s", request.getPurchaser().getName(), request.getRequestPrice());
	}



	public String getDialogHeader(Termin termin) {
		return termin.getCategoryName() + " am " + termin.getDate();
	}

	public String getTreeNodeText(Termin termin) {
		return getDialogHeader(termin);
	}

}
