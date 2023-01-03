package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemLogin;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;

public class HomeFragment extends Fragment {

    Animation easeOutSineTop, easeOutQuadLeft;
    LinearLayout judul;
    ScrollView rvdatalayout;
    private SearchView searchView;
    HomeRvAdapter AdapterCari;

    public static TextView teksttpr, tekstttr, teksnama, teksnomor, teksemail, teksuser, teksalamat;
    CardView btnprofil;
    RecyclerView recyclerView1, recyclerView2;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    APIInterface apiInterface;
    private List<DataItemLogin> dataUserHome = new ArrayList<>();
    private List<DataItemProduk> produkList = new ArrayList<>();

    public static String hssnamakamu;
    public String namamu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisiasi komponen animasi
        judul = view.findViewById(R.id.fhxlinearheader);
        rvdatalayout = view.findViewById(R.id.fhxscrollviewrv);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);

        judul.startAnimation(easeOutSineTop);
        rvdatalayout.startAnimation(easeOutQuadLeft);

        teksttpr = view.findViewById(R.id.fhxteksttpromo);
        tekstttr = view.findViewById(R.id.fhxteksttterakhir);
        teksnama = view.findViewById(R.id.fhxtxtnama);
        teksnomor = view.findViewById(R.id.fhxtxtnomor);
        teksemail = view.findViewById(R.id.fhxtxtemail);
        teksuser = view.findViewById(R.id.fhxtxtuser);
        teksalamat = view.findViewById(R.id.fhxtxtalamat);
        btnprofil = view.findViewById(R.id.fhxbtnprofil);

        // Memanggil List Data pada Recycle View
//        MainHome mh = new MainHome();
//        if (String.valueOf(mh.set1.getText()).equals("0")){
//            System.out.println("");
//        } else if (String.valueOf(mh.set1.getText()).equals("1")){
//            mh.set1.setText("0");
//        }

        Bundle extras = getActivity().getIntent().getExtras();
        namamu = extras.getString("datanama");
        teksnama.setText(namamu);

        getDataLoginRetrofit();
        retriveDataProduk();
        btnprofil.setOnClickListener(view1 -> {
            FragmentTransaction fragtr = getFragmentManager().beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, new ProfilFragment()).addToBackStack("tag").commit();
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

        tekstttr.setVisibility(View.GONE);
        getProdukClicked();
        return view;
    }

    public boolean getProdukClicked(){
        adapterItemListenerInterface = adapterPosition -> {
            InterfaceMenuFragment imf = new InterfaceMenuFragment();
            imf.setDataMenu(produkList.get(adapterPosition).getIdProduk(), produkList.get(adapterPosition).getNamaProduk(), produkList.get(adapterPosition).getDeskripsiProduk(), produkList.get(adapterPosition).getHarga(), produkList.get(adapterPosition).getStok(), produkList.get(adapterPosition).getGambar());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, imf).commit();
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

    private void getDataLoginRetrofit() {
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseLogin> loginCall = apiInterface.setDataHome(teksnama.getText().toString());
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                dataUserHome = response.body().getData();
                teksnama.setText(dataUserHome.get(0).getNamaLengkap());
                teksnomor.setText("+62"+dataUserHome.get(0).getNoHp());
                teksemail.setText(dataUserHome.get(0).getEmail());
                teksuser.setText(dataUserHome.get(0).getUsername());
                teksalamat.setText(dataUserHome.get(0).getAlamat());
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retriveDataProduk(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseProduk> produkCall = apiInterface.getRetrive();
        produkCall.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                produkList = response.body().getData();
                if (produkList != null) {
                    AdapterCari = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                    recyclerView2 = getView().findViewById(R.id.hpxrvterakhir);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    recyclerView2.setAdapter(AdapterCari);
                    AdapterCari.notifyDataSetChanged();
                    tekstttr.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                } else {
                    tekstttr.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }
}