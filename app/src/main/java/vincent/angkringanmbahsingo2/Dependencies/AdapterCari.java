package vincent.angkringanmbahsingo2.Dependencies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemProduk;
import vincent.angkringanmbahsingo2.R;

public class AdapterCari extends RecyclerView.Adapter<AdapterCari.viewdata> {
    Context context;
    List<DataItemProduk> listdata;

    public AdapterCari(Context context, List<DataItemProduk> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    public void setFilteredList(List<DataItemProduk> filteredList) {
        this.listdata = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewdata onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.seach_card, parent, false);
        return new viewdata(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewdata holder, @SuppressLint("RecyclerView") int position) {
        DataItemProduk db = listdata.get(position);

        holder.nmBarang.setText(db.getNamaProduk());
        holder.harga.setText(String.valueOf(db.getHarga()));
        Picasso.get().load(API.BASE_GAMBAR+listdata.get(position).getGambar()).error(R.mipmap.ic_launcher).into(holder.gbrBrg);
        holder.stok.setText(String.valueOf(db.getStok()));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }



    public class viewdata extends RecyclerView.ViewHolder {
        TextView nmBarang, harga, stok;
        ImageView gbrBrg;
        CardView klik;

        public viewdata(@NonNull View itemView) {
            super(itemView);

            nmBarang = itemView.findViewById(R.id.produk);
            harga = itemView.findViewById(R.id.harga);
            stok = itemView.findViewById(R.id.stok_src);
            gbrBrg = itemView.findViewById(R.id.tvimageView);
            klik = itemView.findViewById(R.id.carSearch);
        }
    }


}

