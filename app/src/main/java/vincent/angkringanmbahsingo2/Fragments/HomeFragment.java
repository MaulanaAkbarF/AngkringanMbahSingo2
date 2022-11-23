package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class HomeFragment extends Fragment {

    Animation easeOutSineTop, easeOutQuadLeft;
    LinearLayout judul;
    ScrollView rvdatalayout;

    public static TextView datajudul, datadesc, dataharga, datastok;
    CardView btnprofil;
    RecyclerView recyclerView1, recyclerView2;
    List<HomeRvModel> listDataDaftar;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data Promo pada Recycle View
    void isiDataPromo(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("Nasi Goreng", "Wenak tenaann, gass tukuo boss selak stok e entek diborong abang gojek", 10000, 20, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Kecap", "wis to percoyo o lek iki enak", 12000, 30, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Pedas", "dikandani ngeyel", 13000, 40, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Ayam", "wuuuuuuuuuuuuuuuuuuuuuuueeeeeeeeeeeeennnnnnnnaaaaaaaaakkkkkkkkkkkk", 15000, 15, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Spesial Mbah Singo","sssssiiiiiiiiiiiiiiippppppppppp", 20000, 70, R.drawable.imagefood2));
    }

    // List Data Terakhir Dibeli pada Recycle View
    void isiDataTerakhir(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("Pecel", "deskripsi1", 8000, 210, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("Pecel Lele", "deskripsi2", 12000, 240, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Rice with Peanut Sauce", "deskripsi3", 80000, 330, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("Rice with Peanut Sauce and Fried Catfish","deskripsi4", 120000, 385, R.drawable.imagefood2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisiasi komponen animasi
        judul = (LinearLayout) view.findViewById(R.id.fhxlinearheader);
        rvdatalayout = (ScrollView) view.findViewById(R.id.fhxscrollviewrv);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);

        judul.startAnimation(easeOutSineTop);
        rvdatalayout.startAnimation(easeOutQuadLeft);

        // RECYCLE PROMOSI ------------------
        recyclerView1 = view.findViewById(R.id.hpxrvpromo);

        // RECYCLE TERAKHIR DIBELI ------------------
        recyclerView2 = view.findViewById(R.id.hpxrvterakhir);

        datajudul = view.findViewById(R.id.dataxjudul);
        datadesc = view.findViewById(R.id.dataxdesc);
        dataharga = view.findViewById(R.id.dataxharga);
        datastok = view.findViewById(R.id.dataxstok);
        btnprofil = view.findViewById(R.id.fhxbtnprofil);

        // Memanggil List Data pada Recycle View
        MainHome mh = new MainHome();
        if (String.valueOf(mh.set1.getText()).equals("0")){
            System.out.println("");
        } else if (String.valueOf(mh.set1.getText()).equals("1")){
            isiDataTerakhir();
            mh.set1.setText("0");
        }

        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                Toast.makeText(getActivity(),listDataDaftar.get(adapterPosition).getJudul(), Toast.LENGTH_SHORT).show();
            }
        };
        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView2.setAdapter(adapterItemDaftar);

        btnprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new ProfilFragment()).addToBackStack("tag").commit();
            }
        });

        return view;
    }
}