package org.team3082.chicken_planner.UIElements.Utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;
import java.awt.image.LookupOp;
import java.net.URL;
import java.nio.IntBuffer;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.parser.SVGLoader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

public class Icon {
    public ImageView get(String path, int width, String color) {
        // Loads the actual SVG document from resources.
        SVGLoader loader = new SVGLoader();
        var svgUrl = getClass().getResource("/" + path);
        SVGDocument svgDocument = loader.load(svgUrl);

        // Writes the SVG content to a BufferedImage.
        var size = svgDocument.size();
        BufferedImage image = new BufferedImage((int) size.width, (int) size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        svgDocument.render(null, g);
        g.dispose();

        // Replaces color to themed-color
        
        java.awt.Color from = java.awt.Color.decode("#000000");
        java.awt.Color to = java.awt.Color.decode(Theme.current.get(color));
        BufferedImageOp lookup = new LookupOp(new ColorMapper(from, to), null);
        BufferedImage convertedImage = lookup.filter(image, null);
         

        // Converts the BufferedImage to an Image and returns the ImageView.
        ImageView imageView = new ImageView(getImage(convertedImage));
        imageView.setFitWidth(width);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Converts a BufferedImage (from the SVGLoader class) into an Image object.
     * 
     * @return an Image with the BufferedImage data.
     */
    @SuppressWarnings("unused")
    private IndexColorModel createColorModel(Color newColor) {
        int size = 256;

        byte[] r = new byte[size];
        byte[] g = new byte[size];
        byte[] b = new byte[size];
        byte[] a = new byte[size];

        for (int i = 0; i < size; i++) {
            r[i] = (byte) newColor.getRed();
            g[i] = (byte) newColor.getGreen();
            b[i] = (byte) newColor.getBlue();
            a[i] = (byte) i;
        }

        return new IndexColorModel(16, size, r, g, b, a);
    }

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
}
