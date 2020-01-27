package net.immocrm.domain.vc;

class Calculator {

	static Price plus(Price p1, Price p2) {
		if (p1.isEmpty() && p2.isEmpty()) {
			return Price.EMTPTY_PRICE;
		}
		if (p1.isEmpty()) {
			return p2;
		}
		if (p2.isEmpty()) {
			return p1;
		}
		return new Price(p1.getValue().longValue() + p2.getValue().longValue()); 
	}

	
	static Price multiply(Price p, Percent pc) {
		if (pc.isEmpty()) {
			return Price.EMTPTY_PRICE;
		}
		return multiply(p, pc.getFactor()); 
	}

	static Price multiply(Price p, double value) {
		if (p.isEmpty()) {
			return Price.EMTPTY_PRICE;
		}
		return new Price(p.getDoubleValue() * value); 
	}


	static Price divide(Price p, Area a) {
		if (p.isEmpty() || a.isEmpty()) {
			return Price.EMTPTY_PRICE;
		}
		return new Price(p.getDoubleValue() / a.getValue()); 
	}

}
