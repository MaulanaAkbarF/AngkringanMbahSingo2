package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
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
        holder.harga.setText(String.format(currency, listDataAdapter.get(position).getHarga()));
        holder.jumlah.setText(String.format(stock, listDataAdapter.get(position).getJumlah()));
        holder.gambar.setImageResource(listDataAdapter.get(position).getGambar());
        if (String.valueOf(holder.set.getText()).equals("0")){
            holder.check.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
        CheckBox check;
        TextView set, judul, harga, jumlah;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            set = itemView.findViewById(R.id.friwxcheckinit);
            judul = itemView.findViewById(R.id.hpxjudul);
            harga = itemView.findViewById(R.id.hpxharga);
            jumlah = itemView.findViewById(R.id.hpxjumlah);
            gambar = itemView.findViewById(R.id.hpximage);

            // Inisiasi komponen animasi
            check = itemView.findViewById(R.id.friwxcheckbox);

            // Membuat animasi
            easeOutQuadLeft = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.ease_out_quad_left);
            easeOutQuadRight = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.ease_out_quad_right);
            easeOutQuadLeftOut = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.ease_out_quad_left_out);
            easeOutQuadRightOut = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.ease_out_quad_right_out);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterItemListener.clickItemListener(getAdapterPosition());
        }
    }
}
