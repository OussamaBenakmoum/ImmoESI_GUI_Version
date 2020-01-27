package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;



public class TableauDeBordController implements Initializable {

    @FXML
    private PieChart pieChart = new PieChart();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PieChart.Data slice1 = new PieChart.Data("Maison", 7);
        PieChart.Data slice2 = new PieChart.Data("Appartement", 2);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);


    }
}
