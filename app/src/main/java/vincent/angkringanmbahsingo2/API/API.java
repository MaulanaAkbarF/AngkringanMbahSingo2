package vincent.angkringanmbahsingo2.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static final String BASE_URL = "http://172.16.110.110/mysql/mysql/";

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
