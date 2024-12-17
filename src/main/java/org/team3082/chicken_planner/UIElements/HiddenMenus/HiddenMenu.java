package org.team3082.chicken_planner.UIElements.HiddenMenus;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.UIElements.Menubar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Represents a hidden menu in the Chicken Planner application.
 * Each menu is associated with a button in the menubar that opens a new modal window.
 */
public class HiddenMenu {
    protected final ChickenPlannerApplication application;

    private final String menuName;
    private final Menubar menubar;
    private final Button button;
    protected final Stage menuStage;
    protected final BorderPane borderPane;
    protected Scene scene;

    /**
     * Constructs a new HiddenMenu.
     *
     * @param menuName    The name of the menu.
     * @param application The main application instance.
     * @param menubar     The menubar to which this menu belongs.
     */
    public HiddenMenu(String menuName, ChickenPlannerApplication application, Menubar menubar) {
        this.menuName = menuName;
        this.application = application;
        this.menubar = menubar;
        this.button = createMenuButton();
        this.menuStage = createMenuStage();
        this.borderPane = createBorderPane();

        button.setOnAction(e -> openWindow());
    }

    /**
     * Creates the menu button and adds it to the menubar.
     *
     * @return The created Button instance.
     */
    protected Button createMenuButton() {
        Button menuButton = new Button(menuName);
        menuButton.getStyleClass().add("menuButtons");
        HBox.setMargin(menuButton, new Insets(0, 10, 0, 0));
        menubar.getRoot().getChildren().add(menuButton);
        return menuButton;
    }

    /**
     * Sets up the modal stage for the menu window.
     *
     * @return The configured Stage instance.
     */
    protected Stage createMenuStage() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(menuName);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        return stage;
    }

    /**
     * Sets up the main layout for the menu window.
     *
     * @return The configured BorderPane instance.
     */
    protected BorderPane createBorderPane() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #706fd3; -fx-border-color: #40407a; -fx-border-width: 2; -fx-border-radius: 5;");
        pane.setTop(createMenuBar());
        pane.setBottom(createCloseButton());
        
        scene = new Scene(pane, 450, 375);
        scene.setFill(new Color(0, 0, 0, 0));
        menuStage.setScene(scene);
        return pane;
    }

    /**
     * Creates the top menu bar with the menu label.
     *
     * @return The configured HBox instance.
     */
    protected HBox createMenuBar() {
        HBox menubar = new HBox();
        menubar.setAlignment(Pos.CENTER);
        menubar.setPrefSize(300, 35);
        menubar.setStyle("-fx-background-color: #40407a;");

        Label menuLabel = new Label(menuName + " Menu");
        menuLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #cfcfff; -fx-font-size: 14;");
        menubar.getChildren().add(menuLabel);

        return menubar;
    }

    /**
     * Creates the close button for the bottom of the menu window.
     *
     * @return The configured HBox instance containing the close button.
     */
    protected HBox createCloseButton() {
        HBox hBox = new HBox();
        hBox.setPrefSize(300, 35);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);

        Button closeButton = new Button("X");
        closeButton.setMinSize(25, 25);
        closeButton.setMaxSize(25, 25);
        closeButton.setStyle("-fx-background-color: #40407a; -fx-text-fill: #cfcfff; -fx-font-size: 12; -fx-font-weight: bold;");
        closeButton.setOnAction(e -> menuStage.close());

        HBox.setMargin(closeButton, new Insets(0, 5, 5, 0));
        hBox.getChildren().add(closeButton);

        return hBox;
    }

    /**
     * Opens the menu window, positioning it at the center of the main application stage.
     */
    public void openWindow() {
        Stage appStage = application.getStage();
        menuStage.setX(appStage.getX() + (appStage.getWidth() - 450) / 2);
        menuStage.setY(appStage.getY() + (appStage.getHeight() - 375) / 2);
        menuStage.show();
    }

    /**
     * Gets the BorderPane layout for the menu window.
     *
     * @return The BorderPane instance.
     */
    protected BorderPane getBorderPane() {
        return borderPane;
    }
}
