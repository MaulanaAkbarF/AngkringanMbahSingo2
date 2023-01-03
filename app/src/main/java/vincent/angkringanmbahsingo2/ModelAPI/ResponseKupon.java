package vincent.angkringanmbahsingo2.ModelAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKupon{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItemKupon> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItemKupon> data){
		this.data = data;
	}

	public List<DataItemKupon> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}