package vincent.angkringanmbahsingo2.Interfaces;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vincent.angkringanmbahsingo2.Fragments.MakananFragment;
import vincent.angkringanmbahsingo2.Fragments.MinumanFragment;
import vincent.angkringanmbahsingo2.R;

public class InterfaceMakanan extends AppCompatActivity {

    public static TextView interdatajudul, interdatadesc, interdataharga, interdatastok, txtjumlah, txttotal;
    ImageView interdataimage, plusimage, minimage;
    Dialog dialog;

    static String currency = "Rp. %,d";
    static String stock = "%,d";
    int currentNumber = 1;
    int total;
    int totalPrice = 0;

    static MakananFragment makfrag = new MakananFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaces_makanan);

        interdatajudul = findViewById(R.id.interxjudulmenu);
        interdatadesc = findViewById(R.id.interxdescmenu);
        interdataharga = findViewById(R.id.interxhargamenu);
        interdatastok = findViewById(R.id.interxstokmenu);
        txtjumlah = findViewById(R.id.interxteksjumlah);
        txttotal = findViewById(R.id.interxtotalharga);
        plusimage = findViewById(R.id.interximageplus);
        minimage = findViewById(R.id.interximagemin);

        if (makfrag.getMakananClicked()){
            getDataMakanan();
        }

        countJumlah();
        jumlahClickable();
    }

    public static void getDataMakanan(){
        interdatajudul.setText(makfrag.datajudul.getText());
        interdatadesc.setText(makfrag.datadesc.getText());
        interdataharga.setText(String.format(currency, Integer.parseInt(String.valueOf(makfrag.dataharga.getText()))));
        interdatastok.setText(String.format(stock, Integer.parseInt(String.valueOf(makfrag.datastok.getText()))));
    }

    private void countJumlah(){
        txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(makfrag.dataharga.getText()))));
        total = Integer.parseInt(String.valueOf(makfrag.dataharga.getText()));
        plusimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentNumber = currentNumber+1;
                txtjumlah.setText(String.valueOf(currentNumber));
                totalPrice = total * currentNumber;
                txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
            }
        });
        minimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentNumber>1){
                    currentNumber = currentNumber - 1;
                    txtjumlah.setText(String.valueOf(currentNumber));
                    totalPrice = total * currentNumber;
                    txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
                }
            }
        });
    }

    private void jumlahClickable(){
        txtjumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertJumlah();
                dialog.show();
            }
        });
    }

    private void showAlertJumlah(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_jumlah,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtjumlah.setText(String.valueOf(input.getText()));
                int jumlah = Integer.parseInt(String.valueOf(txtjumlah.getText()));
                totalPrice = total * jumlah;
                txttotal.setText(String.format(currency, Integer.parseInt(String.valueOf(totalPrice))));
                dialog.dismiss();
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}