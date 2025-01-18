package org.team3082.chicken_planner.UIElements.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;

import static java.util.Map.entry;

public class Theme {
    static Map<String, String> dark = Map.ofEntries(
            entry("surface", "#121019"),
            entry("secondary-surface", "#1F1B2B"),
            entry("transparent-surface", "#121019B3"),
            entry("text", "#F7F7F7"),
            entry("secondary-text", "#BDBDBD"),
            entry("error", "#FF4A4A"),
            entry("accent", "#688CDB"),
            entry("inactive", "#625C75"),
            entry("overlay", "#12101933"),
            entry("accent-surface", "#688CDB"));

    static Map<String, String> light = Map.ofEntries(

            entry("surface", "#F7F7F7"),
            entry("secondary-surface", "#E9E9E9"),
            entry("transparent-surface", "#F7F7F766"),
            entry("text", "#1C1B1F"),
            entry("secondary-text", "#3F3D45"),
            entry("error", "#FF4A4A"),
            entry("accent", "#32327C"),
            entry("inactive", "#9C96AE"),
            entry("overlay", "#F7F7F733"),
            entry("accent-surface", "#32327C"));

    static Map<String, String> og = Map.ofEntries(

            entry("surface", "#32327C"),
            entry("secondary-surface", "#807FFF"),
            entry("transparent-surface", "#121019B3"),
            entry("text", "#F7F7F7"),
            entry("secondary-text", "#E5E5E5"),
            entry("error", "#FF4A4A"),
            entry("accent", "#6D6DD2"),
            entry("inactive", "#807FFF"),
            entry("overlay", "#12101933"),
            entry("accent-surface", "#32327C"));

    static Map<String, String> dracula = Map.ofEntries(
            entry("surface", "#282A36"),
            entry("secondary-surface", "#44475A"),
            entry("transparent-surface", "#282A36B3"),
            entry("text", "#F8F8F2"),
            entry("secondary-text", "#CCC4C4"),
            entry("error", "#FF4A4A"),
            entry("accent", "#FF79C6"),
            entry("inactive", "#44475A"),
            entry("overlay", "#282A3633"),
            entry("accent-surface", "#FF79C6"));

    static Map<String, String> github = Map.ofEntries(
            entry("surface", "#0D1117"),
            entry("secondary-surface", "#161B22"),
            entry("transparent-surface", "#0D1117B3"),
            entry("text", "#ECF2F8"),
            entry("secondary-text", "#C6CDD5"),
            entry("error", "#FF4A4A"),
            entry("accent", "#77BDFB"),
            entry("inactive", "#21262D"),
            entry("overlay", "#0D111733"),
            entry("accent-surface", "#77BDFB"));

    static Map<String, String> tokyo = Map.ofEntries(
            entry("surface", "#1a1b26"),
            entry("secondary-surface", "#24283b"),
            entry("transparent-surface", "#1a1b26B3"),
            entry("text", "#a9b1d6"),
            entry("secondary-text", "#9aa5ce"),
            entry("error", "#f7768e"),
            entry("accent", "#a754ff"),
            entry("inactive", "#414868"),
            entry("overlay", "#1a1b2633"),
            entry("accent-surface", "#a754ff"));

    public static Map<String, String> current = light;

    public static void load(String theme, Pane root) {
        if (theme.equals("dark")) {
            current = dark;
        }
        if (theme.equals("light")) {
            current = light;
        }
        if (theme.equals("og")) {
            current = og;
        }
        if (theme.equals("dracula")) {
            current = dracula;
        }
        if (theme.equals("github")) {
            current = github;
        }
        if (theme.equals("tokyo")) {
            current = tokyo;
        }
        current.forEach((k, v) -> {
            root.setStyle("-fx-" + k + " : " + v + ";");
        });
    }

}