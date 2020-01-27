package net.immocrm.domain.quality;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import net.immocrm.db.MerkmalEntity;
import net.immocrm.domain.MerkmalDomain;
import net.immocrm.domain.vc.Area;

public class SquareMerkmalDomain extends MerkmalDomain {

	private static final NumberFormat FORMATTER = NumberFormat.getNumberInstance(Locale.ENGLISH);

	private static Area parse(MerkmalEntity entity) {
		try {
			Number value = FORMATTER.parse(entity.getValue());
			return new Area(value.doubleValue());
		} catch (ParseException e) {
			// can't happen
		}
		return new Area();
	}


	private final Area area;

	public SquareMerkmalDomain(MerkmalType type, MerkmalEntity entity) {
		super(type, entity);
		if (entity.getValue().isEmpty()) {
			area = new Area();
		} else {
			area = parse(entity);
		}
	}

	public SquareMerkmalDomain(MerkmalType type, Area area) {
		super(type, area.getValue().toString());
		this.area = area;
	}


	public Area getArea() {
		return area;
	}

	@Override
	public String getValue() {
		return area.toString();
	}

}
