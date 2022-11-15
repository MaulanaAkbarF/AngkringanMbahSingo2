package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.navigation.NavigationBarView;

import vincent.angkringanmbahsingo2.R;

public class HomeFragment extends Fragment {

    Animation easeOutSineTop, easeOutQuadLeft;
    LinearLayout judul;
    ScrollView rvdatalayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisiasi komponen animasi
        judul = (LinearLayout) view.findViewById(R.id.fhxlinearheader);
        rvdatalayout = (ScrollView) view.findViewById(R.id.fhxscrollviewrv);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);

        judul.startAnimation(easeOutSineTop);
        rvdatalayout.startAnimation(easeOutQuadLeft);

        return view;
    }
}