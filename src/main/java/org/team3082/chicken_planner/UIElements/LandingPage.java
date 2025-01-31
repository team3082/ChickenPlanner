package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.Globals;
import org.team3082.chicken_planner.UIElements.CustomNodes.Icon;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Represents the landing scene for the Chicken Planner application.
 * Displays a logo and various project links for user interaction.
 */
public class LandingPage extends VBox {
    private final Stage stage;

    /**
     * Constructs the LandingScene with specified dimensions from Constants.
     * Initializes the layout and populates the scene with components.
     */
    public LandingPage(Stage stage) {
        super();
        this.stage = stage; // Keep a reference to the stage
        
        HBox hBox = createContentLayout();
        getChildren().add(hBox);
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

        Icon logo = new Icon("assets/chicken.svg", 256, "-fx-accent-surface");
        hBox.getChildren().add(logo);

        VBox projectsTextLayout = createProjectLayout();
        hBox.getChildren().add(projectsTextLayout);

        return hBox;
    }

    /**
     * Creates a VBox containing the text options for the landing scene.
     * Each text node is assigned an ID for styling or event handling.
     *
     * @return a VBox containing the text options.
     */
    private VBox createProjectLayout() {
        // Creates the main text layout that will be to the right of the chicken logo
        VBox projectsTextLayout = new VBox(24);
        projectsTextLayout.setAlignment(Pos.CENTER_LEFT);

        // Creates the title block that includes the title and tagline
        VBox titleTextLayout = new VBox(4);
        {
            Text titleText = createText("ChickenPlanner", "titleText", "title");
            Text taglineText = createText("Effortless auto planning", "taglineText", "subtitle");
            titleTextLayout.getChildren().addAll(titleText, taglineText);
        }

        // Creates the get started controls
        VBox getStartedLayout = new VBox(12);
        {
            Text getStartedText = createText("Get Started", "getStartedText", "h1");
            VBox getStartedOptionsLayout = new VBox(8);
            {
                HBox newProjectLine = new HBox(6);
                {
                    newProjectLine.setAlignment(Pos.CENTER_LEFT);

                    Text newProjectText = createText("New project", "newProjectText", "action");
                    Icon newProjectIcon = new Icon("icons/file-plus-2.svg", 14, "-fx-accent-surface");
                    newProjectLine.getChildren().addAll(newProjectIcon, newProjectText);
                }

                HBox openProjectLine = new HBox(6);
                {
                    openProjectLine.setAlignment(Pos.CENTER_LEFT);

                    Text openProjectText = createText("Open existing project", "openProjectText", "action");
                    Icon openProjectIcon = new Icon("icons/file-input.svg", 14, "-fx-accent-surface");
                    openProjectLine.getChildren().addAll(openProjectIcon, openProjectText);
                }

                HBox openDocumentationLine = new HBox(6);
                {
                    openDocumentationLine.setAlignment(Pos.CENTER_LEFT);

                    Text openDocumentationText = createText("View documentation", "openDocumentationText", "action");
                    Icon openDocumentationIcon = new Icon("icons/book-open.svg", 14, "-fx-accent-surface");
                    openDocumentationLine.getChildren().addAll(openDocumentationIcon, openDocumentationText);
                }

                getStartedOptionsLayout.getChildren().addAll(newProjectLine, openProjectLine, openDocumentationLine);
            }
            getStartedLayout.getChildren().addAll(getStartedText, getStartedOptionsLayout);

        }

        // Creates the recent projects controls
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

        // Adds each layout to the main right-aligned layout
        projectsTextLayout.getChildren().addAll(titleTextLayout, getStartedLayout, recentProjectsLayout);

        // Event handling for the "Open Project" link
        getStartedLayout.lookup("#openProjectText").setOnMouseClicked(_ -> openDirectoryChooser());

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

        if(Globals.themeProperty.getValue().equals("tokyo")){
            Globals.themeProperty.set("catppuccinLatte");
        } else {
            Globals.themeProperty.set("tokyo");
        }

    //     DirectoryChooser directoryChooser = new DirectoryChooser();
    //     directoryChooser.setTitle("Open Project Directory");

    //     // Optional: Set an initial directory if needed
    //     File initialDirectory = new File(System.getProperty("user.home"));
    //     directoryChooser.setInitialDirectory(initialDirectory);

    //     // Show the directory chooser and capture the selected directory
    //     File selectedDirectory = directoryChooser.showDialog(stage);

    //     if (selectedDirectory != null) {
    //         // Handle the selected directory
    //         System.out.println("Directory selected: " + selectedDirectory.getAbsolutePath());
    //     } else {
    //         // Handle the case when no directory is selected (dialog is closed without
    //         // selection)
    //         System.out.println("No directory selected");
    //     }
    }
}
