package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.R;

public class ProfilFragment extends Fragment {

    Animation easeOutQuadRight, easeOutQuadRightOut;
    ScrollView scrollmain;
    ImageView image, btnquit, btneditnomor, btneditalamat, btneditemail, btneditpass;
    TextView nama, nomortlp, alamat, username, email, logout;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        scrollmain = view.findViewById(R.id.profxscrollmain);
        image = view.findViewById(R.id.profximageprofil);
        btnquit = view.findViewById(R.id.profxbtnquit);

        // TextView Info
        nama = view.findViewById(R.id.profxnamaprofil);
        nomortlp = view.findViewById(R.id.profxnomortelp);
        alamat = view.findViewById(R.id.profxalamat);
        email = view.findViewById(R.id.profxemail);
        logout = view.findViewById(R.id.profxlogout);

        // Button Edit
        btneditnomor = view.findViewById(R.id.profxeditnomor);
        btneditalamat = view.findViewById(R.id.profxeditalamat);
        btneditemail = view.findViewById(R.id.profxeditemail);
        btneditpass = view.findViewById(R.id.profxeditpass);

        // Membuat animasi
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        scrollmain.setVisibility(View.VISIBLE);
        scrollmain.startAnimation(easeOutQuadRight);

        btneditnomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertNomor();
                dialog.show();
            }
        });

        btneditalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertAlamat();
                dialog.show();
            }
        });

        btneditemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertEmail();
                dialog.show();
            }
        });

        btneditpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertPassword();
                dialog.show();
            }
        });

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollmain.startAnimation(easeOutQuadRightOut);
                scrollmain.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void showAlertNomor(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_nomortlp,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        input.setText(nomortlp.getText());
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik nomor telepon Anda");
                } else {
                    nomortlp.setText(input.getText());
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showAlertAlamat(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_alamat,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        input.setText(alamat.getText());
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengisi Alamat Anda");
                } else {
                    alamat.setText(input.getText());
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showAlertEmail(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_email,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        input.setText(email.getText());
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengisi E-Mail Anda");
                } else {
                    email.setText(input.getText());
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showAlertPassword(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_password,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik password Anda");
                } else {
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}