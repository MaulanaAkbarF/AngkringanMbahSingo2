package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.R;

public class WelcomeFragment extends Fragment {

    Animation easeOutSineTop, easeOutSineBottom;
    LinearLayout judul, image, button;
    Button btnmasuk;
    TextView daftar;

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

        judul.startAnimation(easeOutSineTop);
        image.startAnimation(easeOutSineBottom);
        button.startAnimation(easeOutSineBottom);

        // Fungsi Tombol Masuk
        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).addToBackStack(null).commit();
            }
        });

        // Membuat Fungsi Daftar Gratis
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }
}