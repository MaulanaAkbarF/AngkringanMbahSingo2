package vincent.angkringanmbahsingo2.ModelAPI;

import com.google.gson.annotations.SerializedName;

public class DataItemTransaksi {

	@SerializedName("totalhargaitem")
	private String totalhargaitem;

	@SerializedName("id_produk")
	private String idProduk;

	@SerializedName("pengiriman")
	private String pengiriman;

	@SerializedName("metode")
	private String metode;

	@SerializedName("nama_produk")
	private String namaProduk;

	@SerializedName("harga")
	private String harga;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("username")
	private String username;

	public void setTotalhargaitem(String totalhargaitem){
		this.totalhargaitem = totalhargaitem;
	}

	public String getTotalhargaitem(){
		return totalhargaitem;
	}

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
	}

	public void setPengiriman(String pengiriman){
		this.pengiriman = pengiriman;
	}

	public String getPengiriman(){
		return pengiriman;
	}

	public void setMetode(String metode){
		this.metode = metode;
	}

	public String getMetode(){
		return metode;
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

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setSubtotal(String subtotal){
		this.subtotal = subtotal;
	}

	public String getSubtotal(){
		return subtotal;
	}

	public void setIdTransaksi(String idTransaksi){
		this.idTransaksi = idTransaksi;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}