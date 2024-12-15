package org.team3082.chicken_planner.UIElements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.FileManagment.AutoRoutineJSON;
import org.team3082.chicken_planner.UIElements.HiddenMenus.HiddenMenu;
import org.team3082.chicken_planner.UIElements.HiddenMenus.SettingsMenu;
import org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI.LoadMenu;
import org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI.SaveMenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Represents the menubar and individual Menus.
 */
@SuppressWarnings("unused")
public class Menubar {

    private final ChickenPlannerApplication application;
    private final HBox root;
    
    private SaveMenu saveMenu;
    private LoadMenu loadMenu;
    private SettingsMenu settingsMenu;

    /**
     * Constructor that sets up the menubar.
     * 
     * @param application The main instance of the application.
     */
    public Menubar(ChickenPlannerApplication application) {
        this.application = application;
        this.root = new HBox();
        setUpRoot();
        setUpTitleLabel();
        addSpacer();
        setUpMenuButtons();
        application.getRoot().setTop(root);
    }

    /**
     * Initializes the root layout for the menubar.
     */
    private void setUpRoot() {
        root.setPrefSize(Constants.APP_PREFERRED_WIDTH, Constants.TOPBAR_HEIGHT);
        root.setId("Menubar");
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Sets up the title label for the menubar.
     */
    private void setUpTitleLabel() {
        Label titleLabel = new Label("Chicken Planner");
        titleLabel.setId("titleLabel");
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 16));
        root.getChildren().add(titleLabel);
    }

    /**
     * Adds a spacer between the title and the menu buttons.
     */
    private void addSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        root.getChildren().add(spacer);
    }

    private void setUpMenuButtons() {
        loadMenu = new LoadMenu(application, this);
        saveMenu = new SaveMenu(application, this);
        settingsMenu = new SettingsMenu(application, this);
    }
    
    public LoadMenu getLoadMenu(){
        return loadMenu;
    }

    public HBox getRoot() {
        return root;
    }

    public SaveMenu getSaveMenu() {
        return saveMenu;
    }
}
