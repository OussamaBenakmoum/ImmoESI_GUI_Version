package sample;

import Noyau.Bien;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class bienListViewCell extends ListCell<Bien> {


    @FXML
    private ImageView bienIcon;

    @FXML
    private Label prix = new Label();

    @FXML
    private Label typeBienLabel = new Label();

    @FXML
    private Label typeTransactionLabel = new Label();

    @FXML
    private Label dateAjoutLabel = new Label();

    @FXML
    private Label adresseLabel = new Label();

    @FXML
    private Label decriptionLabel = new Label();

    @FXML
    private GridPane gridPane;

    FXMLLoader mLLoader;
/*
    public bienListViewCell() {
        loadFXML();
    }
    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bienListViewCell.fxml"));
        loader.setController(this);
        //loader.setRoot(this);
        //loader.load();
    }*/


    @Override
    protected void updateItem(Bien bien, boolean b) {
        super.updateItem(bien, b);

        if (b || bien == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("bienListViewCell.fxml"));
                mLLoader.setController(this);

            }

            typeBienLabel.setText(bien.getClass().getSimpleName());
            typeTransactionLabel.setText(bien.getTransaction().toString());
            prix.setText(String.valueOf(bien.calculPrix(bien.getTransaction(), true)));
            dateAjoutLabel.setText(bien.getDateAjout().toString());
            adresseLabel.setText(bien.getAddresse());
            decriptionLabel.setText(bien.getAddresse());


            setText(null);
            setGraphic(gridPane);

        }
    }
}
