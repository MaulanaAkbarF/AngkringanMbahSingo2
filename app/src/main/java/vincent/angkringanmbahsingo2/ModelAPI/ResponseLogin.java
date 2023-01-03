package vincent.angkringanmbahsingo2.ModelAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLogin{

	@SerializedName("data")
	private List<DataItemLogin> data;

	@SerializedName("kode")
	private int kode;

	public void setData(List<DataItemLogin> data){
		this.data = data;
	}

	public List<DataItemLogin> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}