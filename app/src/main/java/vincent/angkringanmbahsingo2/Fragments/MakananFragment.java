package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class MakananFragment extends Fragment {

    Spinner spinner;
    public static TextView dataidmenu, datajudul, datadesc, dataharga, datastok;
    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemProduk> produkList = new ArrayList<>();

    // List Data pada Recycle View
    void isiDataMakanan(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("MK0001", "Nasi Goreng", "Wenak tenaann, gass tukuo boss selak stok e entek diborong abang gojek", 10000, 20, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("MK0002", "Nasi Goreng Kecap", "wis to percoyo o lek iki enak", 12000, 30, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("MK0003", "Nasi Goreng Pedas", "dikandani ngeyel", 13000, 40, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("MK0004", "Nasi Goreng Ayam", "wuuuuuuuuuuuuuuuuuuuuuuueeeeeeeeeeeeennnnnnnnaaaaaaaaakkkkkkkkkkkk", 15000, 15, R.drawable.imagefood));
        listDataDaftar.add(new HomeRvModel("MK0005", "Nasi Goreng Spesial Mbah Singo","sssssiiiiiiiiiiiiiiippppppppppp", 20000, 70, R.drawable.imagefood2));
    }

    void isiDataCemilan(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HomeRvModel("CM0001", "Sundukan", "Sundukan seng paling wenak sak Nganjuk", 2000, 20, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("CM0002", "Baceman", "Baceman ndek kene paling wenak", 2000, 30, R.drawable.imagefood2));
        listDataDaftar.add(new HomeRvModel("CM0003", "Gorengan", "pokok e enak", 1500, 40, R.drawable.imagefood));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makanan, container, false);

        spinner = (Spinner) view.findViewById(R.id.fmakxspinner);
        dataidmenu = view.findViewById(R.id.dataxidmenu);
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
        MainHome mh = new MainHome();
        if (String.valueOf(mh.set2.getText()).equals("0")){
            System.out.println("");
        } else if (String.valueOf(mh.set2.getText()).equals("1")){
            retriveDataMakanan();
            mh.set2.setText("0");
        }
        retriveDataMakanan();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                if (item == adapterView.getItemAtPosition(0)){
                    retriveDataMakanan();
                } else if (item == adapterView.getItemAtPosition(1)){
                    retriveDataCemilan();
                }
                // Bingung menu panganan ne opo ae
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getMakananClicked();
        return view;
    }

    public boolean getMakananClicked(){
        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
//                dataidmenu.setText(produkList.get(adapterPosition).getIdmenu());
                datajudul.setText(produkList.get(adapterPosition).getNamaProduk());
//                datadesc.setText(listDataDaftar.get(adapterPosition).getDesc());
                dataharga.setText(String.valueOf(produkList.get(adapterPosition).getHarga()));
                datastok.setText(String.valueOf(produkList.get(adapterPosition).getStok()));

                MakananFragment makfrag = new MakananFragment();
                FragmentTransaction fragtr = getActivity().getSupportFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new InterfaceMakananFragment()).addToBackStack(null).commit();
            }
        };
        return true;
    }

    public void retriveDataMakanan(){
        APIInterface apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMakanan();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
                recyclerView.setAdapter(addData);
                addData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retriveDataCemilan(){
        APIInterface apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveCemilan();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
                recyclerView.setAdapter(addData);
                addData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }
}