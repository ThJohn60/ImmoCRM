package net.immocrm.gui.property;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Immobilie;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.refresh.RefreshDispatcher;
import net.immocrm.gui.refresh.RefreshedImmobilien;

public class ImmobilieTableTab extends BaseTab {

	private final RefreshDispatcher refreshDispatcher;

	private ImmobilieController myCtrl;
	private Tab myTab;


	public ImmobilieTableTab(TabPane tabPane, RefreshDispatcher refreshDispatcher) {
		super(tabPane);
		this.refreshDispatcher = refreshDispatcher;
	}


	@Override
	public Immobilie getSelectedImmobilie() {
		if (myTab != null && myCtrl != null && myTab.isSelected()) {
			return myCtrl.getSelectedItem();
		}
		return null;
	}

	public void showTab() {
		if (myCtrl == null) {
			buildtab();
			refresh();
		}
		select(myTab);
		myCtrl.requestFocus();
	}


	private void buildtab() {
		FXMLLoader loader = getLoader("ImmobilieTableView.fxml");
		myTab = loadTab(loader);
		myCtrl = loader.getController();
		tabPane.getTabs().add(myTab);
//		myTab.setOnClosed(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event event) {
//				myCtrl = null;
//				myTab = null;
//			}
//		});
	}

	@Override
	public void refresh() {
		refreshImmobilie(refreshDispatcher.getRefreshedImmobilien());
	}

	@Override
	public void refreshImmobilie(RefreshedImmobilien immobilien) {
		myCtrl.refresh(immobilien);
	}

}
