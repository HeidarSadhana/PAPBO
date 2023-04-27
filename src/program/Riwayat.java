package program;

public class Riwayat {
    public int id_order;
    public int id_akun;
    public int total_harga;
    public int jumlah;
    public String tanggal;

    public Riwayat(int id_order, int id_akun, int total_harga, int jumlah, String tanggal) {
        this.id_order = id_order;
        this.id_akun = id_akun;
        this.jumlah = jumlah;
        this.total_harga = total_harga;
        this.tanggal = tanggal;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_akun() {
        return id_akun;
    }

    public void setId_akun(int id_akun) {
        this.id_akun = id_akun;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
