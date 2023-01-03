package vincent.angkringanmbahsingo2.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String BASE_URL = "https://workshopjti.com/vangkringanmobile/";
    public static final String BASE_GAMBAR = "https://workshopjti.com/v-angkringan/admin/gambarproduk/";

    private static Retrofit retrofit;

    public static Retrofit getService() {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
