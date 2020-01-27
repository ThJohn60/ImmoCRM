package net.immocrm.domain.ref;

import net.immocrm.db.MerkmalTypeEnum;

public class CategoryMapper {

	public static Category getCategory(MerkmalTypeEnum type) {
		switch (type) {
		case bath:
			return Category.Bad;
		case car:
			return Category.KFZ_Stellplatz;
		case equip:
			return Category.Sonstiges;
		case intern:
			return Category.Internes;
		case gen:
			return Category.Allgemeines;
		case heat:
			return Category.Heizung;
		case state:
			return Category.Zustand;
		case type:
			return Category.Immobilentyp;
		default:
			throw new IllegalStateException();
		}
	}

	public static MerkmalTypeEnum getType(Category category) {
		switch (category) {
		case Allgemeines:
			return MerkmalTypeEnum.gen;
		case Sonstiges:
			return MerkmalTypeEnum.equip;
		case Heizung:
			return MerkmalTypeEnum.heat;
		case Immobilentyp:
			return MerkmalTypeEnum.type;
		case Bad:
			return MerkmalTypeEnum.bath;
		case KFZ_Stellplatz:
			return MerkmalTypeEnum.car;
		case Zustand:
			return MerkmalTypeEnum.state;
		default:
			throw new IllegalStateException();
		}
	}

}
