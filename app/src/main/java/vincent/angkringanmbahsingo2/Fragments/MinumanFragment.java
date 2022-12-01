package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class MinumanFragment extends Fragment {

    Spinner spinner;
    static String currency = "Rp. %,d,00";
    static String stock = "%,d";
    public static TextView dataidmenu, datajudul, datadesc, dataharga, datastok;
    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataMinumanPanas(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("MP0001", "Teh Anget", "desc1", 2500, 400, R.drawable.imagedrink));
        listDataDaftar.add(new HomeRvModel("MP0002", "Teh Manis","desc2", 3000, 450, R.drawable.imagedrink2));
        listDataDaftar.add(new HomeRvModel("MP0003", "Jeruk Anget", "desc3", 3000, 460, R.drawable.imagedrink3));
        listDataDaftar.add(new HomeRvModel("MP0004", "Jahe Anget", "desc4", 4000, 660, R.drawable.imagedrink));
    }

    void isiDataMinumanDingin(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("MD0001", "Es Teh", "desc1", 2500, 400, R.drawable.imagedrink));
        listDataDaftar.add(new HomeRvModel("MD0002", "Es Teh Manis","desc2", 3000, 450, R.drawable.imagedrink2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minuman, container, false);

        spinner = (Spinner) view.findViewById(R.id.fminxspinner);
        recyclerView = view.findViewById(R.id.fminxrecycleminuman);
        dataidmenu = view.findViewById(R.id.dataxidmenu);
        datajudul = view.findViewById(R.id.dataxjudul);
        datadesc = view.findViewById(R.id.dataxdesc);
        dataharga = view.findViewById(R.id.dataxharga);
        datastok = view.findViewById(R.id.dataxstok);

        // List Data pada Spinner
        String[] jenis = {"Minuman Panas", "Minuman Dingin", "Minuman Sachet", "Herbal", "Lainnya"};
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(jenis));
        ArrayAdapter<String> arrAdapt = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, arr);
        spinner.setAdapter(arrAdapt);

        MainHome mh = new MainHome();
        if (String.valueOf(mh.set3.getText()).equals("0")){
            System.out.println("");
        } else if (String.valueOf(mh.set3.getText()).equals("1")){
            isiDataMinumanPanas();
            mh.set3.setText("0");
        }
//        adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
//        recyclerView.setAdapter(adapterItemDaftar);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
//                Object item = adapterView.getItemAtPosition(pos);
//                if (item == adapterView.getItemAtPosition(0)){
//                    listDataDaftar.clear();
//                    adapterItemDaftar.notifyDataSetChanged();
//                    isiDataMinumanPanas();
//                    adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
//                    recyclerView.setAdapter(adapterItemDaftar);
//                } else if (item == adapterView.getItemAtPosition(1)){
//                    listDataDaftar.clear();
//                    adapterItemDaftar.notifyDataSetChanged();
//                    isiDataMinumanDingin();
//                    adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
//                    recyclerView.setAdapter(adapterItemDaftar);
//                }
//                // Bingung menu ngombe ne opo ae
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        getMinumanClicked();
        return view;
    }

    public boolean getMinumanClicked(){
        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                dataidmenu.setText(listDataDaftar.get(adapterPosition).getIdmenu());
                datajudul.setText(listDataDaftar.get(adapterPosition).getJudul());
                datadesc.setText(listDataDaftar.get(adapterPosition).getDesc());
                dataharga.setText(String.valueOf(listDataDaftar.get(adapterPosition).getHarga()));
                datastok.setText(String.valueOf(listDataDaftar.get(adapterPosition).getStok()));
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new InterfaceMinumanFragment()).addToBackStack("tag").commit();
            }
        };
        return true;
    }
}