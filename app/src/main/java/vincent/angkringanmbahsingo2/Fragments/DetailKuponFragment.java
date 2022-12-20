package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemKupon;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseKupon;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.KuponRvAdapter;

public class DetailKuponFragment extends Fragment implements Backpressedlistener {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut, fadein, fadeout;
    RecyclerView recyclerView, recyclerView2;
    KuponRvAdapter.AdapterItemListener adapterItemListenerInterface;
    public static Backpressedlistener backpressedlistener;

    DetailPesananFragment dpf = new DetailPesananFragment();
    ScrollView scrollanimate, scroll1, scroll2;
    LinearLayout tampilkan1, tampilkan2;
    TextView minimalbelanja;
    ImageView btnback;
    Button btnpilih;
    static String currency = "Rp. %,d";
    Slide slide = new Slide();

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    List<DataItemKupon> kuponList = new ArrayList<>();
    List<DataItemKupon> kuponList2 = new ArrayList<>();

    private String dataIdTransaksi, dataAlamat, dataMetode, idkupon, idkupon2, namakupon, namakupon2;
    private int check, subtotal, nilai, nilai2, minimal, minimal2;
    public void setDataDefaultPesanan(String dataIdTransaksi, String dataAlamat, String dataMetode, int subtotal, int check) {
        this.dataIdTransaksi = dataIdTransaksi;
        this.dataAlamat = dataAlamat;
        this.dataMetode = dataMetode;
        this.subtotal = subtotal;
        this.check = check;
    }

    public void setDataMetode(String dataMetode) {this.dataMetode = dataMetode;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_detail_kupon, container, false);

        // Inisiasi komponen animasi
        scrollanimate = view.findViewById(R.id.dkxscrollanimate);
        recyclerView = view.findViewById(R.id.dkxrecycleko);
        recyclerView2 = view.findViewById(R.id.dkxrecycleks);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);
        fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        minimalbelanja = view.findViewById(R.id.dkxtxtsubtotal);
        scroll1 = view.findViewById(R.id.dpxscrollrv);
        scroll2 = view.findViewById(R.id.dpxscrollrv2);
        tampilkan1 = view.findViewById(R.id.dpxlineartampilkan);
        tampilkan2 = view.findViewById(R.id.dpxlineartampilkan2);
        btnback = view.findViewById(R.id.dkxbtnback);
        btnpilih = view.findViewById(R.id.dkxbtnpilih);

        btnback.setOnClickListener(view1 -> {
            dpf.setDataIdTransaksi(dataIdTransaksi, check);
            dpf.setDataAlamat(dataAlamat);
            dpf.setDataMetode(dataMetode);
            scrollanimate.startAnimation(easeOutQuadRight);
            recyclerView.startAnimation(easeOutQuadLeft);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
        });

        btnpilih.setOnClickListener(view12 -> {
            if (minimal == 0 && minimal2 == 0) {
                System.out.println("null");
            } else if (minimal > subtotal || minimal > subtotal && minimal2 == 0){
                Toast.makeText(getActivity(), "Tidak mencapai minimal belanja. Kupon Gratis Ongkir tidak bisa digunakan", Toast.LENGTH_SHORT).show();
            } else if (minimal2 > subtotal || minimal2 > subtotal && minimal == 0){
                Toast.makeText(getActivity(), "Tidak mencapai minimal belanja. Kupon Uang Kembali tidak bisa digunakan", Toast.LENGTH_SHORT).show();
            } else {
                dpf.setDataIdTransaksi(dataIdTransaksi, check);
                dpf.setDataAlamat(dataAlamat);
                dpf.setDataMetode(dataMetode);
                dpf.setDataKupon(idkupon, idkupon2, namakupon, namakupon2, nilai, nilai2);
                scrollanimate.startAnimation(easeOutQuadRight);
                recyclerView.startAnimation(easeOutQuadLeft);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragtr = fragmentManager.beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
            }
        });

        slide.setDuration(450);
        slide.setSlideEdge(Gravity.LEFT);
        TransitionManager.beginDelayedTransition(container, slide);

        scrollanimate.startAnimation(easeOutQuadRight);
        recyclerView.startAnimation(easeOutQuadLeft);
        minimalbelanja.setText("Minimal Belanja Anda : "+String.format(currency, subtotal));
        getDataKuponOngkir();
        getDataKuponCashback();
        expandScrollViewKuponOngkir();
        expandScrollViewKuponCashback();
        return view;
    }

    private void getDataKuponOngkir(){
        KuponRvAdapter.setDataKuponSelected setdataKupon = (idkupon, namakupon, nilai, minimal) -> {
            DetailKuponFragment.this.idkupon = idkupon;
            DetailKuponFragment.this.namakupon = namakupon;
            DetailKuponFragment.this.nilai = nilai;
            DetailKuponFragment.this.minimal = minimal;
            System.out.println("Nilai Ongkir = "+DetailKuponFragment.this.minimal);
        };


        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseKupon> kuponCall = apiInterface.retrieveKuponOngkir(hfg.teksuser.getText().toString());
        kuponCall.enqueue(new Callback<ResponseKupon>() {
            @Override
            public void onResponse(Call<ResponseKupon> call, Response<ResponseKupon> response) {
                kuponList = response.body().getData();
                if (kuponList != null) {
                    addData = new KuponRvAdapter(getContext(), kuponList, setdataKupon, null, adapterItemListenerInterface);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Anda tidak memiliki Kupon Gratis Ongkir", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKupon> call, Throwable t) {

            }
        });
    }

    private void getDataKuponCashback(){
        KuponRvAdapter.setDataKuponSelected2 setdataKupon2 = (idkupon, namakupon, nilai, minimal) -> {
            DetailKuponFragment.this.idkupon2 = idkupon;
            DetailKuponFragment.this.namakupon2 = namakupon;
            DetailKuponFragment.this.nilai2 = nilai;
            DetailKuponFragment.this.minimal2 = minimal;
            System.out.println("Nilai Cashback = "+DetailKuponFragment.this.minimal2);
        };


        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseKupon> kuponCall = apiInterface.retrieveKuponCashback(hfg.teksuser.getText().toString());
        kuponCall.enqueue(new Callback<ResponseKupon>() {
            @Override
            public void onResponse(Call<ResponseKupon> call, Response<ResponseKupon> response) {
                kuponList2 = response.body().getData();
                if (kuponList2 != null) {
                    addData = new KuponRvAdapter(getContext(), kuponList2, null, setdataKupon2, adapterItemListenerInterface);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setAdapter(addData);
                    addData.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Anda tidak memiliki Kupon Uang Kembali", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKupon> call, Throwable t) {

            }
        });
    }

    private void expandScrollViewKuponOngkir(){
        tampilkan1.setOnClickListener(view -> {
            int totalHeight = 0;
            for (int i = 0; i < scroll1.getChildCount(); i++) {
                View child = scroll1.getChildAt(i);
                child.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalHeight += child.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = scroll1.getLayoutParams();
            params.height = totalHeight;
            scroll1.setLayoutParams(params);
            tampilkan1.setVisibility(View.GONE);
        });
    }

    private void expandScrollViewKuponCashback(){
        tampilkan2.setOnClickListener(view -> {
            int totalHeight = 0;
            for (int i = 0; i < scroll2.getChildCount(); i++) {
                View child = scroll2.getChildAt(i);
                child.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalHeight += child.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = scroll2.getLayoutParams();
            params.height = totalHeight;
            scroll2.setLayoutParams(params);
            tampilkan2.setVisibility(View.GONE);
        });
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
        dpf.setDataIdTransaksi(dataIdTransaksi, check);
        dpf.setDataAlamat(dataAlamat);
        dpf.setDataMetode(dataMetode);
        scrollanimate.startAnimation(easeOutQuadRight);
        recyclerView.startAnimation(easeOutQuadLeft);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragtr = fragmentManager.beginTransaction();
        fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
    }
}