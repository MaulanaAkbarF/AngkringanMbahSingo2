package vincent.angkringanmbahsingo2.Fragments;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Dependencies.DataHelper;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseRegister;
import vincent.angkringanmbahsingo2.R;

public class RegisterFragment extends Fragment {

    Animation easeOutSineTop, easeOutSineBottom;
    CardView image;
    LinearLayout input, button;
    DataHelper dbhelper;
    EditText user, email,  pass, nama, alamat, nohp;
    Button register;
    TextView login;
    APIInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Inisiasi komponen animasi
        image = view.findViewById(R.id.frxcardlayouttop);
        input = view.findViewById(R.id.frxlinearinput);
        button = view.findViewById(R.id.frxlinearbutton);

        // Inisiasi komponen utama
        dbhelper = new DataHelper(getActivity());
        user = view.findViewById(R.id.rpxusername);
        email = view.findViewById(R.id.rpxemail);
        pass = view.findViewById(R.id.rpxpassword);
        nama = view.findViewById(R.id.rpxnama);
        alamat = view.findViewById(R.id.rpxalamat);
        nohp = view.findViewById(R.id.rpxnohp);
        register = view.findViewById(R.id.frxbtndaftar);
        login = view.findViewById(R.id.frxtxtLogin);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);

        image.startAnimation(easeOutSineTop);
        input.startAnimation(easeOutSineBottom);
        button.startAnimation(easeOutSineBottom);

        // Membuat Fungsi Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRetrofit();
            }
        });

        // Membuat Fungsi Login Sekarang
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).addToBackStack("tag").commit();
            }
        });
        return view;
    }

    public void registerSQLite(){
        String username = user.getText().toString().trim();
        String emailkamu = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String namalengkap = nama.getText().toString().trim();
        String alamatlengkap = alamat.getText().toString().trim();
        String nomorhp = nohp.getText().toString().trim();

        ContentValues values = new ContentValues();

        if (nama.getText().toString().equals("") && nama.getText().toString().isEmpty()){
            nama.setError("Nama Lengkap tidak boleh kosong!");
        }else if (nohp.getText().toString().equals("") && nohp.getText().toString().isEmpty()){
            nohp.setError("Nomor HP tidak boleh kosong!");
        }else if (user.getText().toString().equals("") && user.getText().toString().isEmpty()){
            user.setError("Username tidak boleh kosong!");
        }else if (pass.getText().toString().equals("") && pass.getText().toString().isEmpty()){
            pass.setError("Password tidak boleh kosong!");
        }else {
            values.put(DataHelper.row_username, username);
            values.put(DataHelper.row_password, password);
            values.put(DataHelper.row_nama, namalengkap);
            values.put(DataHelper.row_alamat, alamatlengkap);
            values.put(DataHelper.row_nohp, nomorhp);
            dbhelper.insertData(values);

            Toast.makeText(getActivity(), "Register successful", Toast.LENGTH_SHORT).show();
            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
            fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).addToBackStack("tag").commit();
        }
    }

    public void registerRetrofit(){
        String username = user.getText().toString().trim();
        String emailkamu = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String namalengkap = nama.getText().toString().trim();
        String alamatlengkap = alamat.getText().toString().trim();
        String nomorhp = nohp.getText().toString().trim();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseRegister> simpan = apiInterface.registerResponse(username, emailkamu, password, namalengkap, nomorhp, alamatlengkap);
                if (nama.getText().toString().equals("") && nama.getText().toString().isEmpty()) {
                    nama.setError("Nama Lengkap tidak boleh kosong!");
                } else if (alamat.getText().toString().equals("") && alamat.getText().toString().isEmpty()) {
                    nohp.setError("Alamat tidak boleh kosong!");
                } else if (nohp.getText().toString().equals("") && nohp.getText().toString().isEmpty()) {
                    nohp.setError("Nomor HP tidak boleh kosong!");
                }else if (user.getText().toString().equals("") && user.getText().toString().isEmpty()) {
                    user.setError("Username tidak boleh kosong!");
                } else if (pass.getText().toString().equals("") && pass.getText().toString().isEmpty()) {
                    pass.setError("Password tidak boleh kosong!");
                } else {
                    simpan.enqueue(new Callback<ResponseRegister>() {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                            int kode = response.body().getKode();
                            if (kode == 1) {
                                Toast.makeText(getActivity(), "Register successful", Toast.LENGTH_SHORT).show();
                                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                                fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).addToBackStack("tag").commit();
                            } else {
                                Toast.makeText(getActivity(), "Register gagal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}