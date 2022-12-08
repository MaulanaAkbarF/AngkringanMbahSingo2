package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseProduk;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HomeRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.OrderRvAdapter;

public class DetailPesananFragment extends Fragment {

    LinearLayout linearlay, btnalamat, btnmetode;
    public static TextView teksidtransaksi, teksalamat, teksmetode, teksongkir, tekssubtotal, tekssubtotal2, dataidmenu, datajudul, datajumlah, dataharga;
    ImageView dataimage, btnback;
    Button btnpesan;

    static String currency = "Rp. %,d";
    static String stock = "%,d";
    static String biayaongkir = "5000";

    HomeFragment hfg = new HomeFragment();
    RecyclerView recyclerView;
    OrderRvAdapter.AdapterItemListener adapterItemListenerInterface;
    RecyclerView.Adapter addData;
    APIInterface apiInterface;
    private List<DataItemTransaksi> dataPesanan = new ArrayList<>();

    private String alamatFromDetailAlamat;
    public DetailPesananFragment(String alamat) {
        this.alamatFromDetailAlamat = alamat;
    }

    public DetailPesananFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_pesanan, container, false);

        linearlay = view.findViewById(R.id.dpxlinearlay);
        btnalamat = view.findViewById(R.id.dpxlinearalamat);
        btnmetode =  view.findViewById(R.id.dpxlinearmetode);
        teksidtransaksi = view.findViewById(R.id.dpxidtransaksi);
        teksalamat = view.findViewById(R.id.dpxtxtalamat);
        teksmetode = view.findViewById(R.id.dpxtxtmetode);
        teksongkir = view.findViewById(R.id.dpxongkir);
        tekssubtotal = view.findViewById(R.id.dpxsubtotal);
        tekssubtotal2 = view.findViewById(R.id.dpxsubtotal2);
        dataidmenu = view.findViewById(R.id.dataxidmenu);
        datajudul = view.findViewById(R.id.dataxjudul);
        datajumlah = view.findViewById(R.id.dataxjumlah);
        dataharga = view.findViewById(R.id.dataxharga);
        btnback = view.findViewById(R.id.dpxbtnback);
        btnpesan = view.findViewById(R.id.dpxbtnpesan);

        if(alamatFromDetailAlamat !=null) {
            teksalamat.setText(alamatFromDetailAlamat);
        }

        btnalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailAlamatFragment()).addToBackStack("tag").commit();
            }
        });

        btnmetode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailMetodeFragment()).addToBackStack("tag").commit();
            }
        });

        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrivePesananFinal();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        retrievePesanan();
        linearClickable();
        return view;
    }

    public void retrievePesanan(){
        InterfaceHomeFragment homefrag = new InterfaceHomeFragment();
        InterfaceMakananFragment makfrag = new InterfaceMakananFragment();
        InterfaceMinumanFragment minfrag = new InterfaceMinumanFragment();
        if (makfrag.interxidtransaksi.getText().toString() != null){
            teksidtransaksi.setText(makfrag.interxidtransaksi.getText().toString());
        } else {
            System.out.println("");
        }
        String idtransaksi = teksidtransaksi.getText().toString(), username = hfg.teksuser.getText().toString();
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
                teksongkir.setText(String.format(currency, Integer.parseInt(String.valueOf(biayaongkir))));
                tekssubtotal.setText(String.format(currency, Integer.parseInt(String.valueOf(dataPesanan.get(0).getSubtotal()))));
                tekssubtotal2.setText(String.valueOf(dataPesanan.get(0).getSubtotal()));
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrivePesananFinal(){
        String idtransaksi = teksidtransaksi.getText().toString(), username = hfg.teksuser.getText().toString(), metode = teksmetode.getText().toString();
        String subtotal = String.valueOf(tekssubtotal2.getText());
        String pengiriman = "Antar ke "+teksalamat.getText().toString();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> pesananCall = apiInterface.transaksiBeliFinal(idtransaksi, username, subtotal, pengiriman, metode, "0");
        pesananCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                dataPesanan = response.body().getData();
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new SplashSelesaiFragment()).addToBackStack("tag").commit();
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void linearClickable(){
        linearlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.print(".");
            }
        });
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
    }
}