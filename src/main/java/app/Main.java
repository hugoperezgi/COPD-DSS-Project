package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("PQ no funcionas hijodeputa");
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("src\\main\\java\\gui\\LoginController.fxml"));
		Parent root = loader.load();

        Image windowIcon = new Image("img/logo.png");
        primaryStage.getIcons().add(windowIcon);	

        primaryStage.setTitle("Client"); 
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
