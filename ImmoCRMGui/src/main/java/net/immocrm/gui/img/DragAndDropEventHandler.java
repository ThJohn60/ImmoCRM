package net.immocrm.gui.img;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class DragAndDropEventHandler {

	private final ImageImporter imageImporter;
	private final ImageViewer viewer;

	public DragAndDropEventHandler(ImageImporter imageImporter, ImageViewer viewer) {
		this.imageImporter = imageImporter;
		this.viewer = viewer;
	}

	public void setDropTarget(Node node) {
		node.setOnDragOver(onDraggedOver(node));
		node.setOnDragDropped(onDragDropped());
	}


	private EventHandler<? super DragEvent> onDraggedOver(Node node) {
		return event -> {
			if (event.getGestureSource() != node && event.getDragboard().hasFiles()) {
				event.acceptTransferModes(TransferMode.COPY);
			}
			event.consume();
		};
	}

	private EventHandler<DragEvent> onDragDropped() {
		return event -> {
	        Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasFiles()) {
	        	imageImporter.importFiles(db.getFiles());
	        	viewer.showImages();
	        	success = true;
	        } 
	        event.setDropCompleted(success);
	        event.consume();
    	};
	}

}
