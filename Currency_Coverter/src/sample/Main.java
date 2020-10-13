package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/Currency Converter.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(new Scene(root, 500, 290));
        primaryStage.show();
        controller.setdata();
        controller.getdata();

    }


    public static void main(String[] args) {
        launch(args);


    }
}
