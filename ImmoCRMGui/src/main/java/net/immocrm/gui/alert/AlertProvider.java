package net.immocrm.gui.alert;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.immocrm.domain.BaseDomain;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.Request;
import net.immocrm.domain.valid.StdException;
import net.immocrm.domain.valid.ValidationIssues;
import net.immocrm.domain.vc.IncorrectValueException;

public class AlertProvider {

	public static boolean confirmRemove(BaseDomain item) {
		Text text1 = buildText("Sind Sie sicher, dass Sie " + domainTypeName(item) + "\n\n", FontWeight.NORMAL);
		Text text2 = buildText(item.toString(), FontWeight.BOLD);
		Text text3 = buildText("\n\nlöschen wollen?", FontWeight.NORMAL);
		return new ConfirmationDialog(text1, text2, text3).showAndWait();
	}

	private static String domainTypeName(BaseDomain item) {
		String domainTypeName = null;
		if (item instanceof Immobilie) {
			domainTypeName = "die Immobilie";
		} else if (item instanceof Notar) {
			domainTypeName = "den Notar";
		} else if (item instanceof Order) {
			domainTypeName = "den Auftrag";
		} else if (item instanceof Person) {
			domainTypeName = "die Person";
		} else if (item instanceof Request) {
			domainTypeName = "die Anfrage";
		} else {
			domainTypeName = "den Datensatz";
		}
		return domainTypeName;
	}

	public static boolean confirmCancel() {
		return new ConfirmationDialog("Sind Sie sicher, dass Sie abbrechen wollen?").showAndWait();
	}

	public static boolean confirm(String warning) {
		return new ConfirmationDialog(warning).showAndWait();
	}

	//////////////////////////////////////////

	public static void notYetImplemented() {
		alertInfo("Diese Funktion wird in der nächsten Version umgesetzt.");
	}

	public static void alertMissingUpdateItem() {
		alertInfo("Bitte einen Eintrag zum Ändern anwählen.");
	}

	public static void alertMissingOrderItem() {
		alertInfo("Bitte einen Auftrag auswählen.");
	}

	public static void alertMissingRemoveItem() {
		alertInfo("Bitte einen Eintrag zum Löschen auswählen.");
	}

	public static void alertInfo(String header, String msg) {
		new InformationDialog(msg).withHeader(header).showAndWait();
	}

	public static void alertInfo(String msg) {
		new InformationDialog(msg).showAndWait();
	}

	//////////////////////////////////////////

	public static void alertCannotRemoveItem(Person person) {
		List<Order> orders = person.getOrders();
		Text text1 = buildText("Sie können\n\n", FontWeight.NORMAL);
		Text text2 = buildText(person.toString(), FontWeight.BOLD);
		String t3 = null;
		String t4 = null;
		if (!orders.isEmpty()) {
			t3 = (orders.size() > 1 ? "den folgenden Aufträgen" : "dem folgenden Auftrag");
			t4 = appendOrders(orders);
		} else if (!person.getImmobilien().isEmpty()) {
			t3 = (person.getImmobilien().size() > 1 ? "den folgenden Immobilien" : "der folgenden Immobilie");
			StringBuilder sb = new StringBuilder();
			for (Immobilie immo : person.getImmobilien()) {
				sb.append(immo.toString()).append("\n");
			}
			t4 = sb.toString();
		}
		Text text3 = buildText("\n\nnicht löschen, da die Person mit " + t3 + " verknüpft ist:\n\n", FontWeight.NORMAL);
		Text text4 = buildText(t4, FontWeight.BOLD);
		new WarningDialog(text1, text2, text3, text4).showAndWait();
	}

	public static void alertCannotRemoveItem(Immobilie immobilie) {
		Text text1 = buildText("Sie können\n\n", FontWeight.NORMAL);
		Text text2 = buildText(immobilie.toString(), FontWeight.BOLD);

		String t = "\n\nnicht löschen, da die Immobilie mit "
				+ (immobilie.getOrders().size() > 1 ? "den folgenden Aufträgen" : "dem folgenden Auftrag")
				+ " verknüpft ist:\n\n";
		Text text3 = buildText(t, FontWeight.NORMAL);

		Text text4 = buildText(appendOrders(immobilie.getOrders()), FontWeight.BOLD);
		new WarningDialog(text1, text2, text3, text4).showAndWait();
	}

	public static void alertCannotRemoveItem(Notar notar) {
		Text text1 = buildText("Sie können\n\n", FontWeight.NORMAL);
		Text text2 = buildText(notar.toString(), FontWeight.BOLD);

		String t = "\n\nnicht löschen, da die Immobilie mit "
				+ (notar.getOrders().size() > 1 ? "den folgenden Aufträgen" : "dem folgenden Auftrag")
				+ " verknüpft ist:\n\n";
		Text text3 = buildText(t, FontWeight.NORMAL);

		Text text4 = buildText(appendOrders(notar.getOrders()), FontWeight.BOLD);
		new WarningDialog(text1, text2, text3, text4).showAndWait();
	}

	private static String appendOrders(List<Order> orders) {
		StringBuilder sb = new StringBuilder();
		for (Order order : orders) {
			sb.append(order.toString()).append("\n");
		}
		return sb.toString();
	}

	public static void alertWarning(String msg) {
		new WarningDialog(msg).showAndWait();
	}

	//////////////////////////////////////////

	public static void alertInputError(String invalidVaue, String fieldName, String correctionMsg) {
		Text text1 = buildText("Der Wert ", FontWeight.NORMAL);
		Text text2 = buildText(invalidVaue, FontWeight.BOLD);
		Text text3 = buildText(" im Feld ", FontWeight.NORMAL);
		Text text4 = buildText(fieldName, FontWeight.BOLD);
		Text text5 = buildText(" ist ungültig.\n\n", FontWeight.NORMAL);
		Text text6 = buildText(correctionMsg, FontWeight.NORMAL);
		alertInputError(text1, text2, text3, text4, text5, text6);
	}

	public static void alertException(IncorrectValueException e) {
		if (e.getValue() != null) {
			Text text1 = buildText("Der Wert ", FontWeight.NORMAL);
			Text text2 = buildText(e.getValue().toString(), FontWeight.BOLD);
			Text text3 = buildText(" im Feld ", FontWeight.NORMAL);
			Text text4 = buildText(e.getFieldName(), FontWeight.BOLD);
			Text text5 = buildText(" ist ungültig.\n\n", FontWeight.NORMAL);
			Text text6 = buildText(e.getCorrctionMsg(), FontWeight.NORMAL);
			alertInputError(text1, text2, text3, text4, text5, text6);
		} else {
			alertError(e.getProblemType(), e.getMessage(), e.getCause());
		}
	}

	public static void alertException(StdException e) {
		alertError(e.getProblemType(), e.getMessage(), e.getCause());
	}

	public static void alertRemoveException(Exception e, BaseDomain d) {
		alertError(e.getMessage(), "Problem beim Löschen von\n" + d);
	}

	public static void alertException(Exception e) {
		alertError(e.getMessage());
	}

	public static void alertException(String msg, Exception e) {
		alertError(msg, e.getMessage());
	}

	public static void alertMissingEmail(String name) {
		alertError("Keine E-Mail-Adresse eingetragen für " + name);
	}

	public static void alertError(String msg) {
		new ErrorDialog(msg).showAndWait();
	}

	public static void alertError(String header, String msg) {
		new ErrorDialog(msg).withHeader(header).showAndWait();
	}

	public static void alertError(String header, String msg, Throwable cause) {
		ErrorDialog errorDialog = new ErrorDialog(msg).withHeader(header);
		if (cause != null) {
			errorDialog.withDetails(cause.getMessage());
		}
		errorDialog.showAndWait();
	}

	public static void alertInputError(Text... textList) {
		new ErrorDialog(textList).withHeader("Eingabeproblem").showAndWait();
	}

	public static void alertInputError(String msg) {
		new ErrorDialog(msg).withHeader("Eingabeproblem").showAndWait();
	}


	//////////////////////////////////////////

	public static void alertIssues(ValidationIssues issues) {
		switch (issues.getAlertType()) {
		case CONFIRMATION:
			break;
		case ERROR:
			alertError(issues.getMsgHeader(), issues.getErrorMsg());
			break;
		case INFORMATION:
			alertInfo(issues.getErrorMsg());
			break;
		case WARNING:
			alertWarning(issues.getErrorMsg());
			break;
		case NONE:
		default:
			break;
		}
	}

	private static Text buildText(String text, FontWeight fontWeight) {
		Text text1 = new Text(text);
		text1.setFill(Color.DARKBLUE);
		text1.setFont(Font.font("Tahoma", fontWeight, 12));
		return text1;
	}

}
