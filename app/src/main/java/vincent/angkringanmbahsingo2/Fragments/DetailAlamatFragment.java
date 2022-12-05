package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import vincent.angkringanmbahsingo2.Fragments.LupaPasswordFragment;
import vincent.angkringanmbahsingo2.MainActivity.Lokasi;
import vincent.angkringanmbahsingo2.R;

public class DetailAlamatFragment extends Fragment {

    View includeAntar, includeAmbil;
    LinearLayout linearlay;
    RadioButton btn1, btn2;
    Button btntetapkan;
    public TextView label1, label2, txtalamat;
    ImageView btnback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_alamat, container, false);
        includeAntar = view.findViewById(R.id.daxincludeantar);
        includeAmbil = view.findViewById(R.id.daxincludeambil);

        linearlay = view.findViewById(R.id.daxlinearlay);
        btn1 = view.findViewById(R.id.daxradioalamat);
        btn2 = view.findViewById(R.id.daxradioambil);
        btntetapkan = view.findViewById(R.id.daxbtntetapkan);
        txtalamat = view.findViewById(R.id.daxdataalamat3);
        btnback = view.findViewById(R.id.daxbtnback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailPesananFragment()).commit();
            }
        });

        cekRadioButton();
        btnTetapkanClickable();
        linearlayClickable();
        return view;
    }

    private void cekRadioButton(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeAntar.setVisibility(View.VISIBLE);
                includeAmbil.setVisibility(View.GONE);
                btn2.setChecked(false);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeAntar.setVisibility(View.GONE);
                includeAmbil.setVisibility(View.VISIBLE);
                btn1.setChecked(false);
            }
        });
    }

    public void btnTetapkanClickable() {
        btntetapkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Lokasi.class));
//                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
//                fragtr.replace(R.id.fragmentcontainersplash, new DetailPesananFragment(txtalamat.getText().toString())).commit();
            }
        });
    }

    private void linearlayClickable(){
        linearlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.print(".");
            }
        });
    }
}