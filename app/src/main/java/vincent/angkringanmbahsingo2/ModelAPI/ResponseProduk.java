package vincent.angkringanmbahsingo2.ModelAPI;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseProduk{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItemProduk> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItemProduk> data){
		this.data = data;
	}

	public List<DataItemProduk> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}