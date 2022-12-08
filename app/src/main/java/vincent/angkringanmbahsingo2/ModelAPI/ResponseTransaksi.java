package vincent.angkringanmbahsingo2.ModelAPI;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTransaksi{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItemTransaksi> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItemTransaksi> data){
		this.data = data;
	}

	public List<DataItemTransaksi> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}