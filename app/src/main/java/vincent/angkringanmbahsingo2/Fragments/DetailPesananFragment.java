package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.OrderRvAdapter;

public class DetailPesananFragment extends Fragment implements Backpressedlistener {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutSineTopOut, easeOutQuadRightOut;
    ScrollView scrollanimate;
    ConstraintLayout linearlay, btnalamat, btnmetode, btnkupon;
    public static TextView teksalamat, teksmetode, teksmetode2, tekskupon, teksongkir, teksongkir2, tekssubtotal, tekssubtotal2, labelpengiriman;
    ImageView btnback;
    Button btnpesan;
    Dialog dialog;
    StrikethroughSpan span = new StrikethroughSpan();

    static String currency = "Rp. %,d";
    static String stock = "%,d";
    String alamatButtonAmbil = "Anda belum menentukan Alamat";
    public String dataIdTransaksi, dataPengiriman, dataAlamat, dataMetode, dataIdkupon, dataIdkupon2, namaKupon, namaKupon2;
    public int check, subtotal, nilai, nilai2, biayaongkir = 8000;
    public int totalbayar, totalbiayaongkir, totalsubtotaldefault, totalsubtotal;
    public static Backpressedlistener backpressedlistener;

    HomeFragment hfg = new HomeFragment();
    RecyclerView recyclerView;
    OrderRvAdapter.AdapterItemListener adapterItemListenerInterface;
    RecyclerView.Adapter addData;
    APIInterface apiInterface;
    private List<DataItemTransaksi> dataPesanan = new ArrayList<>();
    private List<DataItemTransaksi> dataSubtotal = new ArrayList<>();

    // Mengisi data ID Transaksi dari Interface
    public String dataAlamatDefault = hfg.teksalamat.getText().toString();
    public void setDataIdTransaksi(String dataIdTransaksi, int check) {
        this.dataIdTransaksi = dataIdTransaksi;
        this.check = check;
    }
    // Mendapatkan data
    public String getDataIdTransaksi() {
        return this.dataIdTransaksi;
    }

    // Mengisi data Alamat dari DetailAlamatFragment
    public void setDataAlamat(String dataAlamat) {
        this.dataAlamat = dataAlamat;
    }

    // Mendapatkan data
    public String getDataAlamat() {return this.dataAlamat;}

    public void setDataPengiriman(String dataPengiriman) {
        this.dataPengiriman = dataPengiriman;
    }

    public String getDataPengiriman() {return this.dataPengiriman;}

    public void setDataMetode(String dataMetode) {this.dataMetode = dataMetode;}

    public String getDataMetode() {return this.dataMetode;}

    public void setDataKupon(String idkupon, String idkupon2, String namakupon, String namakupon2, int nilai, int nilai2){
        this.dataIdkupon = idkupon;
        this.dataIdkupon2 = idkupon2;
        this.namaKupon = namakupon;
        this.namaKupon2 = namakupon2;
        this.nilai = nilai;
        this.nilai2 = nilai2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_pesanan, container, false);

        scrollanimate = view.findViewById(R.id.dpxscrollanimate);
        linearlay = view.findViewById(R.id.dpxconslay);
        btnalamat = view.findViewById(R.id.dpxconsalamat);
        btnmetode =  view.findViewById(R.id.dpxconsmetode);
        btnkupon = view.findViewById(R.id.dpxconskupon);
        labelpengiriman = view.findViewById(R.id.dpxlabelpengiriman);
        teksalamat = view.findViewById(R.id.dpxtxtalamat);
        teksmetode = view.findViewById(R.id.dpxtxtmetode);
        teksmetode2 = view.findViewById(R.id.dpxpembayaran);
        tekskupon = view.findViewById(R.id.dpxtxtkupon);
        teksongkir = view.findViewById(R.id.dpxongkir);
        tekssubtotal = view.findViewById(R.id.dpxsubtotal);
        teksongkir2 = view.findViewById(R.id.dpxongkir2);
        tekssubtotal2 = view.findViewById(R.id.dpxsubtotal2);
        btnback = view.findViewById(R.id.dpxbtnback);
        btnpesan = view.findViewById(R.id.dpxbtnpesan);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutSineTopOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        btnalamat.setOnClickListener(view12 -> {
            DetailAlamatFragment daf = new DetailAlamatFragment();
            daf.setDataIdTransaksi(getDataIdTransaksi(), check);
            daf.setDataAlamat(dataAlamat, dataAlamatDefault);
            daf.setDataKupon( dataIdkupon, dataIdkupon2, namaKupon, namaKupon2, nilai, nilai2);
            daf.setDataMetode(teksmetode.getText().toString());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, daf).commit();
        });

        btnmetode.setOnClickListener(view1 -> {
            DetailMetodeFragment dmf = new DetailMetodeFragment();
            dmf.setDataMetode(teksmetode.getText().toString(), getDataIdTransaksi(), check, dataAlamat);
            dmf.setDataKupon(dataIdkupon, dataIdkupon2, namaKupon, namaKupon2, nilai, nilai2);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dmf).commit();
        });

        btnkupon.setOnClickListener(view15 -> {
            DetailKuponFragment dmf = new DetailKuponFragment();
            dmf.setDataDefaultPesanan(getDataIdTransaksi(), dataAlamat, dataMetode, subtotal, check);
            dmf.setDataMetode(teksmetode.getText().toString());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragtr = fragmentManager.beginTransaction();
            fragtr.replace(R.id.fragmentcontainersplash, dmf).commit();
        });

        btnpesan.setOnClickListener(view13 -> retrivePesananFinal());

        btnback.setOnClickListener(view14 -> {
            showAlertBatalkan();
            dialog.show();
        });

        scrollanimate.startAnimation(easeOutQuadRight);
        checkDataCondition();
        retrievePesanan();
        linearClickable();
        return view;
    }

    private void checkDataCondition(){
        if (dataAlamat == null){
            dataAlamat = dataAlamatDefault;
        } else {
            dataAlamat = getDataAlamat();
            if (dataAlamat.equals(alamatButtonAmbil)){
                teksalamat.setTextColor(Color.parseColor("#FFFF0000"));
            }
        }
        if (dataPengiriman != null){
            dataPengiriman = getDataPengiriman();
            labelpengiriman.setText(dataPengiriman);
        }
        if (dataMetode != null){
            teksmetode.setText(getDataMetode());
            teksmetode2.setText(getDataMetode());
        } else {
            dataMetode = "Tunai";
        }
        if (namaKupon != null && namaKupon2 != null){
            tekskupon.setText("Kupon Gratis Ongkir dan Cashback digunakan");
            tekskupon.setTextColor(Color.parseColor("#FF009C05"));
        } else if (namaKupon != null){
            tekskupon.setText(namaKupon);
        } else if (namaKupon2 != null){
            tekskupon.setText(namaKupon2);
        } else {
            tekskupon.setText("Gunakan Kupon Promo Anda!");
            tekskupon.setTextColor(Color.parseColor("#FFFF0000"));
        }
    }

    public void retrievePesanan(){
        String idtransaksi = getDataIdTransaksi(), username = hfg.teksuser.getText().toString();
        if (check == 0){
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> pesananCall = apiInterface.rangkumanPesanan(idtransaksi, username);
            pesananCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    dataPesanan = response.body().getData();
                    addData = new OrderRvAdapter(getContext(), dataPesanan, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.dpxrecyclemenu);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksalamat.setText(dataAlamat);
                    DetailPesananFragment.this.subtotal = Integer.parseInt(dataPesanan.get(0).getSubtotal());
                    teksongkir2.setVisibility(View.GONE);
                    tekssubtotal2.setVisibility(View.GONE);
                    if (String.valueOf(nilai) != null && nilai > 0){
                        totalbiayaongkir = biayaongkir - nilai;
                        if (totalbiayaongkir <= 0){
                            totalbiayaongkir = 0;
                        }
                        SpannableString spannableString1 = new SpannableString(String.format(currency, Integer.parseInt(String.valueOf(biayaongkir))));
                        spannableString1.setSpan(span, 0, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        teksongkir.setText(spannableString1);
                        teksongkir.setTextColor(Color.parseColor("#FF000000"));
                        teksongkir2.setVisibility(View.VISIBLE);
                    } else {
                        totalbiayaongkir = biayaongkir;
                        teksongkir.setText(String.format(currency, biayaongkir));
                    }
                    if (String.valueOf(nilai2) != null && nilai2 > 0){
                        totalsubtotal = subtotal - nilai2;
                        if (totalsubtotal <= 0){
                            totalsubtotal = 0;
                        }
                        totalsubtotaldefault = subtotal + totalbiayaongkir;
                        totalbayar = totalsubtotal + totalbiayaongkir;
                        SpannableString spannableString2 = new SpannableString(String.format(currency, Integer.parseInt(String.valueOf(totalsubtotaldefault))));
                        spannableString2.setSpan(span, 0, spannableString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tekssubtotal.setText(spannableString2);
                        tekssubtotal.setTextColor(Color.parseColor("#FF000000"));
                        tekssubtotal2.setVisibility(View.VISIBLE);
                    } else {
                        totalbayar = subtotal + totalbiayaongkir;
                        tekssubtotal.setText(String.format(currency, totalbayar));
                    }
                    teksongkir2.setText(String.format(currency, Integer.parseInt(String.valueOf(totalbiayaongkir))));
                    tekssubtotal2.setText(String.format(currency, Integer.parseInt(String.valueOf(totalbayar))));
                }

                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (check == 1){
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> pesananCall = apiInterface.rangkumanKeranjang(idtransaksi, username);
            pesananCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    dataPesanan = response.body().getData();
                    addData = new OrderRvAdapter(getContext(), dataPesanan, adapterItemListenerInterface);
                    recyclerView = getView().findViewById(R.id.dpxrecyclemenu);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                    teksalamat.setText(dataAlamat);
                    apiInterface = API.getService().create(APIInterface.class);
                    Call<ResponseTransaksi> subtotalCall = apiInterface.subtotalKeranjang(idtransaksi, username);
                    subtotalCall.enqueue(new Callback<ResponseTransaksi>() {
                        @Override
                        public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                            dataSubtotal = response.body().getData();
                            DetailPesananFragment.this.subtotal = Integer.parseInt(dataSubtotal.get(0).getSubtotal());
                            teksongkir2.setVisibility(View.GONE);
                            tekssubtotal2.setVisibility(View.GONE);
                            if (String.valueOf(nilai) != null && nilai > 0){
                                totalbiayaongkir = biayaongkir - nilai;
                                if (totalbiayaongkir <= 0){
                                    totalbiayaongkir = 0;
                                }
                                SpannableString spannableString1 = new SpannableString(String.format(currency, Integer.parseInt(String.valueOf(biayaongkir))));
                                spannableString1.setSpan(span, 0, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                teksongkir.setText(spannableString1);
                                teksongkir.setTextColor(Color.parseColor("#FF000000"));
                                teksongkir2.setVisibility(View.VISIBLE);
                            } else {
                                totalbiayaongkir = biayaongkir;
                                teksongkir.setText(String.format(currency, biayaongkir));
                            }
                            if (String.valueOf(nilai2) != null && nilai2 > 0){
                                totalsubtotal = subtotal - nilai2;
                                if (totalsubtotal <= 0){
                                    totalsubtotal = 0;
                                }
                                totalsubtotaldefault = subtotal + totalbiayaongkir;
                                totalbayar = totalsubtotal + totalbiayaongkir;
                                SpannableString spannableString2 = new SpannableString(String.format(currency, Integer.parseInt(String.valueOf(totalsubtotaldefault))));
                                spannableString2.setSpan(span, 0, spannableString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tekssubtotal.setText(spannableString2);
                                tekssubtotal.setTextColor(Color.parseColor("#FF000000"));
                                tekssubtotal2.setVisibility(View.VISIBLE);
                            } else {
                                totalbayar = subtotal + totalbiayaongkir;
                                tekssubtotal.setText(String.format(currency, totalbayar));
                            }
                            teksongkir2.setText(String.format(currency, Integer.parseInt(String.valueOf(totalbiayaongkir))));
                            tekssubtotal2.setText(String.format(currency, Integer.parseInt(String.valueOf(totalbayar))));
                        }

                        @Override
                        public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void retrivePesananFinal(){
        String pengiriman = "Antar ke "+teksalamat.getText().toString();
        if (pengiriman.equals("Antar ke "+alamatButtonAmbil)){
            Toast.makeText(getActivity(), "Alamat belum ditentukan", Toast.LENGTH_SHORT).show();
        } else {
            if (pengiriman.equals("Antar ke Ambil di Angkringan Mbah Singo")){
                pengiriman = "Ambil di Angkringan";
            }
            String idtransaksi = getDataIdTransaksi(), username = hfg.teksuser.getText().toString();
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> pesananCall = apiInterface.transaksiBeliFinal(idtransaksi, username, String.valueOf(totalbayar), pengiriman, dataMetode, "0");
            pesananCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    dataPesanan = response.body().getData();
                    scrollanimate.startAnimation(easeOutSineTopOut);
                    FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                    fragtr.replace(R.id.fragmentcontainersplash, new SplashSelesaiFragment()).commit();
                    dataIdTransaksi = null;
                }

                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showAlertBatalkan(){
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_batalkanpesanan,null);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        batal.setOnClickListener(view1 -> {
            String idtransaksi = getDataIdTransaksi(), username = hfg.teksuser.getText().toString();
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> pesananCall = apiInterface.batalkanPesanan(idtransaksi, username);
            pesananCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    dataIdTransaksi = null;
                    scrollanimate.startAnimation(easeOutQuadRightOut);
                    scrollanimate.setVisibility(View.GONE);
                    closeFragment();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        kirim.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void linearClickable(){
        linearlay.setOnClickListener(view -> System.out.print("."));
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
        showAlertBatalkan();
        dialog.show();
    }
}