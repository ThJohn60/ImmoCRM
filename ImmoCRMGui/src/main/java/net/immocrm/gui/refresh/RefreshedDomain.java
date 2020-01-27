package net.immocrm.gui.refresh;

import java.util.List;

import net.immocrm.domain.BaseDomain;

public class RefreshedDomain<D extends BaseDomain> {

	private final D domain;
	private final List<D> list;
	private final RefreshType causeType;


	public RefreshedDomain(D updatedDomain, List<D> list, RefreshType causeType) {
		this.domain = updatedDomain;
		this.list = list;
		this.causeType = causeType;
	}


	/**
	 * updated or deleted domain object
	 */
	public D getDomain() {
		return domain;
	}

	public List<D> getList() {
		return list;
	}

	public RefreshType getCauseType() {
		return causeType;
	}

	public boolean isRefreshAll() {
		return causeType == RefreshType.all;
	}

	public final boolean isFiltered() {
		return causeType == RefreshType.filtered;
	}

	public boolean isNewObject() {
		return causeType == RefreshType.insertItem;
	}

	public boolean isUpdate() {
		return causeType == RefreshType.updateItem;
	}

	public boolean isDelete() {
		return causeType == RefreshType.deleteItem;
	}

}
