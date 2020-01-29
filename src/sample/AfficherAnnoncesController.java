package sample;


import Noyau.Agence;
import Noyau.Bien;
import Noyau.Wilaya;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import javax.swing.text.Element;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AfficherAnnoncesController  implements Initializable{
    private ObservableList<Bien> biensObservableList  = FXCollections.observableArrayList(Agence.treeBiens);

    @FXML
    private ListView<Bien> biensListView;


    @FXML
    private Label typeBienLabel = new Label();

    @FXML
    private Label transactionLabel = new Label();

    @FXML
    private Label dateLabel = new Label();

    @FXML
    private Label adresseLabel = new Label();

    @FXML
    private Label wilayaLabel = new Label();

    @FXML
    private Label superficieLabel = new Label();

    @FXML
    private Label simplexeLabel = new Label();

    @FXML
    private Label nbchambesLabel= new Label();

    @FXML
    private Label meubleLabel = new Label();

    @FXML
    private Label jardinLabel= new Label();

    @FXML
    private Label garageLabel = new Label();

    @FXML
    private Label prixLabel = new Label();

    @FXML
    private Label negoLabel = new Label();

    @FXML
    private Label propLabel = new Label();






    public static class BienCell extends ListCell<Bien> {

        HBox hBox = new HBox();
        VBox vbox = new VBox();
        VBox titleVBox = new VBox();
        Image house;
        ImageView houseIcon = new ImageView(house);
        Pane pane = new Pane();

        Label typeBienLabel = new Label("");
        Label typeTransaction = new Label("");
        Label adresse = new Label("");
        Label superficie = new Label("");

        Label prop = new Label();
        Label descritption = new Label();

        Label prix = new Label();


        Button detailButton = new Button("Delete");


        public BienCell() {
            super();

            titleVBox.getChildren().addAll(typeBienLabel, typeTransaction);
            vbox.getChildren().addAll(titleVBox, adresse, superficie);
            hBox.prefHeight(100);
            hBox.setSpacing(10);
            if (Agence.access)
                hBox.getChildren().addAll(houseIcon, vbox, pane, prix, detailButton);
            else
                hBox.getChildren().addAll(houseIcon, vbox, pane, prix);

            hBox.setHgrow(pane, Priority.ALWAYS);

        }




        public void updateItem(Bien bien, boolean empty){
            super.updateItem(bien, empty);
            setText(null);
            setGraphic(null);

            if(bien != null && !empty){
               // affectation des champs

                typeBienLabel.setText(bien.getClass().getSimpleName());
                typeBienLabel.prefHeight(10);
                typeTransaction.setText(bien.getTransaction().toString());

                adresse.setText(bien.getAddresse()+" - "+bien.getWilaya().getNom());
                superficie.setText(bien.getSuperficie()+" m²");
                prop.setText("Bien de : "+bien.getProp().getPrenom()+" "+bien.getProp().getNom());

                prix.setText(String.valueOf(bien.calculPrix(bien.getTransaction(), true))+" DA");



                if (bien.getClass().getSimpleName().equals("Maison")){
                    house = new Image("images/maison.png");;

                }else
                if (bien.getClass().getSimpleName().equals("Appartement")) {
                    house = new Image("images/appart.png");
                }
                else
                if (bien.getClass().getSimpleName().equals("Terrain")) {
                    house = new Image("images/terrain.png");
                }
                houseIcon.setImage(house);
                houseIcon.setFitHeight(50);
                houseIcon.setFitWidth(50);
                    setGraphic(hBox);



                detailButton.setOnAction(e -> {
                    Bien bienSelected;
                    bienSelected = getListView().getItems().remove(getIndex());
                    Agence.treeBiens.remove(bienSelected);

                });


                detailButton.setOnMouseEntered(e -> {
                    detailButton.setStyle("-fx-background-color: #558ff2");
                });
                detailButton.setOnMouseExited(e -> {
                    detailButton.setStyle("");
                });
            }




        }
    }



    private void displayDetails(Bien bien){
        typeBienLabel.setText(bien.getClass().getSimpleName());
        transactionLabel.setText(bien.getTransaction().toString());
        dateLabel.setText(bien.getDateAjout().toString());
        adresseLabel.setText(bien.getAddresse());
        wilayaLabel.setText(bien.getWilaya().getNom());
        superficieLabel.setText(String.valueOf(bien.getSuperficie()));
        simplexeLabel.setText("--");
        nbchambesLabel.setText("---");
        meubleLabel.setText("----");
        jardinLabel.setText("----");
        garageLabel.setText("---");
        prixLabel.setText(String.valueOf(bien.calculPrix(bien.getTransaction(), true)));
        negoLabel.setText(Bien.boolToString(bien.isNegociable()));

    }




    static class ListViewHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            //this method will be overrided in next step
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

/*

        try {
            Parent samp = FXMLLoader.load(getClass().getResource("bienDetail.fxml"));
            detailSubscene.setRoot(samp);
        } catch (IOException e) {
            e.printStackTrace();
        }

*/

        biensListView.setItems(biensObservableList);
        biensListView.setCellFactory(param -> new BienCell(){
        });




        biensListView.setOnMouseClicked(new AfficherPropsController.ListViewHandler(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {

                displayDetails(biensListView.getSelectionModel().getSelectedItem());

            }
        });



    }

    
}
