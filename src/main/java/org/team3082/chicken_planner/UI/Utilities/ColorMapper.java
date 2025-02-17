package org.team3082.chicken_planner.UI.Utilities;

import java.awt.Color;
import java.awt.image.LookupTable;

/**
 * The {@code ColorMapper} class extends {@code LookupTable} and provides a way to map a specific color 
 * (in the form of a {@link Color} object) to another color. This is useful for recoloring images by 
 * replacing one color with another.
 * <p>
 * The primary purpose of this class in this codebase is for recoloring of SVGs
 * </p>
 */
public class ColorMapper extends LookupTable {

    private final int[] from; // The color to change from (RGB + Alpha)
    private final int[] to;   // The color to change to (RGB + Alpha)

    /**
     * Constructs a new {@code ColorMapper} that will map one color to another.
     * <p>
     * The color values are represented in terms of RGBA (Red, Green, Blue, Alpha).
     * </p>
     *
     * @param from The {@link Color} to change from. This will be replaced with {@code to}.
     * @param to The {@link Color} to change to. This color will replace {@code from} in the image.
     */
    public ColorMapper(Color from, Color to) {
        super(0, 4); // Initializes the LookupTable with a size of 0 and 4 components (RGBA)

        // Initialize 'from' and 'to' arrays based on the color values (RGBA)
        this.from = new int[] {
            from.getRed(),
            from.getGreen(),
            from.getBlue(),
            from.getAlpha(),
        };
        this.to = new int[] {
            to.getRed(),
            to.getGreen(),
            to.getBlue(),
            to.getAlpha(),
        };
    }

    /**
     * Transforms a source pixel array by mapping the specified color to the target color.
     * <p>
     * If the source pixel matches the color to be replaced, it is replaced with the target color.
     * Otherwise, the pixel is left unchanged.
     * </p>
     *
     * @param src The source pixel array (typically from an image), which contains RGBA values.
     * @param dest The destination pixel array to store the result (can be {@code null} to use a new array).
     * @return The transformed pixel array with the color mapping applied.
     */
    @Override
    public int[] lookupPixel(int[] src, int[] dest) {
        if (dest == null) {
            // Create a new array if the destination is null
            dest = new int[src.length];
        }

        // Preserve the alpha value from the source pixel
        to[3] = src[3];

        // If the source pixel matches the 'from' color, replace it with the 'to' color
        int[] newColor = ((src[0] == from[0] && src[1] == from[1] && src[2] == from[2] && src[3] != 0) ? to : src);

        // Copy the resulting pixel values to the destination array
        System.arraycopy(newColor, 0, dest, 0, newColor.length);

        return dest;
    }
}
