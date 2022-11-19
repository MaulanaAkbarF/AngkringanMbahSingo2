package vincent.angkringanmbahsingo2.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.Fragments.MakananFragment;
import vincent.angkringanmbahsingo2.R;

public class MainInterfaces extends AppCompatActivity {

    public static TextView makfragsign, interdatajudul, interdatadesc, interdataharga, interdatastok;
    ImageView interdataimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interfaces);

        interdatajudul = findViewById(R.id.interxjudulmenu);
        interdatadesc = findViewById(R.id.interxdescmenu);
        interdataharga = findViewById(R.id.interxhargamenu);
        interdatastok = findViewById(R.id.interxstokmenu);

        MakananFragment makfrag = new MakananFragment();

        getData();
    }

    public static void getData(){
        MakananFragment makfrag = new MakananFragment();
        interdatajudul.setText(makfrag.datajudul.getText());
        interdatadesc.setText(makfrag.datadesc.getText());
        interdataharga.setText(makfrag.dataharga.getText());
        interdatastok.setText(makfrag.datastok.getText());
    }
}