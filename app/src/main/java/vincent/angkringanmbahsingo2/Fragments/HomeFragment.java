package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

    public static TextView teksnama, teksnomor, teksemail, teksuser, dataidmenu, datajudul, datadesc, dataharga, datastok, dataimage;
    CardView btnprofil;
    RecyclerView recyclerView1, recyclerView2;
    HomeRvAdapter.AdapterItemListener adapterItemListenerInterface;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
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

        teksnama = view.findViewById(R.id.fhxtxtnama);
        teksnomor = view.findViewById(R.id.fhxtxtnomor);
        teksemail = view.findViewById(R.id.fhxtxtemail);
        teksuser = view.findViewById(R.id.fhxtxtuser);
        dataimage = view.findViewById(R.id.dataximage);
        dataidmenu = view.findViewById(R.id.dataxidmenu);
        datajudul = view.findViewById(R.id.dataxjudul);
        datadesc = view.findViewById(R.id.dataxdesc);
        dataharga = view.findViewById(R.id.dataxharga);
        datastok = view.findViewById(R.id.dataxstok);
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
        btnprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new ProfilFragment()).addToBackStack("tag").commit();
            }
        });

        getProdukClicked();
        return view;
    }

    public boolean getProdukClicked(){
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
                fragtr.replace(R.id.fragmentcontainersplash, new InterfaceHomeFragment()).addToBackStack(null).commit();
            }
        };
        return true;
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
                addData = new HomeRvAdapter(getContext(), produkList, adapterItemListenerInterface);
                recyclerView2 = getView().findViewById(R.id.hpxrvterakhir);
                recyclerView2.setHasFixedSize(true);
                recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(),3));
                recyclerView2.setAdapter(addData);
                addData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {

            }
        });
    }
}