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

import vincent.angkringanmbahsingo2.Dependencies.DataHelper;
import vincent.angkringanmbahsingo2.R;

public class RegisterFragment extends Fragment {

    Animation easeOutSineTop, easeOutSineBottom;
    CardView image;
    LinearLayout input, button;
    DataHelper dbhelper;
    EditText user, pass, nama, alamat, nohp;
    Button register;
    TextView login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Inisiasi komponen animasi
        image = (CardView) view.findViewById(R.id.frxcardlayouttop);
        input = (LinearLayout) view.findViewById(R.id.frxlinearinput);
        button = (LinearLayout) view.findViewById(R.id.frxlinearbutton);

        // Inisiasi komponen utama
        dbhelper = new DataHelper(getActivity());
        user = (EditText) view.findViewById(R.id.rpxusername);
        pass = (EditText) view.findViewById(R.id.rpxpassword);
        nama = (EditText) view.findViewById(R.id.rpxnama);
        alamat = (EditText) view.findViewById(R.id.rpxalamat);
        nohp = (EditText) view.findViewById(R.id.rpxnohp);
        register = (Button) view.findViewById(R.id.frxbtndaftar);
        login = (TextView) view.findViewById(R.id.frxtxtLogin);

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
}