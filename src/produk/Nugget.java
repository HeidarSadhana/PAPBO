package produk;

import javafx.scene.control.Alert;
import program.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Nugget extends Produk{
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    final String jenis = "Nugget";
    private String bentuk;

    public Nugget(String nama, String bahan, int berat, int stok, int harga, String bentuk) {
        super(nama, bahan, berat, stok, harga);
        this.bentuk = bentuk;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public void addtodb() throws SQLException {
        connect = Database.connectDB();
        String query = "INSERT INTO tbproduk(nama, bahan, berat, stok, harga, bentuk, jenis) VALUES (?,?,?,?,?,?,?)";

        prepare = connect.prepareStatement(query);

        prepare.setString(1, this.getNama());
        prepare.setString(2, this.getBahan());
        prepare.setInt(3, this.getBerat());
        prepare.setInt(4, this.getStok());
        prepare.setInt(5, this.getHarga());
        prepare.setString(6, this.getBentuk());
        prepare.setString(7, this.jenis);

        prepare.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tambah Berhasil");
        alert.setContentText("Produk Berhasil di Tambah");
        alert.showAndWait();
    }
    
    public void edittodb(String nama, String bahan, int berat, int stok, int harga, String bentuk, int id) throws SQLException{
        connect = Database.connectDB();
        this.setNama(nama);
        this.setBahan(bahan);
        this.setBerat(berat);
        this.setHarga(harga);
        this.setBentuk(bentuk);

        String query = "update tbproduk set nama=?,bahan=?,berat=?,stok=?,harga=?,bentuk=?,jenis=? where id_produk=?";
        
        prepare = connect.prepareStatement(query);
        
        prepare.setString(1, nama);
        prepare.setString(2, bahan);
        prepare.setInt(3, berat);
        prepare.setInt(4, stok);
        prepare.setInt(5, harga);
        prepare.setString(6, bentuk);
        prepare.setString(7, this.jenis);
        prepare.setInt(8, id);
        
        prepare.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Berhasil");
        alert.setContentText("Produk Berhasil di Edit");
        alert.showAndWait();
    }
    
    public void hapustodb(int id) throws SQLException{
        connect = Database.connectDB();
        String query = "delete from tbproduk where id_produk=?";
        
        prepare = connect.prepareStatement(query);
        prepare.setInt(1, id);
        prepare.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hapus Berhasil");
        alert.setContentText("Produk Berhasil di Hapus");
        alert.showAndWait();
    }
}
