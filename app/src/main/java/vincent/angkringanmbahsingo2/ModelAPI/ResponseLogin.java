package vincent.angkringanmbahsingo2.ModelAPI;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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