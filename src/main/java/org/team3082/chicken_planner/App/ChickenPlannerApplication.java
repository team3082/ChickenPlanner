package org.team3082.chicken_planner.App;


public class ChickenPlannerApplication extends Application {

    private Stage stage;
    private Scene landingScene;
    private Scene editorScene;

    private static ChickenPlannerApplication app;

    public static ChickenPlannerApplication getAppInstance() {
        return app;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        this.stage = stage;

        stage.setMinWidth(Constants.UI.MIN_WINDOW_WIDTH);
        stage.setMinHeight(Constants.UI.MIN_WINDOW_HEIGHT);
        stage.getIcons().add(new Image(ChickenPlannerApplication.class.getResource("/assets/AppIcon.ico").toExternalForm()));

        VBox window = new VBox();
        
        landingScene = WindowBarNode.load(new VBox(), stage, new LandingPage(stage));
        editorScene = WindowBarNode.load(new VBox(), stage, new EditorPage(stage));

        Globals.themeProperty.addListener((_, _, _) -> reloadStyles());
        reloadStyles();

        stage.setScene(landingScene);
        stage.setTitle("ChickenPlanner 2025");
        stage.show();
    }

    private void reloadStyles() {
        while (landingScene.getStylesheets().size() > 1) {
            landingScene.getStylesheets().remove(1);
        }
        while (editorScene.getStylesheets().size() > 1) {
            editorScene.getStylesheets().remove(1);
        }

        String themeStylesheet = getClass().getResource("/styles/themes/" + Globals.themeProperty.getValue() + ".css")
                .toExternalForm();
        String globalStylesheet = getClass().getResource("/styles/style.css").toExternalForm();
        String windowStylesheet = getClass().getResource("/styles/window.css").toExternalForm();

        landingScene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
        editorScene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
    }

    public void switchToLandingScene() {
        stage.setScene(landingScene);
    }

    public void switchToEditorScene() {
        stage.setScene(editorScene);
    }
}
