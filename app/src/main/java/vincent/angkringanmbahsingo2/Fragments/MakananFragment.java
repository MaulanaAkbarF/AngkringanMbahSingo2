package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.MainActivity.MainInterfaces;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class MakananFragment extends Fragment {

    Spinner spinner;
    public static TextView datajudul, datadesc, dataharga;
    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataMakanan(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("Nasi Goreng",10000, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Kecap",12000, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Pedas",13000, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Ayam",15000, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("Nasi Goreng Spesial Mbah Singo",20000, R.drawable.imagefood2));
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
                datajudul = view.findViewById(R.id.dataxjudul);
                datadesc = view.findViewById(R.id.dataxdesc);
                dataharga = view.findViewById(R.id.dataxharga);

                datajudul.setText(listDataDaftar.get(adapterPosition).getJudul());
                datadesc.setText(listDataDaftar.get(adapterPosition).getJudul());

                Intent i = new Intent(getActivity(), MainInterfaces.class);
                i.putExtra("datajudul", (CharSequence) datajudul);
                i.putExtra("datadesc", (Parcelable) datadesc);
                startActivity(i);
            }
        };
        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);

        return view;
    }
}