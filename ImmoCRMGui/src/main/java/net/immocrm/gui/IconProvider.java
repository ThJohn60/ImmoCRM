package net.immocrm.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.ref.ImmobilieCategoryEnum;

public class IconProvider {

	////////////////// Buttons /////////////////////////

	public static ImageView selectButtonIcon() {
		return loadImage("/rsc/ic_reorder_18pt.png");
	}

	public static ImageView editButtonIcon() {
		return loadImage("/rsc/icon/edit_18dp.png");
	}

	public static ImageView searchButtonIcon() {
		return loadImage("/rsc/icon/ic_search_18pt.png");
	}

	public static ImageView clearButtonIcon() {
		return loadImage("/rsc/icon/ic_clear_18pt.png");
	}

	public static ImageView addPersonButtonIcon() {
		return loadImage("/rsc/icon/person_add_18dp.png");
	}



	////////////////// Detail Views /////////////////////////

	public static Image immobilieIcon32dp(Immobilie im) {
		if (im.getImmobilieCategory() != null) {
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Grundstück) {
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/plant_32.png"));
			}
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Wohnung) {
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/sofa_32.png"));
			}
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Haus) {
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/house_32.png"));
			}
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Stellplatz) {
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/car_32.png"));
			}
		}
		return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/factory_32.png"));
	}

	public static Image orderIcon32dp(Order order) {
		if  (order.getOrderState() != null) {
			switch (order.getOrderState()) {
			case BEFORE_NOTAR:
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/meeting_32.png"));
			case AFTER_NOTAR:
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/contract_32.png"));
			case BILLED:
			case HANDOVER:
			case FINISHED:
				return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/deal_30.png"));
			case CANCELED:
			case OPEN:
				break;
			default:
			}
		}
		return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/file_32.png"));
	}

	public static Image personIcon32dp(Person p) {
		if (p.getAnrede() == null) {
			return null;
		}
		if (p.getAnrede().equals("Frau")) {
			return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/woman_32.png"));
		}
		return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/man_32.png"));
	}


	////////////////// Tree, Menu /////////////////////////

	public static ImageView personTreeIcon() {
		return loadImage("/rsc/icon/person_18pt.png");
	}

	public static ImageView openOrder16Icon() {
		return loadImage("/rsc/icon/file_16.png");
	}

	public static ImageView finishedOrder16Icon() {
		return loadImage("/rsc/icon/deal_16.png");
	}

	public static ImageView orderIcon() {
		return loadImage("/rsc/icon/order_18.png");
	}

	public static ImageView immobilieIcon(Immobilie im) {
		if (im.getImmobilieCategory() != null) {
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Grundstück) {
				return loadImage("/rsc/icon/plant_16.png");
			}
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Wohnung) {
				return loadImage("/rsc/icon/sofa_16.png");
			}
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Haus) {
				return loadImage("/rsc/icon/house_16.png");
			}
			if (im.getImmobilieCategory() == ImmobilieCategoryEnum.Stellplatz) {
				return loadImage("/rsc/icon/car_16.png");
			}
		}
		return loadImage("/rsc/icon/factory_16.png");
	}

	public static Image smallAppIcon() {
		return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/house2_18.png"));
	}

	public static Image largeAppIcon() {
		return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/AppIcon_32.png"));
	}

	public static ImageView historyIcon() {
		return loadImage("/rsc/ic_history_18pt.png");
	}


	public static ImageView financeMenuIcon() {
		return loadImage("/rsc/icon/ic_euro_symbol_24dp.png");
	}

	public static ImageView importContactsMenuIcon() {
		return loadImage("/rsc/group_add_18dp.png");
	}


	////////////////// Alerts /////////////////////////

	public static ImageView warningAlertIcon() {
		return loadImage("/rsc/ic_warning_18pt_3x.png");
	}

	public static ImageView errorAlertIcon() {
		return loadImage("/rsc/icon/ic_error_outline_18pt_3x.png");
	}

	public static ImageView infoAlertIcon() {
		return loadImage("/rsc/ic_info_outline_18pt_3x.png");
	}

	public static ImageView confirmationAlertIcon() {
		return loadImage("/rsc/icon/ic_help_outline_18pt_3x.png");
	}



	////////////////// Dialogs /////////////////////////

	public static ImageView merkmalTypeIcon() {
		return loadImage("/rsc/icon/check-list_24.png");
	}


	private static ImageView loadImage(String imgFile) {
		return new ImageView(new Image(IconProvider.class.getResourceAsStream(imgFile)));
	}

	public static Image personDialogIcon() {
		return new Image(IconProvider.class.getResourceAsStream("/rsc/icon/person_18pt.png"));
	}

}
