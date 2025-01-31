package org.team3082.chicken_planner.UIElements.CustomNodes.Editor;

import static org.team3082.chicken_planner.UIElements.Utilities.TextUtil.createText;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SidebarCategory extends VBox {
    private String title = "category";
    
    public SidebarCategory(String title) {
        super(16);
        setAlignment(Pos.TOP_LEFT);
        getChildren().add(createText(title, title + "Title", "h2"));

    }
}
