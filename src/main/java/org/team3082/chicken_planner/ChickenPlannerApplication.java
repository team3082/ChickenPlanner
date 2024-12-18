package org.team3082.chicken_planner;

import org.team3082.chicken_planner.AppState.AppState;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.TrajectoryManager;
import org.team3082.chicken_planner.FileManagment.ProjectLoader;
import org.team3082.chicken_planner.UIElements.Field;
import org.team3082.chicken_planner.UIElements.Menubar;
import org.team3082.chicken_planner.UIElements.Sidebar.Sidebar;

import edu.wpi.first.math.trajectory.TrajectoryConfig;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class for the Chicken Planner application.
 * Handles initialization of the UI and core components.
 */
public class ChickenPlannerApplication extends Application {

    // Core UI components
    private Stage stage;
    private BorderPane root;
    private Field field;
    private Menubar menubar;
    private Sidebar sidebar;

    // Application state and managers
    private TrajectoryManager trajectoryControlManager;
    private AppState appState;
    private ProjectLoader projectLoader;

    // Scene for the main window
    private Scene scene;

    private VBox splashScreen;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.appState = new AppState();

        // Initialize the root
        root = initMainBorderPanel();

        // Create and configure the main scene
        scene = new Scene(root, Constants.APP_PREFERRED_WIDTH, Constants.APP_PREFERRED_HEIGHT);
        configureScene();

        // Initialize main UI components
        menubar = new Menubar(this);
        field = new Field(this);
        
        // Set up the main stage
        stage.setScene(scene);
        stage.setTitle("Chicken Planner 2024");
        stage.show();

        // Initialize supporting components
        trajectoryControlManager = new TrajectoryManager(this);
        projectLoader = new ProjectLoader(this);

        sidebar = new Sidebar(this);

        // Display the splash screen
        setUpSplashScreen();
    }
    
    /**
     * Configures the scene by applying stylesheets and setting the application icon.
     */
    private void configureScene() {
        scene.getStylesheets().add(ChickenPlannerApplication.class.getResource("/MainPage.css").toExternalForm());
        stage.getIcons().add(new Image(ChickenPlannerApplication.class.getResource("/AppIcon.png").toExternalForm()));
    }

    /**
     * Initializes the root BorderPane layout with preferred dimensions.
     *
     * @return a BorderPane layout for the main window
     */
    private BorderPane initMainBorderPanel() {
        BorderPane root = new BorderPane();
        root.setPrefSize(Constants.APP_PREFERRED_WIDTH, Constants.APP_PREFERRED_HEIGHT);
        root.setId("mainBorderPanel"); // Set an ID for CSS styling
        return root;
    }

    /**
     * Sets up the splash screen displayed at application startup.
     */
    private void setUpSplashScreen() {
        splashScreen = new VBox();
        splashScreen.setAlignment(Pos.CENTER);

        // Display the application logo
        ImageView imageView = new ImageView(ChickenPlannerApplication.class.getResource("/Logo.PNG").toExternalForm());
        imageView.fitHeightProperty().bind(root.heightProperty().subtract(Constants.TOPBAR_HEIGHT + 125));
        imageView.setPreserveRatio(true);
        splashScreen.getChildren().add(imageView);

        // Add a label and load menu button
        Label label = new Label("Load a WPLIB Project");
        splashScreen.getChildren().add(label);
        label.setStyle("-fx-font-weight: bold;"+
                       "-fx-text-fill: #32337c;"+
                       "-fx-font-size: 18;"+
                       "-fx-text-alignment: center;"
                    );

        Button menuButton = new Button("Load Menu");
        menuButton.setOnAction(e -> menubar.getLoadMenu().openWindow());
        splashScreen.getChildren().add(menuButton);
        menuButton.getStyleClass().add("menuButtons");
        menuButton.setStyle("-fx-background-color: #32337c;"+
                            "-fx-font-size: 14;"+
                            "-fx-font-weight: bold;"+
                            "-fx-text-fill: #cfcfff;"
                            );

        root.setCenter(splashScreen);
    }

    public void setSplash(){
        root.setCenter(splashScreen);
    }

    // Getter methods for core components
    public Stage getStage() {
        return stage;
    }

    public BorderPane getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public Menubar getMenubar() {
        return menubar;
    }

    public Field getField() {
        return field;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public AppState getAppState() {
        return appState;
    }

    public TrajectoryManager getTrajectoryManager() {
        return trajectoryControlManager;
    }

    public ProjectLoader getProjectLoader() {
        return projectLoader;
    }
}
