package net.immocrm.gui.tools;

import javafx.fxml.FXML;
import net.immocrm.domain.Notar;
import net.immocrm.domain.Person;
import net.immocrm.domain.valid.ValidationTool;
import net.immocrm.gui.Main;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;

public class MailSendController {

	public static final MailSendController INSTANCE = new MailSendController();

	@FXML
	public void sendMail() {
		if (MainController.instance().getOrder() != null) {
			sendMail(MainController.instance().getOrder().getCustomer());
		} else if (MainController.instance().getImmobilie() != null) {
			sendMail(MainController.instance().getImmobilie().getOwner());
		} else if (MainController.instance().getPerson() != null) {
			sendMail(MainController.instance().getPerson());
		} else if (MainController.instance().getNotar() != null) {
			Notar notar = MainController.instance().getNotar();
			sendMail(notar.getEmailAddress(), notar.getName());
		}
	}

	public void sendMail(Person owner) {
		sendMail(owner.getEmailAddress(), owner.getName());
	}

	private void sendMail(String mailAdress, String name) {
		if (!ValidationTool.isNullOrEmpty(mailAdress)) {
			Main.getApplicationHostServices().showDocument("mailto:" + mailAdress);
		} else {
			AlertProvider.alertMissingEmail(name);
		}
	}

}
