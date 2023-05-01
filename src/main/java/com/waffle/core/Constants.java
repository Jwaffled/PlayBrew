package com.waffle.core;

public final class Constants {
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

    public final static Logger LOGGER = new Logger();

}
