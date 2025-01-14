package org.team3082.chicken_planner.UIElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBufferInt;
import java.awt.image.LookupOp;
import java.io.File;
import java.net.URL;
import java.nio.IntBuffer;

import org.team3082.chicken_planner.UIElements.Utilities.Theme;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.Globals;
import org.team3082.chicken_planner.UIElements.Utilities.ColorMapper;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.SVGLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
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
        // Loads the actual SVG document from resources.
        SVGLoader loader = new SVGLoader();
        URL svgUrl = getClass().getResource("/chicken.svg");
        SVGDocument svgDocument = loader.load(svgUrl);

        // Writes the SVG content to a BufferedImage.
        var size = svgDocument.size();
        BufferedImage image = new BufferedImage((int) size.width, (int) size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        svgDocument.render(null, g);
        g.dispose();

        Color from = Color.decode("#000000");
        Color to = Color.decode(Theme.current.get("accent"));
        BufferedImageOp lookup = new LookupOp(new ColorMapper(from, to), null);
        BufferedImage convertedImage = lookup.filter(image, null);

        // Converts the BufferedImage to an Image and returns the ImageView.
        ImageView imageView = new ImageView(getImage(convertedImage));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Converts a BufferedImage (from the SVGLoader class) into an Image object.
     * TODO: Emilio move this somewhere else? we could have a utils class that we
     * access throughout
     * 
     * @return an Image with the BufferedImage data.
     */
    private Image getImage(BufferedImage img) {
        // converting to a good type, read about types here:
        // https://openjfx.io/javadoc/13/javafx.graphics/javafx/scene/image/PixelBuffer.html
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        newImg.createGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

        // converting the BufferedImage to an IntBuffer
        int[] type_int_agrb = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
        IntBuffer buffer = IntBuffer.wrap(type_int_agrb);

        // converting the IntBuffer to an Image, read more about it here:
        // https://openjfx.io/javadoc/13/javafx.graphics/javafx/scene/image/PixelBuffer.html
        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer(newImg.getWidth(), newImg.getHeight(), buffer,
                pixelFormat);
        return new WritableImage(pixelBuffer);
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
