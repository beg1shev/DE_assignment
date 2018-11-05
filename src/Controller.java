package sample ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class Controller {

    @FXML
    public TextField txtfield1;

    @FXML
    private TextField txtfield2;

    @FXML
    private TextField txtfield3;

    @FXML
    private TextField txtfield4;

    @FXML
    private Button btn;

    @FXML
    private CheckBox exact;

    @FXML
    private CheckBox euler;

    @FXML
    private CheckBox euler_err;

    @FXML
    private CheckBox impr;

    @FXML
    private CheckBox impr_err;

    @FXML
    private CheckBox runge;

    @FXML
    private CheckBox runge_err;

    @FXML
    private CheckBox tot_err;

    @FXML
    private CheckBox all_mtd;

    @FXML
    private CheckBox all_err;

    @FXML
    private TextField init_N;

    @FXML
    private TextField fin_N;


    @FXML
    private void handle_methods() {
        if (all_mtd.isSelected()) {
            euler.setSelected(true);
            impr.setSelected(true);
            runge.setSelected(true);
        }
        if (!all_mtd.isSelected()) {
            euler.setSelected(false);
            impr.setSelected(false);
            runge.setSelected(false);
        }
    }
    @FXML
    private void handle_errors(){
        if (all_err.isSelected()){
            euler_err.setSelected(true);
            impr_err.setSelected(true);
            runge_err.setSelected(true);
        }
        if (!all_err.isSelected()){
            euler_err.setSelected(false);
            impr_err.setSelected(false);
            runge_err.setSelected(false);
        }
    }

    @FXML
    void pressButton(ActionEvent event){
            Build_Controller builder = new Build_Controller();
            Stage stage = new Stage();
            stage.setTitle("Charts");
            builder.calc(stage, Double.parseDouble(txtfield1.getText()),Double.parseDouble(txtfield2.getText()), Double.parseDouble(txtfield3.getText()), Double.parseDouble(txtfield4.getText()), Integer.parseInt(init_N.getText()), Integer.parseInt(fin_N.getText()), exact.isSelected(),euler.isSelected(), impr.isSelected(), runge.isSelected(),euler_err.isSelected(), impr_err.isSelected(), runge_err.isSelected(), tot_err.isSelected() );
            stage.show();
        }
    }


