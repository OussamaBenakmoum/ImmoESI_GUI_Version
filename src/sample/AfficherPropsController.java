
package sample;

import Noyau.Bien;
import Noyau.Proprietaire;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficherPropsController implements Initializable {


    private ObservableList<Proprietaire> propsObservableList  = FXCollections.observableArrayList(Proprietaire.proprietaires);



    private ObservableList<Bien> biensPropObservableList;



    @FXML
    private ListView<Proprietaire> proprietairesListView;

// here i define the list vies of a chosen person
    @FXML
    private ListView<Bien> biensPropListView;



    public static class PropCell extends ListCell<Proprietaire>
    {
        HBox hBox = new HBox();

        Pane pane =  new Pane();

        Label nomProp = new Label("");
        Label mailProp = new Label("");
        Button btn = new Button("delete");


        public PropCell(){
            super();


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


    class ListViewHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            //this method will be overrided in next step
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        proprietairesListView.setItems(propsObservableList);
        proprietairesListView.setCellFactory(param -> new PropCell());

        int index=0;


        biensPropObservableList = FXCollections.observableArrayList(proprietairesListView.getItems().get(0).getBiens());


        proprietairesListView.setOnMouseClicked(new ListViewHandler(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                System.out.print(proprietairesListView.getSelectionModel().getSelectedIndex());

                biensPropObservableList = FXCollections.observableArrayList(proprietairesListView.getSelectionModel().getSelectedItem().getBiens());

                biensPropListView.setItems(biensPropObservableList);
            }
        });

        biensPropListView.setItems(biensPropObservableList);


        biensPropListView.setCellFactory(parame -> new AfficherAnnoncesController.BienCell());





    }
}

