package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.RecycleviewModel.HistRvModel;

public class CartRvAdapter extends RecyclerView.Adapter<CartRvAdapter.ViewHolder> {
    List<HistRvModel> listDataAdapter;
    CartRvAdapter.AdapterItemListener adapterItemListener;
    int currentNumber, addNumber, totalPrice;
    static String currency = "Rp. %,d";
    static String stock = "%,d";

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public CartRvAdapter(List<HistRvModel> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.listDataAdapter = listDataAdapter;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public CartRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvkeranjang,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRvAdapter.ViewHolder holder, int position) {
        holder.judul.setText(listDataAdapter.get(position).getJudul());
        holder.harga.setText(String.format(currency, listDataAdapter.get(position).getHarga()));
        holder.jumlah.setText(String.format(stock, listDataAdapter.get(position).getJumlah()));
        holder.totalharga.setText(String.valueOf(holder.harga.getText()));
        holder.gambar.setImageResource(listDataAdapter.get(position).getGambar());
        holder.plusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentNumber = Integer.parseInt(String.valueOf(holder.jumlah.getText()));
                addNumber = currentNumber+1;
                holder.jumlah.setText(String.valueOf(addNumber));
                totalPrice = addNumber * listDataAdapter.get(holder.getAdapterPosition()).getHarga();
                holder.totalharga.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
            }
        });
        holder.minimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(String.valueOf(holder.jumlah.getText())) <= 1){
                    holder.jumlah.setText(String.valueOf(1));
                } else {
                    currentNumber = Integer.parseInt(String.valueOf(holder.jumlah.getText()));
                    addNumber = currentNumber-1;
                    holder.jumlah.setText(String.valueOf(addNumber));
                    totalPrice = addNumber * listDataAdapter.get(holder.getAdapterPosition()).getHarga();
                    holder.totalharga.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView judul, harga, jumlah, totalharga;
        ImageView gambar, minimage, plusimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.hpxjudul);
            harga = itemView.findViewById(R.id.hpxharga);
            jumlah = itemView.findViewById(R.id.hpxjumlah);
            totalharga = itemView.findViewById(R.id.hpxtotal);
            gambar = itemView.findViewById(R.id.hpximage);
            minimage = itemView.findViewById(R.id.fkerximagemin);
            plusimage = itemView.findViewById(R.id.fkerximageplus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterItemListener.clickItemListener(getAdapterPosition());
        }
    }
}
