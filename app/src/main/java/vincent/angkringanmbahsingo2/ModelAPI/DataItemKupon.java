package vincent.angkringanmbahsingo2.ModelAPI;

import com.google.gson.annotations.SerializedName;

public class DataItemKupon {

	@SerializedName("minimal")
	private String minimal;

	@SerializedName("nilai")
	private String nilai;

	@SerializedName("idkupon")
	private String idkupon;

	@SerializedName("nama_kupon")
	private String namaKupon;

	@SerializedName("username")
	private String username;

	public void setMinimal(String minimal){
		this.minimal = minimal;
	}

	public String getMinimal(){
		return minimal;
	}

	public void setNilai(String nilai){
		this.nilai = nilai;
	}

	public String getNilai(){
		return nilai;
	}

	public void setIdkupon(String idkupon){
		this.idkupon = idkupon;
	}

	public String getIdkupon(){
		return idkupon;
	}

	public void setNamaKupon(String namaKupon){
		this.namaKupon = namaKupon;
	}

	public String getNamaKupon(){
		return namaKupon;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}