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
import android.widget.ImageView;
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
    public static TextView teksttmak, dataidmenu, datajudul, datadesc, dataharga, datastok, dataimage;
//    List<HomeRvModel> listDataDaftar;
    RecyclerView recyclerView;
//    HomeRvAdapter adapterItemDaftar;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
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
        dataimage = view.findViewById(R.id.dataximage);
        dataidmenu = view.findViewById(R.id.dataxidmenu);
        datajudul = view.findViewById(R.id.dataxjudul);
        datadesc = view.findViewById(R.id.dataxdesc);
        dataharga = view.findViewById(R.id.dataxharga);
        datastok = view.findViewById(R.id.dataxstok);

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
//                    listDataDaftar.clear();
//                    adapterItemDaftar.notifyDataSetChanged();
                    retrieveDataMakanan();
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

        teksttmak.setVisibility(View.GONE);
        getMakananClicked();
        return view;
    }

    public boolean getMakananClicked(){
        adapterItemListenerInterface = new HomeRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                dataimage.setText(produkList.get(adapterPosition).getGambar());
                dataidmenu.setText(produkList.get(adapterPosition).getIdProduk());
                datajudul.setText(produkList.get(adapterPosition).getNamaProduk());
                datadesc.setText(produkList.get(adapterPosition).getDeskripsiProduk());
                dataharga.setText(String.valueOf(produkList.get(adapterPosition).getHarga()));
                datastok.setText(String.valueOf(produkList.get(adapterPosition).getStok()));

                FragmentTransaction fragtr = getActivity().getSupportFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new InterfaceMakananFragment()).addToBackStack(null).commit();
            }
        };
        return true;
    }

    public void retrieveDataMakanan(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMakanan();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
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
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
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
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
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
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fmakxrecyclemakanan);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
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