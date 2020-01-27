package net.immocrm.gui.create;

import net.immocrm.domain.ref.OrderTypeEnum;

public enum NewOrderSteps {

	OrderType,
	OrderFork,
	Owner,
	ImmobilieType,
	Lage,
	Eigenschaften,
	Ausstattung,
	Images,
	OrderDetails,
	Finance,
	Finish;


	public static NewOrderSteps firstStep(OrderTypeEnum type) {
		if (type == null) {
			return OrderType;
		}
		return OrderFork;
	}

	public NewOrderSteps nextStep(OrderInputMode orderInputMode) {
		if (ordinal() == OrderFork.ordinal()) {
			if (orderInputMode == OrderInputMode.WithoutCustomerAndImmobile) {
				return OrderDetails;
			}
			if (orderInputMode == OrderInputMode.WithoutCustomer) {
				return ImmobilieType;
			}
		}
		if (this == Finish) {
			return null;
		}
		return values()[ordinal()+1];
	}


	public NewOrderSteps previousStep(OrderInputMode orderInputMode) {
		if (orderInputMode == OrderInputMode.WithoutCustomerAndImmobile && ordinal() == OrderDetails.ordinal()) {
			return OrderFork;
		} else if (orderInputMode == OrderInputMode.WithoutCustomer && ordinal() == ImmobilieType.ordinal()) {
			return OrderFork;
		}
		if (ordinal() == 0) {
			return null;
		}
		return values()[ordinal()-1];
	}

}
