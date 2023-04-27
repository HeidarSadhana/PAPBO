package program;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class LoginController {
    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Button regBtn;

    @FXML
    private TextField nama;

    @FXML
    private TextField email;

    @FXML
    private Button gambar;

    @FXML
    private AnchorPane loginPage;

    @FXML
    private AnchorPane regPage;

    @FXML
    private ImageView imageView;

    @FXML
    private Label regLabel;

    @FXML
    private PasswordField regPass;

    @FXML
    private PasswordField regCPass;

    @FXML
    private TextField regUsername;
    
    @FXML
    private Button btnsignin;

    @FXML
    private AnchorPane logopage;
    
    @FXML
    public void exit(MouseEvent event) {
        System.exit(0);
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    final ObservableList<String> bentuk = FXCollections.observableArrayList("alfabet", "angka", "dinosaurus", "bulat", "abstrak");
    final ObservableList<String> panjang = FXCollections.observableArrayList("3.0", "5.0", "7.0", "9.0", "12.0");

    @FXML
    public void login(){
        String query = "SELECT * FROM tbakun WHERE username = ? AND password = ?";
        connect = Database.connectDB();
        try{

            Alert alert;
            prepare = connect.prepareStatement(query);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();

            if(result.next()){
                Parent root;
                if("Admin".equals(result.getString(4))){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
                    root = loader.load();
                    AdminController controller = loader.getController();

                    controller.setUsername(username.getText());
                    controller.setp(System.getProperty("user.dir")+"\\Assets\\Profile\\"+result.getString(6));
                    controller.setCbBentuk(bentuk);
                    controller.setCbPanjang(panjang);
                    controller.setBgMenu1();
                }else{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
                    root = loader.load();
                    UserController controller = loader.getController();

                    controller.setUsername(username.getText());
                    controller.setp(System.getProperty("user.dir")+"\\Assets\\Profile\\"+result.getString(6));
                    controller.setBgMenu1();
                    controller.settablesosis("");
                    controller.id_akun = result.getInt("id_akun");
                }

                btnLogin.getScene().getWindow().hide();
                Stage stage = new Stage();
                Main.drag(stage, root);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(new Scene(root,725,512));
                stage.show();
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Gagal");
                alert.setHeaderText(null);
                alert.setContentText("Username/Password Salah");
                alert.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showRegPage(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.6));
        slide.setNode(logopage);
        slide.setToX(300);
        slide.play();
        btnsignin.setVisible(true);

        regPage.setVisible(true);
    }
    
    @FXML
    public void signin(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setNode(logopage);
        slide.setToX(0);
        slide.play();
        logopage.setTranslateX(-300);
        loginPage.setVisible(true);
        btnsignin.setVisible(false);
        clrRegister(); 
    }
    
    private File fileTerpilih;
    @FXML
    private void pilihGambar(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Pilih Gambar");
        FileChooser fc = new FileChooser();
        
        fileTerpilih = fc.showOpenDialog(stage);
        
        if (fileTerpilih != null){
            String path = fileTerpilih.getAbsolutePath();
            Image image = new Image(path);
            imageView.setImage(image);
            imageView.setFitHeight(50.0);
            imageView.setFitWidth(70.0);
        }
    }
    private void clrRegister(){
        nama.setText("");
        email.setText("");
        regUsername.setText("");
        regPass.setText("");
        regCPass.setText("");
    }
    
    private boolean cekInput(){
        Alert alert = new Alert(AlertType.WARNING);
        if(nama.getText().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Nama Harus Diisi");
            alert.showAndWait();
            nama.requestFocus();
            return false;
        }else if (email.getText().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Email Harus Diisi");
            alert.showAndWait();
            email.requestFocus();
            return false;
        }else if (regUsername.getText().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Username Harus Diisi");
            alert.showAndWait();
            regUsername.requestFocus();
            return false;
        }else if (regPass.getText().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Password Harus Diisi");
            alert.showAndWait();
            regPass.requestFocus();
            return false;
        }else if (regCPass.getText().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Konfirmasi Password Harus Diisi");
            alert.showAndWait();
            regCPass.requestFocus();
            return false;
        }else{
            return true;
        }
        
    }
    
    @FXML
    private void registerClicked(MouseEvent event) throws IOException, SQLException {
        if(cekInput() == false){
            return;
        }
        String selectQuery = "select * from tbakun where username=? OR email=?";
        connect = Database.connectDB();

        prepare = connect.prepareStatement(selectQuery);
        prepare.setString(1,regUsername.getText());
        prepare.setString(2, email.getText());
        result = prepare.executeQuery();

        //Mengecek apakah username dan email duplikat
        if(result.next()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Username atau Email Telah Ada");
            alert.showAndWait();
            email.requestFocus();

            return;
        }

        // Mengecek bahwa password dan konfirmasi password sama
        if (regPass.getText().equals(regCPass.getText())){
            Path source;
            String extension = "";
            // Mengecek apakah pengguna memilih gambar profile
            if (fileTerpilih != null){
                // Set source gambar profile
                // Mengambil extension gambar profile dari user
                source = fileTerpilih.toPath();
                int i = fileTerpilih.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = fileTerpilih.getName().substring(i+1);
                }
                
            }else{
                // Default gambar profile
                extension = "png";
                source = Paths.get(System.getProperty("user.dir"),"/Assets/Profile/default.png");
            }

            Path destination = Paths.get(System.getProperty("user.dir"),"/Assets/Profile/", email.getText() + "."+ extension);

            Files.copy(source,destination);

            String insertQuery = "Insert into tbakun(nama, email, username, password, pgambar) values (?,?,?,?,?)";
            connect = Database.connectDB();

            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1, nama.getText());
            prepare.setString(2, email.getText());
            prepare.setString(3, regUsername.getText());
            prepare.setString(4, regPass.getText());
            prepare.setString(5, email.getText() + "."+ extension);
            prepare.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Registrasi Akun Berhasil");
            alert.showAndWait();

            regPage.setVisible(false);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(logopage);
            slide.setToX(0);
            slide.play();
            logopage.setTranslateX(-300);
            loginPage.setVisible(true);
            btnsignin.setVisible(false);
            clrRegister();
        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Pesan Informasi");
            alert.setContentText("Password dan Konfirm Password Beda");
            alert.showAndWait();
            regPass.requestFocus();
        }

    }
}
