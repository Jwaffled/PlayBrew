package com.waffle.core;

import java.awt.*;

public class Logger {
    public void log(String message, LogLevel level) {
        switch(level) {
            case DEBUG -> System.out.println(color("[DEBUG]: " + message, Color.CYAN));
            case INFO -> System.out.println(color("[INFO]: " + message, Color.CYAN));
            case WARNING -> System.out.println(color("[WARNING]: " + message, Color.YELLOW));
            case SEVERE -> System.out.println(color("[SEVERE]: " + message, Color.RED));
            case FATAL -> System.out.println(color("[FATAL]: " + message, Color.RED));
        }
    }

    public void logInfo(String message) {
        log(message, LogLevel.INFO);
    }

    public void logDebug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public void logWarning(String message) {
        log(message, LogLevel.WARNING);
    }

    public void logSevere(String message) {
        log(message, LogLevel.SEVERE);
    }

    public void logFatal(String message) {
        log(message, LogLevel.FATAL);
    }

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
