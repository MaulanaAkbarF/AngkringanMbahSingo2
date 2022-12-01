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
import vincent.angkringanmbahsingo2.R;

public class ProdukRvAdapter extends RecyclerView.Adapter<ProdukRvAdapter.viewdata> {

    Context context;
    List<DataItemProduk> listdata;

    public ProdukRvAdapter(Context context, List<DataItemProduk> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public viewdata onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.homepage_rvmenu, parent, false);
        return new viewdata(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewdata holder, int position) {
        DataItemProduk db = listdata.get(position);

        holder.nmBarang.setText(db.getNamaProduk());
        holder.harga.setText(String.valueOf(db.getHarga()));
        //Picasso.get().load(IPE.image+listData.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.ivIcon);
        holder.stok.setText(String.valueOf(db.getStok()));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class viewdata extends RecyclerView.ViewHolder {
        TextView nmBarang, harga, stok;
        ImageView gbrBrg;

        public viewdata(@NonNull View itemView) {
            super(itemView);

            nmBarang = itemView.findViewById(R.id.hpxjudul);
            harga = itemView.findViewById(R.id.hpxharga);
            stok = itemView.findViewById(R.id.hpxstok);
            gbrBrg = itemView.findViewById(R.id.hpximage);
        }
    }
}
