package sample;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage primaire;

    Image programIcon = new Image("images/LOGO.png");
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ImmoESI");

        primaryStage.getIcons().add(programIcon);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaire = primaryStage;

    }

     static Stage getStage ()
    {
        return primaire;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
// #577280