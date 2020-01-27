package net.immocrm.gui.form;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.ref.Category;

public class MerkmalInfoMapper {

	private static final int MAX_COLUMN = 1;

	private final GridPane merkmalBox;

	private int startRowNr;


	public MerkmalInfoMapper(GridPane merkmalBox) {
		this(merkmalBox, 0);
	}


	public MerkmalInfoMapper(GridPane merkmalBox, int startRowNr) {
		this.merkmalBox = merkmalBox;
		this.startRowNr = startRowNr;
	}


	public void ausstattungToInfoForm(Immobilie immobilie) {
		merkmalBox.getChildren().clear();
		List<QualityEntry> entries = new ArrayList<>();

		qualityByCategory(entries, immobilie, Category.Bad);
		qualityByCategory(entries, immobilie, Category.Heizung);
		qualityByCategory(entries, immobilie, Category.KFZ_Stellplatz);
		qualityByCategory(entries, immobilie, Category.Sonstiges);
		setQualities(entries);
	}

	private void qualityByCategory(List<QualityEntry> entries, Immobilie immobilie, Category category) {
		List<Merkmal> qualities = immobilie.getMerkmalList(category);
		if (!qualities.isEmpty()) {
			StringBuilder qualList = new StringBuilder();
			for (Merkmal merkmal : qualities) {
				qualList.append(merkmal.getTypeName()).append(", ");
			}
			qualList.delete(qualList.length()-2, qualList.length());
			QualityEntry entry = new QualityEntry();
			entry.label = category.getName() + ":";
			entry.name = qualList.toString();
			entries.add(entry);
		}
	}

	private void setQualities(List<QualityEntry> entries) {
		int row = startRowNr;
		int col = 0;
		for (QualityEntry e : entries) {
			merkmalBox.add(getLabelLabel(e.label), col, row);
			Label contentLabel = getContentLabel(e.name);
			if (contentLabel != null) {
				merkmalBox.add(contentLabel, col+1, row);
			}
			col += 2;
			if (col >= MAX_COLUMN * 2) {
				col = 0;
				row++;
			}
		}
	}

	public static Label getLabelLabel(String label) {
		Label result = new Label(label);
		result.getStyleClass().add("lbl");
		return result;
	}

	public static Label getContentLabel(String c) {
		Label content = new Label(c);
		content.getStyleClass().add("content");
		return content;
	}




	public int eigenschaftenToInfoForm(Immobilie immobilie) {
		merkmalBox.getChildren().clear();
		EnumSet<Category> categories = EnumSet.noneOf(Category.class);
		categories.add(Category.Allgemeines);
		categories.add(Category.Immobilentyp);
		categories.add(Category.Zustand);
		List<Merkmal> merkmalList = immobilie.getMerkmalList(categories);
		return setQualities(immobilie.getImmobilieType(), immobilie.getZustand(), merkmalList);
	}

	private int setQualities(MerkmalType type, MerkmalType zustand, List<Merkmal> qualities) {
		int row = startRowNr;
		int col = 0;
		if (type != null) {
			Label qualityLabel = new Label("Objekttyp:");
			qualityLabel.getStyleClass().add("lbl");
			merkmalBox.add(qualityLabel, col++, row);
			Label content = new Label(type.getName());
			content.getStyleClass().add("content");
			merkmalBox.add(content, col, row);
			row++;
			col = 0;
		}
		if (zustand != null) {
			Label qualityLabel = new Label("Zustand:");
			qualityLabel.getStyleClass().add("lbl");
			merkmalBox.add(qualityLabel, col++, row);
			Label content = new Label(zustand.getName());
			content.getStyleClass().add("content");
			merkmalBox.add(content, col, row);
			row++;
			col = 0;
		}
		for (Merkmal quality : qualities) {
			merkmalBox.add(getQualityLabel(quality), col, row);
			Label contentLabel = getContentLabel(quality);
			if (contentLabel != null) {
				merkmalBox.add(contentLabel, col+1, row);
			}
			col += 2;
			if (col >= MAX_COLUMN * 2) {
				col = 0;
				row++;
			}
		}
		return row;
	}

	private Label getQualityLabel(Merkmal quality) {
		Label label = new Label(getTypeName(quality));
		label.getStyleClass().add(quality.getMerkmalType().isBoolean() ? "content" : "lbl");
		return label;
	}

	private String getTypeName(Merkmal quality) {
		return quality.getTypeName() + (quality.getMerkmalType().isBoolean() ? "" : ":");
	}

	private Label getContentLabel(Merkmal quality) {
		if (quality.getMerkmalType().isBoolean()) {
			return null;
		}
		Label content = new Label(quality.getValue());
		content.getStyleClass().add("content");
		return content;
	}



	private class QualityEntry {
		String label;
		String name;
	}

}
