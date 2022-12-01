package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vincent.angkringanmbahsingo2.R;

public class DetailPesananFragment extends Fragment {

    LinearLayout linearlay, btnalamat, btnmetode;
    public static TextView teksalamat, teksmetode;
    ImageView btnback;
    Button btnpesan;

    private String alamatFromDetailAlamat;
    public DetailPesananFragment(String alamat) {
        this.alamatFromDetailAlamat = alamat;
    }
    public DetailPesananFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_pesanan, container, false);

        linearlay = view.findViewById(R.id.dpxlinearlay);
        btnalamat = view.findViewById(R.id.dpxlinearalamat);
        btnmetode =  view.findViewById(R.id.dpxlinearmetode);
        teksalamat = view.findViewById(R.id.dpxtxtalamat);
        teksmetode = view.findViewById(R.id.dpxtxtmetode);
        btnback = view.findViewById(R.id.dpxbtnback);
        btnpesan = view.findViewById(R.id.dpxbtnpesan);

        if(alamatFromDetailAlamat !=null) {
            teksalamat.setText(alamatFromDetailAlamat);
        }

        btnalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailAlamatFragment()).addToBackStack("tag").commit();
            }
        });

        btnmetode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailMetodeFragment()).addToBackStack("tag").commit();
            }
        });

        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new SplashSelesaiFragment()).addToBackStack("tag").commit();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        linearClickable();
        return view;
    }

    private void linearClickable(){
        linearlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.print(".");
            }
        });
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
    }
}