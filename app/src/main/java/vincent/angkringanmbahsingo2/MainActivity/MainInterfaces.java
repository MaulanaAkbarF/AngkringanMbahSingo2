package vincent.angkringanmbahsingo2.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.R;

public class MainInterfaces extends AppCompatActivity {

    TextView interdatajudul, interdatadesc, interdataharga;
    ImageView interdataimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interfaces);

        interdatajudul = findViewById(R.id.interxjudulmenu);
        interdatadesc = findViewById(R.id.interxdescmenu);

        String datajudul = getIntent().getStringExtra("datajudul");
        String datadesc = getIntent().getStringExtra("datadesc");

        interdatajudul.setText(datajudul);
        interdatadesc.setText(datadesc);
    }
}