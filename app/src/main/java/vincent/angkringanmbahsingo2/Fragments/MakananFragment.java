package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vincent.angkringanmbahsingo2.MainActivity.MainInterfaces;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class MakananFragment extends Fragment {

    Spinner spinner;
    public static TextView sign, datajudul, datadesc, dataharga, datastok;
    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataMakanan(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("Nasi Goreng", "Wenak tenaann, gass tukuo boss selak stok e entek diborong abang gojek", 10000, 20, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Kecap", "wis to percoyo o lek iki enak", 12000, 30, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Pedas", "dikandani ngeyel", 13000, 40, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Ayam", "wuuuuuuuuuuuuuuuuuuuuuuueeeeeeeeeeeeennnnnnnnaaaaaaaaakkkkkkkkkkkk", 15000, 15, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Spesial Mbah Singo","sssssiiiiiiiiiiiiiiippppppppppp", 20000, 70, R.drawable.imagefood2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makanan, container, false);

        spinner = (Spinner) view.findViewById(R.id.fmakxspinner);
        recyclerView = view.findViewById(R.id.fmakxrecyclemakanan);
        sign = view.findViewById(R.id.fmakxsign);
        datajudul = view.findViewById(R.id.dataxjudul);
        datadesc = view.findViewById(R.id.dataxdesc);
        dataharga = view.findViewById(R.id.dataxharga);
        datastok = view.findViewById(R.id.dataxstok);

        // List Data pada Spinner
        String[] jenis = {"Makanan", "Cemilan", "Frozen Food", "Lainnya"};
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(jenis));
        ArrayAdapter<String> arrAdapt = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, arr);
        spinner.setAdapter(arrAdapt);

        // Memanggil List Data pada Recycle View
        isiDataMakanan();

        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                datajudul.setText(listDataDaftar.get(adapterPosition).getJudul());
                datadesc.setText(listDataDaftar.get(adapterPosition).getDesc());
                dataharga.setText("Rp. "+String.valueOf(listDataDaftar.get(adapterPosition).getHarga()));
                datastok.setText(" "+String.valueOf(listDataDaftar.get(adapterPosition).getStok()));
                startActivity(new Intent(getActivity(), MainInterfaces.class));
            }
        };
        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);

        return view;
    }
}