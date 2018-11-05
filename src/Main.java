package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Main extends Application {
//    double h = 1;
//    int x0 = 1, y0 = 1;
//    double X = 10.2;
//
//    int N =  (int)((X - x0)/(h)) + 1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DE Chart");

//        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
//        Stage stage2 = new Stage();
//        stage.setTitle("Chart");
//        stage2.setTitle("Error");
//        stage2.show();
//        stage.show();

//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}