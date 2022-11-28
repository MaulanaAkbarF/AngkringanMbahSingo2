package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    TextView txtalamat;
    ImageView btnback;

    DetailAlamatFragment daf = new DetailAlamatFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_metode, container, false);
        includeTransfer = view.findViewById(R.id.dmxincludetransfer);

        linearlay = view.findViewById(R.id.dmxlinearlay);
        btn1 = view.findViewById(R.id.dmxradiotunai);
        btn2 = view.findViewById(R.id.dmxradiocod);
        btn3 = view.findViewById(R.id.dmxradiotransfer);
        rb1 = view.findViewById(R.id.dtxradio1);
        rb2 = view.findViewById(R.id.dtxradio2);
        btnback = view.findViewById(R.id.dmxbtnback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailPesananFragment()).commit();
            }
        });

        cekRadioButton();
        linearClickable();
        return view;
    }

    private void cekRadioButton(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeTransfer.setVisibility(View.GONE);
                btn2.setChecked(false);
                btn3.setChecked(false);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeTransfer.setVisibility(View.GONE);
                btn1.setChecked(false);
                btn3.setChecked(false);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeTransfer.setVisibility(View.VISIBLE);
                btn2.setChecked(false);
                btn1.setChecked(false);
            }
        });
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb2.setChecked(false);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb1.setChecked(false);
            }
        });
    }

    private void linearClickable(){
        linearlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("OK");
            }
        });
    }
}