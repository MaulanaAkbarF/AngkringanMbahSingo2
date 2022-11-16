package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vincent.angkringanmbahsingo2.RecycleviewModel.HomeRvModel;
import vincent.angkringanmbahsingo2.R;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.ViewHolder> {
    List<HomeRvModel> listDataAdapter;
    HomeRvAdapter.AdapterItemListener adapterItemListener;

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public HomeRvAdapter(List<HomeRvModel> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public HomeRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvmakanan,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvAdapter.ViewHolder holder, int position) {
        holder.judul.setText(listDataAdapter.get(position).getJudul());
        holder.harga.setText(String.valueOf(listDataAdapter.get(position).getHarga()));
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView judul , harga;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.hpxjudul);
            harga = itemView.findViewById(R.id.hpxharga);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterItemListener.clickItemListener(getAdapterPosition());
        }
    }
}