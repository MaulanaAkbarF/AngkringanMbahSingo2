package vincent.angkringanmbahsingo2.RecycleviewModel;

public class HomeRvModel {
    private String judul;
    private int harga;
    private int gambar;

    public HomeRvModel(String judul, int harga, int gambar) {
        this.judul = judul;
        this.harga = harga;
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

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
