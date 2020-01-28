package sample;

import Noyau.Agence;
import Noyau.Bien;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficherAnnoncesController implements Initializable {


    private ObservableList<Bien> biensObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<Bien> biensListView;




    public AfficherAnnoncesController(){
        biensObservableList = FXCollections.observableArrayList();
        biensObservableList.addAll(Agence.treeBiens);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        biensListView.setItems(biensObservableList);

        biensListView.setCellFactory(bienListView -> new bienListViewCell());


/*

        typeBienLabel.setText(biens.get(0).getClass().getSimpleName());
        transactionLabel.setText(biens.get(0).getTransaction().toString());
        prixBienLabel.setText(String.valueOf(biens.get(0).calculPrix(biens.get(0).getTransaction(),true )));


*/




    }
}
