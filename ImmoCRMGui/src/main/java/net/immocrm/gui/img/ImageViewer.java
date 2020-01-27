package net.immocrm.gui.img;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import net.immocrm.domain.img.ImageProvider;
import net.immocrm.gui.alert.AlertProvider;

public class ImageViewer {

	private static final int IMAGE_HEIGHT = 400;
	private static final int IMAGE_WIDTH = 600;
	private final Pane imageArea;
	private final ImageView imageView;
	private final ImageProvider imageProvider;
	private final Label tipLabel;

	private Pagination pagination;


	public ImageViewer(Pane imageArea, ImageProvider imageProvider) {
		this.imageArea = imageArea;
		this.imageProvider = imageProvider;
		this.imageView = createImageView();
		tipLabel = createTipLabel();
	}

	private Label createTipLabel() {
		Label result = new Label("Einfach die Bilder mit der Maus in diesen Bereich ziehen.");
		result.setTextAlignment(TextAlignment.CENTER);
		result.setAlignment(Pos.CENTER);
		result.setStyle("-fx-font: NORMAL 14 Tahoma;");
		result.setPrefHeight(300);
		result.setPrefWidth(400);
		return result;
	}

	private ImageView createImageView() {
		ImageView result = new ImageView();
        result.setPreserveRatio(true);
        result.setSmooth(true);
        result.setCache(true);
		return result;
	}

	public void setCurrentPageIndex(int index) {
		pagination.setCurrentPageIndex(index);
	}

	public int getCurrentPageIndex() {
		return pagination.getCurrentPageIndex();
	}

	public final int getPageCount() {
		if (pagination == null) {
			return 0;
		}
		return pagination.getPageCount();
	}

	public void showImages() {
    	showImages(imageProvider.getFiles());
    }

	public void showImages(List<File> files) {
		imageArea.getChildren().clear();
		if (!files.isEmpty()) {
			pagination = createPagination(files);
			imageArea.getChildren().add(pagination);
		} else {
			imageArea.getChildren().add(tipLabel);
		}
	}

	private Pagination createPagination(List<File> files) {
		Pagination result = new Pagination(files.size());
		result.setPageFactory(pageFactory(files));
        return result;
	}

	private Callback<Integer, Node> pageFactory(List<File> files) {
		return pageIndex -> {
            File imgFile = files.get(pageIndex);
            try {
                Image image = new Image(imgFile.toURI().toURL().toString());
                imageArea.setMinHeight(IMAGE_HEIGHT);
                imageArea.setMinWidth(IMAGE_WIDTH);
                imageArea.setPrefHeight(IMAGE_HEIGHT);
                imageArea.setPrefWidth(IMAGE_WIDTH);
                imageView.setFitHeight(IMAGE_HEIGHT);
                imageView.setFitWidth(IMAGE_WIDTH);
                AnchorPane.setTopAnchor(pagination, 5.0);
                AnchorPane.setRightAnchor(pagination, 1.0);
                AnchorPane.setBottomAnchor(pagination, 1.0);
                AnchorPane.setLeftAnchor(pagination, 1.0);
                imageView.setImage(image);
            } catch (IOException e) {
                AlertProvider.alertException("Problem beim Lesen der Bilddatei " + imgFile.getName(), e);
            }
            return imageView;
        };
	}

}
