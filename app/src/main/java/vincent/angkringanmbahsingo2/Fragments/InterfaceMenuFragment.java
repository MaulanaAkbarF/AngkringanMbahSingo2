package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;

public class InterfaceMenuFragment extends Fragment implements Backpressedlistener {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    ConstraintLayout consmain;
    public static Backpressedlistener backpressedlistener;
    public static TextView interxidtransaksi, interidmenu, interdatajudul, interdatadesc, interdataharga, interdatastok, txtjumlah, txttotal;
    public static ImageView interdataimage, plusimage, minimage, imageback;
    Button btnkeranjang, btnbeli;
    Dialog dialog;

    APIInterface apiInterface;
    private List<DataItemTransaksi> dataTransaksiBeli = new ArrayList<>();

    static String currency = "Rp. %,d";
    static String stock = "%,d";
    int currentNumber = 1;
    int total;
    int totalPrice = 0;

    // Mengisi data dari ViewPager Menu
    private static String idmenu, namamenu, deskripsimenu, hargamenu, stokmenu, gambarmenu;

    // Mengisi data Alamat dari DetailPesananFragment
    public void setDataMenu(String idmenu, String namamenu, String deskripsimenu, String hargamenu, String stokmenu, String gambarmenu) {
        this.idmenu = idmenu;
        this.namamenu = namamenu;
        this.deskripsimenu = deskripsimenu;
        this.hargamenu = hargamenu;
        this.stokmenu = stokmenu;
        this.gambarmenu = gambarmenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interface_home, container, false);

        consmain = view.findViewById(R.id.fhomexconsmain);
        interxidtransaksi = view.findViewById(R.id.interxidtransaksi);
        interidmenu = view.findViewById(R.id.interxidmenu);
        interdatajudul = view.findViewById(R.id.interxjudulmenu);
        interdatadesc = view.findViewById(R.id.interxdescmenu);
        interdataharga = view.findViewById(R.id.interxhargamenu);
        interdatastok = view.findViewById(R.id.interxstokmenu);
        interdataimage = view.findViewById(R.id.interximage);
        txtjumlah = view.findViewById(R.id.interxteksjumlah);
        txttotal = view.findViewById(R.id.interxtotalharga);
        plusimage = view.findViewById(R.id.interximageplus);
        minimage = view.findViewById(R.id.interximagemin);
        imageback = view.findViewById(R.id.fhomexback);
        btnkeranjang = view.findViewById(R.id.interxbtnkeranjang);
        btnbeli = view.findViewById(R.id.interxbtnbeli);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        consmain.setVisibility(View.VISIBLE);
        consmain.startAnimation(easeOutQuadRight);

        btnkeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnbeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment hfg = new HomeFragment();
                int totalharga = Integer.parseInt(String.valueOf(txtjumlah.getText())) * Integer.parseInt(String.valueOf(hargamenu));
                String totalhargaitem = Integer.toString(totalharga);
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> transaksiCall = apiInterface.transaksiBeli(interxidtransaksi.getText().toString(), interidmenu.getText().toString(), hfg.teksuser.getText().toString(), String.valueOf(txtjumlah.getText()), totalhargaitem);
                transaksiCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        consmain.startAnimation(easeOutQuadLeftOut);
                        sendDataToPesanan();
                    }
                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consmain.startAnimation(easeOutQuadRightOut);
                consmain.setVisibility(View.GONE);
                interxidtransaksi.setText(null);
            }
        });

        getDataMenu();
        autokodeTransaksi();
        countJumlah();
        jumlahClickable();
        return view;
    }

    public static void getDataMenu(){
        Picasso.get().load(API.BASE_GAMBAR+gambarmenu).error(R.mipmap.ic_launcher).into(interdataimage);
        interidmenu.setText(idmenu);
        interdatajudul.setText(namamenu);
        interdatadesc.setText(deskripsimenu);
        interdataharga.setText(String.format(currency, Integer.parseInt(String.valueOf(hargamenu))));
        interdatastok.setText(String.format(stock, Integer.parseInt(String.valueOf(stokmenu))));
    }

    public void sendDataToPesanan() {
        DetailPesananFragment dpf = new DetailPesananFragment();
        dpf.setDataIdTransaksi(interxidtransaksi.getText().toString());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragtr = fragmentManager.beginTransaction();
        fragtr.replace(R.id.fragmentcontainersplash, dpf).commit();
    }

    private void autokodeTransaksi(){
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> transaksiCall = apiInterface.autoKodeTransaksi();
        transaksiCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                dataTransaksiBeli = response.body().getData();
                interxidtransaksi.setText(dataTransaksiBeli.get(0).getIdTransaksi());
            }
            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countJumlah(){
        txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(hargamenu))));
        total = Integer.parseInt(String.valueOf(hargamenu));
        plusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentNumber = currentNumber+1;
                txtjumlah.setText(String.valueOf(currentNumber));
                totalPrice = total * currentNumber;
                txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
            }
        });
        minimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentNumber>1){
                    currentNumber = currentNumber - 1;
                    txtjumlah.setText(String.valueOf(currentNumber));
                    totalPrice = total * currentNumber;
                    txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
                }
            }
        });
    }

    private void jumlahClickable(){
        txtjumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertJumlah();
                dialog.show();
            }
        });
    }

    private void showAlertJumlah(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_jumlah,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik jumlah pesanan");
                } else {
                    txtjumlah.setText(String.valueOf(input.getText()));
                    int jumlah = Integer.parseInt(String.valueOf(txtjumlah.getText()));
                    totalPrice = total * jumlah;
                    txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
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
        consmain.startAnimation(easeOutQuadRightOut);
        consmain.setVisibility(View.GONE);
        interxidtransaksi.setText(null);
    }
}