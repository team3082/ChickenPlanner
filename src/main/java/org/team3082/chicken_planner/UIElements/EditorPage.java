package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.UIElements.CustomNodes.Editor.Field;
import org.team3082.chicken_planner.UIElements.CustomNodes.Editor.Sidebar;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditorPage extends BorderPane {

    private final Stage stage;

    public EditorPage(Stage stage) {
        super();
        createContentLayout();
        this.stage = stage; // Keep a reference to the stage
    }

    private void createContentLayout() {
        // Create the sidebar and field
        Sidebar sidebar = new Sidebar();
        VBox sidebarContainer = new VBox();  // Create a container for the sidebar
        sidebarContainer.getChildren().add(sidebar);  // Add the sidebar to the container
        
        Field field = new Field(this, sidebarContainer);  // Pass the sidebar container to Field
        
        // Set the sidebar and field in the layout
        setLeft(sidebarContainer);  // Set sidebar to the left
        setCenter(field);  // Set field to the center
    }
}
