
package sample;

import Noyau.Proprietaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class AfficherPropsController implements Initializable {


    private ObservableList<Proprietaire> propsObservableList  = FXCollections.observableArrayList(Proprietaire.proprietaires);

    @FXML
    private ListView<Proprietaire> proprietairesListView;





    static class Cell extends ListCell<Proprietaire>
    {
        HBox hBox = new HBox();

        Pane pane =  new Pane();

        Label nomProp = new Label("");
        Label mailProp = new Label("");
        Button btn = new Button("delete");


        public Cell(){
            super();


            hBox.prefHeight(100);
            hBox.getChildren().addAll(nomProp, mailProp, pane, btn);
            hBox.setHgrow(pane, Priority.ALWAYS);

    }

    public void updateItem(Proprietaire prop, boolean empty){
            super.updateItem(prop, empty);
            setText(null);
            setGraphic(null);

            if(prop != null && !empty){
                nomProp.setText(prop.getNom()+" "+prop.getPrenom());
                mailProp.setText("E-mail : "+prop.getadrMail());
                setGraphic(hBox);

                btn.setOnAction(e -> {
                    Proprietaire propSelected;
                    propSelected = getListView().getItems().remove(getIndex());
                    Proprietaire.proprietaires.remove(propSelected);

                    System.out.println("this was deleted  "+propSelected.getPrenom());

                });





                btn.setOnMouseEntered(e -> {
                    btn.setStyle("-fx-background-color: #fc4e03");
                });
                btn.setOnMouseExited(e -> {
                    btn.setStyle("");
                });
            }





        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        proprietairesListView.setItems(propsObservableList);
        proprietairesListView.setCellFactory(param -> new Cell(){

        });


    }
}

