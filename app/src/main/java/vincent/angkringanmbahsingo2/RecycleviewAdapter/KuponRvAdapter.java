package vincent.angkringanmbahsingo2.RecycleviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vincent.angkringanmbahsingo2.Fragments.RiwayatFragment;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemKupon;
import vincent.angkringanmbahsingo2.R;

public class KuponRvAdapter extends RecyclerView.Adapter<KuponRvAdapter.ViewHolder> {
    Context context;
    List<DataItemKupon> listDataAdapter;
    List<Integer> itemSelected = new ArrayList<>();
    private List<RiwayatFragment> items;
    private boolean showCheck = true;
    KuponRvAdapter.setDataKuponSelected dataKuponSelected;
    KuponRvAdapter.setDataKuponSelected2 dataKuponSelected2;
    KuponRvAdapter.AdapterItemListener adapterItemListener;
    static String currency = "Rp. %,d";

    public interface AdapterItemListener{
        void clickItemListener(int adapterPosition);
    }

    public KuponRvAdapter(Context context, List<DataItemKupon> listDataAdapter, setDataKuponSelected dataKuponSelected, setDataKuponSelected2 dataKuponSelected2, AdapterItemListener adapterItemListener) {
        this.context = context;
        this.listDataAdapter = listDataAdapter;
        this.dataKuponSelected = dataKuponSelected;
        this.dataKuponSelected2 = dataKuponSelected2;
        this.adapterItemListener = adapterItemListener;
    }

    @NonNull
    @Override
    public KuponRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_rvkupon,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KuponRvAdapter.ViewHolder holder, int position) {
        DataItemKupon db = listDataAdapter.get(position);

        holder.idkupon.setText(db.getIdkupon());
        holder.judul.setText(db.getNamaKupon());
        holder.minimal.setText(String.format("Minimal Belanja "+currency, Integer.parseInt(db.getMinimal())));
        holder.nilai.setText(String.format(currency, Integer.parseInt(db.getNilai())));

        if (holder.idkupon.getText().toString().contains("KOC")){
            holder.gambar.setImageResource(R.drawable.kuponongkirc);
        } else if (holder.idkupon.getText().toString().contains("KOB")){
            holder.gambar.setImageResource(R.drawable.kuponongkirb);
        } else if (holder.idkupon.getText().toString().contains("KOA")){
            holder.gambar.setImageResource(R.drawable.kuponongkira);
        } else if (holder.idkupon.getText().toString().contains("KOS")){
            holder.gambar.setImageResource(R.drawable.kuponongkirs);
        } else if (holder.idkupon.getText().toString().contains("KSC")){
            holder.gambar.setImageResource(R.drawable.kupondiskonc);
        } else if (holder.idkupon.getText().toString().contains("KSB")){
            holder.gambar.setImageResource(R.drawable.kupondiskonb);
        } else if (holder.idkupon.getText().toString().contains("KSA")){
            holder.gambar.setImageResource(R.drawable.kupondiskona);
        } else if (holder.idkupon.getText().toString().contains("KSS")){
            holder.gambar.setImageResource(R.drawable.kupondiskons);
        } else {
            System.out.println("Null");
        }

        if (holder.idkupon.getText().toString().contains("KO")){
            holder.teksdiskon.setText("Diskon Ongkir : ");
        } else if (holder.idkupon.getText().toString().contains("KS")){
            holder.teksdiskon.setText("Uang Kembali : ");
        }

        if (itemSelected.get(position) ==1){
            holder.check.setChecked(true);
        } else {
            holder.check.setChecked(false);
        }

        holder.check.setOnClickListener(view -> {
            for (int checkCondition = 0; checkCondition<itemSelected.size(); checkCondition++){
                if (checkCondition == position){
                    itemSelected.set(checkCondition,1);
                } else {
                    itemSelected.set(checkCondition,0);
                }
            }
            notifyDataSetChanged();
        });

        holder.check.setOnCheckedChangeListener((compoundButton, cekCondition) -> {
            if (cekCondition == true){
                if (holder.idkupon.getText().toString().contains("KO")){
                    dataKuponSelected.setDataKupon(db.getIdkupon(), db.getNamaKupon(), Integer.parseInt(db.getNilai()), Integer.parseInt(db.getMinimal()));
                }
                if (holder.idkupon.getText().toString().contains("KS")){
                    dataKuponSelected2.setDataKupon2(db.getIdkupon(), db.getNamaKupon(), Integer.parseInt(db.getNilai()), Integer.parseInt(db.getMinimal()));
                }
            }
        });
    }

    public interface setDataKuponSelected{
        void setDataKupon(String idkupon, String namakupon, int nilai, int minimal);
    }

    public interface setDataKuponSelected2{
        void setDataKupon2(String idkupon, String namakupon, int nilai, int minimal);
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // implements View.OnClickListener
        TextView idkupon, teksdiskon, judul, minimal, nilai;
        CheckBox check;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idkupon = itemView.findViewById(R.id.hpxidkupon);
            teksdiskon = itemView.findViewById(R.id.hpxtxtdiskon);
            judul = itemView.findViewById(R.id.hpxjudul);
            minimal = itemView.findViewById(R.id.hpxnama);
            nilai = itemView.findViewById(R.id.hpxnilai);
            gambar = itemView.findViewById(R.id.hpximage);
            check = itemView.findViewById(R.id.friwxcheckbox);

            if (showCheck == true){
                check.setVisibility(View.VISIBLE);
            } else {
                check.setVisibility(View.GONE);
            }

            for (int i = 0; i<listDataAdapter.size(); i++){
                itemSelected.add(0);
            }

//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            adapterItemListener.clickItemListener(getAdapterPosition());
//        }
    }
}

