package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class OrderRvAdapter extends RecyclerView.Adapter<OrderRvAdapter.ViewHolder> {
    Context context;
    List<DataItemTransaksi> listDataAdapter;
    OrderRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";
    static String stock = "x%,d";

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public OrderRvAdapter(Context context, List<DataItemTransaksi> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public OrderRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvrangkuman,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRvAdapter.ViewHolder holder, int position) {
        DataItemTransaksi db = listDataAdapter.get(position);

        holder.judul.setText(db.getNamaProduk());
        holder.harga.setText(String.format(currency, Integer.parseInt(String.valueOf(db.getTotalhargaitem()))));
        holder.jumlah.setText(String.format(stock, Integer.parseInt(String.valueOf(db.getJumlah()))));
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // implements View.OnClickListener
        TextView judul, harga, jumlah;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.hpxjudul);
            harga = itemView.findViewById(R.id.hpxharga);
            jumlah = itemView.findViewById(R.id.hpxjumlah);
            gambar = itemView.findViewById(R.id.hpximage);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            adapterItemListener.clickItemListener(getAdapterPosition());
//        }
    }
}
