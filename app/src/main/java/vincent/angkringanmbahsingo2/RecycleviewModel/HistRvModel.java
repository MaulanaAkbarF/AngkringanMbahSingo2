package vincent.angkringanmbahsingo2.RecycleviewModel;

public class HistRvModel {
    private String judul;
    private int harga, jumlah, gambar;

    public HistRvModel(String judul, int harga, int jumlah, int gambar) {
        this.judul = judul;
        this.harga = harga;
        this.jumlah = jumlah;
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int addPlus(){
        int total = jumlah * harga;
        return total;
    }
}
