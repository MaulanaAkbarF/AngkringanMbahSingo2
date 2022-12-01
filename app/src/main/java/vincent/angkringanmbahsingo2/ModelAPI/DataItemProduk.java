package vincent.angkringanmbahsingo2.ModelAPI;

import com.google.gson.annotations.SerializedName;

public class DataItemProduk {

	@SerializedName("id_produk")
	private String idProduk;

	@SerializedName("nama_produk")
	private String namaProduk;

	@SerializedName("harga")
	private String harga;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("deskripsi_produk")
	private String deskripsiProduk;

	@SerializedName("stok")
	private String stok;

	@SerializedName("gambar")
	private String gambar;

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
	}

	public void setNamaProduk(String namaProduk){
		this.namaProduk = namaProduk;
	}

	public String getNamaProduk(){
		return namaProduk;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setDeskripsiProduk(String deskripsiProduk){
		this.deskripsiProduk = deskripsiProduk;
	}

	public String getDeskripsiProduk(){
		return deskripsiProduk;
	}

	public void setStok(String stok){
		this.stok = stok;
	}

	public String getStok(){
		return stok;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}
}