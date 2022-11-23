package vincent.angkringanmbahsingo2.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseRegister;

public interface APIInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> setLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseRegister> registerResponse(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("no_hp") String no_hp,
            @Field("alamat") String alamat
    );
}