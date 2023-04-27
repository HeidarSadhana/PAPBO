package produk;

import java.sql.SQLException;

public abstract class Produk {
    protected String nama;
    protected String bahan;
    protected int berat;
    protected int stok;
    protected int harga;
    protected int id_produk;

    public Produk(String nama, String bahan, int berat, int stok, int harga) {
        this.nama = nama;
        this.bahan = bahan;
        this.berat = berat;
        this.stok = stok;
        this.harga = harga;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    protected abstract void addtodb() throws SQLException;
}
