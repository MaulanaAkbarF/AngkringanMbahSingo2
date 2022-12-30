package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.CartRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HistRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class KeranjangFragment extends Fragment {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut, fadein, fadeout;
    public static TextView btnfilter, btnubah, btnhapussemua, btnselesai, btnhapusfilter, teksttk;
    Button btnbeli;
    RecyclerView recyclerView;
    Dialog dialog;
    DatePickerDialog picker;
    CartRvAdapter.AdapterItemListener adapterItemListenerInterface;

    private String idTransaksiKeranjang;
    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemTransaksi> dataListKeranjang = new ArrayList<>();
    private List<DataItemTransaksi> autoKodeKrj = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        // Inisiasi komponen animasi
        btnfilter = view.findViewById(R.id.fkerxbtnfilter);
        btnubah = view.findViewById(R.id.fkerxbtnubah);
        btnhapussemua = view.findViewById(R.id.fkerxbtnhapussemua);
        btnselesai = view.findViewById(R.id.fkerxbtnselesai);
        btnhapusfilter = view.findViewById(R.id.fkerxbtnhapusfilter);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);
        fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        recyclerView = view.findViewById(R.id.fkerxrecyclekeranjang);
        btnbeli = view.findViewById(R.id.fkerxbtnbeli);
        teksttk = view.findViewById(R.id.fkerxteksttkeranjang);
        btnubah.setVisibility(View.VISIBLE);
        btnfilter.setVisibility(View.VISIBLE);
        btnhapussemua.setVisibility(View.GONE);
        btnselesai.setVisibility(View.GONE);

        // Memanggil List Data pada Recycle View
//        MainHome mh = new MainHome();
//        if (String.valueOf(mh.set5.getText()).equals("0")){
//            System.out.println("");
//        } else if (String.valueOf(mh.set5.getText()).equals("1")){
//            isiDataKeranjang();
//            mh.set5.setText("0");
//        }

        btnbeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOutSemuaKeranjang();
            }
        });

        getKeranjangClicked();
        getDataKeranjang();
        ubahClickable();
        hapusFilterClickable();
        hapusSemuaClickable();
        selesaiClickable();
        filterClickable();
        return view;
    }

    public boolean getKeranjangClicked(){
        adapterItemListenerInterface = new CartRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                Toast.makeText(getActivity(), dataListKeranjang.get(adapterPosition).getNamaProduk(), Toast.LENGTH_SHORT).show();
            }
        };
        return true;
    }

    public void getDataKeranjang(){
        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> transaksiCall = apiInterface.keranjangList(hfg.teksuser.getText().toString());
        transaksiCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                dataListKeranjang = response.body().getData();
                if (dataListKeranjang != null) {
                    addData = new CartRvAdapter(getContext(), dataListKeranjang, adapterItemListenerInterface);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttk.setVisibility(View.GONE);
                    btnhapusfilter.setVisibility(View.GONE);
                } else {
                    teksttk.setVisibility(View.VISIBLE);
                    btnhapusfilter.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Anda tidak memiliki List Keranjang", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkOutSemuaKeranjang(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> transaksiCall = apiInterface.autoKodeTransaksi();
        transaksiCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                autoKodeKrj = response.body().getData();
                idTransaksiKeranjang = autoKodeKrj.get(0).getIdTransaksi();
                if (idTransaksiKeranjang != null){
                    HomeFragment hfg = new HomeFragment();
                    apiInterface = API.getService().create(APIInterface.class);
                    Call<ResponseTransaksi> transaksiCall = apiInterface.pesananFromKeranjang(idTransaksiKeranjang, hfg.teksuser.getText().toString());
                    transaksiCall.enqueue(new Callback<ResponseTransaksi>() {
                        @Override
                        public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                            DetailPesananFragment dpf = new DetailPesananFragment();
                            dpf.setDataIdTransaksi(idTransaksiKeranjang, 1);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragtr = fragmentManager.beginTransaction();
                            fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
                        }
                        @Override
                        public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "ID Transaksi kosong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean ubahClickable(){
        btnubah.setOnClickListener(view -> {
            btnhapussemua.setVisibility(View.VISIBLE);
            btnhapussemua.startAnimation(easeOutQuadRight);
            btnselesai.setVisibility(View.VISIBLE);
            btnubah.setVisibility(View.GONE);
            btnfilter.setVisibility(View.GONE);
            btnfilter.startAnimation(easeOutQuadLeftOut);
            btnubah.startAnimation(easeOutQuadLeftOut);
            btnselesai.startAnimation(easeOutQuadRight);
        });
        return false;
    }

    public void hapusSemuaClickable(){
        btnhapussemua.setOnClickListener(view -> {
            showAlertHapusSemua();
            dialog.show();
        });
    }

    public boolean selesaiClickable(){
        btnselesai.setOnClickListener(view -> {
            btnhapussemua.setVisibility(View.GONE);
            btnhapussemua.startAnimation(easeOutQuadRightOut);
            btnselesai.setVisibility(View.GONE);
            btnubah.setVisibility(View.VISIBLE);
            btnfilter.setVisibility(View.VISIBLE);
            btnfilter.startAnimation(easeOutQuadLeft);
            btnubah.startAnimation(easeOutQuadLeft);
            btnselesai.startAnimation(easeOutQuadRightOut);
        });
        return false;
    }

    private void filterClickable(){
        btnfilter.setOnClickListener(view -> {
            showAlertFilter1();
            dialog.show();
        });
    }

    private void showAlertHapusSemua(){
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_hapussemuakeranjang,null);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        batal.setOnClickListener(view1 -> {
            HomeFragment hfg = new HomeFragment();
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> riwayatCall = apiInterface.hapusSemuaKeranjang(hfg.teksuser.getText().toString());
            riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    dataListKeranjang.clear();
                    addData = new CartRvAdapter(getContext(), dataListKeranjang, adapterItemListenerInterface);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    dataListKeranjang = null;
                    teksttk.setVisibility(View.VISIBLE);
                    btnhapussemua.setVisibility(View.GONE);
                    btnhapussemua.startAnimation(easeOutQuadRightOut);
                    Toast.makeText(getActivity(), "Keranjang berhasil dikosongkan!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
        kirim.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void showAlertFilter1(){
        Button batal, kirim;
        RadioButton rBtn2, rBtn3;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_filter2,null);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        rBtn2 = view.findViewById(R.id.daxradiomakanan);
        rBtn3 = view.findViewById(R.id.daxradiominuman);
        builder.setView(view);
        dialog = builder.create();
        rBtn2.setOnClickListener(view12 -> {
            rBtn3.setChecked(false);
        });
        rBtn3.setOnClickListener(view13 -> {
            rBtn2.setChecked(false);
        });

        kirim.setOnClickListener(view15 -> {
            if (rBtn2.isChecked()){
                HomeFragment hfg = new HomeFragment();
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> riwayatCall = apiInterface.filterRiwayatMakanan(hfg.teksuser.getText().toString());
                riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        dataListKeranjang = response.body().getData();
                        if (dataListKeranjang != null) {
                            addData = new CartRvAdapter(getContext(), dataListKeranjang, adapterItemListenerInterface);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(addData);
                            addData.notifyDataSetChanged();
                            btnhapusfilter.setVisibility(View.VISIBLE);
                            btnhapusfilter.startAnimation(fadein);
                        } else {
                            Toast.makeText(getActivity(), "Tidak ada menu Makanan di Keranjang Anda", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            } else if (rBtn3.isChecked()){
                HomeFragment hfg = new HomeFragment();
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> riwayatCall = apiInterface.filterRiwayatMinuman(hfg.teksuser.getText().toString());
                riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        dataListKeranjang = response.body().getData();
                        if (dataListKeranjang != null) {
                            addData = new CartRvAdapter(getContext(), dataListKeranjang, adapterItemListenerInterface);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(addData);
                            addData.notifyDataSetChanged();
                            btnhapusfilter.setVisibility(View.VISIBLE);
                            btnhapusfilter.startAnimation(fadein);
                        } else {
                            Toast.makeText(getActivity(), "Tidak ada menu Minuman di Keranjang Anda", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            } else {
                dialog.dismiss();
            }
        });
        batal.setOnClickListener(view16 -> dialog.dismiss());
    }

    private void hapusFilterClickable(){
        btnhapusfilter.setOnClickListener(view -> {
            getDataKeranjang();
            btnhapusfilter.startAnimation(fadeout);
            btnhapusfilter.setVisibility(View.GONE);
        });
    }
}