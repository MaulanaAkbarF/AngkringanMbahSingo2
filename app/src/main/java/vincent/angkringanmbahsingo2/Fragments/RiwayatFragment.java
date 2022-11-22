package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HistRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class RiwayatFragment extends Fragment {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    public static TextView datajudul, datatanggal, dataharga, datajumlah, btnfilter, btnubah, btnhapussemua, btnselesai;
    CheckBox check;
    List<HistRvModel> listDataDaftar;
    RecyclerView recyclerView;
    HistRvAdapter adapterItemDaftar;
    HistRvAdapter.AdapterItemListener adapterItemListenerInterface;
    Dialog dialog;
    DatePickerDialog picker;

    static RiwayatAdapterItem rav = new RiwayatAdapterItem();

    // List Data pada Recycle View
    void isiDataRiwayat(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HistRvModel("Nasi Goreng", 10000, 20, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Kecap", 12000, 30, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Pedas", 13000, 40, R.drawable.imagefood2));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Ayam", 15000, 15, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Spesial Mbah Singo",20000, 70000, R.drawable.imagefood2));
    }

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
//        datatanggal = view.findViewById(R.id.dataxtanggal);
        dataharga = view.findViewById(R.id.dataxharga);
        datajumlah = view.findViewById(R.id.dataxjumlah);

        // Memanggil List Data pada Recycle View
        MainHome mh = new MainHome();
        if (String.valueOf(mh.set4.getText()).equals("0")){
            System.out.println("");
        } else if (String.valueOf(mh.set4.getText()).equals("1")){
            isiDataRiwayat();
            mh.set4.setText("0");
        }
        adapterItemDaftar = new HistRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);
        btnhapussemua.setVisibility(View.GONE);
        btnselesai.setVisibility(View.GONE);

        getRiwayatClicked();
        ubahClickable();
        selesaiClickable();
        filterClickable();
        return view;
    }

    public boolean getRiwayatClicked(){
        adapterItemListenerInterface = new HistRvAdapter.AdapterItemListener() {
            @Override
            public void clickItemListener(int adapterPosition) {
                datajudul.setText(listDataDaftar.get(adapterPosition).getJudul());
//                datatanggal.setText(listDataDaftar.get(adapterPosition).getTanggal());
                dataharga.setText(String.valueOf(listDataDaftar.get(adapterPosition).getHarga()));
                datajumlah.setText(String.valueOf(listDataDaftar.get(adapterPosition).getJumlah()));
//                startActivity(new Intent(getActivity(), InterfaceMakanan.class));
                Toast.makeText(getActivity(), listDataDaftar.get(adapterPosition).getJudul(), Toast.LENGTH_SHORT).show();
            }
        };
        return true;
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
        return true;
    }

    private void selesaiClickable(){
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
        TextView tgl1;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_filter,null);
        tgl1 = view.findViewById(R.id.filter1xtxttgl1);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();

        final Calendar cldr = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        tgl1.setText(simpleDateFormat.format(cldr.getTime()));

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
                            }
                        },year,month,day);
                        picker.show();
                    }
                });
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (input.getText().toString().isEmpty()){
//                    input.setError("Anda belum mengetik jumlah pesanan");
//                } else {
//                    txtjumlah.setText(String.valueOf(input.getText()));
//                    int jumlah = Integer.parseInt(String.valueOf(txtjumlah.getText()));
//                    totalPrice = total * jumlah;
//                    txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
//                    dialog.dismiss();
//                }
                dialog.dismiss();
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