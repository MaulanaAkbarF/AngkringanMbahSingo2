package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Fragments.HomeFragment;
import vincent.angkringanmbahsingo2.Fragments.RiwayatFragment;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;

public class AntrianRvAdapter extends RecyclerView.Adapter<AntrianRvAdapter.ViewHolder> {
    Context context;
    List<DataItemTransaksi> listDataAdapter;
    AntrianRvAdapter.AdapterItemListener adapterItemListener;
    ChildAntrianRvAdapter.AdapterItemListener adapterItemListenerInterface;
    int item;
    static String currency = "Rp. %,d";
    static String stock = "x%,d";

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemTransaksi> childList = new ArrayList<>();

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public AntrianRvAdapter(Context context, List<DataItemTransaksi> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public AntrianRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvantrian,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AntrianRvAdapter.ViewHolder holder, int position) {
        DataItemTransaksi db = listDataAdapter.get(position);

        holder.teksidtrans.setText(db.getIdTransaksi());
        holder.teksstatus.setText(db.getMetode());
        holder.jumlah.setText(item+" Produk");
        holder.tekskirim.setText("Kirim Bukti Transfer");
        holder.tekstampilkan.setText("Tampilkan Pesanan");

        if (holder.teksstatus.getText().toString().equals("0")){
            holder.teksstatus.setText("Menunggu Konfirmasi Penjual");
        }

        if (holder.tekskirim.getText().toString().equals("Kirim Bukti Transfer")){
            holder.gambar.setImageResource(R.drawable.kupondiskonc);
        } else {
            holder.gambar.setImageResource(R.drawable.kuponongkirs);
        }

        holder.tekstampilkan.setOnClickListener(view -> {
            if (holder.tekstampilkan.getText().toString().equals("Tampilkan Pesanan")){
                holder.scrollView.setVisibility(View.VISIBLE);
                holder.tekstampilkan.setText("Sembunyikan Pesanan");
            } else if (holder.tekstampilkan.getText().toString().equals("Sembunyikan Pesanan")){
                holder.scrollView.setVisibility(View.GONE);
                holder.tekstampilkan.setText("Tampilkan Pesanan");
            }
        });

        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> riwayatCall = apiInterface.childPesanan(hfg.teksuser.getText().toString(), holder.teksidtrans.getText().toString());
        riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                childList = response.body().getData();
                if (childList != null) {
                    addData = new ChildAntrianRvAdapter(context.getApplicationContext(), childList, adapterItemListenerInterface);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                } else {
                    Toast.makeText(context.getApplicationContext(), "Anda tidak memiliki Antrian Pesanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        item = listDataAdapter.size();
        return item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // implements View.OnClickListener
        TextView teksidtrans, teksstatus, jumlah, tekskirim, tekstampilkan;
        ScrollView scrollView;
        RecyclerView recyclerView;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teksidtrans = itemView.findViewById(R.id.hpxteksidtrs);
            teksstatus = itemView.findViewById(R.id.hpxteksstatus);
            jumlah = itemView.findViewById(R.id.hpxteksproduk);
            tekskirim = itemView.findViewById(R.id.hpxtekskirimbukti);
            tekstampilkan = itemView.findViewById(R.id.hpxtekstampilkan);
            gambar = itemView.findViewById(R.id.hpximagestatus);
            scrollView = itemView.findViewById(R.id.hpxscrollpesanan);
            recyclerView = itemView.findViewById(R.id.fantxrvchildantrian);

//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            adapterItemListener.clickItemListener(getAdapterPosition());
//        }
    }
}

