package vincent.angkringanmbahsingo2.ModelAPI;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister{

	@SerializedName("kode")
	private int kode;

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}