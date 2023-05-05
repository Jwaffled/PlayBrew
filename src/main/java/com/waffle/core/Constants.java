package com.waffle.core;

/**
 * A set of constants used within the PlayBrew engine
 */
public final class Constants {
    /**
     * For internal use only
     */
    public static final boolean DEBUG_MODE = false;
    /**
     * Enum representing the mode to draw a geometric shape in<br>
     * Filled - Fills this shape a solid color<br>
     * Outline - Only outlines this shape<br>
     * Filled Border - Fills the shape AND produces an outline on the border of the shape
     */
    public enum DrawMode {
        FILLED,
        OUTLINE,
        FILLED_BORDER;

        public String toString() {
            switch(this) {
                case FILLED -> {
                    return "Filled";
                }
                case OUTLINE -> {
                    return "Outline";
                }
                case FILLED_BORDER -> {
                    return "Filled Border";
                }
                default -> {
                    return "UNKNOWN DRAW MODE";
                }
            }
        }
    }

    /**
     * Enum representing the options for a shape
     */
    public enum ShapeType {
        RECTANGLE,
        ELLIPSE;

        public String toString() {
            switch(this) {
                case RECTANGLE -> {
                    return "Rectangle";
                }
                case ELLIPSE -> {
                    return "Ellipse";
                }
                default -> {
                    return "UNKNOWN SHAPE";
                }
            }
        }
    }

    /**
     * The global logger for PlayBrew games
     */
    public final static Logger LOGGER = new Logger();

}
