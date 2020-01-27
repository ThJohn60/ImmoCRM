package net.immocrm.domain.quality;

import net.immocrm.db.MerkmalEntity;
import net.immocrm.domain.MerkmalDomain;
import net.immocrm.domain.vc.Price;

public class CurrencyMerkmalDomain extends MerkmalDomain {

	private final Price price;


	public CurrencyMerkmalDomain(MerkmalType type, MerkmalEntity entity) {
		super(type, entity);
		price = new Price(entity.getValue());
	}

	public CurrencyMerkmalDomain(MerkmalType type, Price price) {
		super(type, price.getValue().toString());
		this.price = price;
	}


	public Price getPrice() {
		return price;
	}


	@Override
	public String getValue() {
		return price.toString();
	}

}
