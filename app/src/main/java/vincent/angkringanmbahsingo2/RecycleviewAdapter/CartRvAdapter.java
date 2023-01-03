package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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

public class CartRvAdapter extends RecyclerView.Adapter<CartRvAdapter.ViewHolder> {
    Context context;
    List<DataItemTransaksi> listDataAdapter;
    CartRvAdapter.AdapterItemListener adapterItemListener;
    int currentNumber, addNumber, totalPrice;
    static String currency = "Rp. %,d";
    static String stock = "%,d";
    APIInterface apiInterface;
    CartRvAdapter.setDataSubtotal dataSubtotal;
    private List<DataItemTransaksi> dataListKeranjang = new ArrayList<>();

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public CartRvAdapter(Context context, List<DataItemTransaksi> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
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
        DataItemTransaksi db = listDataAdapter.get(position);
        DataItemTransaksi dbpos = listDataAdapter.get(holder.getAdapterPosition());

        holder.idmenu.setText(db.getIdProduk());
        holder.judul.setText(db.getNamaProduk());
        holder.harga.setText(String.format(currency, db.getHarga()));
        holder.jumlah.setText(String.format(stock, Integer.parseInt(db.getJumlah())));
        Picasso.get().load(API.BASE_GAMBAR+db.getPengiriman()).error(R.mipmap.ic_launcher).into(holder.gambar);
        holder.plusimage.setOnClickListener(view -> {
            currentNumber = Integer.parseInt(holder.jumlah.getText().toString());
            addNumber = currentNumber + 1;
            holder.jumlah.setText(String.valueOf(addNumber));
            totalPrice = addNumber * db.getHarga();
            holder.totalharga.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));

            HomeFragment hfg = new HomeFragment();
            apiInterface = API.getService().create(APIInterface.class);
            Call<ResponseTransaksi> riwayatCall = apiInterface.updateKeranjang(holder.idmenu.getText().toString(), hfg.teksuser.getText().toString(), String.valueOf(addNumber), String.valueOf(totalPrice));
            riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                    dataListKeranjang = response.body().getData();
                    currentNumber = Integer.parseInt(String.valueOf(listDataAdapter.get(holder.getAdapterPosition()).getJumlah()));
                }

                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

                }
            });
        });
        holder.minimage.setOnClickListener(view -> {
            if (currentNumber <= 1){
                holder.jumlah.setText(String.valueOf(1));
            } else {
                currentNumber = Integer.parseInt(holder.jumlah.getText().toString());
                addNumber = currentNumber - 1;
                holder.jumlah.setText(String.valueOf(addNumber));
                totalPrice = addNumber * db.getHarga();
                holder.totalharga.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));

                HomeFragment hfg = new HomeFragment();
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseTransaksi> riwayatCall = apiInterface.updateKeranjang(holder.idmenu.getText().toString(), hfg.teksuser.getText().toString(), String.valueOf(addNumber), String.valueOf(totalPrice));
                riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        dataListKeranjang = response.body().getData();
                        currentNumber = Integer.parseInt(String.valueOf(listDataAdapter.get(holder.getAdapterPosition()).getJumlah()));
                    }

                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

                    }
                });
            }
        });
        currentNumber = Integer.parseInt(String.valueOf(holder.jumlah.getText()));
        totalPrice = currentNumber * db.getHarga();
        holder.totalharga.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public void getSubtotalForFragments(){
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < listDataAdapter.size(); i++) {
            values.add(listDataAdapter.get(i).getHarga());
            dataSubtotal.setSubtotal(String.valueOf(values));
        }
    }

    public interface setDataSubtotal{
        void setSubtotal(String subtotal);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idmenu, judul, harga, jumlah, totalharga;
        ImageView gambar, minimage, plusimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idmenu = itemView.findViewById(R.id.hpxidmenu);
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
