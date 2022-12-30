package vincent.angkringanmbahsingo2.Fragments;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
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
import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.Dependencies.DataHelper;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseRegister;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.AntrianRvAdapter;

public class RegisterFragment extends Fragment implements Backpressedlistener {

    Animation easeOutSineTop, easeOutSineTopOut, easeOutSineBottom, easeOutSineBottomOut, easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    CardView image;
    ConstraintLayout consanimate;
    LinearLayout input, linearcode, button;
    DataHelper dbhelper;
    EditText user, email,  pass, pass2, nama, alamat, nohp, inputcode;
    Button register, btnverify, kembali;
    TextView login, kirimulang, tekscekemail;
    APIInterface apiInterface;
    String emailkamu;
    public static Backpressedlistener backpressedlistener;
    private List<DataItemLogin> cekCodeVerify = new ArrayList<>();

    private static final int TIME_INTERVAL = 30000;
    private long generating;

    String check;
    public void setTransition(String check) {this.check = check;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Inisiasi komponen animasi
        consanimate = view.findViewById(R.id.consanimate);
        image = view.findViewById(R.id.frxcardlayouttop);
        input = view.findViewById(R.id.frxlinearinput);
        linearcode = view.findViewById(R.id.frxlinearverify);
        button = view.findViewById(R.id.frxlinearbutton);

        // Inisiasi komponen utama
        dbhelper = new DataHelper(getActivity());
        user = view.findViewById(R.id.rpxusername);
        email = view.findViewById(R.id.rpxemail);
        pass = view.findViewById(R.id.rpxpassword);
        pass2 = view.findViewById(R.id.rpxpassword2);
        nama = view.findViewById(R.id.rpxnama);
        alamat = view.findViewById(R.id.rpxalamat);
        nohp = view.findViewById(R.id.rpxnohp);
        tekscekemail = view.findViewById(R.id.rpxtxtcekemail);
        inputcode = view.findViewById(R.id.rpxinputcode);
        kirimulang = view.findViewById(R.id.frxbtnkirimulang);
        register = view.findViewById(R.id.frxbtndaftar);
        btnverify = view.findViewById(R.id.frxbtnverify);
        kembali = view.findViewById(R.id.frxbtnkembali);
        login = view.findViewById(R.id.frxtxtLogin);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineTopOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top_out);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);
        easeOutSineBottomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom_out);
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        // Membuat Fungsi Register
        register.setOnClickListener(v -> registerRetrofit());

        kembali.setOnClickListener(view12 -> {
            hapusRegister();
            register.setVisibility(View.VISIBLE);
            btnverify.setVisibility(View.GONE);
            input.startAnimation(easeOutQuadLeft);
            linearcode.startAnimation(easeOutQuadRightOut);
            kembali.setVisibility(View.GONE);
            linearcode.setVisibility(View.GONE);
            input.setVisibility(View.VISIBLE);
        });

        kirimulang.setOnClickListener(view13 -> {
            if (generating + TIME_INTERVAL > System.currentTimeMillis()) {
                Toast.makeText(getActivity(), "Tunggu 30 detik untuk mengirim ulang kode", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Kode dikirim ulang", Toast.LENGTH_SHORT).show();
                generateCode();
            }
            generating = System.currentTimeMillis();
        });

        // Membuat Fungsi Login Sekarang
        login.setOnClickListener(view1 -> {
            consanimate.startAnimation(easeOutSineTopOut);
            image.startAnimation(easeOutSineTopOut);
            input.startAnimation(easeOutSineBottomOut);
            button.startAnimation(easeOutSineBottomOut);
            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
            fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).commit();
        });

        if (check != null){
            Slide slide = new Slide();
            slide.setDuration(450);
            slide.setSlideEdge(Gravity.RIGHT);
            TransitionManager.beginDelayedTransition(container, slide);
        } else {
            image.startAnimation(easeOutSineTop);
            input.startAnimation(easeOutSineBottom);
            button.startAnimation(easeOutSineBottom);
        }

        kembali.setVisibility(View.GONE);
        linearcode.setVisibility(View.GONE);
        btnverify.setVisibility(View.GONE);
        verifikasiEmail();
        return view;
    }

    public void registerSQLite(){
        String username = user.getText().toString().trim();
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
        }else if (pass.getText().toString() != pass2.getText().toString()){
            pass.setError("Password tidak sama dengan password dibawah!");
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
        emailkamu = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String password2 = pass2.getText().toString().trim();
        String namalengkap = nama.getText().toString().trim();
        String alamatlengkap = alamat.getText().toString().trim();
        String nomorhp = nohp.getText().toString().trim();
        register.setOnClickListener(v -> {
            if (nama.getText().toString().equals("") && nama.getText().toString().isEmpty()) {
                nama.setError("Nama Lengkap tidak boleh kosong!");
            } else if (alamat.getText().toString().equals("") && alamat.getText().toString().isEmpty()) {
                alamat.setError("Alamat tidak boleh kosong!");
            } else if (nohp.getText().toString().equals("") && nohp.getText().toString().isEmpty()) {
                nohp.setError("Nomor HP tidak boleh kosong!");
            } else if (user.getText().toString().equals("") && user.getText().toString().isEmpty()) {
                user.setError("Username tidak boleh kosong!");
            } else if (pass.getText().toString().equals("") && pass.getText().toString().isEmpty()) {
                pass.setError("Password tidak boleh kosong!");
            } else if (!pass.getText().toString().equals(pass2.getText().toString())){
                pass.setError("Password tidak sama dengan password dibawah!");
            } else {
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseRegister> simpan = apiInterface.registerResponse(username, emailkamu, password, namalengkap, nomorhp, alamatlengkap);
                simpan.enqueue(new Callback<ResponseRegister>() {
                    @Override
                    public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                        int kode = response.body().getKode();
                        if (kode == 1) {
                            generateCode();
                            register.setVisibility(View.GONE);
                            input.startAnimation(easeOutQuadLeftOut);
                            linearcode.startAnimation(easeOutQuadRight);
                            button.startAnimation(easeOutSineBottomOut);
                            kembali.setVisibility(View.VISIBLE);
                            linearcode.setVisibility(View.VISIBLE);
                            input.setVisibility(View.GONE);
                            button.setVisibility(View.GONE);
                            tekscekemail.setText("Silakan cek E-Mail yang telah terkirim ke "+emailkamu);
                            button.startAnimation(easeOutSineBottom);
                            btnverify.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), "Tidak bisa membuat akun.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRegister> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void generateCode(){
        String judulpesan = "Kode Verifikasi untuk Akun Baru Kamu";
        String deskripsipesan = "Kode Verifikasi Akun Kamu ";
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseRegister> generate = apiInterface.kirimKodeVerify(emailkamu, judulpesan, deskripsipesan);
        generate.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                Toast.makeText(getActivity(), "Kode verifikasi terkirim!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
//                Toast.makeText(getActivity(), "Generate Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(".");
            }
        });
    }

    public void verifikasiEmail(){
        btnverify.setOnClickListener(v -> {
            if (inputcode.getText().toString().trim().equals("") && inputcode.getText().toString().trim().isEmpty()) {
                inputcode.setError("Kode verifikasi tidak boleh kosong!");
            } else {
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseLogin> verifikasi = apiInterface.cekKodeVerify(emailkamu);
                verifikasi.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        cekCodeVerify = response.body().getData();
                        if (inputcode.getText().toString().equals(String.valueOf(cekCodeVerify.get(0).getUsername()))) {
                            Toast.makeText(getActivity(), "Register successful", Toast.LENGTH_SHORT).show();
                            consanimate.startAnimation(easeOutSineTopOut);
                            image.startAnimation(easeOutSineTopOut);
                            inputcode.startAnimation(easeOutSineBottomOut);
                            button.startAnimation(easeOutSineBottomOut);
                            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                            fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).commit();
                        } else {
                            Toast.makeText(getActivity(), "Kode verifikasi salah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(getActivity(), "Verify Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void hapusRegister(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseRegister> verifikasi = apiInterface.hapusRegister(emailkamu);
        verifikasi.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                System.out.println("done");
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPause() {
        backpressedlistener=null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        backpressedlistener=this;
    }

    @Override
    public void onBackPressed() {
        if (emailkamu != null){
            hapusRegister();
        }
        consanimate.startAnimation(easeOutSineTopOut);
        image.startAnimation(easeOutSineTopOut);
        input.startAnimation(easeOutSineBottomOut);
        button.startAnimation(easeOutSineBottomOut);
        FragmentTransaction fragtr = getFragmentManager().beginTransaction();
        fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).commit();
    }
}