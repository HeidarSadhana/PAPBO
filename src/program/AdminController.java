package program;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import produk.Nugget;
import produk.Sosis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;


public class AdminController implements AdminSetterInterface {
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // penggunaan interface dengan beberapa method untuk
    // menentukan value dari object pada gui

    @FXML
    private ImageView p;

    @FXML
    private TextField txtEditNama;

    @FXML
    private TextField txtBahanEdit;

    @FXML
    private TextField txtBeratEdit;

    @FXML
    private TextField txtStokEdit;

    @FXML
    private TextField txtxHargaEdit;

    @FXML
    private ComboBox<String> cbBentukEdit;

    @FXML
    private ComboBox<String> cbPanjangEdit;

    @FXML
    private TextField txtCari;
    
    @FXML
    private Button menuriwayat;

    @FXML
    private AnchorPane riwayatpesanan;

    @FXML
    private TableColumn<Riwayat, String> totalR;

    @FXML
    private TableColumn<Riwayat, String> hargaR;

    @FXML
    private TableColumn<Riwayat, String> orderR;

    @FXML
    private TableColumn<Riwayat, String> akunR;

    @FXML
    private TableColumn<Riwayat, String> tanggalR;

    @FXML
    private TableView<Riwayat> tableRiwayat;
    
    ObservableList<Riwayat> riwayatList;

    public void setp(String lokasi) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(lokasi));
        p.setImage(image);
    }

    @FXML
    private Button menuTambah;

    public void setBgMenu1(){
        menuTambah.setStyle("-fx-background-color: #981824; ");
    }

    @FXML
    private Button menuLihat;

    public void setBgMenu2(){
        menuLihat.setStyle("-fx-background-color: #981824; ");
    }
    
    public void setBgMenu3(){
        menuriwayat.setStyle("-fx-background-color: #981824; ");
    }

    @FXML
    private ComboBox<String> cbBentuk;

    public void setCbBentuk(ObservableList<String> bentuk) {
        cbBentuk.setItems(bentuk);
        cbBentukEdit.setItems(bentuk);
    }

    @FXML
    private ComboBox<String> cbPanjang;

    public void setCbPanjang(ObservableList<String> panjang) {
        cbPanjang.setItems(panjang);
        cbPanjangEdit.setItems(panjang);
    }

    @FXML
    private Label username;

    public void setUsername(String user) {
        username.setText(user);
    }

    @FXML
    private TableView<Sosis> tableSosis;

    @FXML
    private TableColumn<Sosis, String> namaS;

    @FXML
    private TableColumn<Sosis, String> hargaS;

    @FXML
    private TableColumn<Sosis, String> stokS;

    @FXML
    private TableColumn<Sosis, String> beratS;

    @FXML
    private TableColumn<Sosis, String> bahanS;

    @FXML
    private TableColumn<Sosis, String> panjangS;

    ObservableList<Sosis> sosisList;

    public void settablesosis(String s){
        try{
            connect = Database.connectDB();
            String SQL = "SELECT * FROM tbproduk WHERE nama LIKE '%" + s + "%' AND jenis LIKE '%Sosis%'";
            prepare = connect.prepareStatement(SQL);
            result = prepare.executeQuery(SQL);
            
            this.sosisList = FXCollections.observableArrayList();

            while(result.next()){
                Sosis sosisBaru = new Sosis(
                        result.getString("nama"),
                        result.getString("bahan"),
                        result.getInt("berat"),
                        result.getInt("stok"),
                        result.getInt("harga"),
                        result.getDouble("panjang")
                );
                sosisList.add(sosisBaru);
            }

            namaS.setCellValueFactory(new PropertyValueFactory<>("nama"));
            bahanS.setCellValueFactory(new PropertyValueFactory<> ("bahan"));
            beratS.setCellValueFactory(new PropertyValueFactory<> ("berat"));
            stokS.setCellValueFactory(new PropertyValueFactory<> ("stok"));
            hargaS.setCellValueFactory(new PropertyValueFactory<> ("harga"));
            panjangS.setCellValueFactory(new PropertyValueFactory<> ("panjang"));

            tableSosis.setItems(sosisList);
        }
        catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("FAILED TO LOAD DATA!");
            alert.setContentText("Load Data Produk Gagal");
            alert.showAndWait();
        }
    }

    @FXML
    private TableView<Nugget> tableNugget;

    @FXML
    private TableColumn<Nugget, String> namaN;

    @FXML
    private TableColumn<Nugget, String> hargaN;

    @FXML
    private TableColumn<Nugget, String> beratN;

    @FXML
    private TableColumn<Nugget, String> bentukN;

    @FXML
    private TableColumn<Nugget, String> stokN;

    @FXML
    private TableColumn<Nugget, String> bahanN;

    ObservableList<Nugget> nuggetList;

    public void settablenugget(String s){
        try{
            connect = Database.connectDB();
            String SQL = "SELECT * FROM tbproduk WHERE nama LIKE '%" + s + "%' AND jenis LIKE '%Nugget%'";
            prepare = connect.prepareStatement(SQL);
            result = prepare.executeQuery(SQL);

            this.nuggetList = FXCollections.observableArrayList();

            while(result.next()){
                Nugget nuggetBaru = new Nugget(result.getString("nama"),
                        result.getString("bahan"),
                        result.getInt("berat"),
                        result.getInt("stok"),
                        result.getInt("harga"),
                        result.getString("bentuk")
                );
                nuggetList.add(nuggetBaru);
            }

            namaN.setCellValueFactory(new PropertyValueFactory<> ("nama"));
            bahanN.setCellValueFactory(new PropertyValueFactory<> ("bahan"));
            beratN.setCellValueFactory(new PropertyValueFactory<> ("berat"));
            stokN.setCellValueFactory(new PropertyValueFactory<> ("stok"));
            hargaN.setCellValueFactory(new PropertyValueFactory<> ("harga"));
            bentukN.setCellValueFactory(new PropertyValueFactory<> ("bentuk"));

            tableNugget.setItems(nuggetList);
        }
        catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("FAILED TO LOAD DATA!");
            alert.setContentText("Load Data Produk Gagal");
            alert.showAndWait();
        }
    }

    // deklarasi object-object yang ada pada gui

    @FXML
    private Button btnSosis;

    @FXML
    private Button btnNugget;

    @FXML
    private Label labelproduk;

    @FXML
    private AnchorPane lihatproduct;

    @FXML
    private AnchorPane tambahproduct;

    @FXML
    private AnchorPane jenisproduk;
    
    @FXML
    private AnchorPane editProduk;
    
    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogout;

    @FXML
    private Button nugget;

    @FXML
    private Button sosis;

    @FXML
    private TextField txtBahan;

    @FXML
    private TextField txtBerat;

    @FXML
    private TextField txtHarga;

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtStok;

    // beberapa method dan fungsi

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void logout(MouseEvent event) {
        try {
            btnLogout.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            Stage stage = new Stage();
            Main.drag(stage, root);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void tambahnugget(MouseEvent event) {
        jenisproduk.setVisible(false);
        tambahproduct.setVisible(true);
        cbBentuk.setVisible(true);
        cbPanjang.setVisible(false);
    }

    @FXML
    void tambahsosis(MouseEvent event) {
        jenisproduk.setVisible(false);
        tambahproduct.setVisible(true);
        cbPanjang.setVisible(true);
        cbBentuk.setVisible(false);
    }

    @FXML
    private void addData(MouseEvent event) {
        try{
            String query = "SELECT * FROM tbproduk WHERE nama = ?";
            connect = Database.connectDB();
            prepare = connect.prepareStatement(query);
            prepare.setString(1,txtNama.getText());
            result = prepare.executeQuery();
            if (!result.next()){
                if (cbPanjang.isVisible()) {
                    int berat = Integer.parseInt(txtBerat.getText());
                    int stok = Integer.parseInt(txtStok.getText());
                    int harga = Integer.parseInt(txtHarga.getText());
                    double panjang = Double.parseDouble(cbPanjang.getValue());

                    Sosis sausis = new Sosis(txtNama.getText(), txtBahan.getText(), berat, stok, harga, panjang);
                    sausis.addtodb();

                } else if (cbBentuk.isVisible()){
                    int berat = Integer.parseInt(txtBerat.getText());
                    int stok = Integer.parseInt(txtStok.getText());
                    int harga = Integer.parseInt(txtHarga.getText());

                    Nugget nauget = new Nugget(txtNama.getText(), txtBahan.getText(), berat, stok, harga, cbBentuk.getValue());
                    nauget.addtodb();

                }
                clrForm();
            } else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Duplikat Nama Produk");
                alert.setContentText("Add Data Produk Gagal");
                alert.showAndWait();
            }
        } catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Add Data Produk Gagal (Input Tidak Sesuai)");
            alert.showAndWait();
        }

    }
    
    private void clrForm(){
        txtNama.clear();
        txtHarga.clear();
        txtStok.clear();
        txtBerat.clear();
        txtBahan.clear();
        cbPanjang.setValue(null);
        cbBentuk.setValue(null);
        
        txtEditNama.clear();
        txtxHargaEdit.clear();
        txtStokEdit.clear();
        txtBeratEdit.clear();
        txtBahanEdit.clear();
        cbPanjangEdit.setValue(null);
        cbBentukEdit.setValue(null);
    }

    @FXML
    private void clrForm(MouseEvent event) {
        clrForm();
    }

    @FXML
    private void lihatProduk(MouseEvent event) throws SQLException {
        menuTambah.setStyle(null);
        menuriwayat.setStyle(null);
        setBgMenu2();
        jenisproduk.setVisible(false);
        tambahproduct.setVisible(false);
        riwayatpesanan.setVisible(false);
        tableRiwayat.setVisible(false);
        lihatproduct.setVisible(true);
        settablesosis("");
    }

    @FXML
    private void tambahProduk(MouseEvent event) {
        menuLihat.setStyle(null);
        menuriwayat.setStyle(null);
        setBgMenu1();
        jenisproduk.setVisible(true);
        lihatproduct.setVisible(false);
        tambahproduct.setVisible(false);
        riwayatpesanan.setVisible(false);
        tableRiwayat.setVisible(false);
    }

    @FXML
    private void tampilSosis(MouseEvent event) throws SQLException {
        settablesosis(txtCari.getText());
        tableSosis.setVisible(true);
        tableNugget.setVisible(false);
        labelproduk.setText("DATA PRODUK SOSIS");
    }

    @FXML
    private void tampilNugget(MouseEvent event) throws SQLException {
        settablenugget(txtCari.getText());
        tableNugget.setVisible(true);
        tableSosis.setVisible(false);
        labelproduk.setText("DATA PRODUK NUGGET");
    }
    
    
    @FXML
    private void btnHpsClicked(MouseEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Ya", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Tidak", ButtonBar.ButtonData.CANCEL_CLOSE);
        if(tableSosis.isVisible()){
            Sosis sosis = tableSosis.getSelectionModel().getSelectedItem();
            
            int id_produk = 0;
            String ambil_id = "SELECT id_produk FROM tbproduk WHERE nama = ?";
            prepare = connect.prepareStatement(ambil_id);
            prepare.setString(1, sosis.getNama());
            result = prepare.executeQuery();
            if(result.next()){
                id_produk = result.getInt("id_produk");
            }
            
            Alert alert = new Alert(AlertType.WARNING,
                    "Yakin ingin menghapus "+sosis.getNama(),
                    yes,
                    no);

            alert.setTitle("Date format warning");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                sosis.hapustodb(id_produk);
            }

            txtCari.setText("");
            settablesosis(txtCari.getText());
        }else{
            Nugget nugget = tableNugget.getSelectionModel().getSelectedItem();
            
            int id_produk = 0;
            String ambil_id = "SELECT id_produk FROM tbproduk WHERE nama = ?";
            prepare = connect.prepareStatement(ambil_id);
            prepare.setString(1, nugget.getNama());
            result = prepare.executeQuery();
            if(result.next()){
                id_produk = result.getInt("id_produk");
            }
            
            Alert alert = new Alert(AlertType.WARNING,
                    "Yakin ingin menghapus "+nugget.getNama(),
                    yes,
                    no);

            alert.setTitle("Date format warning");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                nugget.hapustodb(id_produk);
            }

            txtCari.setText("");
            settablenugget(txtCari.getText());
        }
    }

    @FXML
    private void btnEditClicked(MouseEvent event) {
        lihatproduct.setVisible(false);
        editProduk.setVisible(true);
        
        if(tableSosis.isVisible()){
            Sosis sosis = tableSosis.getSelectionModel().getSelectedItem();
            txtEditNama.setPromptText(sosis.getNama());
            txtBahanEdit.setPromptText(sosis.getBahan());
            
            int berat = sosis.getBerat();
            String beratString = Integer.toString(berat);
            txtBeratEdit.setPromptText(beratString);
            
            int stok = sosis.getStok();
            String stokString = Integer.toString(stok);
            txtStokEdit.setPromptText(stokString);
            
            int harga = sosis.getHarga();
            String hargaString = Integer.toString(harga);
            txtxHargaEdit.setPromptText(hargaString);
            
            double panjang = sosis.getPanjang();
            String panjangString = Double.toString(panjang);
            cbPanjangEdit.setValue(panjangString);
            
            cbPanjangEdit.setVisible(true);
            cbBentukEdit.setVisible(false);
        }else{
            Nugget nugget = tableNugget.getSelectionModel().getSelectedItem();
            txtEditNama.setPromptText(nugget.getNama());
            txtBahanEdit.setPromptText(nugget.getBahan());
            
            int berat = nugget.getBerat();
            String beratString = Integer.toString(berat);
            txtBeratEdit.setPromptText(beratString);
            
            int stok = nugget.getStok();
            String stokString = Integer.toString(stok);
            txtStokEdit.setPromptText(stokString);
            
            int harga = nugget.getHarga();
            String hargaString = Integer.toString(harga);
            txtxHargaEdit.setPromptText(hargaString);
            
            cbBentukEdit.setValue(nugget.getBentuk());
            
            cbPanjangEdit.setVisible(false);
            cbBentukEdit.setVisible(true);
        }
    }
    
    @FXML
    private void editData(MouseEvent event) throws SQLException {
        int id_produk = 0;
        String nama = txtEditNama.getText();
        String bahan = txtBahanEdit.getText();
        int berat = 0;
        int stok = 0;
        int harga = 0;
        try{
            if(tableSosis.isVisible()){
                Sosis sosis = tableSosis.getSelectionModel().getSelectedItem();

                if(nama.isEmpty()){
                    nama = sosis.getNama();
                }
                if (bahan.isEmpty()){
                    bahan = sosis.getBahan();
                }
                if (txtBeratEdit.getText().isEmpty()){
                    berat = sosis.getBerat();
                }else{
                    berat = Integer.parseInt(txtBeratEdit.getText());
                }
                if (txtStokEdit.getText().isEmpty()){
                    stok = sosis.getStok();
                }else{
                    stok = Integer.parseInt(txtStokEdit.getText());
                }
                if (txtxHargaEdit.getText().isEmpty()){
                    harga = sosis.getHarga();
                } else {
                    harga = Integer.parseInt(txtxHargaEdit.getText());
                }

                String ambil_id = "SELECT id_produk FROM tbproduk WHERE nama = ?";
                prepare = connect.prepareStatement(ambil_id);
                prepare.setString(1, sosis.getNama());
                result = prepare.executeQuery();
                if(result.next()){
                    id_produk = result.getInt("id_produk");
                }

                System.out.println(id_produk+"id");


                sosis.edittodb(nama, bahan, berat, stok, harga, Double.parseDouble(cbPanjangEdit.getValue()), id_produk);

                txtCari.setText("");
                settablesosis(txtCari.getText());
            }else{
                Nugget nugget = tableNugget.getSelectionModel().getSelectedItem();

                if(nama.isEmpty()){
                    nama = nugget.getNama();
                }
                if (bahan.isEmpty()){
                    bahan = nugget.getBahan();
                }
                if (txtBeratEdit.getText().isEmpty()){
                    berat = nugget.getBerat();
                }else{
                    berat = Integer.parseInt(txtBeratEdit.getText());
                }
                if (txtStokEdit.getText().isEmpty()){
                    stok = nugget.getStok();
                }else{
                    stok = Integer.parseInt(txtStokEdit.getText());
                }
                if (txtxHargaEdit.getText().isEmpty()){
                    harga = nugget.getHarga();
                }else{
                    harga = Integer.parseInt(txtxHargaEdit.getText());
                }

                String ambil_id = "SELECT id_produk FROM tbproduk WHERE nama = ?";
                prepare = connect.prepareStatement(ambil_id);
                prepare.setString(1, nugget.getNama());
                result = prepare.executeQuery();
                if(result.next()){
                    id_produk = result.getInt("id_produk");
                }

                System.out.println(id_produk+"id");


                nugget.edittodb(nama, bahan, berat, stok, harga, cbBentukEdit.getValue(), id_produk);

                txtCari.setText("");
                settablenugget(txtCari.getText());
            }
            clrForm();
            lihatproduct.setVisible(true);
            editProduk.setVisible(false);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Edit Data Produk Gagal (Input Tidak Sesuai)");
            alert.showAndWait();
        }
    }
    
    public void settableriwayat(){
        try{
            connect = Database.connectDB();
            String SQL = "SELECT * FROM tborder";
            prepare = connect.prepareStatement(SQL);
            result = prepare.executeQuery(SQL);

            this.riwayatList = FXCollections.observableArrayList();

            while(result.next()){
                Riwayat riwayatbaru = new Riwayat(
                        result.getInt("id_order"),
                        result.getInt("id_akun"),
                        result.getInt("total_harga"),
                        result.getInt("jumlah"),
                        result.getDate("tanggal").toString()
                );
                riwayatList.add(riwayatbaru);
            }

            orderR.setCellValueFactory(new PropertyValueFactory<> ("id_order"));
            akunR.setCellValueFactory(new PropertyValueFactory<> ("id_akun"));
            tanggalR.setCellValueFactory(new PropertyValueFactory<> ("tanggal"));
            hargaR.setCellValueFactory(new PropertyValueFactory<> ("total_harga"));
            totalR.setCellValueFactory(new PropertyValueFactory<> ("jumlah"));

            tableRiwayat.setItems(riwayatList);
        } catch(SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("FAILED TO LOAD DATA!");
            alert.setContentText("Load Data Riwayat Pesanan Gagal");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void txtCariData(KeyEvent event) {
        settablenugget(txtCari.getText());
        settablesosis(txtCari.getText());
    }

    @FXML
    private void riwayatproduk(MouseEvent event) {
        menuriwayat.setStyle(null);
        menuLihat.setStyle(null);
        setBgMenu3();
        settableriwayat();
        jenisproduk.setVisible(false);
        lihatproduct.setVisible(false);
        tambahproduct.setVisible(false);
        riwayatpesanan.setVisible(true);
        tableRiwayat.setVisible(true);
    }
}