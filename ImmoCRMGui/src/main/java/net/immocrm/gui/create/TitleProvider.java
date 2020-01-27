package net.immocrm.gui.create;

import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;
import net.immocrm.domain.ref.OrderTypeEnum;

public class TitleProvider {

	public static String getTitle(BaseDomain d) {
		if (d instanceof Order) {
			return getTitle((Order)d);
		}
		if (d instanceof Immobilie) {
			return getTitle((Immobilie)d);
		}
		return "Neu";
	}

	private static String getTitle(Order order) {
		if (order.getOrderType() == null) {
			return "Neuer Auftrag";
		}
		if (order.getOrderType() == OrderTypeEnum.Vermietung) {
			return "Neue Vermietung";
		}
		return "Neuer Verkaufsauftrag";
	}


	private static String getTitle(Immobilie immobilie) {
		ImmobilieCategoryEnum category = immobilie.getImmobilieCategory();
		if (category != null) {
			switch (category) {
			case Gewerbegebäude:
			case Grundstück:
			case Haus:
				return "Neues " + category.name();
			case Stellplatz:
				return "Neuer " + category.name();
			case Wohnung:
				return "Neue " + category.name();
			default:
				break;
			}
		}
		return "Neue Immobilie";
	}

}
