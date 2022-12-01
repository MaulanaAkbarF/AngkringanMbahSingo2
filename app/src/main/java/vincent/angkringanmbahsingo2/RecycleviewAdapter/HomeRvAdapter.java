package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;
import vincent.angkringanmbahsingo2.R;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.ViewHolder> {
    Context context;
    List<DataItemProduk> listDataAdapter;
    HomeRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";
    static String stock = "%,d";

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public HomeRvAdapter(Context context, List<DataItemProduk> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public HomeRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvmenu,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvAdapter.ViewHolder holder, int position) {
        DataItemProduk db = listDataAdapter.get(position);

//        holder.idmenu.setText(db.getNamaProduk());
        holder.judul.setText(db.getNamaProduk());
//        holder.desc.setText(db.getDesc());
        holder.harga.setText(String.format(currency, Integer.parseInt(db.getHarga())));
        holder.stok.setText(String.format(stock, Integer.parseInt(db.getStok())));
//        holder.gambar.setImageResource(listDataAdapter.get(position).getGambar());
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idmenu, judul, desc, harga, stok;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idmenu = itemView.findViewById(R.id.hpxidmenu);
            judul = itemView.findViewById(R.id.hpxjudul);
            desc = itemView.findViewById(R.id.hpxdesc);
            harga = itemView.findViewById(R.id.hpxharga);
            stok = itemView.findViewById(R.id.hpxstok);
            gambar = itemView.findViewById(R.id.hpximage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterItemListener.clickItemListener(getAdapterPosition());
        }
    }
}
