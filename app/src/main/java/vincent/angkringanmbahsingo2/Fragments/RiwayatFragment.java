package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.MainActivity.MainLogin;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.AntrianRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HistRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class RiwayatFragment extends Fragment {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut, fadein, fadeout;
    public static TextView btnfilter, btnubah, btnhapussemua, btnselesai, btnhapusfilter, teksttr;
    ConstraintLayout consoption;
    CardView cardleft, cardright;
    RecyclerView recyclerView;
    HistRvAdapter.AdapterItemListener adapterItemListenerInterface;
    AntrianRvAdapter.AdapterItemListener adapterItemListenerInterface2;
    Dialog dialog;
    DatePickerDialog picker;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemTransaksi> riwayatList = new ArrayList<>();
    private List<DataItemTransaksi> antrianList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        // Inisiasi komponen animasi
        consoption = view.findViewById(R.id.consoption);
        btnfilter = view.findViewById(R.id.friwxbtnfilter);
        btnubah = view.findViewById(R.id.friwxbtnubah);
        btnhapussemua = view.findViewById(R.id.friwxbtnhapussemua);
        btnselesai = view.findViewById(R.id.friwxbtnselesai);
        btnhapusfilter = view.findViewById(R.id.friwxbtnhapusfilter);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);
        fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        recyclerView = view.findViewById(R.id.friwxrecycleriwayat);
        cardleft = view.findViewById(R.id.driwxcardleft);
        cardright = view.findViewById(R.id.driwxcardright);
        teksttr = view.findViewById(R.id.friwxteksttriwayat);
        btnubah.setVisibility(View.VISIBLE);
        btnfilter.setVisibility(View.VISIBLE);
        btnhapussemua.setVisibility(View.GONE);
        btnselesai.setVisibility(View.GONE);

        // Memanggil List Data pada Recycle View
//        MainHome mh = new MainHome();
//        if (String.valueOf(mh.set4.getText()).equals("0")){
//            System.out.println("");
//        } else if (String.valueOf(mh.set4.getText()).equals("1")){
//            isiDataRiwayat();
//            mh.set4.setText("0");
//        }

//        getRiwayatClicked();
        getRiwayat();
        riwayatClickable();
        antrianClickable();
        ubahClickable();
        hapusFilterClickable();
        hapusSemuaClickable();
        selesaiClickable();
        filterClickable();
        return view;
    }

    public void getRiwayat(){
        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> riwayatCall = apiInterface.Riwayat(hfg.teksuser.getText().toString());
        riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                riwayatList = response.body().getData();
                if (riwayatList != null) {
                    addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttr.setVisibility(View.GONE);
                    btnhapusfilter.setVisibility(View.GONE);
                } else {
                    teksttr.setVisibility(View.VISIBLE);
                    teksttr.setText("Riwayat Kosong");
                    btnhapusfilter.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Anda tidak memiliki Riwayat apapun", Toast.LENGTH_SHORT).show();
                }
                cardleft.setBackgroundColor(Color.parseColor("#FFEAEAEA"));
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    public void getAntrian(){
        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> riwayatCall = apiInterface.Antrian(hfg.teksuser.getText().toString());
        riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                antrianList = response.body().getData();
                if (antrianList != null) {
                    addData = new AntrianRvAdapter(getContext(), antrianList, adapterItemListenerInterface2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksttr.setVisibility(View.GONE);
                } else {
                    teksttr.setVisibility(View.VISIBLE);
                    teksttr.setText("Antrian Pesanan Kosong");
                    Toast.makeText(getActivity(), "Anda tidak memiliki Antrian Pesanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    private void riwayatClickable(){
        cardleft.setOnClickListener(view -> {
            getRiwayat();
            consoption.startAnimation(fadein);
            consoption.setVisibility(View.VISIBLE);
            cardleft.setBackgroundColor(Color.parseColor("#FFEAEAEA"));
            cardright.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        });
    }

    private void antrianClickable(){
        cardright.setOnClickListener(view -> {
            getAntrian();
            consoption.startAnimation(fadeout);
            consoption.setVisibility(View.GONE);
            cardright.setBackgroundColor(Color.parseColor("#FFEAEAEA"));
            cardleft.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        });
    }

    public boolean ubahClickable(){
        btnubah.setOnClickListener(view -> {
            HistRvAdapter hra = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
            hra.showCheckCondition(true);
            if (riwayatList != null){
                btnhapussemua.setVisibility(View.VISIBLE);
                btnhapussemua.startAnimation(easeOutQuadRight);
            }
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
            if (riwayatList != null){
                btnhapussemua.setVisibility(View.GONE);
                btnhapussemua.startAnimation(easeOutQuadRightOut);
            }
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
        View view = getLayoutInflater().inflate(R.layout.alert_hapussemuariwayat,null);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        batal.setOnClickListener(view1 -> {
            HomeFragment hfg = new HomeFragment();
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> riwayatCall = apiInterface.hapusSemuaRiwayat(hfg.teksuser.getText().toString());
            riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    riwayatList.clear();
                    addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    riwayatList = null;
                    teksttr.setVisibility(View.VISIBLE);
                    btnhapussemua.setVisibility(View.GONE);
                    btnhapussemua.startAnimation(easeOutQuadRightOut);
                    Toast.makeText(getActivity(), "Riwayat berhasil dikosongkan!", Toast.LENGTH_SHORT).show();
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
        TextView tgl1, tgl2;
        Button batal, kirim;
        RadioButton rBtn1, rBtn2, rBtn3;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_filter,null);
        tgl1 = view.findViewById(R.id.filter1xtxttgl1);
        tgl2 = view.findViewById(R.id.filter1xtxttgl2);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        rBtn1 = view.findViewById(R.id.daxradioalamat);
        rBtn2 = view.findViewById(R.id.daxradiomakanan);
        rBtn3 = view.findViewById(R.id.daxradiominuman);
        builder.setView(view);
        dialog = builder.create();

        rBtn1.setOnClickListener(view1 -> {
            rBtn2.setChecked(false);
            rBtn3.setChecked(false);
        });
        rBtn2.setOnClickListener(view12 -> {
            rBtn1.setChecked(false);
            rBtn3.setChecked(false);
        });
        rBtn3.setOnClickListener(view13 -> {
            rBtn1.setChecked(false);
            rBtn2.setChecked(false);
        });

        final Calendar cldr = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        tgl1.setText(simpleDateFormat.format(cldr.getTime()));
        tgl2.setText(simpleDateFormat2.format(cldr.getTime()));

        tgl1.setOnClickListener(view14 -> {
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            tgl1.setOnClickListener(view141 -> {
                picker = new DatePickerDialog(getActivity(), (datePicker, year1, month1, day1) -> {
                    tgl1.setText(String.valueOf(day1) + " " + getMonth((month1 +1)) + " " + String.valueOf(year1));
                    tgl2.setText(String.valueOf(year1) + "-" + String.valueOf(month1 +1) + "-" + String.valueOf(day1));
                },year,month,day);
                picker.show();
                rBtn1.setChecked(true);
            });
        });

        kirim.setOnClickListener(view15 -> {
            if (rBtn1.isChecked()){
                HomeFragment hfg = new HomeFragment();
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> riwayatCall = apiInterface.filterRiwayatTanggal(hfg.teksuser.getText().toString(), tgl2.getText().toString());
                riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        riwayatList = response.body().getData();
                        if (riwayatList != null) {
                            addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(addData);
                            addData.notifyDataSetChanged();
                            btnhapusfilter.setVisibility(View.VISIBLE);
                            btnhapusfilter.startAnimation(fadein);
                        } else {
                            Toast.makeText(getActivity(), "Tanggal yang anda pilih tidak ada di Riwayat Anda", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            } else if (rBtn2.isChecked()){
                HomeFragment hfg = new HomeFragment();
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> riwayatCall = apiInterface.filterRiwayatMakanan(hfg.teksuser.getText().toString());
                riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        riwayatList = response.body().getData();
                        if (riwayatList != null) {
                            addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(addData);
                            addData.notifyDataSetChanged();
                            btnhapusfilter.setVisibility(View.VISIBLE);
                            btnhapusfilter.startAnimation(fadein);
                        } else {
                            Toast.makeText(getActivity(), "Tidak ada menu Makanan di Riwayat Anda", Toast.LENGTH_SHORT).show();
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
                        riwayatList = response.body().getData();
                        if (riwayatList != null) {
                            addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(addData);
                            addData.notifyDataSetChanged();
                            btnhapusfilter.setVisibility(View.VISIBLE);
                            btnhapusfilter.startAnimation(fadein);
                        } else {
                            Toast.makeText(getActivity(), "Tidak ada menu Minuman di Riwayat Anda", Toast.LENGTH_SHORT).show();
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
            getRiwayat();
            btnhapusfilter.startAnimation(fadeout);
            btnhapusfilter.setVisibility(View.GONE);
        });
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}