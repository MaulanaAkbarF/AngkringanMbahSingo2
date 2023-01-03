package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Fragments.HomeFragment;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;

public class HistRvAdapter extends RecyclerView.Adapter<HistRvAdapter.ViewHolder> {
    Context context;
    List<DataItemTransaksi> listDataAdapter;
    private boolean showCheck = true;
    HistRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";
    static String stock = "x%,d";

    APIInterface apiInterface;
    RecyclerView.Adapter addData;
    private List<DataItemTransaksi> childList = new ArrayList<>();

    public void showCheckCondition(boolean showCheck){
        this.showCheck = showCheck;
    }

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public void setFilteredList(List<DataItemTransaksi> filteredList) {
        this.listDataAdapter = filteredList;
        notifyDataSetChanged();
    }

    public HistRvAdapter(Context context, List<DataItemTransaksi> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public HistRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvriwayat,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistRvAdapter.ViewHolder holder, int position) {
        DataItemTransaksi db = listDataAdapter.get(position);

        holder.teksidtrans.setText(db.getIdTransaksi());
        holder.tekstanggal.setText(db.getJumlah());
        holder.status.setText(db.getMetode());
        holder.subtotal.setText(String.format(currency, Integer.parseInt(String.valueOf(db.getSubtotal()))));
        holder.tekstampilkan.setText("Tampilkan Pesanan");

        if (holder.status.getText().toString().equals("2")){
            holder.teksstatus.setText("Pesanan Selesai");
            holder.gambar.setImageResource(R.drawable.finishedicon);
            holder.catatan.setText(db.getNamaProduk());
            holder.catatanpj.setVisibility(View.GONE);
        } else if (holder.status.getText().toString().equals("3")){
            holder.teksstatus.setText("Pesanan dibatalkan");
            holder.gambar.setImageResource(R.drawable.canceledicon);
            holder.catatan.setText(db.getNamaProduk());
            holder.catatanpj.setVisibility(View.VISIBLE);
            holder.tekscatatanpj.setText(db.getTotalhargaitem());
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
                    addData = new ChildAntrianRvAdapter(context, childList, null);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setAdapter(addData);
                    addData.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Anda tidak memiliki Antrian Pesanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // implements View.OnClickListener
        TextView teksidtrans, tekstanggal, teksstatus, status, jumlah, subtotal, catatan, tekscatatan, tekscatatanpj, tekstampilkan;
        ScrollView scrollView;
        CardView catatanpj;
        RecyclerView recyclerView;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teksidtrans = itemView.findViewById(R.id.hpxtxtidtransaksi);
            tekstanggal = itemView.findViewById(R.id.hpxtxttanggaltransaksi);
            teksstatus = itemView.findViewById(R.id.hpxteksstatus);
            status = itemView.findViewById(R.id.hpxstatus);
            subtotal = itemView.findViewById(R.id.hpxsubtotal);
            catatan = itemView.findViewById(R.id.hpxtxtcatatan);
            catatanpj = itemView.findViewById(R.id.hpxlinearcatatanpj);
            tekscatatan = itemView.findViewById(R.id.hpxtekscat);
            tekscatatanpj = itemView.findViewById(R.id.hpxtxtcatatanpj);
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
