package org.team3082.chicken_planner.UIElements;

import java.io.File;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.Globals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Represents the landing scene for the Chicken Planner application.
 * Displays a logo and various project links for user interaction.
 */
public class LandingScene extends Scene {
    private final VBox root;
    private final Stage stage;

    /**
     * Constructs the LandingScene with specified dimensions from Constants.
     * Initializes the layout and populates the scene with components.
     */
    public LandingScene(Stage stage) {
        super(new VBox(), Constants.UI.WINDOW_WIDTH, Constants.UI.WINDOW_HEIGHT);
        this.stage = stage; // Keep a reference to the stage

        root = (VBox) getRoot();

        // root.getStyleClass().addAll("root");
        root.setAlignment(Pos.CENTER_RIGHT);

        HBox hBox = createContentLayout();
        root.getChildren().add(hBox);
    }

    /**
     * Creates the main content layout for the landing scene.
     * This includes the logo and the project options.
     *
     * @return an HBox containing the content layout.
     */
    private HBox createContentLayout() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        ImageView logoImageView = createLogoImageView();
        hBox.getChildren().add(logoImageView);

        VBox projectsTextLayout = createProjectLayout();
        hBox.getChildren().add(projectsTextLayout);

        return hBox;
    }

    /**
     * Creates the ImageView for the Chicken Planner logo.
     * Configures the image size and ensures the aspect ratio is preserved.
     *
     * @return an ImageView containing the logo image.
     */
    private ImageView createLogoImageView() {
        Image chickenLogoImage = new Image(getClass().getResource("/themes/" + Globals.theme + "/chicken.png").toExternalForm());
        ImageView imageView = new ImageView(chickenLogoImage);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Creates a VBox containing the text options for the landing scene.
     * Each text node is assigned an ID for styling or event handling.
     *
     * @return a VBox containing the text options.
     */
    private VBox createProjectLayout() {
        VBox projectsTextLayout = new VBox();
        projectsTextLayout.setAlignment(Pos.CENTER_LEFT);

        // Create and style the text nodes
        Text titleText = createText("ChickenPlanner", "titleText");
        Text taglineText = createText("Effortless Auto Planning", "taglineText");
        Text getStartedText = createText("Get Started", "getStartedText");
        Text openProjectText = createText("Open Project", "openProjectText");
        VBox.setMargin(openProjectText, new Insets(0, 0, 0, 20));
        Text openDocumentationText = createText("Open Documentation", "openDocumentationText");
        VBox.setMargin(openDocumentationText, new Insets(0, 0, 0, 20));
        Text recentProjectsText = createText("Recent Projects", "recentProjectsText");

        projectsTextLayout.getChildren().addAll(
                titleText,
                taglineText,
                getStartedText,
                openProjectText,
                openDocumentationText,
                recentProjectsText);

        // Event handling for the "Open Project" link
        openProjectText.setOnMouseClicked(event -> openDirectoryChooser());

        return projectsTextLayout;
    }

    /**
     * Creates a Text object with the specified content, ID, and style class.
     *
     * @param content    the text content to display.
     * @param id         the ID to assign to the text object for identification.
     * @param styleClass the CSS style class to apply to the text object.
     * @return a Text object with the specified properties.
     */
    private Text createText(String content, String id) {
        Text text = new Text(content);
        text.setId(id);
        return text;
    }

    /**
     * Opens a DirectoryChooser dialog when "Open Project" is clicked.
     */
    private void openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Project Directory");

        // Optional: Set an initial directory if needed
        File initialDirectory = new File(System.getProperty("user.home"));
        directoryChooser.setInitialDirectory(initialDirectory);

        // Show the directory chooser and capture the selected directory
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            // Handle the selected directory
            System.out.println("Directory selected: " + selectedDirectory.getAbsolutePath());
        } else {
            // Handle the case when no directory is selected (dialog is closed without
            // selection)
            System.out.println("No directory selected");
        }
    }
}
