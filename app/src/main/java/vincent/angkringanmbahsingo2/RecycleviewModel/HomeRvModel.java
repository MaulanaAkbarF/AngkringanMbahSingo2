package vincent.angkringanmbahsingo2.RecycleviewModel;

public class HomeRvModel {
    private String idmenu, judul, desc;
    private int harga, stok, gambar;

    public HomeRvModel(String idmenu, String judul, String desc, int harga, int stok, int gambar) {
        this.idmenu = idmenu;
        this.judul = judul;
        this.desc = desc;
        this.harga = harga;
        this.stok = stok;
        this.gambar = gambar;
    }

    public String getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(String idmenu) {
        this.idmenu = idmenu;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
