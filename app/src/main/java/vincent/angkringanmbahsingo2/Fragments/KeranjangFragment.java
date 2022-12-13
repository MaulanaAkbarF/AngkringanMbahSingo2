package vincent.angkringanmbahsingo2.Fragments;

import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.CartRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.HistRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class KeranjangFragment extends Fragment {

    public static TextView datajudul, datatanggal, dataharga, datajumlah, hargabelitotal;
    Button btnbeli;
    RecyclerView recyclerView;
    HistRvAdapter.AdapterItemListener adapterItemListenerInterface;

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemTransaksi> dataListKeranjang = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        recyclerView = view.findViewById(R.id.fkerxrecyclekeranjang);
        datajudul = view.findViewById(R.id.dataxjudul);
        dataharga = view.findViewById(R.id.dataxharga);
        datajumlah = view.findViewById(R.id.dataxjumlah);
        hargabelitotal = view.findViewById(R.id.dataxhargabelitotal);
        btnbeli = view.findViewById(R.id.fkerxbtnbeli);

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
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainersplash, new DetailPesananFragment()).addToBackStack("tag").commit();
            }
        });

        getDataKeranjang();
        return view;
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
                    addData = new HistRvAdapter(getContext(), dataListKeranjang, adapterItemListenerInterface);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Anda tidak memiliki Riwayat apapun", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}