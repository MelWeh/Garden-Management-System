/* Author:
 * Date: 12/7/2025
 * Purpose: Main JavaFX application. Sets up GUI and user interaction
 */

package application;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	private GardenManager manager;
	private GridPane gardenGrid;
	private ListView<Vegetable> vegList;
	private TextArea infoArea;
	private TextArea statusArea;
	private TextField rowsField, colsField;
	private int selectedRow = -1, selectedCol = -1;
	
	@Override
	public void start(Stage primaryStage) {
		manager = new GardenManager();
		new GardenContainer<>();
		
		// Initialize
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		
		// Top: Control Panel
		root.setTop(createControls());
		
		// Center: Garden Grid
		gardenGrid = new GridPane();
		gardenGrid.setHgap(5);
		gardenGrid.setVgap(5);
		gardenGrid.setPadding(new Insets(10));
		ScrollPane gridScroll = new ScrollPane(gardenGrid);
		

		// Bottom: Log and Stats
		statusArea = new TextArea();
		statusArea.setPrefHeight(150);
		statusArea.setEditable(false);
		
		// Create a SplitPane for the center and bottom areas
	    SplitPane centerSplitPane = new SplitPane();
	    centerSplitPane.setOrientation(Orientation.VERTICAL);
	    centerSplitPane.getItems().addAll(gridScroll, statusArea);
	    centerSplitPane.setDividerPositions(0.7); // 70% for grid, 30% for status
	    
	    root.setCenter(centerSplitPane);
		
		// Rightside: Veggie Info Panel
		vegList = new ListView<>();
		vegList.setPrefWidth(200);
		
		infoArea = new TextArea();
		infoArea.setPrefHeight(350);
		infoArea.setEditable(false);
		
		VBox rightPanel = new VBox(10);
		rightPanel.setPadding(new Insets(5));
		rightPanel.setPrefWidth(400);
		rightPanel.getChildren().addAll(
				new Label("		Vegetables:"), vegList, 
				new Label("		Info:"), infoArea);
		root.setRight(rightPanel);
		
		// Set up scene
		Scene scene = new Scene(root, 1200, 700);
		primaryStage.setTitle("Vegetable Garden Management System");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Load data and initialize it
		loadData();
	}

	private HBox createControls() {
		HBox controls = new HBox(10);
		controls.setPadding(new Insets(5));
		
		rowsField = new TextField("5");
		rowsField.setPrefWidth(50);
		colsField = new TextField("5");
		colsField.setPrefWidth(50);
		
		Button createButton = new Button("Create Garden");
		Button loadButton = new Button("Load Vegetables");
		Button saveButton = new Button("Save Garden");
		Button undoButton = new Button("Undo");
		Button removeButton = new Button("Remove Selected");
		
		// Lambda create button
		createButton.setOnAction(e -> {
			int rows = 0, cols = 0;
			try {
				rows = Integer.parseInt(rowsField.getText());
				cols = Integer.parseInt(colsField.getText());
			} catch (NumberFormatException ex) {
				showAlert("Error: Please enter valid numbers");
				return;
			}
			
			// Limit grid sizes
			if (rows < 1 || rows > 10) {
				showAlert("Rows must be between 1 and 10.");
				return;
			}

			if (cols < 1 || cols > 15) {
				showAlert("Columns must be between 1 and 15.");
				return;
			}
			
			manager.createGarden(rows, cols);
			displayGarden();
			updateStatusArea();
		});
		
		// Load button lambda
		loadButton.setOnAction(e -> loadData());
		
		// Save button lambda
		saveButton.setOnAction(e -> {
			try {
				manager.saveGarden("files/garden_save.csv");
				showAlert("Garden saved!");
				updateStatusArea();
			} catch (Exception ex) {
				showAlert("Error saving: " + ex.getMessage());
				return;
			}
		});
		
		// Undo and Remove button lambda
		undoButton.setOnAction(e -> undoAction());
		removeButton.setOnAction(e -> removeSelected());
		
		controls.getChildren().addAll(
				new Label("Rows:"), rowsField,
				new Label("Columns:"), colsField,
				createButton, loadButton, saveButton, undoButton, removeButton);
		return controls;
	}
	
	// Displays garden
	private void displayGarden() {
		gardenGrid.getChildren().clear();
		
		GardenGrid grid = manager.getGrid();
		
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				GardenCell cell = grid.getCell(i, j);
				Button button = new Button();
				button.setPrefSize(60, 60);
				
				if (cell.hasVegetable()) {
					button.setText(cell.getVegetable().getName());
					button.setStyle("-fx-background-color: lightgreen;");
				}  else {
                    button.setText("Empty");
                    button.setStyle("-fx-background-color: lightgray;");
				}
				
				final int row = i;
				final int col = j;
				button.setOnAction(e -> handleCellClick(row, col));
				
				gardenGrid.add(button, j, i);
			}
		}
	}
	
	// Event handler for cell clicking: plants selected vegetables or displays cell info
	private void handleCellClick(int row, int col) {
		Vegetable selected = vegList.getSelectionModel().getSelectedItem();
		GardenCell cell = manager.getGrid().getCell(row, col);
		
		// Update cell tracking
		selectedRow = row;
		selectedCol = col;
		
		if (selected != null) {
			// Plant veggie
			if (manager.plant(row, col, selected)) {
				displayGarden();
				showInfo(selected);
				updateStatusArea();
				
				// Clear selection after planting
				vegList.getSelectionModel().clearSelection();
			} else {
				showAlert("Cell already occupied!");
			}
		} else if (cell.hasVegetable()) { // Show veg info
			showInfo(cell.getVegetable());
		} else {
			// Empty cell clicked
			infoArea.setText("Empty cell selected at (" + row + "," + col + ")");
		}
	}
	
	private void removeSelected() {
		if (selectedRow >= 0 && selectedCol >= 0 ) {
			if (manager.remove(selectedRow, selectedCol)) {
				displayGarden();
				updateStatusArea();
				infoArea.setText("Removed vegetables from (" + selectedRow +
						"," + selectedCol + ")");
			}
		} else {
			showAlert("Select a cell first");
		}
	}
	
	private void undoAction() {
        manager.undo();
        displayGarden();
        updateStatusArea();
        infoArea.setText("Last action undone");
    }
	
	private void loadData() {
		try {
			manager.loadVegetableFile("files/7a_veg_info.txt");
			vegList.getItems().setAll(manager.getVegetables());
			
			// Generic GardenContainer
			GardenContainer<Vegetable> container = new GardenContainer<>();
			for (Vegetable veg : manager.getVegetables()) {
				container.add(veg);
			}
			GardenContainer.printAllNames(container);
			
			updateStatusArea();
			infoArea.setText("Loaded " + manager.getVegetables().size() + " vegetables");
			
		} catch (Exception e) {
			showAlert("Error loading vegetables: " + e.getMessage());
		}
	}
	
	private void showInfo(Vegetable veg) {
		if (veg != null) {
			infoArea.setText(veg.getName() + "\nType: " + veg.getType() + 
					"\nPlanting Instructions:\n" + veg.getPlantingInstructions());
		}
	}
	
	private void updateStatusArea() {
		if (statusArea != null) {
			StringBuilder status = new StringBuilder();
			status.append(manager.getActionHistory()).append("\n");
			
			status.append("\n\n=== Garden Stats ===\n");
			status.append("Total plants: ").append(manager.getTotalVegetables()).append("\n");
			
			statusArea.setText(status.toString());
		}
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
