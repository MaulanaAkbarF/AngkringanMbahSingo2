package vincent.angkringanmbahsingo2.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseRegister;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;

public interface APIInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> setLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("datauserhome.php")
    Call<ResponseLogin> setDataHome(
            @Field("nama_lengkap") String nama_lengkap
    );

    @FormUrlEncoded
    @POST("updateusernama.php")
    Call<ResponseLogin> setUserNama(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("updateuserusername.php")
    Call<ResponseLogin> setUserUsername(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("updateuseralamat.php")
    Call<ResponseLogin> setUserAlamat(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @POST("updateusernohp.php")
    Call<ResponseLogin> setUserNohp(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("no_hp") String no_hp
    );

    @FormUrlEncoded
    @POST("updateuseremail.php")
    Call<ResponseLogin> setUserEmail(
            @Field("nama_lengkap") String nama_lengkap,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updateuserpassword.php")
    Call<ResponseLogin> setUserPassword(
            @Field("nama_lengkap") String nama_lengkap,
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

    @GET("produk.php")
    Call<ResponseProduk> getRetrive();

    @GET ("makanan.php")
    Call<ResponseProduk> getRetriveMakanan();

    @GET ("cemilan.php")
    Call<ResponseProduk> getRetriveCemilan();

    @GET ("frozenfood.php")
    Call<ResponseProduk> getRetriveFrozenfood();

    @GET ("makananlainnya.php")
    Call<ResponseProduk> getRetriveMakananLainnya();

    @GET ("minumanpanas.php")
    Call<ResponseProduk> getRetriveMinumanPanas();

    @GET ("minumandingin.php")
    Call<ResponseProduk> getRetriveMinumanDingin();

    @GET ("minumansachet.php")
    Call<ResponseProduk> getRetriveMinumanSachet();

    @GET ("minumanherbal.php")
    Call<ResponseProduk> getRetriveMinumanHerbal();

    @GET ("minumanlainnya.php")
    Call<ResponseProduk> getRetriveMinumanLainnya();

    @GET ("autokodetransaksi.php")
    Call<ResponseTransaksi> autoKodeTransaksi();

    @FormUrlEncoded
    @POST("transaksibeli.php")
    Call<ResponseTransaksi> transaksiBeli(
            @Field("id_transaksi") String id_transaksi,
            @Field("id_produk") String id_produk,
            @Field("username") String username,
            @Field("jumlah") String jumlah,
            @Field("totalhargaitem") String totalhargaitem
    );

    @FormUrlEncoded
    @POST("rangkumanpesanan.php")
    Call<ResponseTransaksi> rangkumanPesanan(
            @Field("id_transaksi") String id_transaksi,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("transaksibelifinal.php")
    Call<ResponseTransaksi> transaksiBeliFinal(
            @Field("id_transaksi") String id_transaksi,
            @Field("username") String username,
            @Field("subtotal") String subtotal,
            @Field("pengiriman") String pengiriman,
            @Field("metode") String metode,
            @Field("status") String status
    );
}