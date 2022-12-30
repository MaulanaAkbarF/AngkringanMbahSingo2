package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Dependencies.AdapterCari;
import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.R;

public class CariMenuFragment extends Fragment implements Backpressedlistener {

    Animation easeOutSineTop, easeOutSineTopOut;
    private List<DataItemProduk> produkList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    AdapterCari adaptercr;

    public static Backpressedlistener backpressedlistener;
    RecyclerView recyclerViewSrc;
    FrameLayout frameanimate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cari_menu, container, false);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineTopOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top_out);

        frameanimate = view.findViewById(R.id.searchframe);
        recyclerViewSrc = view.findViewById(R.id.Tvrecycleview12);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSrc.setLayoutManager(layoutManager);

        retriveCari();
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

        frameanimate.startAnimation(easeOutSineTop);
        return view;
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
            adaptercr.setFilteredList(FilteredList);
        }
    }

    public void retriveCari(){
        APIInterface apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetrive();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                adaptercr = new AdapterCari(getActivity(),produkList);
                recyclerViewSrc.setAdapter(adaptercr);
                adaptercr.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
    }

    @Override
    public void onPause() {
        backpressedlistener=null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        backpressedlistener=this;
    }

    @Override
    public void onBackPressed() {
        frameanimate.startAnimation(easeOutSineTopOut);
        closeFragment();
    }
}