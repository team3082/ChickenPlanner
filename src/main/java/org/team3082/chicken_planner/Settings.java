package org.team3082.chicken_planner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Settings {
    //Singleton
    private static Settings instance;
    
    //File Loading
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String settingsFileString = Paths.get(System.getProperty("user.home"), "Documents", "ChickenPlannerSettings.json").toString();

    //Settings
    private ArrayList<String> pastProjects;
    private String colorTheme;

    private Settings() {
        this.pastProjects = new ArrayList<>();
        this.colorTheme = "light";
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = loadFromFile();
        }
        return instance;
    }

    public ArrayList<String> getPastProjects() {
        return pastProjects;
    }

    public void addProject(String project) {
        for(String pastProject : pastProjects){
            if(pastProject.equals(project)) return;
        }
        pastProjects.add(0, project);
        saveToFile();
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    private static Settings loadFromFile() {
        File settingsFile = new File(settingsFileString);

        if (settingsFile.exists()) {
            try (FileReader reader = new FileReader(settingsFile)) {
                Type type = new TypeToken<Settings>() {}.getType();
                return GSON.fromJson(reader, type);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return new Settings();
    }

    public void saveToFile() {
        File settingsFile = new File(settingsFileString);

        try (FileWriter writer = new FileWriter(settingsFile)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
