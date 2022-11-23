package vincent.angkringanmbahsingo2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import vincent.angkringanmbahsingo2.R;

public class ProfilFragment extends Fragment {

    Animation easeOutQuadRight, easeOutQuadRightOut;
    ScrollView scrollmain;
    ImageView image, btnquit, btneditnomor, btneditalamat, btneditemail, btneditpass;
    TextView nama, nomortlp, alamat, username, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        scrollmain = view.findViewById(R.id.profxscrollmain);
        image = view.findViewById(R.id.profximageprofil);
        btnquit = view.findViewById(R.id.profxbtnquit);
        btneditnomor = view.findViewById(R.id.profxeditnomor);
        btneditalamat = view.findViewById(R.id.profxeditalamat);
        btneditemail = view.findViewById(R.id.profxeditemail);
        btneditpass = view.findViewById(R.id.profxeditpass);

        // Membuat animasi
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        scrollmain.setVisibility(View.VISIBLE);
        scrollmain.startAnimation(easeOutQuadRight);

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollmain.startAnimation(easeOutQuadRightOut);
                scrollmain.setVisibility(View.GONE);
            }
        });

        return view;
    }
}