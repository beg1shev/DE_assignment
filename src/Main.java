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
    double h = 0.1;
    int x0 = 1, y0 = 3;
    double X = 18.2;

    int N =  (int)((X - x0)/(h)) + 1;
    double[] arr_x = new double[N];
    double[] arr_y = new double[N];
    double[] arr_y1 = new double[N];
    double[] arr_y2 = new double[N];
    double[] arr_y3 = new double[N];
    double[] exact = new double[N];
    double[] ex_sol = new double[N];
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DE Chart");
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        arr_x[0] = x0;
        arr_y[0] = y0;
        arr_y1[0] = y0;
        arr_y2[0] = y0;
        arr_y3[0] = y0;
        exact[0] = arr_x[0];
        ex_sol[0] = y0;
        for (int i = 1; i < N; i++){
            arr_x[i] = arr_x[i - 1] + h;
        }

        LineChart<Number, Number> coordinateSystem = new LineChart<Number, Number>(x,y);
        coordinateSystem.setTitle("Charts");
        XYChart.Series chart1 = new XYChart.Series();
        XYChart.Series chart2 = new XYChart.Series();
        XYChart.Series chart3 = new XYChart.Series();
        XYChart.Series chart4 = new XYChart.Series();
        chart1.setName("Exact function");
        chart2.setName("Euler's method");
        chart3.setName("Improved Euler's method");
        chart4.setName("Runge-Kaffa method");
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas3 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas4 = FXCollections.observableArrayList();
        for (int i = 0; i < N; i++){
            ex_sol[i] = exact_sol(arr_x[i]);
//            System.out.println(ex_sol[i] + " " + arr_x[i]);
        }
        for(int i = 0; i < N; i++){
            datas.add(new XYChart.Data(arr_x[i],ex_sol[i]));
//            datas2.add(new XYChart.Data(i,Math.cos(i)));
        }
        Euler_method();
        for (int i = 0; i < N; i++){
            datas2.add(new XYChart.Data(arr_x[i],  arr_y1[i]));
        }
        Improved_euler();
        for (int i = 0; i < N; i++){
            datas3.add(new XYChart.Data(arr_x[i],  arr_y2[i]));
        }
        Runge_Kuffa();
        for (int i = 0; i < N; i++){
            datas4.add(new XYChart.Data(arr_x[i],  arr_y3[i]));
        }
        for (int i = 0; i < N; i ++) {
            System.out.println("i = " + i + " " + ex_sol[i] + " " + arr_y1[i] + " " + arr_y2[i] + " " + arr_y3[i]);
        }
        chart1.setData(datas);
        chart2.setData(datas2);
        chart3.setData(datas3);
        chart4.setData(datas4);

        Scene scene = new Scene(coordinateSystem, 600,600);
        coordinateSystem.getData().add(chart1);
        coordinateSystem.getData().add(chart2);
        coordinateSystem.getData().add(chart3);
        coordinateSystem.getData().add(chart4);

        primaryStage.setScene(scene);
        primaryStage.show();
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    public double func(double x, double y){
//        return (y*y)/(x*x) - 2;
        return -x + y*(2*x+1)/x;
    }
    public double exact_sol(double x){
//        return (- x*(x*x*x -4)/(x*x*x + 2));
        return 5/2 * Math.pow(Math.E,2*x-2) * x + x/2;
    }
    public void Euler_method(){
        for (int i = 1; i < N; i ++) {
            arr_x[i] = arr_x[i - 1] + h;
        }
        for (int i = 1; i < N; i ++){
            arr_y1[i] = arr_y1[i - 1] + h*func(arr_x[i - 1], arr_y1[i - 1]);
        }
    }
    public void Improved_euler(){
//        for (int i = 1; i < N; i++){
//            arr_x[i] = arr_x[i - 1] + h;
//        }
        for (int i = 1; i < N; i++){
            arr_x[i] = arr_x[i - 1] + h;
            arr_y2[i] = arr_y2[i - 1] +  h * func(arr_x[i - 1] + h/2, arr_y2[i - 1] + (h/2)*func(arr_x[i - 1], arr_y2[i - 1]));
//            System.out.println("Runge:\n" + arr_x[i] + " " + arr_y[i] + "\n");
        }
    }
    public void Runge_Kuffa(){
        double k1, k2, k3, k4;
//        for (int i = 1; i < N; i++){
//            arr_x[i] = arr_x[i - 1] + h;
//        }
//        System.out.println(" N = " + N + " Runge: ");
        for (int i = 1; i < N; i++){
            arr_x[i] = arr_x[i - 1] + h;
            k1 = func(arr_x[i - 1], arr_y3[i - 1]);
            k2 = func(arr_x[i - 1] + h/2, arr_y3[i - 1] + (h*k1)/2);
            k3 = func(arr_x[i - 1] + h/2,arr_y3[i - 1] + (h*k2)/2);
            k4 = func(arr_x[i - 1] + h, arr_y3[i - 1] + h*k3);
            arr_y3[i] = arr_y3[i - 1] + h* (k1/6 + k2/3 + k3/3 + k4/6);

//            System.out.println("i = " + i + " " + arr_x[i] + " " + arr_y[i] + "\n");
        }

    }
}