
package org.team3082.chicken_planner.UIElements.CustomNodes.Editor;

import org.team3082.chicken_planner.UIElements.CustomNodes.Icon;
import static org.team3082.chicken_planner.UIElements.Utilities.TextUtil.createText;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Sidebar extends VBox {

    private HBox tabs;
    private VBox contents;

    public Sidebar() {
        super();
        VBox layout = new VBox(16);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPrefWidth(292);
        layout.setPrefHeight(656);
        layout.getStyleClass().add("sidebar");

        tabs = makeTabs();

        contents = new VBox(24);
        contents.setPrefWidth(292);
        VBox.setVgrow(contents, Priority.ALWAYS);
        contents.getStyleClass().add("sidebarContents");
        loadSidebarPage("edit", "move");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        contents.getChildren().add(spacer);

        HBox controls = makeControls();
        contents.getChildren().add(controls);

        layout.getChildren().addAll(tabs, contents);

        getChildren().add(layout);
    }
        
    private HBox makeControls() {
        HBox controls = new HBox(24);
        controls.setAlignment(Pos.CENTER_RIGHT);
        controls.setPrefHeight(64);
        controls.setPrefWidth(292);

        Icon settings = new Icon("icons/settings.svg", 14, "-fx-text");
        controls.getChildren().add(settings);

        return controls;
    }

    private HBox makeTabs() {
        HBox tabs = new HBox(16);

        Button editButton = new Button();
        {
            Icon icon = new Icon("icons/pen-line.svg", 14, "-fx-text");
            Text text = createText("Edit", "editTabText", "tabText");
            HBox buttonLayout = new HBox(8, icon, text);
            buttonLayout.setAlignment(Pos.CENTER);
            editButton.setGraphic(buttonLayout);
            editButton.getStyleClass().addAll("tab-selected");
            editButton.setId("editTab");
            editButton.setPrefSize(138, 40);
        }

        Button botButton = new Button();
        {
            Icon icon = new Icon("icons/bot.svg", 14, "-fx-text");
            Text text = createText("Robot", "botTabText", "selectedTabText");
            HBox buttonLayout = new HBox(8, icon, text);
            buttonLayout.setAlignment(Pos.CENTER);
            botButton.setGraphic(buttonLayout);
            botButton.getStyleClass().add("tab");
            botButton.setId("botTab");
            botButton.setPrefSize(138, 40);
        }

        tabs.getChildren().addAll(editButton, botButton);
        return tabs;
    }

    private void loadSidebarPage(String tab, String mode) {
        Node editTab = tabs.lookup("#editTab");
        Node botTab = tabs.lookup("#botTab");

        if (tab.equals("edit")) {
            editTab.getStyleClass().remove(0);
            editTab.getStyleClass().add("tab-selected");
            botTab.getStyleClass().remove(0);
            botTab.getStyleClass().add("tab");

            VBox editingCategory = new SidebarCategory("Editing");
            {
                SidebarOption editModeOption = new SidebarOption("Mode", "button", "editModeOption", "move", "action");

                editingCategory.getChildren().addAll(editModeOption);
            }

            VBox nodeCategory = new SidebarCategory("Selected node");
            {
                SidebarOption positionOption = new SidebarOption("Position", "field", "positionOption", "t");
                SidebarOption robotOption = new SidebarOption("Robot", "field", "robotOption", "dir", "vel");
                SidebarOption actionOption = new SidebarOption("", "icons", "actionOption", "icons/bot.svg", "icons/file-input.svg");
                nodeCategory.getChildren().addAll(positionOption, robotOption, actionOption);
            }
            contents.getChildren().addAll(editingCategory, nodeCategory);
        }
    }

}
