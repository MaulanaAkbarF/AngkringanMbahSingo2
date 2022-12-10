package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Dependencies.DataHelper;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.R;

public class LoginFragment extends Fragment {

    Animation easeOutSineTop, easeOutSineBottom;
    LinearLayout image, input, button;
    DataHelper dbhelper;
    Button btnmasuk;
    public static EditText username, password;
    TextView daftar, lupapass;
    APIInterface apiInterface;
    private List<DataItemLogin> dataLogin = new ArrayList<>();

    // Digunakan ketika Login menggunakan fungai cekLogin()
    String dataUser = "";
    String dataPassword = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inisiasi komponen animasi
        image = view.findViewById(R.id.flxlinearimage);
        input = view.findViewById(R.id.flxlinearinput);
        button = view.findViewById(R.id.flxlinearbutton);

        // Inisiasi komponen utama
        dbhelper = new DataHelper(getActivity());
        username = view.findViewById(R.id.lpxusername);
        password = view.findViewById(R.id.lpxpassword);
        btnmasuk = view.findViewById(R.id.lpxbtnMasuk);
        daftar = view.findViewById(R.id.flxtxtDaftar);
        lupapass = view.findViewById(R.id.lpxtxtlupapass);
        lupapass.setVisibility(View.INVISIBLE);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);

        image.startAnimation(easeOutSineTop);
        input.startAnimation(easeOutSineBottom);
        button.startAnimation(easeOutSineBottom);

        // Fungsi Tombol Login
        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekLoginRetrofit();
            }
        });

        // Fungsi Tombol Lupa Password
        lupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new LupaPasswordFragment()).addToBackStack(null).commit();
            }
        });

        // Membuat Fungsi Daftar Gratis
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }

    // Deklarasi Fungsi Login menggunakan String
    private void cekLogin(){
        String nama = username.getText().toString();
        if (dataUser.contentEquals(username.getText()) && dataPassword.contentEquals(password.getText())){
            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
            fragtr.replace(R.id.fragmentcontainer, new HomeSplashScreenFragment()).commit();
        } else {
            Toast.makeText(getActivity(), "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            lupapass.setVisibility(View.VISIBLE);
        }
    }

    // Deklarasi Fungsi Login menggunakan SQLite
    private void cekLoginSQLite(){
        String nama = username.getText().toString();
        String dataUser = username.getText().toString().trim();
        String dataPass = password.getText().toString().trim();

        // Fungsi checkUser dideklarasikan di class DataHelper
        Boolean res = dbhelper.checkUser(dataUser,dataPass);
        if (username.getText().toString().equals("") && username.getText().toString().isEmpty()){
            username.setError("Username belum diisi!");
        }else if (password.getText().toString().equals("") && password.getText().toString().isEmpty()){
            password.setError("Password belum diisi!");
        }else if (res == true){
            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
            fragtr.replace(R.id.fragmentcontainer, new HomeSplashScreenFragment()).commit();
        }else {
            Toast.makeText(getActivity(), "Login Gagal!", Toast.LENGTH_SHORT).show();
            lupapass.setVisibility(View.VISIBLE);
        }
    }

    // Deklarasi Fungsi Login menggunakan Retrofit
    private void cekLoginRetrofit() {
        String dataUser = username.getText().toString().trim();
        String dataPass = password.getText().toString().trim();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseLogin> loginCall = apiInterface.setLogin(dataUser, dataPass);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body().getKode() == 1) {
                    dataLogin = response.body().getData();
                    username.setText(dataLogin.get(0).getNamaLengkap());
                    FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                    fragtr.replace(R.id.fragmentcontainer, new HomeSplashScreenFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Login Gagal", Toast.LENGTH_SHORT).show();
                    lupapass.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}