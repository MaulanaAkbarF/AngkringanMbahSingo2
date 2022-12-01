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

import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.R;

public class SplashSelesaiFragment extends Fragment {

    Animation easeOutQuadRight, easeOutQuadLeftOut;
    ImageView image;
    TextView teks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_selesai, container, false);

        // Inisiasi komponen animasi
        teks = (TextView) view.findViewById(R.id.ssfxtext);
        image = (ImageView) view.findViewById(R.id.ssfximage);

        // Membuat animasi
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);

        image.startAnimation(easeOutQuadRight);
        teks.startAnimation(easeOutQuadRight);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.startAnimation(easeOutQuadLeftOut);
                teks.startAnimation(easeOutQuadLeftOut);
                image.setVisibility(View.GONE);
                teks.setVisibility(View.GONE);
                closeFragment();
            }
        }, 1500L); // Untuk mengatur waktu Splash Screen. 1000L = 1 detik
        return view;
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
    }
}