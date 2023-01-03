package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;

public class MakananFragment extends Fragment {

    Spinner spinner;
    public static TextView teksttmak;
    RecyclerView recyclerView;
    private SearchView searchView;
    HomeRvAdapter AdapterCari;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;
//    List<HomeRvModel> listDataDaftar;
//    HomeRvAdapter adapterItemDaftar;

    APIInterface apiInterface;
    private List<DataItemProduk> produkList = new ArrayList<>();

    // List Data pada Recycle View
//    void isiDataCemilan(){
//        if(listDataDaftar == null){
//            listDataDaftar = new ArrayList<>();
//        }
//        listDataDaftar.add(new HomeRvModel("CM0001", "Sundukan", "Sundukan seng paling wenak sak Nganjuk", 2000, 20, R.drawable.imagefood2));
//        listDataDaftar.add(new HomeRvModel("CM0002", "Baceman", "Baceman ndek kene paling wenak", 2000, 30, R.drawable.imagefood2));
//        listDataDaftar.add(new HomeRvModel("CM0003", "Gorengan", "pokok e enak", 1500, 40, R.drawable.imagefood));
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makanan, container, false);

        teksttmak = view.findViewById(R.id.fmakxteksttmakanan);
        spinner = view.findViewById(R.id.fmakxspinner);

        // Memanggil List Data pada Recycle View
//        MainHome mh = new MainHome();
//        if (String.valueOf(mh.set2.getText()).equals("0")){
//            System.out.println("");
//        } else if (String.valueOf(mh.set2.getText()).equals("1")){
//            retriveDataMakanan();
//            mh.set2.setText("0");
//        }

        // List Data pada Spinner
        String[] jenis = {"Makanan", "Cemilan", "Frozen Food", "Lainnya"};
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(jenis));
        ArrayAdapter<String> arrAdapt = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, arr);
        spinner.setAdapter(arrAdapt);

        retrieveDataMakanan();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                if (item == adapterView.getItemAtPosition(0)){
                    retrieveDataMakanan();
//                    listDataDaftar.clear();
//                    adapterItemDaftar.notifyDataSetChanged();
//                    adapterItemDaftar = new HomeRvAdapter(listDataDaftar,adapterItemListenerInterface);
//                    recyclerView.setAdapter(adapterItemDaftar);
                } else if (item == adapterView.getItemAtPosition(1)){
                    retrieveDataCemilan();
                } else if (item ==  adapterView.getItemAtPosition(2)){
                    retrieveDataFrozenfood();
                } else if (item ==  adapterView.getItemAtPosition(3)){
                    retrieveDataMakananLainnya();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchView = view.findViewById(R.id.search2);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FilterList(newText);
                return true;
            }
        });

        teksttmak.setVisibility(View.GONE);
        getMakananClicked();
        return view;
    }

    public boolean getMakananClicked(){
        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                InterfaceMenuFragment imf = new InterfaceMenuFragment();
                imf.setDataMenu(produkList.get(adapterPosition).getIdProduk(), produkList.get(adapterPosition).getNamaProduk(), produkList.get(adapterPosition).getDeskripsiProduk(), produkList.get(adapterPosition).getHarga(), produkList.get(adapterPosition).getStok(), produkList.get(adapterPosition).getGambar());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragtr = fragmentManager.beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, imf).commit();
            }
        };
        return true;
    }

    private void FilterList(String newText) {
        List<DataItemProduk> FilteredList = new ArrayList<>();
        for (DataItemProduk brg : produkList){
            if (brg.getNamaProduk().toLowerCase().contains(newText.toLowerCase())){
                FilteredList.add(brg);
            }
        }
        if (FilteredList.isEmpty()){
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
        }else {
            AdapterCari.setFilteredList(FilteredList);
        }
    }

    public void retrieveDataMakanan(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMakanan();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    AdapterCari = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(AdapterCari);
                    AdapterCari.notifyDataSetChanged();
                    teksttmak.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmak.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataCemilan(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveCemilan();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    AdapterCari = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(AdapterCari);
                    AdapterCari.notifyDataSetChanged();
                    teksttmak.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmak.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataFrozenfood(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveFrozenfood();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    AdapterCari = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(AdapterCari);
                    AdapterCari.notifyDataSetChanged();
                    teksttmak.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmak.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataMakananLainnya(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMakananLainnya();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    AdapterCari = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(AdapterCari);
                    AdapterCari.notifyDataSetChanged();
                    teksttmak.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmak.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }
}