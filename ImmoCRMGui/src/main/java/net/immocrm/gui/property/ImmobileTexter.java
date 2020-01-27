package net.immocrm.gui.property;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.immocrm.domain.Immobilie;
import net.immocrm.domain.quality.Merkmal;
import net.immocrm.domain.ref.Category;

public class ImmobileTexter {

	private final Immobilie im;
	private final String text;


	public ImmobileTexter(Immobilie immobilie) {
		this.im = immobilie;
		 text = create();
	}

	public String getText() {
		return text;
	}

	private String create() {
		StringBuilder sb = new StringBuilder();
		sb.append("Adresse").append("\n");
		sb.append(im.getAddress().getCompleteStreet()).append("\n");
		sb.append(im.getAddress().getPostalCodeAndCity()).append("\n");
		sb.append("\n");
		sb.append("Eigentümer").append("\n");
		sb.append(im.getOwner().getName()).append("\n");
		sb.append(im.getOwner().getHomeAddress().getCompleteStreet()).append("\n");
		sb.append(im.getOwner().getHomeAddress().getPostalCodeAndCity()).append("\n");
		sb.append("\n");
		sb.append("Eigenschaften").append("\n");
		sb.append("Objekttyp: ").append(im.getImmobilieType()).append("\n");
		sb.append("Zustand: ").append(im.getZustandName()).append("\n");
		eigenschaften(sb);

		sb.append("Energieausweistyp: ").append(im.getEnergieausweisType().name()).append("\n");
		sb.append("Verbrauchskennwert: ").append(im.getEnergieverbrauchskennwert()).append("\n");
		sb.append("\n");
		sb.append("Ausstattung").append("\n");
		ausstattungToInfoForm(sb);
		sb.append(im.getAusstattung()).append("\n");
		sb.append("\n");
		sb.append("Objektbeschreibung").append("\n");
		sb.append(im.getObjektbeschreibung()).append("\n");
		sb.append("Lagebeschreibung").append("\n");
		sb.append(im.getLagebeschreibung()).append("\n");
		sb.append("\n");
		sb.append("Interne Notizen").append("\n");
		sb.append(im.getInternalNotice()).append("\n");
		return sb.toString();
	}

	private void eigenschaften(StringBuilder sb) {
		EnumSet<Category> categories = EnumSet.noneOf(Category.class);
		categories.add(Category.Allgemeines);
		categories.add(Category.Immobilentyp);
		categories.add(Category.Zustand);
		merkmale(sb, categories);
	}

	private void merkmale(StringBuilder sb, EnumSet<Category> categories) {
		for (Merkmal merkmal : im.getMerkmalList(categories)) {
			sb.append(merkmal.getTypeName() + (merkmal.getMerkmalType().isBoolean() ? "           " : ": "))
				.append(merkmal.getValue()).append("\n");
		}
	}

	private void ausstattungToInfoForm(StringBuilder sb) {
		List<QualityEntry> entries = new ArrayList<>();

		qualityByCategory(entries, Category.Bad);
		qualityByCategory(entries, Category.Heizung);
		qualityByCategory(entries, Category.KFZ_Stellplatz);
		qualityByCategory(entries, Category.Sonstiges);
		for (QualityEntry qe : entries) {
			sb.append(qe.label).append(" ").append(qe.name).append("\n");
		}
	}

	private void qualityByCategory(List<QualityEntry> entries, Category category) {
		List<Merkmal> qualities = im.getMerkmalList(category);
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

	private class QualityEntry {
		String label;
		String name;
	}

}
