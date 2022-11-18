package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class MinumanFragment extends Fragment {

    Spinner spinner;
    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataMinuman(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("Es Teh",2500, R.drawable.imagedrink));
        listDataDaftar.add(new HomeRvModel("Es Teh Manis",3000, R.drawable.imagedrink2));
        listDataDaftar.add(new HomeRvModel("Nasi Pecel",3000, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Spesial",3000, R.drawable.imagefood));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minuman, container, false);

        spinner = (Spinner) view.findViewById(R.id.fminxspinner);
        recyclerView = view.findViewById(R.id.fminxrecycleminuman);

        // List Data pada Spinner
        String[] jenis = {"Minuman Panas", "Minuman Dingin", "Minuman Sachet", "Herbal", "Lainnya"};
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(jenis));
        ArrayAdapter<String> arrAdapt = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, arr);
        spinner.setAdapter(arrAdapt);

        // Memanggil List Data pada Recycle View
        isiDataMinuman();

        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                Toast.makeText(getActivity(),listDataDaftar.get(adapterPosition).getHarga(), Toast.LENGTH_LONG).show();
            }
        };
        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);

        return view;
    }
}