package program;

import com.sun.javafx.charts.Legend;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import produk.Nugget;
import produk.Sosis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javafx.scene.shape.Line;

public class UserController implements UserSetterInterface {
    public int id_akun;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Label harga;

    @FXML
    private Label total;

    @FXML
    private Button menuProduk;

    @FXML
    private Button addtocart;

    @FXML
    private Button btnPesan;

    @FXML
    private Button btnhapus;

    @FXML
    private Button menuRiwayat;

    @FXML
    private TableView<Riwayat> tableriwayat;

    @FXML
    private TableColumn<Riwayat, String> orderR;

    @FXML
    private TableColumn<Riwayat, String> tanggalR;

    @FXML
    private TableColumn<Riwayat, String> itemR;

    @FXML
    private TableColumn<Riwayat, String> hargaR;
    
    ObservableList<Riwayat> riwayatList;

    public void setBgMenu1(){
        menuProduk.setStyle("-fx-background-color: #981824; ");
    }

    @FXML
    private Button menuKeranjang;

    public void setBgMenu2(){
        menuKeranjang.setStyle("-fx-background-color: #981824; ");
    }
    
    public void setBgMenu3(){
        menuRiwayat.setStyle("-fx-background-color: #981824; ");
    }
    
    @FXML
    private ImageView p;

    public void setp(String lokasi) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(lokasi));
        p.setImage(image);
    }

    @FXML
    private Label username;

    public void setUsername(String user) {
        username.setText(user);
    }

    @FXML
    private AnchorPane riwayat;

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
                Sosis sosisBaru = new Sosis(result.getString("nama"),
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
        } catch(SQLException e) {
            System.out.println("Gagal Load Data Produk");
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
        } catch(SQLException e) {
            System.out.println("Gagal Load Data Produk");
        }
    }

    @FXML
    private TableView<Keranjang> tableKeranjang;

    @FXML
    private TableColumn<Keranjang, String> namaP;

    @FXML
    private TableColumn<Keranjang, String> jumlahP;

    @FXML
    private TableColumn<Keranjang, String> hargaP;

    ObservableList<Keranjang> keranjanglist;

    public void settablekeranjang(){
        String query = "SELECT * FROM tbkeranjang WHERE id_akun = ? AND dibayar = 0";
        connect = Database.connectDB();
        this.keranjanglist = FXCollections.observableArrayList();
        try{
            prepare = connect.prepareStatement(query);
            prepare.setInt(1, id_akun);
            result = prepare.executeQuery();
            total_harga = 0;
            jumlah_produk = 0;
            while(result.next()) {
                int id_produk = result.getInt("id_produk");
                int jumlah = result.getInt("jumlah");
                int harga = result.getInt("harga");

                String SQL = "SELECT * FROM tbproduk WHERE id_produk = ?";

                prepare = connect.prepareStatement(SQL);
                prepare.setInt(1, id_produk);
                ResultSet hasil = prepare.executeQuery();
                if(hasil.next()){
                    Keranjang keranjangBaru = new Keranjang(hasil.getString("nama"), jumlah, harga);
                    total_harga += keranjangBaru.getHarga();
                    jumlah_produk += keranjangBaru.getJumlah();
                    keranjanglist.add(keranjangBaru);
                }
            }

            total.setText("Rp. " + total_harga);

            namaP.setCellValueFactory(new PropertyValueFactory<> ("nama"));
            jumlahP.setCellValueFactory(new PropertyValueFactory<> ("jumlah"));
            hargaP.setCellValueFactory(new PropertyValueFactory<> ("harga"));

            tableKeranjang.setItems(keranjanglist);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label labelproduk;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSosis;

    @FXML
    private Button btnNugget;

    @FXML
    private AnchorPane product;

    @FXML
    private AnchorPane keranjang;

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
    void tambahkeranjang(MouseEvent event) throws SQLException{
        Alert alert;
        int id_produk = 0;
        int harga = 0;

        ButtonType yes = new ButtonType("Ya", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Tidak", ButtonBar.ButtonData.CANCEL_CLOSE);

        if(tableSosis.isVisible()){
            Sosis sosis = tableSosis.getSelectionModel().getSelectedItem();
            alert = new Alert(Alert.AlertType.WARNING,"Tambah " + sosis.getNama() + " ke Keranjang?", yes, no);
            alert.setTitle("Tambah");
            Optional<ButtonType> iyah = alert.showAndWait();
            if(iyah.orElse(no) == yes){
                if(sosis.getStok() != 0){
                    String ambil_id = "SELECT id_produk FROM tbproduk WHERE nama = ?";
                    prepare = connect.prepareStatement(ambil_id);
                    prepare.setString(1, sosis.getNama());
                    result = prepare.executeQuery();
                    if(result.next()){
                        id_produk = result.getInt("id_produk");
                        harga = sosis.getHarga();
                    }
                }else{
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Stok Habis");
                    alert.setContentText("Tidak dapat dimasukkan ke keranjang");
                    alert.showAndWait(); return;
                }
            } else {
                return;
            }
        } else if (tableNugget.isVisible()){
            Nugget nugget = tableNugget.getSelectionModel().getSelectedItem();
            alert = new Alert(Alert.AlertType.WARNING,"Tambah "+ nugget.getNama() +"ke Keranjang?", yes, no);
            alert.setTitle("Tambah");
            Optional<ButtonType> iyah = alert.showAndWait();
            if(iyah.orElse(no) == yes) {
                if (nugget.getStok() != 0) {
                    String ambil_id = "SELECT id_produk FROM tbproduk WHERE nama = ?";
                    prepare = connect.prepareStatement(ambil_id);
                    prepare.setString(1, nugget.getNama());
                    result = prepare.executeQuery();
                    if (result.next()) {
                        id_produk = result.getInt("id_produk");
                        harga = nugget.getHarga();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Stok Habis");
                    alert.setContentText("Tidak dapat dimasukkan ke keranjang");
                    alert.showAndWait();
                    return;
                }
            } else {
                return;
            }
        }

        String query = "SELECT * FROM tbkeranjang WHERE id_akun = ? AND id_produk = ? AND dibayar = 0";

        prepare = connect.prepareStatement(query);
        prepare.setInt(1, id_akun);
        prepare.setInt(2, id_produk);
        result = prepare.executeQuery();

        if (!(result.next())){
            query = "INSERT INTO tbkeranjang(id_akun, id_produk, jumlah, harga, dibayar) VALUES(?,?,1,?,0)";
            connect = Database.connectDB();

            prepare = connect.prepareStatement(query);
            prepare.setInt(1, id_akun);
            prepare.setInt(2, id_produk);
            prepare.setInt(3, harga);

            prepare.execute();

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Berhasil di Tambah!");
            alert.setContentText("Produk Berhasil Ditambah ke Keranjang");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sudah Ada Di Keranjang");
            alert.setContentText("Produk Sudah Ada Di Keranjang");
            alert.showAndWait();
        }
    }

    @FXML
    private void keranjang(MouseEvent event) throws SQLException {
        menuProduk.setStyle(null);
        menuRiwayat.setStyle(null);
        setBgMenu2();
        keranjang.setVisible(true);
        product.setVisible(false);
        riwayat.setVisible(false);
        settablekeranjang();
    }

    @FXML
    private void produk(MouseEvent event) throws SQLException {
        menuKeranjang.setStyle(null);
        menuRiwayat.setStyle(null);
        setBgMenu1();
        keranjang.setVisible(false);
        product.setVisible(true);
        riwayat.setVisible(false);
        txtCari.clear();
        settablesosis("");
    }

    @FXML
    private Button tambah;

    @FXML
    private Button kurang;

    @FXML
    private TextField namaProduk;

    @FXML
    private TextField jumlahProduk;

    Keranjang cart;

    private int total_harga;
    private int jumlah_produk;

    @FXML
    private void ambilkeranjang(MouseEvent event){
        if (tableKeranjang.getSelectionModel().getSelectedItem() != null) {
            cart = tableKeranjang.getSelectionModel().getSelectedItem();
            namaProduk.setText(cart.getNama());
            int jumlah = cart.getJumlah();
            jumlahProduk.setText(String.valueOf(jumlah));
        }
    }

    @FXML
    private void tambahjumlah(MouseEvent event) throws SQLException {
        if(cart != null){
            String ambil_id = "SELECT * FROM tbproduk WHERE nama = ?";
            prepare = connect.prepareStatement(ambil_id);
            prepare.setString(1, cart.getNama());
            result = prepare.executeQuery();
            result.next();
            if(cart.getJumlah() < result.getInt("stok")){
                cart.setJumlah(cart.getJumlah()+1);
            }
            cart.setHarga(cart.getJumlah()*result.getInt("harga"));
            namaProduk.setText(cart.getNama());
            jumlahProduk.setText(String.valueOf(cart.getJumlah()));
            updatekeranjang();
        }
    }

    private void updatekeranjang() throws SQLException {
        if(cart != null){
            String ambil_id = "SELECT * FROM tbproduk WHERE nama = ?";
            prepare = connect.prepareStatement(ambil_id);
            prepare.setString(1, cart.getNama());
            result = prepare.executeQuery();
            result.next();
            String query = "UPDATE tbkeranjang SET harga = ?, jumlah = ? WHERE id_akun = ? AND id_produk = ? AND dibayar = 0";
            prepare = connect.prepareStatement(query);
            prepare.setInt(1, cart.getHarga());
            prepare.setInt(2, cart.getJumlah());
            prepare.setInt(3, id_akun);
            prepare.setInt(4, result.getInt("id_produk"));
            prepare.executeUpdate();
            settablekeranjang();
        }
    }

    @FXML
    private void kurangjumlah(MouseEvent event) throws SQLException {
        if(cart != null) {
            String ambil_id = "SELECT * FROM tbproduk WHERE nama = ?";
            prepare = connect.prepareStatement(ambil_id);
            prepare.setString(1, cart.getNama());
            result = prepare.executeQuery();
            result.next();
            if (cart.getJumlah() != 1) {
                cart.setJumlah(cart.getJumlah() - 1);
            }
            cart.setHarga(cart.getJumlah()*result.getInt("harga"));
            namaProduk.setText(cart.getNama());
            jumlahProduk.setText(String.valueOf(cart.getJumlah()));
            updatekeranjang();
        }
    }

    @FXML
    private void pesan(MouseEvent event) throws SQLException {
        if(tableKeranjang.hasProperties()){
            ButtonType yes = new ButtonType("Ya", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Tidak", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.WARNING,"Pesan Produk di Keranjang?", yes, no);
            alert.setTitle("Pesan");
            Optional<ButtonType> iyah = alert.showAndWait();
            if(iyah.orElse(no) == yes) {
                connect = Database.connectDB();
                String query = "SELECT * FROM tbkeranjang WHERE id_akun = ? AND dibayar = 0";
                prepare = connect.prepareStatement(query);
                prepare.setInt(1, id_akun);
                result = prepare.executeQuery();

                while (result.next()) {
                    int id_produk = result.getInt("id_produk");
                    int jumlah = result.getInt("jumlah");
                    String SQL = "UPDATE tbproduk SET stok = stok - ? WHERE id_produk = ?";
                    prepare = connect.prepareStatement(SQL);
                    prepare.setInt(1, jumlah);
                    prepare.setInt(2, id_produk);
                    prepare.executeUpdate();
                }

                String bayar = "UPDATE tbkeranjang SET dibayar = 1 WHERE id_akun = ? AND dibayar = 0";

                prepare = connect.prepareStatement(bayar);
                prepare.setInt(1, id_akun);

                prepare.execute();

                query = "INSERT INTO tborder(id_akun, total_harga, jumlah, tanggal) VALUES(?,?,?,?)";

                prepare = connect.prepareStatement(query);
                prepare.setInt(1, id_akun);
                prepare.setInt(2, total_harga);
                prepare.setInt(3, jumlah_produk);

                Date date = new Date(new java.util.Date().getTime());
                prepare.setDate(4, date);

                prepare.execute();

                settablekeranjang();
            }
        }
    }

    @FXML
    private void hpskranjang(MouseEvent event) throws SQLException{
        String ambil_id = "SELECT * FROM tbproduk WHERE nama = ?";
        prepare = connect.prepareStatement(ambil_id);
        prepare.setString(1, cart.getNama());
        result = prepare.executeQuery();
        result.next();
        String query = "DELETE FROM tbkeranjang WHERE id_produk = ?";
        prepare = connect.prepareStatement(query);
        prepare.setInt(1, result.getInt("id_produk"));
        prepare.execute();
        settablekeranjang();
    }
    
     public void settableriwayat(){
        try{
            connect = Database.connectDB();
            String SQL = "SELECT * FROM tborder WHERE id_akun = ?";
            prepare = connect.prepareStatement(SQL);
            prepare.setInt(1, id_akun);
            result = prepare.executeQuery();

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
            tanggalR.setCellValueFactory(new PropertyValueFactory<> ("tanggal"));
            hargaR.setCellValueFactory(new PropertyValueFactory<> ("total_harga"));
            itemR.setCellValueFactory(new PropertyValueFactory<> ("jumlah"));

            tableriwayat.setItems(riwayatList);
        }
        catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("FAILED TO LOAD DATA!");
            alert.setContentText("Load Data Riwayat Pesanan Gagal");
            alert.showAndWait();
        }
    }
     
    @FXML
    private void riwayat(MouseEvent event) {
        menuProduk.setStyle(null);
        menuKeranjang.setStyle(null);
        setBgMenu3();
        settableriwayat();
        product.setVisible(false);
        keranjang.setVisible(false);
        riwayat.setVisible(true);
    }

    @FXML
    private TextField txtCari;

    @FXML
    private void txtCariData(KeyEvent event) {
        settablenugget(txtCari.getText());
        settablesosis(txtCari.getText());
    }
}
