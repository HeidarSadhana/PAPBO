package program;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import produk.Produk;

public class Keranjang{
    private String nama;
    private int jumlah;
    private int harga;

    public Keranjang(String nama, int jumlah, int harga) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
