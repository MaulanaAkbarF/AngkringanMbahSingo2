package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class MakananFragment extends Fragment {

    Spinner spinner;
    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataMakanan(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("Nasi Goreng",10000));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Kecap",12000));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Pedas",13000));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Ayam",15000));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Spesial Mbah Singo",20000));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makanan, container, false);

        spinner = (Spinner) view.findViewById(R.id.fmakxspinner);
        recyclerView = view.findViewById(R.id.fmakxrecyclemakanan);

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
                Toast.makeText(getActivity(),listDataDaftar.get(adapterPosition).getJudul(), Toast.LENGTH_SHORT).show();
            }
        };
        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);

        return view;
    }
}