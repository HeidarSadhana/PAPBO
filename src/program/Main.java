package program;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import produk.Produk;

public class Main extends Application {

    private static double x = 0;
    private static double y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        primaryStage.setTitle("SosisKuu");
        Scene scene = new Scene(root, 600, 400);

        drag(primaryStage, root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void drag(Stage stage, Parent root){
        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(.7);
        });

        root.setOnMouseReleased((MouseEvent event) ->{
            stage.setOpacity(1);
        });
    }

    public void exit(MouseEvent event) {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
