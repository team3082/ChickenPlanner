package org.team3082.chicken_planner.UI.Main;

import java.io.File;
import java.util.ArrayList;

import org.team3082.chicken_planner.Settings;
import org.team3082.chicken_planner.State.EventBus;
import org.team3082.chicken_planner.State.Events.Event;
import org.team3082.chicken_planner.State.Events.ProjectLoadRequest;
import org.team3082.chicken_planner.State.Events.ProjectLoadedEvent;
import org.team3082.chicken_planner.UI.Components.Icon;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
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
                HBox openProjectLine = new HBox(6);
                {
                    openProjectLine.setAlignment(Pos.CENTER_LEFT);

                    Text openProjectText = createText("Open project", "openProjectText", "action");
                    Icon openProjectIcon = new Icon("icons/file-input.svg", 14, "-fx-accent-surface");
                    openProjectLine.getChildren().addAll(openProjectIcon, openProjectText);
                }

                openProjectLine.setOnMouseClicked((MouseEvent event) -> {
                    // Create a directory chooser dialog
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("Select Project Directory");
        
                    // Set the initial directory (optional)
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
                    // Show the directory chooser dialog and wait for the user to select a directory
                    File selectedDirectory = directoryChooser.showDialog(stage);
        
                    // If the user selected a directory, fire the ProjectLoadRequest event
                    if (selectedDirectory != null) {
                        EventBus.fireEvent(new ProjectLoadRequest(selectedDirectory));
                    }
                });

                HBox openDocumentationLine = new HBox(6);
                {
                    openDocumentationLine.setAlignment(Pos.CENTER_LEFT);

                    Text openDocumentationText = createText("View documentation", "openDocumentationText", "action");
                    Icon openDocumentationIcon = new Icon("icons/book-open.svg", 14, "-fx-accent-surface");
                    openDocumentationLine.getChildren().addAll(openDocumentationIcon, openDocumentationText);
                }

                getStartedOptionsLayout.getChildren().addAll(openProjectLine, openDocumentationLine);
            }
            getStartedLayout.getChildren().addAll(getStartedText, getStartedOptionsLayout);

        }

        // Creates the recent projects controls
        VBox recentProjectsLayout = new VBox(12);
        {
            Text recentProjectsText = createText("Recent Projects", "recentProjectsText", "h1");
            VBox recentProjectsOptionsLayout = new VBox(8);
            {
                ArrayList<String> pastProjects = Settings.getInstance().getPastProjects();
                int maxLength = 5;

                for(String pastProject : pastProjects){
                    File file = new File(pastProject);
                    if(!file.exists()){
                        continue;
                    }
                    Text projectText = createText(file.getName(), "project1Text", "action");
                    recentProjectsOptionsLayout.getChildren().add(projectText);
                    
                    maxLength--;
                    if(maxLength < 1){
                        break;
                    }
                }
            }
            recentProjectsLayout.getChildren().addAll(recentProjectsText, recentProjectsOptionsLayout);

            EventBus.register((Event event) -> {
                switch (event) {
                    case ProjectLoadedEvent projectLoadedEvent -> {
                        recentProjectsOptionsLayout.getChildren().clear();
                        ArrayList<String> pastProjects = Settings.getInstance().getPastProjects();
                        int maxLength = 5;

                        for(String pastProject : pastProjects){
                            File file = new File(pastProject);
                            if(!file.exists()){
                                continue;
                            }
                            Text projectText = createText(file.getName(), "project1Text", "action");
                            HBox projectTextLine = new HBox(projectText);
                            projectTextLine.setPadding(new Insets(5, 5, 5, 5));
                            projectTextLine.setOnMouseClicked((MouseEvent mouseEvent) -> {
                                EventBus.fireEvent(new ProjectLoadRequest(file));
                            });

                            recentProjectsOptionsLayout.getChildren().add(projectTextLine);
                            
                            maxLength--;
                            if(maxLength < 1){
                                break;
                            }
                        }   
                    }
                    default -> {}
                    
                }
            });
        }

        // Adds each layout to the main right-aligned layout
        projectsTextLayout.getChildren().addAll(titleTextLayout, getStartedLayout, recentProjectsLayout);

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
}
