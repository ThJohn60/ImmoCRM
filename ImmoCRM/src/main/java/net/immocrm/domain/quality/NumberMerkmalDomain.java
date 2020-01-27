package net.immocrm.domain.quality;

import java.text.ParseException;

import net.immocrm.db.MerkmalEntity;
import net.immocrm.domain.MerkmalDomain;
import net.immocrm.domain.vc.NumberValue;
import net.immocrm.domain.vc.ValuesParser;

public class NumberMerkmalDomain extends MerkmalDomain {

	private final NumberValue num;


	public NumberMerkmalDomain(MerkmalType type, MerkmalEntity entity) {
		super(type, entity);
		if (entity.getValue().isEmpty()) {
			num = new NumberValue();
		} else {
			num = parse(entity);
		}
	}

	private NumberValue parse(MerkmalEntity entity) {
		try {
			return ValuesParser.parseNumber(entity.getValue());
		} catch (ParseException e) {
			// can't happen
		}
		return NumberValue.EMTPTY_NUMBER;
	}


	public NumberMerkmalDomain(MerkmalType type, NumberValue val) {
		super(type, val.getValue().toString());
		this.num = val;
	}


	public NumberValue getNumber() {
		return num;
	}

	@Override
	public String getValue() {
		return num.toString();
	}

}
