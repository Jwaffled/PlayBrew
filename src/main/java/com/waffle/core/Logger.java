package com.waffle.core;

import java.awt.*;

/**
 * A class used for logging different colors and different severity levels
 */
public class Logger {
    /**
     * Logs a message in stdout with a specified LogLevel
     * @param message the message to log
     * @param level the level to log the message under
     */
    public void log(Object message, LogLevel level) {
        switch(level) {
            case DEBUG -> System.out.println(color("[DEBUG]: " + message, Color.CYAN));
            case INFO -> System.out.println(color("[INFO]: " + message, Color.CYAN));
            case WARNING -> System.out.println(color("[WARNING]: " + message, Color.YELLOW));
            case SEVERE -> System.out.println(color("[SEVERE]: " + message, Color.RED));
            case FATAL -> System.out.println(color("[FATAL]: " + message, Color.RED));
        }
    }

    /**
     * Logs a message in stdout at LogLevel.INFO
     * @param message the message to log
     */
    public void logInfo(Object message) {
        log(message, LogLevel.INFO);
    }

    /**
     * Logs a message in stdout at LogLevel.DEBUG
     * @param message the message to log
     */
    public void logDebug(Object message) {
        log(message, LogLevel.DEBUG);
    }

    /**
     * Logs a message in stdout at LogLevel.WARNING
     * @param message the message to log
     */
    public void logWarning(Object message) {
        log(message, LogLevel.WARNING);
    }

    /**
     * Logs a message in stdout at LogLevel.SEVERE<br>
     * Usually denotes a non-fatal error
     * @param message the message to log
     */
    public void logSevere(Object message) {
        log(message, LogLevel.SEVERE);
    }

    /**
     * Logs a message in stdout at LogLevel.FATAL<br>
     * Usually denotes an unrecoverable error
     * @param message
     */
    public void logFatal(Object message) {
        log(message, LogLevel.FATAL);
    }

    /**
     * Logs an exception with a specified LogLevel
     * @param e the exception to log
     * @param level the level to log at
     */
    public void logException(Exception e, LogLevel level) {
        String msg = e.getMessage();
        StringBuilder st = new StringBuilder(msg + "\n");
        for(StackTraceElement s : e.getStackTrace()) {
            st.append("\tat ");
            st.append(s.toString());
            st.append('\n');
        }
        log(st.toString(), level);
    }

    private String color(String s, Color c) {
        String color = "";
        if (c.equals(Color.BLACK)) {
            color = "\u001B[30m";
        } else if (c.equals(Color.RED)) {
            color = "\u001B[31m";
        } else if (c.equals(Color.GREEN)) {
            color = "\u001B[32m";
        } else if (c.equals(Color.YELLOW)) {
            color = "\u001B[33m";
        } else if (c.equals(Color.BLUE)) {
            color = "\u001B[34m";
        } else if (c.equals(Color.MAGENTA)) {
            color = "\u001B[35m";
        } else if (c.equals(Color.CYAN)) {
            color = "\u001B[36m";
        } else if (c.equals(Color.WHITE)) {
            color = "\u001B[37m";
        }
        return color + s + "\u001B[0m";
    }
}
