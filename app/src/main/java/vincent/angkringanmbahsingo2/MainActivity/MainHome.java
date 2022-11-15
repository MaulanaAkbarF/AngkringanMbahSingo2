package vincent.angkringanmbahsingo2.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import vincent.angkringanmbahsingo2.Dependencies.AdapterViewPager;
import vincent.angkringanmbahsingo2.Fragments.HomeFragment;
import vincent.angkringanmbahsingo2.Fragments.HomeSplashScreenFragment;
import vincent.angkringanmbahsingo2.Fragments.KeranjangFragment;
import vincent.angkringanmbahsingo2.Fragments.MakananFragment;
import vincent.angkringanmbahsingo2.Fragments.MinumanFragment;
import vincent.angkringanmbahsingo2.Fragments.RegisterFragment;
import vincent.angkringanmbahsingo2.Fragments.RiwayatFragment;
import vincent.angkringanmbahsingo2.R;

public class MainHome extends AppCompatActivity {

    Animation easeOutSineBottom;
    ViewPager2 pagerMain;
    BottomNavigationView navbar;

    ArrayList<Fragment> fragarr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // Inisiasi komponen animasi
        navbar = (BottomNavigationView) findViewById(R.id.fhxbottomnavbar);

        // Inisiasi komponen utama
        pagerMain = findViewById(R.id.fragmentcontainerhome);

        // Membuat animasi
        easeOutSineBottom = AnimationUtils.loadAnimation(this, R.anim.ease_out_sine_bottom);

        navbar.startAnimation(easeOutSineBottom);

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
                switch (position){
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
                switch (item.getItemId()){
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
    }
}