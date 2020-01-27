package net.immocrm.gui.create;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.Person;
import net.immocrm.domain.img.ImageProvider;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.ref.Category;
import net.immocrm.domain.vc.DomainTool;

public class ImmobilieFinishedController {

	@FXML Label lbPropertyType;
	@FXML Label lbOwner;
	@FXML Label lbImmobilie;
	@FXML Label lbAusstattung;
	@FXML Label lbImages;


	public void valuesToForm(Immobilie immobilie) {
		lbPropertyType.setText(immobilie.getImmobilieTypeName());
		lbOwner.setText(ownerText(immobilie.getOwner()));
		lbImmobilie.setText(immobilieText(immobilie));
		lbAusstattung.setText(ausstattungText(immobilie));
		lbImages.setText(imageText(immobilie));
	}

	protected String ausstattungText(Immobilie immobilie) {
		StringBuilder ausstattText = new StringBuilder();
		if (!DomainTool.isEmpty(immobilie.getAusstattung())) {
			ausstattText.append(immobilie.getAusstattung()).append("\n");
		}
		for (Merkmal m : immobilie.getMerkmalList(Category.Sonstiges)) {
			if (!m.isEmpty()) {
				ausstattText.append(m.getTypeName()).append("\n");
			}
		}
		if (ausstattText.length() > 0) {
			ausstattText.append("\n");
		}
		ausstattText.append(appendCategoryText(immobilie, "Bad: ", Category.Bad));
		ausstattText.append(appendCategoryText(immobilie, "Heizung: ", Category.Heizung));
		ausstattText.append(appendCategoryText(immobilie, "KFZ-Stellplatz: ", Category.KFZ_Stellplatz));
		return ausstattText.toString();
	}

	private String appendCategoryText(Immobilie immobilie, String label, Category category) {
		StringBuilder result = new StringBuilder();
		result.append(label);
		List<Merkmal> lst = immobilie.getMerkmalList(category);
		if (lst.isEmpty()) {
			result.append("unbekannt\n");
		} else {
			for (Merkmal b : lst) {
				result.append(b.getTypeName()).append(", ");
			}
			result.delete(result.length()-2, result.length()).append("\n");
		}
		return result.toString();
	}


	protected String ownerText(Person owner) {
		return owner.getName() + "\n" + owner.getHomeAddress().getAddressText();
	}

	protected String immobilieText(Immobilie immobilie) {
		StringBuilder immoText = new StringBuilder();
		immoText.append(immobilie.getAddress().getAddressText()).append("\n\n");
		immoText.append("Zustand").append(": ");
		if (immobilie.getZustand() == null) {
			immoText.append("unbekannt\n");
		} else {
			immoText.append(immobilie.getZustandName()).append("\n");
		}
		for (Merkmal merkmal : immobilie.getMerkmalList(Category.Allgemeines)) {
			if (!merkmal.isEmpty()) {
				immoText.append(merkmal.getTypeName()).append(": ").append(merkmal.getValue()).append("\n");
			}
		}
		return immoText.toString();
	}

	protected String imageText(Immobilie immobilie) {
		ImageProvider imageProvider = new ImageProvider(immobilie.getImageDir());
		List<File> imgFiles = imageProvider.getFiles();
		if (imgFiles.isEmpty()) {
			return "Keine Bilder";
		}
		if (imgFiles.size() == 1) {
			return "1 Bild";
		}
		return imgFiles.size() + " Bilder";
	}

}
