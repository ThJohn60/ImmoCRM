package net.immocrm.gui.notar;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import net.immocrm.domain.Notar;
import net.immocrm.gui.BaseTab;
import net.immocrm.gui.refresh.RefreshDispatcher;
import net.immocrm.gui.refresh.RefreshedNotars;

public class NotarTableTab extends BaseTab {

	private final RefreshDispatcher refreshDispatcher;

	private NotarController myCtrl;
	private Tab myTab;


	public NotarTableTab(TabPane tabPane, RefreshDispatcher refreshDispatcher) {
		super(tabPane);
		this.refreshDispatcher = refreshDispatcher;
	}


	public Notar getSelectedNotar() {
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
		FXMLLoader loader = getLoader("NotarView.fxml");
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
		myCtrl.refresh(refreshDispatcher.getRefreshedNotars());
	}

	public void refresh(RefreshedNotars notars) {
		myCtrl.refresh(notars);
	}

}
