package notes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notes.controller.MainController;
import notes.dao.ManagerBD;

public class MainApp extends Application {

    public static MainApp mainApp;

    private MainController mainController;
    private Stage stage;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void main(String[] args) throws Exception {
        mainApp = new MainApp();
        ManagerBD.initDB();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/hello.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("Заметки");
        stage.setScene(new Scene(root));
        mainApp.setMainController((MainController) loader.getController());
        mainApp.setStage(stage);
        stage.show();
    }
}