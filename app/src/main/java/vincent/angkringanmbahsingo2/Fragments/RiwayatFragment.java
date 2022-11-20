package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HistRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class RiwayatFragment extends Fragment {

    public static TextView datajudul, datatanggal, dataharga, datajumlah;
    List<HistRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HistRvAdapter adapterItemDaftar;
    HistRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataRiwayat(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HistRvModel("Nasi Goreng", 10000, 20, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Kecap", 12000, 30, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Pedas", 13000, 40, R.drawable.imagefood2));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Ayam", 15000, 15, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Spesial Mbah Singo",20000, 70000, R.drawable.imagefood2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        recyclerView = view.findViewById(R.id.friwxrecycleriwayat);
        datajudul = view.findViewById(R.id.dataxjudul);
//        datatanggal = view.findViewById(R.id.dataxtanggal);
        dataharga = view.findViewById(R.id.dataxharga);
        datajumlah = view.findViewById(R.id.dataxstok);

        isiDataRiwayat();
        adapterItemDaftar = new HistRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);

        getRiwayatClicked();
        return view;
    }

    public boolean getRiwayatClicked(){
        adapterItemListenerInterface = new HistRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
//                datajudul.setText(listDataDaftar.get(adapterPosition).getJudul());
////                datatanggal.setText(listDataDaftar.get(adapterPosition).getTanggal());
//                dataharga.setText(String.valueOf(listDataDaftar.get(adapterPosition).getHarga()));
//                datajumlah.setText(String.valueOf(listDataDaftar.get(adapterPosition).getJumlah()));
//                startActivity(new Intent(getActivity(), InterfaceMakanan.class));
                Toast.makeText(getActivity(), "Dipilih", Toast.LENGTH_SHORT).show();
            }
        };
        return true;
    }
}