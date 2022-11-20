package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class HistRvAdapter extends RecyclerView.Adapter<HistRvAdapter.ViewHolder> {
    List<HistRvModel> listDataAdapter;
    HistRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";
    static String stock = "x%,d";

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public HistRvAdapter(List<HistRvModel> listDataAdapter, AdapterItemListener adapterItemListener) {
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
        holder.judul.setText(listDataAdapter.get(position).getJudul());
//        holder.tanggal.setText(listDataAdapter.get(position).getTanggal());
        holder.harga.setText(String.format(currency, listDataAdapter.get(position).getHarga()));
        holder.jumlah.setText(String.format(stock, listDataAdapter.get(position).getJumlah()));
        holder.gambar.setImageResource(listDataAdapter.get(position).getGambar());
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView judul, tanggal, harga, jumlah;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.hpxjudul);
//            tanggal = itemView.findViewById(R.id.hpx);
            harga = itemView.findViewById(R.id.hpxharga);
            jumlah = itemView.findViewById(R.id.hpxjumlah);
            gambar = itemView.findViewById(R.id.hpximage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterItemListener.clickItemListener(getAdapterPosition());
        }
    }
}
