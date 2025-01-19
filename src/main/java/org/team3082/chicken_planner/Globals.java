package org.team3082.chicken_planner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Globals {
    private static StringProperty themeProperty = new SimpleStringProperty("dracula");
    public static String theme = themeProperty.get();
    // theme can be dark. light, og, or dracula
}
