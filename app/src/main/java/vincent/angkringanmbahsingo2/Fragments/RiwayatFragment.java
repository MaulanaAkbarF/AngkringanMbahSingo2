package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

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
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HistRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class RiwayatFragment extends Fragment {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    public static TextView datajudul, datatanggal, dataharga, datajumlah, btnfilter, btnubah, btnhapussemua, btnselesai;
    RecyclerView recyclerView;
    HistRvAdapter.AdapterItemListener adapterItemListenerInterface;
    Dialog dialog;
    DatePickerDialog picker;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemTransaksi> riwayatList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        // Inisiasi komponen animasi
        btnfilter = view.findViewById(R.id.friwxbtnfilter);
        btnubah = view.findViewById(R.id.friwxbtnubah);
        btnhapussemua = view.findViewById(R.id.friwxbtnhapussemua);
        btnselesai = view.findViewById(R.id.friwxbtnselesai);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        recyclerView = view.findViewById(R.id.friwxrecycleriwayat);
        datajudul = view.findViewById(R.id.dataxjudul);
        dataharga = view.findViewById(R.id.dataxharga);
        datajumlah = view.findViewById(R.id.dataxjumlah);

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
        ubahClickable();
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
                } else {
                    Toast.makeText(getActivity(), "Anda tidak memiliki Riwayat apapun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    public boolean ubahClickable(){
        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnhapussemua.setVisibility(View.VISIBLE);
                btnselesai.setVisibility(View.VISIBLE);
                btnubah.setVisibility(View.GONE);
                btnfilter.setVisibility(View.GONE);
                btnfilter.startAnimation(easeOutQuadLeftOut);
                btnubah.startAnimation(easeOutQuadLeftOut);
                btnhapussemua.startAnimation(easeOutQuadRight);
                btnselesai.startAnimation(easeOutQuadRight);
            }
        });
        return false;
    }

    public boolean selesaiClickable(){
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnhapussemua.setVisibility(View.GONE);
                btnselesai.setVisibility(View.GONE);
                btnubah.setVisibility(View.VISIBLE);
                btnfilter.setVisibility(View.VISIBLE);
                btnfilter.startAnimation(easeOutQuadLeft);
                btnubah.startAnimation(easeOutQuadLeft);
                btnhapussemua.startAnimation(easeOutQuadRightOut);
                btnselesai.startAnimation(easeOutQuadRightOut);
            }
        });
        return false;
    }

    private void filterClickable(){
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertFilter1();
                dialog.show();
            }
        });
    }

    private void showAlertFilter1(){
        final String[] dateString = new String[1];
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

        rBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rBtn2.setChecked(false);
                rBtn3.setChecked(false);
            }
        });
        rBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rBtn1.setChecked(false);
                rBtn3.setChecked(false);
            }
        });
        rBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rBtn1.setChecked(false);
                rBtn2.setChecked(false);
            }
        });

        final Calendar cldr = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        tgl1.setText(simpleDateFormat.format(cldr.getTime()));
        tgl2.setText(simpleDateFormat2.format(cldr.getTime()));

        tgl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                tgl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month , int day) {
                                tgl1.setText(String.valueOf(day) + " " + getMonth((month+1)) + " " + String.valueOf(year));
                                tgl2.setText(String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day));
                            }
                        },year,month,day);
                        picker.show();
                        rBtn1.setChecked(true);
                    }
                });
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    Call<ResponseTransaksi> riwayatCall = apiInterface.filterRiwayatMakanan();
                    riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                        @Override
                        public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                            riwayatList = response.body().getData();
                            if (riwayatList != null) {
                                addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(addData);
                                addData.notifyDataSetChanged();
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
                    Call<ResponseTransaksi> riwayatCall = apiInterface.filterRiwayatMinuman();
                    riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                        @Override
                        public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                            riwayatList = response.body().getData();
                            if (riwayatList != null) {
                                addData = new HistRvAdapter(getContext(), riwayatList, adapterItemListenerInterface);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(addData);
                                addData.notifyDataSetChanged();
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
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}