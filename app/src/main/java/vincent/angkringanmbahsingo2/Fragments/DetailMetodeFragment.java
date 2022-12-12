package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.Toast;

import vincent.angkringanmbahsingo2.R;

public class DetailMetodeFragment extends Fragment {

    View includeTransfer;
    LinearLayout linearlay;
    RadioButton btn1, btn2, btn3, rb1, rb2;
    TextView label1, label2, label3, txtalamat;
    ImageView btnback;
    Slide slide = new Slide();
    String dataIdTransaksi, dataAlamat, dataMetode;

    public void setDataMetode(String dataMetode, String dataIdTransaksi, String dataAlamat) {
        this.dataMetode = dataMetode;
        this.dataIdTransaksi = dataIdTransaksi;
        this.dataAlamat = dataAlamat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_metode, container, false);
        includeTransfer = view.findViewById(R.id.dmxincludetransfer);

        linearlay = view.findViewById(R.id.dmxlinearlay);
        label1 = view.findViewById(R.id.dmxlabel1);
        label2 = view.findViewById(R.id.dmxlabel2);
        label3 = view.findViewById(R.id.dmxlabel3);
        btn1 = view.findViewById(R.id.dmxradiotunai);
        btn2 = view.findViewById(R.id.dmxradiocod);
        btn3 = view.findViewById(R.id.dmxradiotransfer);
        rb1 = view.findViewById(R.id.dtxradio1);
        rb2 = view.findViewById(R.id.dtxradio2);
        btnback = view.findViewById(R.id.dmxbtnback);

        btnback.setOnClickListener(view1 -> {
            DetailPesananFragment dpf = new DetailPesananFragment();
            dpf.setDataIdTransaksi(dataIdTransaksi);
            dpf.setDataAlamat(dataAlamat);
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
}