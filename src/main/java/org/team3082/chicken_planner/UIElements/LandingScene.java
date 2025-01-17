package org.team3082.chicken_planner.UIElements;

import java.io.File;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.Globals;
import org.team3082.chicken_planner.UIElements.Utilities.Icons;
import org.team3082.chicken_planner.UIElements.Utilities.Theme;

import javafx.geometry.Pos;
import javafx.scene.Scene;
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
        Theme.load(Globals.theme, root);

        root.getStyleClass().addAll("root", Globals.theme);
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
        HBox hBox = new HBox(96);
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
        Icons logo = new Icons();
        ImageView imageView = logo.get("chicken.svg", 256, "accent-surface");

        return imageView;
    }

    /**
     * Creates a VBox containing the text options for the landing scene.
     * Each text node is assigned an ID for styling or event handling.
     *
     * @return a VBox containing the text options.
     */
    private VBox createProjectLayout() {
        VBox projectsTextLayout = new VBox(24);
        projectsTextLayout.setAlignment(Pos.CENTER_LEFT);

        // Create and style the text nodes
        VBox titleTextLayout = new VBox(4);
        {
            Text titleText = createText("ChickenPlanner", "titleText", "title");
            Text taglineText = createText("Effortless auto planning", "taglineText", "subtitle");
            titleTextLayout.getChildren().addAll(titleText, taglineText);
        }

        VBox getStartedLayout = new VBox(12);
        {
            Text getStartedText = createText("Get Started", "getStartedText", "h1");
            VBox getStartedOptionsLayout = new VBox(8);
            {
                HBox newProjectLine = new HBox(6);
                {
                    newProjectLine.setAlignment(Pos.CENTER_LEFT);

                    Text newProjectText = createText("New project", "newProjectText", "action");
                    Icons icon = new Icons();
                    ImageView newProjectIcon = icon.get("icons/file-plus-2.svg", 14, "accent-surface");
                    newProjectLine.getChildren().addAll(newProjectIcon, newProjectText);
                }

                HBox openProjectLine = new HBox(6);
                {
                    openProjectLine.setAlignment(Pos.CENTER_LEFT);

                    Text openProjectText = createText("Open existing project", "openProjectText", "action");
                    Icons icon = new Icons();
                    ImageView openProjectIcon = icon.get("icons/file-input.svg", 14, "accent-surface");
                    openProjectLine.getChildren().addAll(openProjectIcon, openProjectText);
                }

                HBox openDocumentationLine = new HBox(6);
                {
                    openDocumentationLine.setAlignment(Pos.CENTER_LEFT);

                    Text openDocumentationText = createText("View documentation", "openDocumentationText", "action");
                    Icons icon = new Icons();
                    ImageView openDocumentationIcon = icon.get("icons/book-open.svg", 14, "accent-surface");
                    openDocumentationLine.getChildren().addAll(openDocumentationIcon, openDocumentationText);
                }

                getStartedOptionsLayout.getChildren().addAll(newProjectLine, openProjectLine, openDocumentationLine);
            }
            getStartedLayout.getChildren().addAll(getStartedText, getStartedOptionsLayout);

        }

        VBox recentProjectsLayout = new VBox(12);
        {
            Text recentProjectsText = createText("Recent Projects", "recentProjectsText", "h1");
            VBox recentProjectsOptionsLayout = new VBox(8);
            {
                Text project1Text = createText("project 1", "project1Text", "action");
                Text project2Text = createText("project 2", "project2Text", "action");
                recentProjectsOptionsLayout.getChildren().addAll(project1Text, project2Text);
            }
            recentProjectsLayout.getChildren().addAll(recentProjectsText, recentProjectsOptionsLayout);
        }

        projectsTextLayout.getChildren().addAll(titleTextLayout, getStartedLayout, recentProjectsLayout);

        // Event handling for the "Open Project" link
        getStartedLayout.lookup("#openProjectText").setOnMouseClicked(event -> openDirectoryChooser());

        return projectsTextLayout;
    }

    /**
     * Creates a Text object with the specified content, ID, and style class.
     *
     * @param content the text content to display.
     * @param id      the ID to assign to the text object for identification.
     * @return a Text object with the specified properties.
     */
    private Text createText(String content, String id, String textClass) {
        Text text = new Text(content);
        text.setId(id);
        text.getStyleClass().add(textClass);
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
