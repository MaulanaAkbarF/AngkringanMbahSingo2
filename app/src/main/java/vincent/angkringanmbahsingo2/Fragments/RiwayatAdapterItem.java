package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.R;

public class RiwayatAdapterItem extends Fragment {

    Animation easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    CheckBox check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_rvriwayat, container, false);

        // Inisiasi komponen animasi
        check = view.findViewById(R.id.friwxcheckbox);

        // Membuat animasi
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        check.setVisibility(View.GONE);
        return view;
    }

    public void hideCheck(){
        check.setVisibility(View.GONE);
        check.startAnimation(easeOutQuadLeftOut);
    }

    public void showCheck(){
        check.setVisibility(View.VISIBLE);
        check.startAnimation(easeOutQuadLeft);
    }
}