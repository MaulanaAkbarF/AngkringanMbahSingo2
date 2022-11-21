package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewAdapter.CartRvAdapter;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class KeranjangFragment extends Fragment {

    public static TextView datajudul, datatanggal, dataharga, datajumlah;
    List<HistRvModel> listDataDaftar;
    RecyclerView recyclerView;
    CartRvAdapter adapterItemDaftar;
    CartRvAdapter.AdapterItemListener adapterItemListenerInterface;

    // List Data pada Recycle View
    void isiDataKeranjang(){
        if(listDataDaftar == null){
            listDataDaftar = new ArrayList<>();
        }
        listDataDaftar.add(new HistRvModel("Nasi Goreng", 10000, 20, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Kecap", 12000, 30, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Pedas", 13000, 40, R.drawable.imagefood2));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Ayam", 15000, 15, R.drawable.imagefood));
        listDataDaftar.add(new HistRvModel("Nasi Goreng Spesial Mbah Singo",20000, 12, R.drawable.imagefood2));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        recyclerView = view.findViewById(R.id.fkerxrecyclekeranjang);
        datajudul = view.findViewById(R.id.dataxjudul);
//        datatanggal = view.findViewById(R.id.dataxtanggal);
        dataharga = view.findViewById(R.id.dataxharga);
        datajumlah = view.findViewById(R.id.dataxjumlah);

        isiDataKeranjang();
        adapterItemDaftar = new CartRvAdapter(listDataDaftar,adapterItemListenerInterface);
        recyclerView.setAdapter(adapterItemDaftar);

        getKeranjangClicked(); //Penyebab data ne looping mergo fungsi ini dijalanno meneh pas jarak 4 bottom navmenu di klik/geser
        return view;
    }

    public boolean getKeranjangClicked(){
        adapterItemListenerInterface = new CartRvAdapter.AdapterItemListener() {
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
}