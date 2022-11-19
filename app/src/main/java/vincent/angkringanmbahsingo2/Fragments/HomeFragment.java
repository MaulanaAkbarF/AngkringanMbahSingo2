package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class HomeFragment extends Fragment {

    Animation easeOutSineTop, easeOutQuadLeft;
    LinearLayout judul;
    ScrollView rvdatalayout;

    RecyclerView recyclerView1, recyclerView2;
    List<HomeRvModel> listDataDaftar;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
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

        // RECYCLE TERAKHIR DIBELI ------------------
        recyclerView2 = view.findViewById(R.id.hpxrvterakhir);

        // Memanggil List Data pada Recycle View
        isiDataTerakhir();

        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                Toast.makeText(getActivity(),listDataDaftar.get(adapterPosition).getJudul(), Toast.LENGTH_SHORT).show();
            }
        };
        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView2.setAdapter(adapterItemDaftar);

        return view;
    }
}