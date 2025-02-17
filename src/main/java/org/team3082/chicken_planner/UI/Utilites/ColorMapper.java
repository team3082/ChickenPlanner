package org.team3082.chicken_planner.UIElements.Utilities;

import java.awt.Color;
import java.awt.image.LookupTable;


public class ColorMapper
extends LookupTable {

    private final int[] from;
    private final int[] to;

    /**
     * Creates a ColorMapper object that allows for recoloring a BufferedImage.
     * 
     * @param from The color to change from.
     * @param to The color to change to.
     */
    public ColorMapper(Color from,
                       Color to) {
        super(0, 4);

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

    @Override
    public int[] lookupPixel(int[] src,
                             int[] dest) {
        if (dest == null) {
            dest = new int[src.length];
        }

        to[3] = src[3];

        int[] newColor = ((src[0] == from[0] && src[1] == from[1] && src[2] == from[2] && src[3] != 0) ? to : src);

        System.arraycopy(newColor, 0, dest, 0, newColor.length);

        return dest;
    }
}