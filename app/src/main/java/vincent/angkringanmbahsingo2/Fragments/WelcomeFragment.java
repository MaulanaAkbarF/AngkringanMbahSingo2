package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.R;

public class WelcomeFragment extends Fragment implements Backpressedlistener {

    Animation easeOutSineTop, easeOutSineBottom;
    LinearLayout judul, image, button;
    Button btnmasuk;
    TextView daftar;
    public static Backpressedlistener backpressedlistener;

    String check;
    public void setTransition(String check) {this.check = check;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        // Inisiasi komponen animasi
        judul = (LinearLayout) view.findViewById(R.id.fwxlinearjudul);
        image = (LinearLayout) view.findViewById(R.id.fwxlinearimage);
        button = (LinearLayout) view.findViewById(R.id.fwxlinearbutton);

        // Inisiasi komponen utama
        btnmasuk = (Button) view.findViewById(R.id.wpxbtnmasuk);
        daftar = (TextView) view.findViewById(R.id.wpxtxtDaftar);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);

        // Fungsi Tombol Masuk
        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment lf = new LoginFragment();
                lf.setTransition("go");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragtr = fragmentManager.beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, lf).commit();
            }
        });

        // Membuat Fungsi Daftar Gratis
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment rf = new RegisterFragment();
                rf.setTransition("go");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragtr = fragmentManager.beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, rf).commit();
            }
        });

        if (check != null){
            Slide slide = new Slide();
            slide.setDuration(450);
            slide.setSlideEdge(Gravity.RIGHT);
            TransitionManager.beginDelayedTransition(container, slide);
        } else {
            judul.startAnimation(easeOutSineTop);
            image.startAnimation(easeOutSineBottom);
            button.startAnimation(easeOutSineBottom);
        }

        return view;
    }

    @Override
    public void onPause() {
        backpressedlistener=null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        backpressedlistener=this;
    }

    @Override
    public void onBackPressed() {
        Runtime.getRuntime().exit(0);
    }
}