package vincent.angkringanmbahsingo2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.R;

public class HomeSplashScreenFragment extends Fragment {

    Animation easeOutSineTop, easeOutSineBottom;
    ImageView image;
    LinearLayout judul;

    // Digunakan ketika Splash Screen berada di Activity Login
    TextView nama;

    String dataNama;
    public void setDataNama(String dataNama) {this.dataNama = dataNama;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_splash_screen, container, false);

        // Inisiasi komponen animasi
        judul = view.findViewById(R.id.hssxlinearjudul);
        image = view.findViewById(R.id.hssximageView);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);

        judul.startAnimation(easeOutSineTop);
        image.startAnimation(easeOutSineBottom);

        // Inisiasi komponen utama
        nama = (TextView) view.findViewById(R.id.hssxtextNama);

        // Digunakan ketika Splash Screen berada di Activity Login
        nama.setText(dataNama);

//        // Digunakan ketika Splash Screen berada di Activity Home
//        Bundle extras = getActivity().getIntent().getExtras();
//        namamu = extras.getString(KEY_NAME);
//        nama.setText(namamu);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            // Digunakan ketika Splash Screen berada di Activity Home
//            public void run() {
//                startActivity(new Intent(getActivity(), MainHome.class));
//            }

            // Digunakan ketika Splash Screen berada di Activity Login
            public void run() {
                Intent i = new Intent(getActivity(), MainHome.class);
                i.putExtra("datanama", nama.getText().toString());
                startActivity(i);
            }
        }, 1400L); // Untuk mengatur waktu Splash Screen. 1000L = 1 detik
        return view;
    }
}