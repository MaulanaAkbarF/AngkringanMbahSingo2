package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.R;

public class DetailMetodeFragment extends Fragment implements Backpressedlistener {

    View includeTransfer;
    LinearLayout linearlay;
    RadioButton btn1, btn2, btn3, rb1, rb2;
    TextView label1, label2, label3, txtalamat, rek1, rek2;
    ImageView btnback;
    Slide slide = new Slide();
    String dataIdTransaksi, dataAlamat, dataMetode, noRek, dataIdkupon, dataIdkupon2, namaKupon, namaKupon2;
    public static Backpressedlistener backpressedlistener;
    int check, nilai, nilai2;

    public void setDataMetode(String dataMetode, String dataIdTransaksi, int check, String dataAlamat) {
        this.dataMetode = dataMetode;
        this.dataIdTransaksi = dataIdTransaksi;
        this.check = check;
        this.dataAlamat = dataAlamat;
    }

    public void setDataKupon(String idkupon, String idkupon2, String namakupon, String namakupon2, int nilai, int nilai2){
        this.dataIdkupon = idkupon;
        this.dataIdkupon2 = idkupon2;
        this.namaKupon = namakupon;
        this.namaKupon2 = namakupon2;
        this.nilai = nilai;
        this.nilai2 = nilai2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_metode, container, false);
        includeTransfer = view.findViewById(R.id.dmxincludetransfer);

        linearlay = view.findViewById(R.id.dmxlinearlay);
        label1 = view.findViewById(R.id.dmxlabel1);
        label2 = view.findViewById(R.id.dmxlabel2);
        label3 = view.findViewById(R.id.dmxlabel3);
        rek1 = view.findViewById(R.id.dtxrek1);
        rek2 = view.findViewById(R.id.dtxrek2);
        btn1 = view.findViewById(R.id.dmxradiotunai);
        btn2 = view.findViewById(R.id.dmxradiocod);
        btn3 = view.findViewById(R.id.dmxradiotransfer);
        rb1 = view.findViewById(R.id.dtxradio1);
        rb2 = view.findViewById(R.id.dtxradio2);
        btnback = view.findViewById(R.id.dmxbtnback);

        btnback.setOnClickListener(view1 -> {
            DetailPesananFragment dpf = new DetailPesananFragment();
            dpf.setDataIdTransaksi(dataIdTransaksi, check);
            dpf.setDataAlamat(dataAlamat);
            dpf.setDataKupon(dataIdkupon, dataIdkupon2, namaKupon, namaKupon2, nilai, nilai2);
            if (btn1.isChecked()){
                dpf.setDataMetode("Tunai");
            } else if (btn2.isChecked()){
                dpf.setDataMetode("COD");
            } else if (btn3.isChecked()){
                dpf.setDataMetode("Transfer");
                if (rb1.isChecked()){
                    noRek = rek1.getText().toString();
                    if (noRek.contains(" ")){
                        noRek = noRek.replace(" ", "");
                    }
                    dpf.setDataRekening(noRek);
                } else if (rb2.isChecked()){
                    noRek = rek2.getText().toString();
                    if (noRek.contains(" ")){
                        noRek = noRek.replace(" ", "");
                    }
                    dpf.setDataRekening(noRek);
                }
            } else {
                dpf.setDataMetode(dataMetode);
            }
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
        });

        slide.setDuration(450);
        slide.setSlideEdge(Gravity.LEFT);
        TransitionManager.beginDelayedTransition(container, slide);

        cekRadioButton();
        linearClickable();
        return view;
    }

    private void cekRadioButton(){
        btn1.setOnClickListener(view -> {
            includeTransfer.setVisibility(View.GONE);
            btn2.setChecked(false);
            btn3.setChecked(false);
        });
        btn2.setOnClickListener(view -> {
            includeTransfer.setVisibility(View.GONE);
            btn1.setChecked(false);
            btn3.setChecked(false);
        });
        btn3.setOnClickListener(view -> {
            includeTransfer.setVisibility(View.VISIBLE);
            btn2.setChecked(false);
            btn1.setChecked(false);
        });
        rb1.setOnClickListener(view -> rb2.setChecked(false));
        rb2.setOnClickListener(view -> rb1.setChecked(false));
    }

    private void linearClickable(){
        linearlay.setOnClickListener(view -> System.out.println("OK"));
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
        DetailPesananFragment dpf = new DetailPesananFragment();
        dpf.setDataIdTransaksi(dataIdTransaksi, check);
        dpf.setDataAlamat(dataAlamat);
        dpf.setDataKupon(dataIdkupon, dataIdkupon2, namaKupon, namaKupon2, nilai, nilai2);
        if (btn1.isChecked()){
            dpf.setDataMetode("Tunai");
        } else if (btn2.isChecked()){
            dpf.setDataMetode("COD");
        } else if (btn3.isChecked()){
            dpf.setDataMetode("Transfer");
        } else {
            dpf.setDataMetode(dataMetode);
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragtr = fragmentManager.beginTransaction();
        fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
    }
}