package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.R;

public class ChildAntrianRvAdapter extends RecyclerView.Adapter<ChildAntrianRvAdapter.ViewHolder> {
    Context context;
    List<DataItemTransaksi> listDataAdapter;
    public int item;
    ChildAntrianRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";
    static String stock = "x%,d";

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public ChildAntrianRvAdapter(Context context, List<DataItemTransaksi> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public ChildAntrianRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_childrvantrian,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAntrianRvAdapter.ViewHolder holder, int position) {
        DataItemTransaksi db = listDataAdapter.get(position);

        holder.judul.setText(db.getNamaProduk());
        holder.hargatotal.setText(String.format(currency, Integer.parseInt(db.getTotalhargaitem())));
        holder.jumlah.setText(String.format(stock, Integer.parseInt(db.getJumlah())));
        Picasso.get().load(API.BASE_GAMBAR+db.getPengiriman()).error(R.mipmap.ic_launcher).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        item = listDataAdapter.size();
        return item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // implements View.OnClickListener
        TextView judul, hargatotal, jumlah;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.hpxjudul);
            hargatotal = itemView.findViewById(R.id.hpxhargatotal);
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
