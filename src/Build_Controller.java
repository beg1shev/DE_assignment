package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Build_Controller {
    void calc(Stage stage, double x0, double y0, double X, double h, int init_N, int fin_N, boolean ex_fun, boolean f1, boolean f2, boolean f3, boolean f1_err, boolean f2_err, boolean f3_err, boolean tot) {
        int N =  (int)((X - x0)/(h)) + 1;
        double[] arr_x = new double[N];
        double[] arr_y = new double[N];
        double[] arr_y1 = new double[N];
        double[] arr_y2 = new double[N];
        double[] arr_y3 = new double[N];
        double[] exact = new double[N];
        double[] ex_sol = new double[N];
        double[] euler_error = new double[N];
        double[] impr_error = new double[N];
        double[] runge_error = new double[N];
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();


        euler_error[0] = 0;
        impr_error[0] = 0;
        runge_error[0] = 0;
        arr_x[0] = x0;
        arr_y[0] = y0;
        arr_y1[0] = y0;
        arr_y2[0] = y0;
        arr_y3[0] = y0;
        exact[0] = arr_x[0];
        ex_sol[0] = y0;
        for (int i = 1; i < N; i++) {
            arr_x[i] = arr_x[i - 1] + h;
        }

        LineChart<Number, Number> coordinateSystem = new LineChart<Number, Number>(x, y);

        coordinateSystem.setTitle("Charts");
        XYChart.Series chart1 = new XYChart.Series();
        XYChart.Series chart2 = new XYChart.Series();
        XYChart.Series chart3 = new XYChart.Series();
        XYChart.Series chart4 = new XYChart.Series();
        chart1.setName("Exact solution");
        chart2.setName("Euler's method");
        chart3.setName("Improved Euler's method");
        chart4.setName("Runge-Kutta method");
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas3 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas4 = FXCollections.observableArrayList();
        //Exact
        double Constant = (y0 - 2 * x0)/(Math.pow (x0,3) * (y0 + x0));
        for (int i = 0; i < N; i++) {
            ex_sol[i] = exact_sol(arr_x[i], Constant);
        }
        //Euler
        for (int i = 1; i < N; i ++){
            arr_y1[i] = arr_y1[i - 1] + h*func(arr_x[i - 1], arr_y1[i - 1]);
        }

        //Improved Euler
        for (int i = 1; i < N; i++){
            arr_y2[i] = arr_y2[i - 1] +  h * func(arr_x[i - 1] + h/2, arr_y2[i - 1] + (h/2)*func(arr_x[i - 1], arr_y2[i - 1]));
        }

        //Runge-Kuffa
        double k1, k2, k3, k4;
        for (int i = 1; i < N; i++) {
            k1 = func(arr_x[i - 1], arr_y3[i - 1]);
            k2 = func(arr_x[i - 1] + h / 2, arr_y3[i - 1] + (h * k1) / 2);
            k3 = func(arr_x[i - 1] + h / 2, arr_y3[i - 1] + (h * k2) / 2);
            k4 = func(arr_x[i - 1] + h, arr_y3[i - 1] + h * k3);
            arr_y3[i] = arr_y3[i - 1] + h * (k1 / 6 + k2 / 3 + k3 / 3 + k4 / 6);
        }

        for (int i = 0; i < N; i++) {
            Rectangle rect = new Rectangle(0, 0);
            rect.setVisible(false);
            Rectangle rect2 = new Rectangle(0, 0);
            rect.setVisible(false);
            Rectangle rect3 = new Rectangle(0, 0);
            rect.setVisible(false);
            Rectangle rect4 = new Rectangle(0, 0);
            rect.setVisible(false);
            XYChart.Data to_add1 = new XYChart.Data(arr_x[i], ex_sol[i]);
            XYChart.Data to_add2 = new XYChart.Data(arr_x[i], arr_y1[i]);
            XYChart.Data to_add3 = new XYChart.Data(arr_x[i], arr_y2[i]);
            XYChart.Data to_add4 = new XYChart.Data(arr_x[i], arr_y3[i]);
            to_add1.setNode(rect);
            to_add2.setNode(rect2);
            to_add3.setNode(rect3);
            to_add4.setNode(rect4);
            datas.add(to_add1);
            datas2.add(to_add2);
            datas3.add(to_add3);
            datas4.add(to_add4);
        }
//        for (int i = 0; i < N; i ++) {
//            System.out.println("i = " + i + " " + ex_sol[i] + " " + arr_y1[i] + " " + arr_y2[i] + " " + arr_y3[i]);
//        }
        chart1.setData(datas);
        chart2.setData(datas2);
        chart3.setData(datas3);
        chart4.setData(datas4);
        if (ex_fun) coordinateSystem.getData().add(chart1);
        if (f1) coordinateSystem.getData().add(chart2);
        if (f2) coordinateSystem.getData().add(chart3);
        if (f3) coordinateSystem.getData().add(chart4);


//        LineChart<Number, Number> errors = new LineChart<>(x, y);
//        errors.setTitle("Errors");

        XYChart.Series err1 = new XYChart.Series();
        XYChart.Series err2 = new XYChart.Series();
        XYChart.Series err3 = new XYChart.Series();
        err1.setName("Euler's error");
        err2.setName("Improved Euler's error");
        err3.setName("Runge-Kutta's error");

        for (int i = 1; i < N; i++) {
            euler_error[i] = Math.abs(ex_sol[i] - arr_y1[i]);
            impr_error[i] = Math.abs(ex_sol[i] - arr_y2[i]);
            runge_error[i] = Math.abs(ex_sol[i] - arr_y3[i]);
        }

        for (int i = 0; i < N; i++) {
            Rectangle rect = new Rectangle(0, 0);
            rect.setVisible(false);
            Rectangle rect2 = new Rectangle(0, 0);
            rect.setVisible(false);
            Rectangle rect3 = new Rectangle(0, 0);
            rect.setVisible(false);
            XYChart.Data to_add1 = new XYChart.Data(arr_x[i], euler_error[i]);
            XYChart.Data to_add2 = new XYChart.Data(arr_x[i], impr_error[i]);
            XYChart.Data to_add3 = new XYChart.Data(arr_x[i], runge_error[i]);
            to_add1.setNode(rect);
            to_add2.setNode(rect2);
            to_add3.setNode(rect3);
            err1.getData().add(to_add1);
            err2.getData().add(to_add2);
            err3.getData().add(to_add3);
        }

        if (f1_err) coordinateSystem.getData().add(err1);
        if (f2_err) coordinateSystem.getData().add(err2);
        if (f3_err) coordinateSystem.getData().add(err3);

        double[] max_err = new double[N];
        for (int i = init_N; i < fin_N; i++){
            max_err[i] = maxx(euler_error[i], impr_error[i], runge_error[i]);
        }
        XYChart.Series max_error = new XYChart.Series();
        max_error.setName("Total errors");

        for (int i = init_N; i < fin_N; i++){
            Rectangle rect = new Rectangle(0, 0);
            rect.setVisible(false);
            XYChart.Data to_add = new XYChart.Data(arr_x[i], max_err[i]);
            to_add.setNode(rect);
            max_error.getData().add(to_add);
        }

        if (tot) coordinateSystem.getData().add(max_error);
//        for (int i = 0; i < N; i++) {
//            System.out.println("Exact solution: " + ex_sol[i] + "; Euler: " + arr_y1[i] + "; Improved: " + arr_y2[i] + "; Runge: " + arr_y3[i]);
//        }
//        Scene scene2 = new Scene(errors, 600, 600);
        //        stage2.setScene(scene2);
//        System.out.println(N);
//        double maxx_eu = 0, maxx_imp = 0, maxx_rung = 0;
//        for (int i = 1; i < N; i++){
//            if (euler_error[i] > maxx_eu) maxx_eu = euler_error[i];
//            if (impr_error[i] > maxx_imp) maxx_imp = impr_error[i];
//            if (runge_error[i] > maxx_rung) maxx_rung = runge_error[i];
//        }
//        System.out.println(maxx_eu + " " + maxx_imp + " " + maxx_rung);
        Scene scene = new Scene(coordinateSystem, 600, 600);
        stage.setScene(scene);


    }

    private double maxx(double a, double b, double c){
        double maximum = Math.max(a, b);
        return Math.max(maximum, c);
    }
    private double func(double x, double y) {
        return (y * y) / (x * x) - 2;
    }
    private double exact_sol(double x, double Const){
//        double C;
//        C = Math.log((y - 2 * x)/(x * x * x * (y + x))) / 3;

        return (-x * ((Const) * Math.pow(x, 3) + 2)/ ((Const) * Math.pow(x, 3) - 1));
//        return (- x*(x*x*x -4)/(x*x*x + 2));
//        return 5/2 * Math.pow(Math.E,2*x-2) * x + x/2;
    }

//    public void Euler_method(int N, double arr_x[], double arr_y1[], double h){
//        for (int i = 1; i < N; i ++) {
//            arr_x[i] = arr_x[i - 1] + h;
//        }
//        for (int i = 1; i < N; i ++){
//            arr_y1[i] = arr_y1[i - 1] + h*func(arr_x[i - 1], arr_y1[i - 1]);
//        }
//    }
//    public void Improved_euler(double arr_x[], double arr_y2[], int N, double h){
////        for (int i = 1; i < N; i++){
////            arr_x[i] = arr_x[i - 1] + h;
////        }
//        for (int i = 1; i < N; i++){
//            arr_x[i] = arr_x[i - 1] + h;
//            arr_y2[i] = arr_y2[i - 1] +  h * func(arr_x[i - 1] + h/2, arr_y2[i - 1] + (h/2)*func(arr_x[i - 1], arr_y2[i - 1]));
////            System.out.println("Runge:\n" + arr_x[i] + " " + arr_y[i] + "\n");
//        }
//    }
//    public void Runge_Kuffa(double arr_x[], double arr_y3[], int N, double h){
//        double k1, k2, k3, k4;
////        for (int i = 1; i < N; i++){
////            arr_x[i] = arr_x[i - 1] + h;
////        }
////        System.out.println(" N = " + N + " Runge: ");
//        for (int i = 1; i < N; i++){
//            arr_x[i] = arr_x[i - 1] + h;
//            k1 = func(arr_x[i - 1], arr_y3[i - 1]);
//            k2 = func(arr_x[i - 1] + h/2, arr_y3[i - 1] + (h*k1)/2);
//            k3 = func(arr_x[i - 1] + h/2,arr_y3[i - 1] + (h*k2)/2);
//            k4 = func(arr_x[i - 1] + h, arr_y3[i - 1] + h*k3);
//            arr_y3[i] = arr_y3[i - 1] + h* (k1/6 + k2/3 + k3/3 + k4/6);
//
////            System.out.println("i = " + i + " " + arr_x[i] + " " + arr_y[i] + "\n");
//        }
//
//    } us
}