package vincent.angkringanmbahsingo2.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String BASE_URL = "http://192.168.1.14/mysql/mysql/";
    public static final String BASE_GAMBAR = "http://192.168.1.14/mysql/mysql/gambar/";

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
