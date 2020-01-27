package net.immocrm.domain.ref;

public enum CountryNameEnum {

	AL("Albanien"),
	AD("Andorra"),
	AU("Australien"),
	BE("Belgien"),
	BA("Bosnien und Herzegowina"),
	BG("Bulgarien"),
	DE("Deutschland"),
	DK("Dänemark"),
	EE("Estland"),
	FI("Finnland"),
	FR("Frankreich"),
	FO("Färöer-Inseln"),
	GI("Gibraltar"),
	GR("Griechenland"),
	GG("Guernsey"),
	IE("Irland"),
	IS("Island"),
	IM("Isle of Man"),
	IT("Italien"),
	JP("Japan"),
	JE("Jersey"),
	CA("Kanada"),
	HR("Kroatien"),
	LV("Lettland"),
	LI("Liechtenstein"),
	LT("Litauen"),
	LU("Luxemburg"),
	MT("Malta"),
	MC("Monaco"),
	ME("Montenegro"),
	NZ("Neuseeland"),
	NL("Niederlande"),
	NO("Norwegen"),
	PL("Polen"),
	PT("Portugal"),
	RO("Rumänien"),
	RU("Russland"),
	SM("San Marino"),
	SE("Schweden"),
	CH("Schweiz"),
	RS("Serbien"),
	SG("Singapur"),
	SK("Slowakei"),
	SI("Slowenien"),
	ES("Spanien"),
	TH("Thailand"),
	TR("Türkei"),
	HU("Ungarn"),
	VA("Vatikanstadt"),
	GB("Vereinigtes Königreich"),
	CY("Zypern"),
	AT("Österreich");

	private String countryName;

	private CountryNameEnum(String name) {
		this.countryName = name;
	}


	public String getCountryName() {
		return countryName;
	}

	public static CountryNameEnum getByCountryName(String cName) {
		CountryNameEnum[] values = CountryNameEnum.values();
		for (CountryNameEnum en : values) {
			if (en.name().equals(cName)) {
				return en;
			}
		}
		throw new IllegalArgumentException("Unknown country: " + cName);
	}

	@Override
	public String toString() {
		return countryName;
	}

}
