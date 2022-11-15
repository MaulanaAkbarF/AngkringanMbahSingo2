package vincent.angkringanmbahsingo2.Dependencies;

public class HomeRvModel {
    private String judul;
    private int harga;

    public HomeRvModel(String judul, int harga) {
        this.judul = judul;
        this.harga = harga;
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
}
