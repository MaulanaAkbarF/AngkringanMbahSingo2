package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.MainActivity.Lokasi;
import vincent.angkringanmbahsingo2.R;

public class DetailAlamatFragment extends Fragment {

    View includeAntar, includeAmbil;
    ConstraintLayout consantar, consambil;
    LinearLayout linearlay;
    RadioButton btn1, btn2;
    Button btngps, btntetapkan;
    DetailPesananFragment dpf = new DetailPesananFragment();
    String alamat = dpf.teksalamat.getText().toString();
    EditText inputalamat;
    public TextView label1, label2, txtalamat;
    ImageView btnback;

    // Mengisi data ID Transaksi dari Interface
    private String dataIdTransaksi;
    public void setDataIdTransaksi(String dataIdTransaksi) {
        this.dataIdTransaksi = dataIdTransaksi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_alamat, container, false);
        includeAntar = view.findViewById(R.id.daxincludeantar);
        includeAmbil = view.findViewById(R.id.daxincludeambil);

        consantar = view.findViewById(R.id.daxconsantar);
        consambil = view.findViewById(R.id.daxconsambil);
        linearlay = view.findViewById(R.id.daxlinearlay);
        btn1 = view.findViewById(R.id.daxradioalamat);
        btn2 = view.findViewById(R.id.daxradioambil);
        btngps = view.findViewById(R.id.daxbtngps);
        btntetapkan = view.findViewById(R.id.daxbtntetapkan);
        inputalamat = view.findViewById(R.id.damxinput);
        txtalamat = view.findViewById(R.id.daxdataalamat3);
        btnback = view.findViewById(R.id.daxbtnback);

        btnback.setOnClickListener(view1 -> {
            DetailPesananFragment dpf = new DetailPesananFragment();
            dpf.setDataIdTransaksi(dataIdTransaksi);
            dpf.setDataAlamat(alamat);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
        });

        inputalamat.setOnFocusChangeListener((view12, hasFocus) -> {
            if (hasFocus) {
                consantar.setVisibility(View.GONE);
                consambil.setVisibility(View.GONE);
            } else {
                consantar.setVisibility(View.VISIBLE);
                consambil.setVisibility(View.VISIBLE);
            }
        });

        if (inputalamat.getText().toString() == null){
            txtalamat.setText("Ketik alamat anda diatas atau cari dengan menekan tombol 'Gunakan GPS' dibawah");
        } else {
            TextWatcher watcher = new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    txtalamat.setText(inputalamat.getText().toString());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            };
            inputalamat.addTextChangedListener(watcher);
        }

        cekRadioButton();
        btnGpsClickable();
        btnTetapkanClickable();
        linearlayClickable();
        return view;
    }

    private void cekRadioButton(){
        btn1.setOnClickListener(view -> {
            includeAntar.setVisibility(View.VISIBLE);
            includeAmbil.setVisibility(View.GONE);
            btn2.setChecked(false);
        });
        btn2.setOnClickListener(view -> {
            includeAntar.setVisibility(View.GONE);
            includeAmbil.setVisibility(View.VISIBLE);
            btn1.setChecked(false);
        });
    }

    public void btnGpsClickable() {
        btngps.setOnClickListener(view -> startActivity(new Intent(getActivity(), Lokasi.class)));
    }

    public void btnTetapkanClickable() {
        btntetapkan.setOnClickListener(view -> {
            DetailPesananFragment dpf = new DetailPesananFragment();
            dpf.setDataIdTransaksi(dataIdTransaksi);
            dpf.setDataAlamat(txtalamat.getText().toString());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
        });
    }

    private void linearlayClickable(){
        linearlay.setOnClickListener(view -> System.out.print("."));
    }
}