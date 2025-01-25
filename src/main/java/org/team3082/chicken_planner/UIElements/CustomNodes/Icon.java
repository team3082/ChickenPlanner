package org.team3082.chicken_planner.UIElements.CustomNodes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBufferInt;
import java.awt.image.LookupOp;
import java.net.URL;
import java.nio.IntBuffer;

import org.team3082.chicken_planner.UIElements.Utilities.ColorMapper;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.geometry.size.FloatSize;
import com.github.weisj.jsvg.parser.SVGLoader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Icon extends StackPane {
    private final ImageView imageView;
    private final Rectangle colorChecker;
    private final BufferedImage orginalImage;

    public Icon(String path, int size, String colorVariable){
        imageView = new ImageView();

        colorChecker = getColorChecker(colorVariable);
        orginalImage = getOrginalImage(path);
        
        colorChecker.fillProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(getColorImage());
        });

        imageView.setImage(getImage(orginalImage));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        getChildren().add(imageView);
    }

    public BufferedImage getOrginalImage(String path){
        SVGLoader loader = new SVGLoader();
        URL svgUrl = getClass().getResource("/" + path);
        SVGDocument svgDocument = loader.load(svgUrl);
        FloatSize size = svgDocument == null ? new FloatSize(0, 0) : svgDocument.size();
        return new BufferedImage((int) size.width, (int) size.height, BufferedImage.TYPE_INT_ARGB);
    }

    public Rectangle getColorChecker(String colorVariable){
        Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        rectangle.setStyle("-fx-background-color: " + colorVariable + ";"); 
        rectangle.setManaged(false);
        rectangle.setVisible(false);
        getChildren().add(rectangle);
        return rectangle;
    }

    public Image getColorImage(){
        Graphics2D g = orginalImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.dispose();

        // Replaces color to themed-color

        java.awt.Color from = java.awt.Color.decode("#000000");
        Color color = (Color) colorChecker.getFill();
        java.awt.Color to = new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(),
                (float) color.getOpacity());
        
        BufferedImageOp lookup = new LookupOp(new ColorMapper(from, to), null);
        BufferedImage convertedImage = lookup.filter(orginalImage, null);

        return getImage(convertedImage);
    }

    /**
     * Converts a BufferedImage (from the SVGLoader class) into an Image object.
     * 
     * @return an Image with the BufferedImage data.
     */
    private Image getImage(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        newImg.createGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        int[] type_int_agrb = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
        IntBuffer buffer = IntBuffer.wrap(type_int_agrb);
        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(newImg.getWidth(), newImg.getHeight(), buffer,
                pixelFormat);
        return new WritableImage(pixelBuffer);
    }
}
