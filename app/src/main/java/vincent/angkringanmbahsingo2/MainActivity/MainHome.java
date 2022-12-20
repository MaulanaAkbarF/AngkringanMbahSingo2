package vincent.angkringanmbahsingo2.MainActivity;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import vincent.angkringanmbahsingo2.Dependencies.AdapterViewPager;
import vincent.angkringanmbahsingo2.Fragments.DetailAlamatFragment;
import vincent.angkringanmbahsingo2.Fragments.DetailKuponFragment;
import vincent.angkringanmbahsingo2.Fragments.DetailMetodeFragment;
import vincent.angkringanmbahsingo2.Fragments.DetailPesananFragment;
import vincent.angkringanmbahsingo2.Fragments.HomeFragment;
import vincent.angkringanmbahsingo2.Fragments.InterfaceMenuFragment;
import vincent.angkringanmbahsingo2.Fragments.KeranjangFragment;
import vincent.angkringanmbahsingo2.Fragments.MakananFragment;
import vincent.angkringanmbahsingo2.Fragments.MinumanFragment;
import vincent.angkringanmbahsingo2.Fragments.ProfilFragment;
import vincent.angkringanmbahsingo2.Fragments.RiwayatFragment;
import vincent.angkringanmbahsingo2.R;

public class MainHome extends AppCompatActivity {

    Animation easeOutSineBottom;
    ViewPager2 pagerMain;
    BottomNavigationView navbar;
//    Path path = new Path();
    public static TextView set1, set2, set3, set4, set5;

    ArrayList<Fragment> fragarr = new ArrayList<>();
    private OnBackPressedDispatcher onBackPressedDispatcher;
    private OnBackPressedCallback onBackPressedCallback;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // Inisiasi komponen animasi
        navbar = (BottomNavigationView) findViewById(R.id.fhxbottomnavbar);

        // Inisiasi komponen utama
        pagerMain = findViewById(R.id.fragmentcontainerhome);
        set1 = findViewById(R.id.mainxtxtset1);
        set2 = findViewById(R.id.mainxtxtset2);
        set3 = findViewById(R.id.mainxtxtset3);
        set4 = findViewById(R.id.mainxtxtset4);
        set5 = findViewById(R.id.mainxtxtset5);

        // Membuat animasi
        easeOutSineBottom = AnimationUtils.loadAnimation(this, R.anim.ease_out_sine_bottom);

        navbar.startAnimation(easeOutSineBottom);

//        path.addRoundRect(50, 50, navbar.getWidth(), navbar.getHeight(), 25, 25, Path.Direction.CCW);
//        ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(path, navbar.getWidth(), navbar.getHeight()));
//        shapeDrawable.getPaint().setColor(Color.parseColor("#FFB0F9FF"));
//        navbar.setBackground(shapeDrawable);
        navbar.setBackgroundColor(Color.parseColor("#FFDFFDFF"));

        set1.setText("1");
        set2.setText("1");
        set3.setText("1");
        set4.setText("1");
        set5.setText("1");
        if (set1.getText().toString().equals("1") && set2.getText().toString().equals("1") && set3.getText().toString().equals("1") && set4.getText().toString().equals("1") && set5.getText().toString().equals("1")) {
            // Menambahkan Array Fragment ke dalam Bottom Navigation
            fragarr.add(new HomeFragment());
            fragarr.add(new MakananFragment());
            fragarr.add(new MinumanFragment());
            fragarr.add(new RiwayatFragment());
            fragarr.add(new KeranjangFragment());

            AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragarr);
            pagerMain.setAdapter(adapterViewPager);

            // Fungsi ketika Bottom Navigation di swipe
            pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            navbar.setSelectedItemId(R.id.hfxmenu1);
                            break;
                        case 1:
                            navbar.setSelectedItemId(R.id.hfxmenu2);
                            break;
                        case 2:
                            navbar.setSelectedItemId(R.id.hfxmenu3);
                            break;
                        case 3:
                            navbar.setSelectedItemId(R.id.hfxmenu4);
                            break;
                        case 4:
                            navbar.setSelectedItemId(R.id.hfxmenu5);
                            break;
                    }
                    navbar.setSelectedItemId(position);
                    super.onPageSelected(position);
                }
            });

            // Fungsi ketika Bottom Navigation di klik
            navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.hfxmenu1:
                            pagerMain.setCurrentItem(0);
                            break;
                        case R.id.hfxmenu2:
                            pagerMain.setCurrentItem(1);
                            break;
                        case R.id.hfxmenu3:
                            pagerMain.setCurrentItem(2);
                            break;
                        case R.id.hfxmenu4:
                            pagerMain.setCurrentItem(3);
                            break;
                        case R.id.hfxmenu5:
                            pagerMain.setCurrentItem(4);
                            break;
                    }
                    return true;
                }
            });
        } else if (set1.getText().toString().equals("0") && set2.getText().toString().equals("0") && set3.getText().toString().equals("0") && set4.getText().toString().equals("0") && set5.getText().toString().equals("0")) {
            System.out.println("Done");
        }

    }

    // Fungsi ketika tombol Back Button di klik
    @Override
    public void onBackPressed() {
        if (InterfaceMenuFragment.backpressedlistener!=null || ProfilFragment.backpressedlistener!=null || DetailPesananFragment.backpressedlistener!=null || DetailAlamatFragment.backpressedlistener!=null || DetailMetodeFragment.backpressedlistener!=null || DetailKuponFragment.backpressedlistener!=null){
            System.out.println("back");
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                Runtime.getRuntime().exit(0);
            } else {
                Toast.makeText(getBaseContext(), "Tekan Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
            }
            mBackPressed = System.currentTimeMillis();
        }
        if(InterfaceMenuFragment.backpressedlistener!=null){
            InterfaceMenuFragment.backpressedlistener.onBackPressed();
        } else if(ProfilFragment.backpressedlistener!=null){
            ProfilFragment.backpressedlistener.onBackPressed();
        } else if(DetailPesananFragment.backpressedlistener!=null){
            DetailPesananFragment.backpressedlistener.onBackPressed();
        } else if(DetailAlamatFragment.backpressedlistener!=null){
            DetailAlamatFragment.backpressedlistener.onBackPressed();
        } else if(DetailMetodeFragment.backpressedlistener!=null){
            DetailMetodeFragment.backpressedlistener.onBackPressed();
        } else if(DetailKuponFragment.backpressedlistener!=null){
            DetailKuponFragment.backpressedlistener.onBackPressed();
        }
    }
}