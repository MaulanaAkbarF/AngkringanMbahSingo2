package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.Fragments.RiwayatFragment;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemTransaksi;
import vincent.angkringanmbahsingo2.R;

public class HistRvAdapter extends RecyclerView.Adapter<HistRvAdapter.ViewHolder> {
    Context context;
    List<DataItemTransaksi> listDataAdapter;
    private boolean showCheck = true;
    HistRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";
    static String stock = "x%,d";

    public void showCheckCondition(boolean showCheck){
        this.showCheck = showCheck;
    }

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public HistRvAdapter(Context context, List<DataItemTransaksi> listDataAdapter, AdapterItemListener adapterItemListener) {
        this.context = context;
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
        DataItemTransaksi db = listDataAdapter.get(position);

        holder.judul.setText(db.getNamaProduk());
        holder.harga.setText(String.format(currency, Integer.parseInt(db.getHarga())));
        holder.jumlah.setText(String.format(stock, Integer.parseInt(db.getJumlah())));
        holder.tanggal.setText(db.getSubtotal());
        Picasso.get().load(API.BASE_GAMBAR+db.getPengiriman()).error(R.mipmap.ic_launcher).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // implements View.OnClickListener
        TextView tanggal, judul, harga, jumlah;
        CheckBox check;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.hpxtanggal);
            judul = itemView.findViewById(R.id.hpxjudul);
            harga = itemView.findViewById(R.id.hpxharga);
            jumlah = itemView.findViewById(R.id.hpxjumlah);
            gambar = itemView.findViewById(R.id.hpximage);
            check = itemView.findViewById(R.id.friwxcheckbox);

            if (showCheck == true){
                check.setVisibility(View.VISIBLE);
            } else {
                check.setVisibility(View.GONE);
            }

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){

                    } else {

                    }
                }
            });

//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            adapterItemListener.clickItemListener(getAdapterPosition());
//        }
    }

//    public List<RiwayatFragment> getSelectedItems() {
//        List<RiwayatFragment> selectedItems = new ArrayList<>();
//        for (RiwayatFragment item : items) {
//            if (item.isSelected()) {
//                selectedItems.add(item);
//            }
//        }
//        return selectedItems;
//    }
}
