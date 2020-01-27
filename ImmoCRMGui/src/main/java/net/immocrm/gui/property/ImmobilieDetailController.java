package net.immocrm.gui.property;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import net.immocrm.domain.Document;
import net.immocrm.domain.DocumentManager;
import net.immocrm.domain.DocumentReader;
import net.immocrm.domain.ImmoTransaction;
import net.immocrm.domain.Immobilie;
import net.immocrm.domain.ImmobilieReader;
import net.immocrm.domain.Order;
import net.immocrm.domain.Person;
import net.immocrm.domain.TextMaker;
import net.immocrm.gui.BaseDialogBuilder;
import net.immocrm.gui.IconProvider;
import net.immocrm.gui.Main;
import net.immocrm.gui.MainController;
import net.immocrm.gui.alert.AlertProvider;
import net.immocrm.gui.doc.DokuDialog;
import net.immocrm.gui.doc.DokuRow;
import net.immocrm.gui.doc.PDFManager;
import net.immocrm.gui.form.MerkmalInfoMapper;
import net.immocrm.gui.img.ImageViewController;
import net.immocrm.gui.order.OrderRow;
import net.immocrm.gui.order.OrderTableInDetailsMenuController;
import net.immocrm.gui.refresh.RefreshType;
import net.immocrm.gui.tools.EditItemController;

public class ImmobilieDetailController implements Initializable  {

	@FXML ImageView detailViewIcon;
	@FXML ScrollPane scrollPane;

	@FXML Label headLine;

	@FXML Label street;
	@FXML Label city;

	@FXML Label ownerName;
	@FXML Label ownerStreet;
	@FXML Label ownerCity;

	@FXML GridPane eigenschaftenBox;
	@FXML GridPane ausstattungBox;
	@FXML Label ausstattung;

	@FXML Pane imageArea;
	@FXML Label objektbeschreibungLabel;
	@FXML Label objektbeschreibung;
	@FXML Label lagebeschreibungLabel;
	@FXML Label lagebeschreibung;
	@FXML Label internalNoticeLabel;
	@FXML Label internalNotice;

	@FXML TableView<ImmoActionRow> immoActionTable;
	@FXML TableView<OrderRow> orderTable;
	@FXML FlowPane orderTableHyperLinks;

	@FXML TableView<DokuRow> dokuTable;
	@FXML FlowPane dokuArea;

	private Immobilie immobilie;
	private ImageViewController imageCtrl;
	private OrderTableInDetailsMenuController orderMenuCtrl;
	private OrderTableInDetailsMenuController orderHyperlinkCtrl;

	private PDFManager pdfMan;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pdfMan = new PDFManager();
		setupVerticalScrolling();
		imageCtrl = new ImageViewController(imageArea);

		addContextMenuToOrderTable();
		addHyperlinksToOrderTable();
	}

	private void addHyperlinksToOrderTable() {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("order/OrderTableInDetailsHyperlinks.fxml");
		Pane hyperlinkPane = BaseDialogBuilder.loadPane(fxmlLoader);
		orderHyperlinkCtrl = fxmlLoader.getController();
		orderTableHyperLinks.getChildren().add(hyperlinkPane);
		orderHyperlinkCtrl.setOrderTable(orderTable);
	}

	private void addContextMenuToOrderTable() {
		FXMLLoader fxmlLoader = BaseDialogBuilder.getFxmlLoader("order/OrderTableInDetailsMenu.fxml");
		ContextMenu contextMenu = BaseDialogBuilder.load(fxmlLoader);
		orderMenuCtrl = fxmlLoader.getController();
		orderTable.setContextMenu(contextMenu);
		orderMenuCtrl.setOrderTable(orderTable);
	}

	private void setupVerticalScrolling() {
		scrollPane.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent scrollEvent) {
				double deltaY = scrollEvent.getDeltaY() * 4; // *4 to make the scrolling a bit faster
				double height = scrollPane.getContent().getBoundsInLocal().getHeight();
				double vValue = scrollPane.getVvalue();
				scrollPane.setVvalue(vValue + -deltaY / height);
			}
		});
	}


	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void requestFocus() {
		scrollPane.requestFocus();
	}


	public void showImmobilie(Immobilie im) {
		immobilie = im;
		detailViewIcon.setImage(IconProvider.immobilieIcon32dp(im));
		headLine.setText(TextMaker.INSTANCE.getDetailsHeader(im));

		orderMenuCtrl.setImmobilie(immobilie);
		orderHyperlinkCtrl.setImmobilie(immobilie);

		street.setText(immobilie.getAddress().getCompleteStreet());
		city.setText(immobilie.getAddress().getPostalCodeAndCity());

		fillOwnerFields(immobilie.getOwner());

		showEigenschaften(immobilie);
		showAusstattung(immobilie);
		if (immobilie.getAusstattung() != null) {
			ausstattung.setText(immobilie.getAusstattung());
		}
		fillTextArea(internalNoticeLabel, internalNotice, immobilie.getInternalNotice());
		fillTextArea(objektbeschreibungLabel, objektbeschreibung, immobilie.getObjektbeschreibung());
		fillTextArea(lagebeschreibungLabel, lagebeschreibung, immobilie.getLagebeschreibung());

		fillOrderTable(immobilie.getOrders());

		fillImmoActionTable();
//		fillDokuTable(immobilie.getId());
		fillDokuArea(immobilie.getId());
		imageCtrl.showImages(immobilie.getImageDir());
	}

	private void fillOwnerFields(Person owner) {
		ownerName.setText(owner.getName());
		ownerStreet.setText(owner.getHomeAddress().getStreet());
		ownerCity.setText(owner.getHomeAddress().getPostalCodeAndCity());
	}

	private void fillTextArea(Label textAreaFieldLabel, Label textAreaField, String text) {
		if (text != null && !text.isEmpty()) {
			textAreaFieldLabel.setVisible(true);
			textAreaField.setText(text);
			textAreaField.setPadding(new Insets(0, 0, 20, 0));
		} else {
			textAreaFieldLabel.setVisible(true);
			textAreaField.setPadding(new Insets(0));
		}
	}


	private void showEigenschaften(Immobilie im) {
		MerkmalInfoMapper merkmalMapper = new MerkmalInfoMapper(eigenschaftenBox);
		int row = merkmalMapper.eigenschaftenToInfoForm(im);
		eigenschaftenBox.add(MerkmalInfoMapper.getLabelLabel("Energieausweistyp:"), 0, row);
		eigenschaftenBox.add(MerkmalInfoMapper.getContentLabel(im.getEnergieausweisType().name()), 1, row++);
		eigenschaftenBox.add(MerkmalInfoMapper.getLabelLabel("Verbrauchskennwert:"), 0, row);
		eigenschaftenBox.add(MerkmalInfoMapper.getContentLabel(im.getEnergieverbrauchskennwert().toString()), 1, row++);
		eigenschaftenBox.add(MerkmalInfoMapper.getLabelLabel("Wohneinheit:"), 0, row);
		eigenschaftenBox.add(MerkmalInfoMapper.getContentLabel(im.getWohneinheit()), 1, row++);
	}

	private void showAusstattung(Immobilie im) {
		ausstattungBox.getChildren().clear();
		MerkmalInfoMapper merkmalMapper = new MerkmalInfoMapper(ausstattungBox);
		merkmalMapper.ausstattungToInfoForm(im);
	}


	private void fillOrderTable(List<Order> orders) {
		ObservableList<OrderRow> tableContent = orderTable.getItems();
		tableContent.clear();
		for (Order order : orders) {
			tableContent.add(new OrderRow(order));
		}
		orderTable.setItems(tableContent);
		orderTable.getSelectionModel().clearSelection();
	}

	@FXML
	public void orderTableKlicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			OrderRow selectedItem = orderTable.getSelectionModel().getSelectedItem();
			if  (selectedItem != null) {
				MainController.instance().showOrderDetails(selectedItem.getDomain());
			}
		}
	}

	private void fillImmoActionTable() {
		ObservableList<ImmoActionRow> tableContent = immoActionTable.getItems();
		tableContent.clear();
		tableContent.addAll(getRows());
		immoActionTable.getSelectionModel().clearSelection();
	}

	private Collection<ImmoActionRow> getRows() {
		Set<ImmoActionRow> rows = new HashSet<>();
		for (ImmoTransaction immo : ImmobilieReader.INSTANCE.fetchTransactions(immobilie)) {
			rows.add(new TransactionRow(immo));
		}
		rows.add(new ImmobilieActionRow(immobilie));
		List<ImmoActionRow> result = new ArrayList<>(rows);
		Collections.sort(result);
		return result;
	}


	private void fillDokuTable(Integer immoId) {
		List<Document> docs = DocumentReader.INSTANCE.fetchByImmobilie(immoId);
		dokuTable.getItems().clear();
		for (Document document : docs) {
			dokuTable.getItems().add(new DokuRow(document));
		}
	}

	private void fillDokuArea(Integer immoId) {
		dokuArea.getChildren().clear();
		List<Document> docs = DocumentReader.INSTANCE.fetchByImmobilie(immoId);
		for (Document document : docs) {
			Hyperlink hl = imageAsHyperlink(document);
			VBox vBox = new VBox();
			Label docLabel = new Label(document.getDocName());
			docLabel.setPrefWidth(300);
			docLabel.setTextAlignment(TextAlignment.CENTER);
			docLabel.getStyleClass().add("content");
			vBox.getChildren().addAll(hl, docLabel);
			dokuArea.getChildren().add(vBox);
		}
	}

	private Hyperlink imageAsHyperlink(Document document) {
		WritableImage image = pdfMan.loadFrontPage(document.getFile());
		ImageView frontPageArea = new ImageView(image);
		setSize(image, frontPageArea);

		Hyperlink hl = new Hyperlink("", frontPageArea);
		hl.setOnAction(event -> openDoku(document));
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(editDokuMenu(document), openDokuMenu(document), new SeparatorMenuItem(), deleteMenu(document));
		hl.setContextMenu(contextMenu);
		return hl;
	}

	private void setSize(WritableImage image, ImageView frontPageArea) {
		double imageHeight = image.getHeight();
		double imageWidth = image.getWidth();
		frontPageArea.setFitWidth(300);
		frontPageArea.setFitHeight(frontPageArea.getFitWidth() * imageHeight / imageWidth);
	}

	private MenuItem editDokuMenu(Document document) {
		MenuItem menuItem = new MenuItem();
		menuItem.setText("Bearbeiten");
		menuItem.setGraphic(new ImageView(new Image(IconProvider.class.getResourceAsStream("/rsc/icon/edit_18dp.png"))));
		menuItem.setOnAction(event -> editDoku(document));
		return menuItem;
	}

	private MenuItem openDokuMenu(Document document) {
		MenuItem menuItem = new MenuItem();
		menuItem.setText("Öffnen");
		menuItem.setOnAction(event -> openDoku(document));
		return menuItem;
	}

	private MenuItem deleteMenu(Document document) {
		MenuItem menuItem = new MenuItem();
		menuItem.setText("Löschen");
		menuItem.setGraphic(new ImageView(new Image(IconProvider.class.getResourceAsStream("/rsc/icon/arrow_18dp.png"))));
		menuItem.setOnAction(event -> deleteDoku(document));
		return menuItem;
	}

	@FXML
	public void editImmobilie() {
		new EditItemController().editImmobilie(immobilie);
	}

	@FXML
	public void editOwner() {
		new EditItemController().editPerson(immobilie.getOwner(), true);
	}

	@FXML
	public void editEigenschaften() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(immobilie, 1);
		MainController.getRefreshDispatcher().refreshImmobilieViews(changedImmobilie, RefreshType.updateItem);
	}

	@FXML
	public void editAustattung() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(immobilie, 2);
		MainController.getRefreshDispatcher().refreshImmobilieViews(changedImmobilie, RefreshType.updateItem);
	}


	@FXML
	public void editNotice() {
		Immobilie changedImmobilie = new ImmobilieDialog().showDialog(immobilie, 4);
		MainController.getRefreshDispatcher().refreshImmobilieViews(changedImmobilie, RefreshType.updateItem);
	}


	@FXML
	public void showInMap() {
		MainController.instance().openMapTab(immobilie.getAddress());
	}

	@FXML
	public void showOwnerAddressInMap() {
		if (!immobilie.getOwner().getHomeAddress().isEmpty()) {
			MainController.instance().openMapTab(immobilie.getOwner().getHomeAddress());
		}
	}

	@FXML
	public void gotoPerson() {
		MainController.instance().showPersonDetails(immobilie.getOwner());
	}

	@FXML
	public void transTableKlicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			ImmoActionRow selectedItem = immoActionTable.getSelectionModel().getSelectedItem();
			if  (selectedItem != null) {
				MainController.instance().showPersonDetails(selectedItem.getPerson());
			}
		}
	}


	@FXML
	public void importImage() {
		imageCtrl.importImage();
	}

	@FXML
	public void deleteImage() {
		if (AlertProvider.confirm("Sind Sie sicher, dass Sie das Bild löschen wollen?")) {
			imageCtrl.deleteImage();
		}
	}

	private void editDoku(Document doc) {
		Document document = new DokuDialog().showDialog(immobilie, doc);
		if (document != null) {
			fillDokuArea(immobilie.getId());
		}
	}

	private void openDoku(Document document) {
		File docFile = document.getFile();
		try {
			Main.getApplicationHostServices().showDocument(docFile.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			// can't happen
		}
	}

	private void deleteDoku(Document document) {
		if (AlertProvider.confirmRemove(document)) {
			DocumentManager docMan = new DocumentManager();
			docMan.delete(document);
			fillDokuTable(immobilie.getId());
		}
	}

	@FXML
	public void newDoku() {
		Document document = pdfMan.importDocument(immobilie);
		if (document != null) {
			fillDokuArea(immobilie.getId());
		}
	}

}
