package org.team3082.chicken_planner.FileManagment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AppState.AppState;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handles loading and saving ChickenPlanner projects in WPLIB project directories.
 */
public class ProjectLoader {
    private final ChickenPlannerApplication application;

    /**
     * Constructs a new ProjectLoader.
     *
     * @param application The application instance associated with this loader.
     */
    public ProjectLoader(ChickenPlannerApplication application) {
        this.application = application;
    }

    /**
     * Loads a WPLIB project folder and sets up the ChickenPlanner environment.
     *
     * @param folder The WPLIB project folder to load.
     * @return True if the folder is a valid WPLIB project, false otherwise.
     */
    public boolean loadWPLIBFolder(File folder) {
        if (folder == null) {
            throw new IllegalArgumentException("Folder cannot be null.");
        }

        // Check if the folder is a valid WPLIB project.
        if (doesFileExist(folder, "/.wpilib")) {
            System.out.println("Valid WPLIB project detected.");

            // Create ChickenPlanner folder if it's missing.
            createChickenPlannerFolderIfMissing(folder);

            // Update application state and UI.
            application.getStage().setTitle("ChickenPlanner 2024 - " + folder.getName());
            application.getAppState().setProjectPath(folder.toString() );

            // Load JSON files from the ChickenPlanner folder.
            File[] jsonFiles = new File(application.getAppState().getProjectPath()+"/src/main/deploy/ChickenPlanner")
                    .listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

            if (jsonFiles != null && jsonFiles.length > 0) {
                ArrayList<AutoRoutine> autoRoutines = new ArrayList<>();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                for (File file : jsonFiles) {
                    try (FileReader reader = new FileReader(file)) {
                        AutoRoutineJSON routine = gson.fromJson(reader, AutoRoutineJSON.class);
                        autoRoutines.add(new AutoRoutine(routine));
                        System.out.println("Processed: " + file.getName());
                    } catch (IOException e) {
                        System.err.println("Error processing file " + file.getName() + ": " + e.getMessage());
                    }
                }

                application.getMenubar().getLoadMenu().showRoutines(autoRoutines);
                application.getAppState().setLoadedRoutines(autoRoutines);
            } else {
                application.getMenubar().getLoadMenu().showRoutines(new ArrayList<>());
            }

            return true;
        }

        System.out.println("Invalid WPLIB project folder.");
        return false;
    }

    /**
     * Saves the current routine to a JSON file in the ChickenPlanner project directory.
     */
    public void saveCurrentRoutine() {
        AutoRoutineJSON autoRoutineJSON = new AutoRoutineJSON(application.getAppState().getCurrentAutoRoutine());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        AppState appState = application.getAppState();
        String routinePath = appState.getProjectPath() + "/src/main/deploy/ChickenPlanner/"+appState.getCurrentAutoRoutine().getRoutineName() + ".json";

        try (FileWriter writer = new FileWriter(routinePath)) {
            gson.toJson(autoRoutineJSON, writer);
        } catch (IOException e) {
            System.err.println("Error saving routine: " + e.getMessage());
        }
    }

    /**
     * Checks if a file or directory exists at the specified relative path.
     *
     * @param baseDirectory The base directory.
     * @param relativePath  The relative path to check.
     * @return True if the file or directory exists, false otherwise.
     */
    private boolean doesFileExist(File baseDirectory, String relativePath) {
        File filePath = new File(baseDirectory.getAbsolutePath() + relativePath);
        return filePath.exists();
    }

    /**
     * Creates the ChickenPlanner folder in the WPLIB project directory if it does not exist.
     *
     * @param baseDirectory The base directory of the WPLIB project.
     */
    private void createChickenPlannerFolderIfMissing(File baseDirectory) {
        String chickenPlannerPath = baseDirectory.getAbsolutePath() + "/src/main/deploy/ChickenPlanner";
        File chickenPlannerFolder = new File(chickenPlannerPath);

        if (chickenPlannerFolder.exists()) return;
        chickenPlannerFolder.mkdirs();
    }

    public void remove(String routineName) {
        String routinePath = application.getAppState().getProjectPath() + "/src/main/deploy/ChickenPlanner/"+routineName + ".json";
        File routine = new File(routinePath);
        System.out.println("Delety+:"+routine.delete());
    }
}
