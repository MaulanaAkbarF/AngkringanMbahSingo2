package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.FrameLayout;
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
    Button btndefault, btngps, btntetapkan;
    DetailPesananFragment dpf = new DetailPesananFragment();
    String alamat = dpf.teksalamat.getText().toString();
    EditText inputalamat;
    public TextView label1, label2, txtalamat;
    ImageView btnback;
    Slide slide = new Slide();
    String alamatButtonAmbil = "Ambil di Angkringan Mbah Singo";

    // Mengisi data ID Transaksi dari Interface
    private String dataIdTransaksi, dataAlamat, dataAlamatDefault;
    // Mengisi data ID Transaksi dari DetailPesananFragment
    public void setDataIdTransaksi(String dataIdTransaksi) {this.dataIdTransaksi = dataIdTransaksi;}

    // Mengisi data Alamat dari DetailPesananFragment
    public void setDataAlamat(String dataAlamat, String dataAlamatDefault) {
        this.dataAlamat = dataAlamat;
        this.dataAlamatDefault = dataAlamatDefault;
    }

    // Mendapatkan data
    public String getDataAlamat() {return this.dataAlamat;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_alamat, container, false);
        includeAntar = view.findViewById(R.id.daxincludeantar);
        includeAmbil = view.findViewById(R.id.daxincludeambil);

        consantar = view.findViewById(R.id.daxconsantar);
        consambil = view.findViewById(R.id.daxconsambil);
        linearlay = view.findViewById(R.id.daxlinearlay);
        label1 = view.findViewById(R.id.daxlabel1);
        label2 = view.findViewById(R.id.daxlabel2);
        btn1 = view.findViewById(R.id.daxradioalamat);
        btn2 = view.findViewById(R.id.daxradioambil);
        btndefault = view.findViewById(R.id.daxbtnalamatdefault);
        btngps = view.findViewById(R.id.daxbtngps);
        btntetapkan = view.findViewById(R.id.daxbtntetapkan);
        inputalamat = view.findViewById(R.id.damxinput);
        txtalamat = view.findViewById(R.id.daxdataalamat3);
        btnback = view.findViewById(R.id.daxbtnback);

        btnback.setOnClickListener(view1 -> {
            dpf.setDataIdTransaksi(dataIdTransaksi);
            dpf.setDataAlamat(alamat);
            if (btn1.isChecked()){
                dpf.setDataAlamat(txtalamat.getText().toString());
                dpf.setDataPengiriman(label1.getText().toString());
            } else if (btn2.isChecked()){
                dpf.setDataAlamat(alamatButtonAmbil);
                dpf.setDataPengiriman(label2.getText().toString());
            }
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

        slide.setDuration(450);
        slide.setSlideEdge(Gravity.LEFT);
        TransitionManager.beginDelayedTransition(container, slide);
        btndefault.setVisibility(View.GONE);

        cekDataAlamat();
        cekRadioButton();
        btnDefaultClickable();
        btnGpsClickable();
        btnTetapkanClickable();
        linearlayClickable();
        return view;
    }

    private void cekDataAlamat(){
        txtalamat.setText(getDataAlamat());
        if (txtalamat.getText().toString().equals(alamatButtonAmbil) || txtalamat.getText().toString().equals("Anda belum menentukan Alamat")){
            txtalamat.setText("Anda belum menentukan Alamat");
            btndefault.setVisibility(View.VISIBLE);
        }
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

    public void btnDefaultClickable(){
        btndefault.setOnClickListener(view -> {
            if (btn1.isChecked()){
                dpf.setDataAlamat(dataAlamatDefault);
            }
            txtalamat.setText(dataAlamatDefault);
            btndefault.setVisibility(View.GONE);
        });
    }

    public void btnGpsClickable() {
        btngps.setOnClickListener(view -> startActivity(new Intent(getActivity(), Lokasi.class)));
    }

    public void btnTetapkanClickable() {
        btntetapkan.setOnClickListener(view -> {
            dpf.setDataIdTransaksi(dataIdTransaksi);
            if (btn1.isChecked()){
                dpf.setDataAlamat(txtalamat.getText().toString());
                dpf.setDataPengiriman(label1.getText().toString());
            }
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
        });
    }

    private void linearlayClickable(){
        linearlay.setOnClickListener(view -> System.out.print("."));
    }
}