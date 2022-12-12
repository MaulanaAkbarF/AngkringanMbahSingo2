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

import com.google.android.material.navigation.NavigationBarView;

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
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;

public class MinumanFragment extends Fragment {

    Spinner spinner;
    public static TextView teksttmin, dataidmenu, datajudul, datadesc, dataharga, datastok, dataimage;
    RecyclerView recyclerView;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemProduk> produkList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minuman, container, false);

        teksttmin = view.findViewById(R.id.fminxteksttminuman);
        spinner = view.findViewById(R.id.fminxspinner);
        recyclerView = view.findViewById(R.id.fminxrecycleminuman);
        dataimage = view.findViewById(R.id.dataximage);
        dataidmenu = view.findViewById(R.id.dataxidmenu);
        datajudul = view.findViewById(R.id.dataxjudul);
        datadesc = view.findViewById(R.id.dataxdesc);
        dataharga = view.findViewById(R.id.dataxharga);
        datastok = view.findViewById(R.id.dataxstok);

//        MainHome mh = new MainHome();
//        if (String.valueOf(mh.set3.getText()).equals("0")){
//            System.out.println("");
//        } else if (String.valueOf(mh.set3.getText()).equals("1")){
//            isiDataMinumanPanas();
//            mh.set3.setText("0");
//        }

        // List Data pada Spinner
        String[] jenis = {"Minuman Panas", "Minuman Dingin", "Minuman Sachet", "Herbal", "Lainnya"};
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(jenis));
        ArrayAdapter<String> arrAdapt = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, arr);
        spinner.setAdapter(arrAdapt);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Object item = adapterView.getItemAtPosition(pos);
                if (item == adapterView.getItemAtPosition(0)){
                    retrieveDataMinumanPanas();
                } else if (item == adapterView.getItemAtPosition(1)){
                    retrieveDataMinumanDingin();
                } else if (item == adapterView.getItemAtPosition(2)){
                    retrieveDataMinumanSachet();
                } else if (item == adapterView.getItemAtPosition(3)){
                    retrieveDataMinumanHerbal();
                } else if (item == adapterView.getItemAtPosition(4)){
                    retrieveDataMinumanLainnya();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        teksttmin.setVisibility(View.GONE);
        getMinumanClicked();
        return view;
    }

    public boolean getMinumanClicked(){
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
                fragtr.replace(R.id.fragmentcontainersplash, new InterfaceMinumanFragment()).addToBackStack(null).commit();
            }
        };
        return true;
    }

    public void retrieveDataMinumanPanas(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMinumanPanas();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fminxrecycleminuman);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttmin.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataMinumanDingin(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMinumanDingin();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fminxrecycleminuman);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttmin.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataMinumanSachet(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMinumanSachet();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fminxrecycleminuman);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttmin.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataMinumanHerbal(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMinumanHerbal();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fminxrecycleminuman);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttmin.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    public void retrieveDataMinumanLainnya(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetriveMinumanLainnya();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.fminxrecycleminuman);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttmin.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksttmin.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }
}