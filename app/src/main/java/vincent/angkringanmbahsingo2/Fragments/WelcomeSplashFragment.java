package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import vincent.angkringanmbahsingo2.R;

public class WelcomeSplashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_splash, container, false);

        View decor = getActivity().getWindow().getDecorView();
        decor.setSystemUiVisibility(decor.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WelcomeFragment wf = new WelcomeFragment();
                wf.setTransition("go");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragtr = fragmentManager.beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, wf).commit();
            }
        }, 1500L); // Untuk mengatur waktu Splash Screen. 1000L = 1 detik
        return view;
    }
}