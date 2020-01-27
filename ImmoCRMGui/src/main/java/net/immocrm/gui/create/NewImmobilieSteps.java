package net.immocrm.gui.create;

public enum NewImmobilieSteps {

	Type,
	Fork,
	Owner,
	Details,
	Eigenschaften,
	Ausstattung,
	Images,
	Finish;


	public NewImmobilieSteps nextStep(ImmoInputMode inputMode) {
		if (ordinal() == Fork.ordinal() && inputMode == ImmoInputMode.WithoutCustomer) {
			return Details;
		}
		if (this == Finish) {
			return null;
		}
		return values()[ordinal()+1];
	}

	public NewImmobilieSteps previousStep(ImmoInputMode inputMode) {
		if (ordinal() == Details.ordinal() && inputMode == ImmoInputMode.WithoutCustomer) {
			return Fork;
		}
		if (ordinal() == 0) {
			return null;
		}
		return values()[ordinal()-1];
	}

}
