package net.immocrm.domain;

import java.text.ParseException;

import net.immocrm.db.AddressEntity;
import net.immocrm.db.ContactEntity;
import net.immocrm.db.DocumentEntity;
import net.immocrm.db.ImmobilieEntity;
import net.immocrm.db.ImmobilieTransactionEntity;
import net.immocrm.db.MerkmalEntity;
import net.immocrm.db.MerkmalTypeEntity;
import net.immocrm.db.NotarEntity;
import net.immocrm.db.OrderEntity;
import net.immocrm.db.PersonEntity;
import net.immocrm.db.RequestEntity;
import net.immocrm.domain.quality.BooleanMerkmalDomain;
import net.immocrm.domain.quality.CurrencyMerkmalDomain;
import net.immocrm.domain.quality.MerkmalType;
import net.immocrm.domain.quality.NumberMerkmalDomain;
import net.immocrm.domain.quality.SquareMerkmalDomain;
import net.immocrm.domain.vc.IncorrectAreaException;
import net.immocrm.domain.vc.IncorrectNumberException;
import net.immocrm.domain.vc.IncorrectPriceException;
import net.immocrm.domain.vc.IncorrectYearException;
import net.immocrm.domain.vc.ValuesParser;

public class DomainFactory {

	public static Person newPerson() {
		return new PersonDomain(new PersonEntity());
	}

	public static Immobilie newImmobilie() {
		return new ImmobilieDomain(new ImmobilieEntity());
	}

	public static Order newOrder() {
		return new OrderDomain(new OrderEntity());
	}

	public static Notar newNotar() {
		return new NotarDomain(new NotarEntity());
	}

	public static Request newRequest(Order o) {
		RequestEntity emptyRequestEntity = new RequestEntity();
		OrderDomain order = (OrderDomain)o;
		emptyRequestEntity.setOrder(order.getEntity());
		return new RequestDomain(order, emptyRequestEntity);
	}

	public static Address newAddress() {
		return new AddressDomain(new AddressEntity());
	}

	public static Contact newContact() {
		return new ContactDomain(new ContactEntity());
	}

	public static Document newDocument() {
		return new DocumentDomain(new DocumentEntity());
	}



	static AddressDomain createDomain(AddressEntity e) {
		if (e == null) {
			e = new AddressEntity();
		}
		return new AddressDomain(e);
	}

	static PersonDomain createDomain(PersonEntity e) {
		if (e == null) {
			e = new PersonEntity();
		}
		return new PersonDomain(e);
	}

	static ImmobilieDomain createDomain(ImmobilieEntity e) {
		if (e == null) {
			e = new ImmobilieEntity();
		}
		return new ImmobilieDomain(e);
	}

	static OrderDomain createDomain(OrderEntity e) {
		if (e == null) {
			e = new OrderEntity();
		}
		return new OrderDomain(e);
	}

	static ImmoTransactionDomain createDomain(ImmobilieTransactionEntity e) {
		if (e == null) {
			e = new ImmobilieTransactionEntity();
		}
		return new ImmoTransactionDomain(e);
	}

	static NotarDomain createDomain(NotarEntity e) {
		if (e == null) {
			e = new NotarEntity();
		}
		return new NotarDomain(e);
	}

	static Request createDomain(RequestEntity e) {
		OrderDomain order = createDomain(e.getOrder());
		return new RequestDomain(order, e);
	}

	static Document createDomain(DocumentEntity e) {
		return new DocumentDomain(e);
	}

	public static MerkmalTypeDomain createDomain(MerkmalTypeEntity e) {
		return new MerkmalTypeDomain(e);
	}


	/**
	 * From data base
	 */
	static MerkmalDomain createDomain(MerkmalEntity e) {
		if (e == null) {
			throw new IllegalArgumentException("MerkmalEntity must not be null");
		}
		MerkmalType type = createDomain(e.getType());
		switch (type.getDataType()) {
		case area:
			return new SquareMerkmalDomain(type, e);
		case bool:
			return new BooleanMerkmalDomain(type, e);
		case curr:
			return new CurrencyMerkmalDomain(type, e);
		case num:
		case year:
			return new NumberMerkmalDomain(type, e);
		default:
			return new MerkmalDomain(type, e);
		}
	}

	/**
	 * From dialogs
	 */
	static MerkmalDomain createMerkmalDomain(MerkmalType type, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		switch (type.getDataType()) {
		case area:
			try {
				return new SquareMerkmalDomain(type, ValuesParser.parseArea(value));
			} catch (ParseException e) {
				throw new IncorrectAreaException(type.getName(), value, e);
			}
		case bool:
			return new BooleanMerkmalDomain(type, Boolean.parseBoolean(value));
		case curr:
			try {
				return new CurrencyMerkmalDomain(type, ValuesParser.parsePrice(value));
			} catch (ParseException e) {
				throw new IncorrectPriceException(type.getName(), value, e);
			}
		case num:
			try {
				return new NumberMerkmalDomain(type, ValuesParser.parseNumber(value));
			} catch (ParseException e) {
				throw new IncorrectNumberException(type.getName(), value, e);
			}
		case year:
			try {
				return new NumberMerkmalDomain(type, ValuesParser.parseYear(value));
			} catch (ParseException e) {
				throw new IncorrectYearException(type.getName(), value, e);
			}
		default:
			return new MerkmalDomain(type, value);
		}
	}

	private DomainFactory() {
	}

}
