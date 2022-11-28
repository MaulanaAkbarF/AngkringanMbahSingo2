package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.R;

public class InterfaceMakananFragment extends Fragment {

    Animation easeOutQuadRight, easeOutQuadRightOut;
    ConstraintLayout consmain;
    public static TextView interdatajudul, interdatadesc, interdataharga, interdatastok, txtjumlah, txttotal;
    ImageView interdataimage, plusimage, minimage, imageback;
    Dialog dialog;

    static String currency = "Rp. %,d";
    static String stock = "%,d";
    int currentNumber = 1;
    int total;
    int totalPrice = 0;

    static MakananFragment makfrag = new MakananFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interface_makanan, container, false);

        consmain = view.findViewById(R.id.fmakxconsmain);
        interdatajudul = view.findViewById(R.id.interxjudulmenu);
        interdatadesc = view.findViewById(R.id.interxdescmenu);
        interdataharga = view.findViewById(R.id.interxhargamenu);
        interdatastok = view.findViewById(R.id.interxstokmenu);
        txtjumlah = view.findViewById(R.id.interxteksjumlah);
        txttotal = view.findViewById(R.id.interxtotalharga);
        plusimage = view.findViewById(R.id.interximageplus);
        minimage = view.findViewById(R.id.interximagemin);
        imageback = view.findViewById(R.id.fmakxback);

        // Membuat animasi
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        consmain.setVisibility(View.VISIBLE);
        consmain.startAnimation(easeOutQuadRight);

        if (makfrag.getMakananClicked()){
            getDataMakanan();
        }

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consmain.startAnimation(easeOutQuadRightOut);
                consmain.setVisibility(View.GONE);
            }
        });

        countJumlah();
        jumlahClickable();
        return view;
    }

    public static void getDataMakanan(){
        interdatajudul.setText(makfrag.datajudul.getText());
        interdatadesc.setText(makfrag.datadesc.getText());
        interdataharga.setText(String.format(currency, Integer.parseInt(String.valueOf(makfrag.dataharga.getText()))));
        interdatastok.setText(String.format(stock, Integer.parseInt(String.valueOf(makfrag.datastok.getText()))));
    }

    private void countJumlah(){
        txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(makfrag.dataharga.getText()))));
        total = Integer.parseInt(String.valueOf(makfrag.dataharga.getText()));
        plusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentNumber = currentNumber+1;
                txtjumlah.setText(String.valueOf(currentNumber));
                totalPrice = total * currentNumber;
                txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
            }
        });
        minimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentNumber>1){
                    currentNumber = currentNumber - 1;
                    txtjumlah.setText(String.valueOf(currentNumber));
                    totalPrice = total * currentNumber;
                    txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
                }
            }
        });
    }

    private void jumlahClickable(){
        txtjumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertJumlah();
                dialog.show();
            }
        });
    }

    private void showAlertJumlah(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_jumlah,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik jumlah pesanan");
                } else {
                    txtjumlah.setText(String.valueOf(input.getText()));
                    int jumlah = Integer.parseInt(String.valueOf(txtjumlah.getText()));
                    totalPrice = total * jumlah;
                    txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
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