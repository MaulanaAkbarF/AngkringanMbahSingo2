package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseRegister;
import vincent.angkringanmbahsingo2.R;

public class LupaPasswordFragment extends Fragment implements Backpressedlistener {

    Animation easeOutSineTop, easeOutSineBottom, easeOutSineTopOut, easeOutSineBottomOut, easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    LinearLayout lineartop, linearinput,  linearbottom;
    TextView tekscekemail, metode, kirimulang, loginskrg;
    EditText user, inputcode, pass1, pass2;
    TextInputLayout usera, userb, passa, passb;
    Button btnkirimemail, btnkirimcode, btnkembali, btnubah;

    APIInterface apiInterface;
    String emailkamu;
    public static Backpressedlistener backpressedlistener;
    private List<DataItemLogin> cekCodeVerify = new ArrayList<>();

    private static final int TIME_INTERVAL = 30000;
    private long generating, generating2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lupa_password, container, false);

        // Inisiasi komponen animasi
        lineartop = view.findViewById(R.id.lpassxlinearimage);
        linearinput = view.findViewById(R.id.lpassxlinearinput);
        linearbottom = view.findViewById(R.id.lpassxlinearbutton);
        metode = view.findViewById(R.id.lpassxtxtmetodelain);
        kirimulang = view.findViewById(R.id.lpassxtxtkirimulang);
        tekscekemail = view.findViewById(R.id.lpassxtxtcekemail);

        //TextInputLayout
        usera = view.findViewById(R.id.lpassxusernamea);
        userb = view.findViewById(R.id.lpassxcodeb);
        passa = view.findViewById(R.id.lpassxpassworda);
        passb = view.findViewById(R.id.lpassxpasswordb);
        //EditText
        user = view.findViewById(R.id.lpassxusername);
        inputcode = view.findViewById(R.id.lpassxcode);
        pass1 = view.findViewById(R.id.lpassxpassword1);
        pass2 = view.findViewById(R.id.lpassxpassword2);

        btnkirimemail = view.findViewById(R.id.lpassxbtnKirimemail);
        btnkirimcode = view.findViewById(R.id.lpassxbtnKonfirmcode);
        btnkembali = view.findViewById(R.id.lpassxbtnKembali);
        btnubah = view.findViewById(R.id.lpassxbtnubah);
        loginskrg = view.findViewById(R.id.lpassxtxtloginskrg);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);
        easeOutSineTopOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top_out);
        easeOutSineBottomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom_out);
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        lineartop.startAnimation(easeOutSineTop);
        linearbottom.startAnimation(easeOutSineBottom);
        linearinput.startAnimation(easeOutSineBottom);

        // Menyembunyikan layout
        tekscekemail.setVisibility(View.GONE);
        kirimulang.setVisibility(View.GONE);
        inputcode.setVisibility(View.GONE);
        passa.setVisibility(View.GONE);
        passb.setVisibility(View.GONE);
        btnkirimcode.setVisibility(View.GONE);
        btnkembali.setVisibility(View.GONE);
        btnubah.setVisibility(View.GONE);

        btnkirimemail.setOnClickListener(view13 -> {
            if (user.getText().toString().equals("")||user.getText().toString().isEmpty()){
                user.setError("Username tidak boleh kosong");
            } else {
                emailkamu = user.getText().toString();
                generateCode();
                tekscekemail.setText("Silakan cek E-Mail yang telah terkirim ke "+emailkamu);
                generating2 = System.currentTimeMillis();
                tekscekemail.setVisibility(View.VISIBLE);
                userb.setVisibility(View.VISIBLE);
                inputcode.setVisibility(View.VISIBLE);
                btnkirimcode.setVisibility(View.VISIBLE);
                btnkembali.setVisibility(View.VISIBLE);
                kirimulang.setVisibility(View.VISIBLE);
                tekscekemail.startAnimation(easeOutSineBottom);
                userb.startAnimation(easeOutSineBottom);
                inputcode.startAnimation(easeOutSineBottom);
                btnkirimcode.startAnimation(easeOutSineBottom);
                btnkembali.startAnimation(easeOutSineBottom);
                kirimulang.startAnimation(easeOutSineBottom);
                btnkirimemail.startAnimation(easeOutQuadRightOut);
                metode.startAnimation(easeOutQuadRightOut);
                btnkirimemail.setVisibility(View.GONE);
                metode.setVisibility(View.GONE);
            }
        });

        metode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnkirimcode.setOnClickListener(view12 -> {
            verifikasiEmail();
        });

        kirimulang.setOnClickListener(view16 -> {
            if (generating + TIME_INTERVAL > System.currentTimeMillis()) {
                Toast.makeText(getActivity(), "Tunggu 30 detik untuk mengirim ulang kode", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Kode dikirim ulang", Toast.LENGTH_SHORT).show();
                generateCode();
            }
            generating = System.currentTimeMillis();
        });

        btnkembali.setOnClickListener(view1 -> {
            if (generating2 + TIME_INTERVAL > System.currentTimeMillis()) {
                Toast.makeText(getActivity(), "Tunggu 30 detik untuk kembali", Toast.LENGTH_SHORT).show();
            } else {
                btnkirimemail.setVisibility(View.VISIBLE);
                metode.setVisibility(View.VISIBLE);
                btnkirimemail.startAnimation(easeOutQuadRight);
                metode.startAnimation(easeOutQuadRight);
                userb.startAnimation(easeOutQuadRightOut);
                inputcode.startAnimation(easeOutQuadRightOut);
                tekscekemail.startAnimation(easeOutQuadRightOut);
                btnkirimcode.startAnimation(easeOutQuadRightOut);
                btnkembali.startAnimation(easeOutQuadRightOut);
                kirimulang.startAnimation(easeOutQuadRightOut);
                userb.setVisibility(View.GONE);
                inputcode.setVisibility(View.GONE);
                tekscekemail.setVisibility(View.INVISIBLE);
                btnkirimcode.setVisibility(View.INVISIBLE);
                btnkembali.setVisibility(View.INVISIBLE);
                kirimulang.setVisibility(View.INVISIBLE);
            }
        });

        btnubah.setOnClickListener(view14 -> ubahPassword());

        loginskrg.setOnClickListener(view15 -> {
            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
            fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).commit();
        });
        return view;
    }

    private void generateCode(){
        String judulpesan = "Kode Verifikasi untuk Reset Password Kamu";
        String deskripsipesan = "Kode Verifikasi Reset Password ";
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
                        Toast.makeText(getActivity(), "Verifikasi berhasil", Toast.LENGTH_SHORT).show();
                        passa.setVisibility(View.VISIBLE);
                        passb.setVisibility(View.VISIBLE);
                        pass1.setVisibility(View.VISIBLE);
                        pass2.setVisibility(View.VISIBLE);
                        btnubah.setVisibility(View.VISIBLE);
                        passa.startAnimation(easeOutQuadLeft);
                        passb.startAnimation(easeOutQuadLeft);
                        pass1.startAnimation(easeOutQuadLeft);
                        pass2.startAnimation(easeOutQuadLeft);
                        btnubah.startAnimation(easeOutSineBottom);
                        usera.startAnimation(easeOutQuadRightOut);
                        userb.startAnimation(easeOutQuadRightOut);
                        user.startAnimation(easeOutQuadRightOut);
                        tekscekemail.startAnimation(easeOutQuadRightOut);
                        inputcode.startAnimation(easeOutQuadRightOut);
                        btnkirimcode.startAnimation(easeOutQuadRightOut);
                        btnkembali.startAnimation(easeOutQuadRightOut);
                        kirimulang.startAnimation(easeOutQuadRightOut);
                        usera.setVisibility(View.GONE);
                        userb.setVisibility(View.GONE);
                        user.setVisibility(View.GONE);
                        tekscekemail.setVisibility(View.GONE);
                        inputcode.setVisibility(View.GONE);
                        btnkirimcode.setVisibility(View.GONE);
                        btnkembali.setVisibility(View.GONE);
                        kirimulang.setVisibility(View.GONE);
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
    }

    private void ubahPassword(){
        String passreset = pass1.getText().toString();
        if (pass1.getText().toString().equals(pass2.getText().toString())){
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseLogin> loginCall = apiInterface.setResetPassword(emailkamu, passreset);
            loginCall.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    Toast.makeText(getActivity(), "Password berhasil diubah!", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                    fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).commit();
                }
                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Password tidak sama", Toast.LENGTH_SHORT).show();
        }
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
        FragmentTransaction fragtr = getFragmentManager().beginTransaction();
        fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).commit();
    }
}