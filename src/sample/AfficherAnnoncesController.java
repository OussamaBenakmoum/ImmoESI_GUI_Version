package sample;


import Noyau.Agence;
import Noyau.Bien;
import Noyau.Wilaya;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jdk.jshell.spi.ExecutionControl;

import javax.swing.text.Element;
import java.net.URL;
import java.util.ResourceBundle;

public class AfficherAnnoncesController  implements Initializable{
    private ObservableList<Bien> biensObservableList  = FXCollections.observableArrayList(Agence.treeBiens);

    @FXML
    private ListView<Bien> biensListView;





    public static class BienCell extends ListCell<Bien>
    {

        HBox hBox = new HBox();
        VBox vbox = new VBox();
        VBox titleVBox = new VBox();
        Image house;
        ImageView houseIcon = new ImageView(house);
        Pane pane =  new Pane();

        Label typeBienLabel = new Label("");
        Label typeTransaction = new Label("");
        Label adresse = new Label("");
        Label superficie = new Label("");

        Label prop = new Label();
        Label descritption = new Label();

        Label prix = new Label();


        Button detailButton = new Button();

        public BienCell(){
            super();


            titleVBox.getChildren().addAll(typeBienLabel, typeTransaction);
            vbox.getChildren().addAll(titleVBox, adresse, superficie);
            hBox.prefHeight(100);
            hBox.setSpacing(10);

            hBox.getChildren().addAll(houseIcon, vbox, pane, prix, detailButton);
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

                prix.setText(String.valueOf(bien.calculPrix(bien.getTransaction(), true)));


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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        biensListView.setItems(biensObservableList);
        biensListView.setCellFactory(param -> new BienCell(){
        });


    }

    
}
