package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AntrianRvAdapter extends RecyclerView.Adapter<AntrianRvAdapter.ViewHolder> {
    Context context;
    Dialog dialog;
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

    public void setFilteredList(List<DataItemTransaksi> filteredList) {
        this.listDataAdapter = filteredList;
        notifyDataSetChanged();
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
        holder.status.setText(db.getMetode());
        holder.subtotal.setText(String.format(currency, Integer.parseInt(String.valueOf(db.getSubtotal()))));
        holder.tekstampilkan.setText("Tampilkan Pesanan");

        if (holder.status.getText().toString().equals("0")){
            holder.teksstatus.setText("Menunggu Konfirmasi Penjual");
            holder.gambar.setImageResource(R.drawable.waitingicon);
            holder.catatan.setText(db.getNamaProduk());
            holder.tekscatatan.setText("Catatan :");
            holder.teksterima.setVisibility(View.GONE);
        } else if (holder.status.getText().toString().equals("1")){
            holder.teksstatus.setText("Pesanan sedang diproses");
            holder.gambar.setImageResource(R.drawable.finishedicon);
            holder.catatan.setText(db.getNamaProduk());
            holder.tekscatatan.setText("Catatan :");
            holder.tekskirim.setVisibility(View.GONE);
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

        holder.teksterima.setOnClickListener(view -> {
            TextView idtransaksi;
            Button batal, kirim;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view2 = layoutInflater.inflate(R.layout.alert_terimapesanan, null);
            idtransaksi = view2.findViewById(R.id.alertxtxtidtrs);
            batal = view2.findViewById(R.id.alertxbtnbatal);
            kirim = view2.findViewById(R.id.alertxbtnkirim);
            builder.setView(view2);
            dialog = builder.create();
            idtransaksi.setText(holder.teksidtrans.getText().toString());
            dialog.show();
            batal.setOnClickListener(view1 -> {
                HomeFragment hfg = new HomeFragment();
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> riwayatCall = apiInterface.updateTransaksiToFinished(holder.teksidtrans.getText().toString(), hfg.teksuser.getText().toString());
                riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        Toast.makeText(context, "Pesanan Diterima!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            });
            kirim.setOnClickListener(view12 -> dialog.dismiss());
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
        item = listDataAdapter.size();
        return item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // implements View.OnClickListener
        TextView teksidtrans, teksstatus, status, jumlah, subtotal, catatan, tekscatatan, tekstampilkan;
        CardView tekskirim, teksterima;
        ScrollView scrollView;
        RecyclerView recyclerView;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teksidtrans = itemView.findViewById(R.id.hpxtxtidtransaksi);
            teksstatus = itemView.findViewById(R.id.hpxteksstatus);
            status = itemView.findViewById(R.id.hpxstatus);
            subtotal = itemView.findViewById(R.id.hpxsubtotal);
            catatan = itemView.findViewById(R.id.hpxtxtcatatan);
            tekscatatan = itemView.findViewById(R.id.hpxtekscat);
            tekskirim = itemView.findViewById(R.id.hpxcardkirim);
            teksterima = itemView.findViewById(R.id.hpxcardterima);
            tekstampilkan = itemView.findViewById(R.id.hpxtekstampilkan);
            gambar = itemView.findViewById(R.id.hpximagestatus);
            scrollView = itemView.findViewById(R.id.hpxscrollpesanan);
            recyclerView = itemView.findViewById(R.id.fantxrvchildantrian);

            tekskirim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            adapterItemListener.clickItemListener(getAdapterPosition());
//        }
    }
}

