package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sql.DbManager;

public class Main extends Application {
    public static void main(String[] args) throws Exception{
        
        try {DbManager.start_db(); launch();} catch (Exception e) {/* Nope */}
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/gui/LoginController.fxml"));
        Parent root = loader.load();

        Image windowIcon = new Image("/img/logo.png");
        primaryStage.getIcons().add(windowIcon);	

        primaryStage.setTitle("COPD Decision Support System"); 
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
