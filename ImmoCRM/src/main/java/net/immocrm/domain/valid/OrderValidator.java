package net.immocrm.domain.valid;

import static net.immocrm.domain.valid.ValidationTool.checkNull;

import net.immocrm.domain.Order;

public class OrderValidator implements Validator<Order> {

	@Override
	public ValidationIssues validate(Order order) {
		ValidationIssues issues = new ValidationIssues();
		checkNull(order.getOrderType(), "Auftragstyp", issues);
		checkNull(order.getOrderState(), "Auftragsstatus", issues);

		if (order.getCustomer().isEmpty()) {
			issues.addMissingField("Auftraggeber");
		} else {
			issues.addAllIssues(order.getCustomer().validate());
		}

		if (order.getImmobilie().isEmpty()) {
			issues.addMissingField("Objekt/Immobilie");
		} else {
			issues.addAllIssues(order.getImmobilie().validate());
		}
		return issues;
	}

}
