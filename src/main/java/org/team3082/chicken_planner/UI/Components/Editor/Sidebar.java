package org.team3082.chicken_planner.UI.Components.Editor;

import org.team3082.chicken_planner.State.EventBus;
import org.team3082.chicken_planner.State.Events.PageSwitchEvent;
import org.team3082.chicken_planner.UI.Components.Icon;
import org.team3082.chicken_planner.UI.Main.Page;
import org.team3082.chicken_planner.UI.Utilities.TextUtilities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Sidebar extends VBox {
    private HBox tabs;
    private VBox contents;

    SimpleIntegerProperty pointView = new SimpleIntegerProperty(0);
    

    public Sidebar() {
        super();
        VBox layout = new VBox(16);
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setPrefWidth(292);
        layout.setPrefHeight(656);
        layout.getStyleClass().add("sidebar");

        tabs = makeTabs();
        ScrollPane scrollContainer = new ScrollPane();
        scrollContainer.setFitToHeight(true);
        scrollContainer.getStyleClass().clear();
        scrollContainer.getStyleClass().add("sidebarScroll");
        scrollContainer.setFitToWidth(true);
        scrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollContainer, Priority.ALWAYS);

        contents = new VBox(24);
        VBox.setVgrow(contents, Priority.ALWAYS);
        contents.setPrefWidth(292);
        contents.getStyleClass().add("sidebarContents");
        
        contents.getChildren().add(new ControlPointSelector());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        contents.getChildren().add(spacer);

        scrollContainer.setContent(contents);
        layout.getChildren().addAll(tabs, scrollContainer);

        

        getChildren().add(layout);
    }
        
    private HBox makeTabs() {
        HBox tabs = new HBox(16);
        tabs.setPrefHeight(40);
        tabs.setMinHeight(40);

        Button editButton = new Button();
        {
            Icon icon = new Icon("icons/pen-line.svg", 14, "-fx-text");
            Text text = TextUtilities.createText("Edit", "editTabText", "tabText");
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
            Text text = TextUtilities.createText("Robot", "botTabText", "selectedTabText");
            HBox buttonLayout = new HBox(8, icon, text);
            buttonLayout.setAlignment(Pos.CENTER);
            botButton.setGraphic(buttonLayout);
            botButton.getStyleClass().add("tab");
            botButton.setId("botTab");
            botButton.setPrefSize(138, 40);
        }

        botButton.setOnMouseClicked((MouseEvent event) -> {
            EventBus.fireEvent(new PageSwitchEvent(Page.LANDING_PAGE));
        });

        tabs.getChildren().addAll(editButton, botButton);
        return tabs;
    }



}
