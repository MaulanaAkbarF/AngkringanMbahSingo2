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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import vincent.angkringanmbahsingo2.R;

public class LupaPasswordFragment extends Fragment {

    Animation easeOutSineTop, easeOutSineBottom, easeOutSineTopOut, easeOutSineBottomOut, easeOutQuadLeft, easeOutQuadRight, easeOutQuadLeftOut, easeOutQuadRightOut;
    LinearLayout lineartop, linearinput,  linearbottom;
    TextView metode, kirimulang, loginskrg;
    EditText user, inputcode, pass1, pass2;
    TextInputLayout usera, userb, passa, passb;
    Button btnkirimemail, btnkirimcode, btnkembali, btnubah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lupa_password, container, false);

        // Inisiasi komponen animasi
        lineartop = view.findViewById(R.id.lpassxlinearimage);
        linearinput = view.findViewById(R.id.lpassxlinearinput);
        linearbottom = view.findViewById(R.id.lpassxlinearbutton);
        metode = view.findViewById(R.id.lpassxtxtmetodelain);
        kirimulang = view.findViewById(R.id.lpassxtxtkirimulang);

        //TextInputLayout
        usera = view.findViewById(R.id.lpassxusernamea);
        userb = view.findViewById(R.id.lpassxcodeb);
        passa = view.findViewById(R.id.lpassxpassworda);
        passb = view.findViewById(R.id.lpassxpasswordb);
        //EditText
        user = view.findViewById(R.id.lpassxusername);
        inputcode = view.findViewById(R.id.lpassxcode);
        pass1 = view.findViewById(R.id.lpassxpassword1);
        pass2 = view.findViewById(R.id.lpassxpassword2);

        btnkirimemail = view.findViewById(R.id.lpassxbtnKirimemail);
        btnkirimcode = view.findViewById(R.id.lpassxbtnKonfirmcode);
        btnkembali = view.findViewById(R.id.lpassxbtnKembali);
        btnubah = view.findViewById(R.id.lpassxbtnubah);
        loginskrg = view.findViewById(R.id.lpassxtxtloginskrg);

        // Membuat animasi
        easeOutSineTop = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top);
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);
        easeOutSineTopOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top_out);
        easeOutSineBottomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom_out);
        easeOutQuadLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left);
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_left_out);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        lineartop.startAnimation(easeOutSineTop);
        linearbottom.startAnimation(easeOutSineBottom);
        linearinput.startAnimation(easeOutSineBottom);

        // Menyembunyikan layout
        kirimulang.setVisibility(View.GONE);
        inputcode.setVisibility(View.GONE);
        passa.setVisibility(View.GONE);
        passb.setVisibility(View.GONE);
        btnkirimcode.setVisibility(View.GONE);
        btnkembali.setVisibility(View.GONE);
        btnubah.setVisibility(View.GONE);

        btnkirimemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userb.setVisibility(View.VISIBLE);
                inputcode.setVisibility(View.VISIBLE);
                btnkirimcode.setVisibility(View.VISIBLE);
                btnkembali.setVisibility(View.VISIBLE);
                kirimulang.setVisibility(View.VISIBLE);
                userb.startAnimation(easeOutSineBottom);
                inputcode.startAnimation(easeOutSineBottom);
                btnkirimcode.startAnimation(easeOutSineBottom);
                btnkembali.startAnimation(easeOutSineBottom);
                kirimulang.startAnimation(easeOutSineBottom);
                btnkirimemail.startAnimation(easeOutQuadRightOut);
                metode.startAnimation(easeOutQuadRightOut);
                btnkirimemail.setVisibility(View.GONE);
                metode.setVisibility(View.GONE);
            }
        });

        btnkirimcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passa.setVisibility(View.VISIBLE);
                passb.setVisibility(View.VISIBLE);
                pass1.setVisibility(View.VISIBLE);
                pass2.setVisibility(View.VISIBLE);
                btnubah.setVisibility(View.VISIBLE);
                passa.startAnimation(easeOutQuadLeft);
                passb.startAnimation(easeOutQuadLeft);
                pass1.startAnimation(easeOutQuadLeft);
                pass2.startAnimation(easeOutQuadLeft);
                btnubah.startAnimation(easeOutSineBottom);
                usera.startAnimation(easeOutQuadRightOut);
                userb.startAnimation(easeOutQuadRightOut);
                user.startAnimation(easeOutQuadRightOut);
                inputcode.startAnimation(easeOutQuadRightOut);
                btnkirimcode.startAnimation(easeOutQuadRightOut);
                btnkembali.startAnimation(easeOutQuadRightOut);
                kirimulang.startAnimation(easeOutQuadRightOut);
                usera.setVisibility(View.GONE);
                userb.setVisibility(View.GONE);
                user.setVisibility(View.GONE);
                inputcode.setVisibility(View.GONE);
                btnkirimcode.setVisibility(View.GONE);
                btnkembali.setVisibility(View.GONE);
                kirimulang.setVisibility(View.GONE);
            }
        });

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnkirimemail.setVisibility(View.VISIBLE);
                metode.setVisibility(View.VISIBLE);
                btnkirimemail.startAnimation(easeOutQuadRight);
                metode.startAnimation(easeOutQuadRight);
                userb.startAnimation(easeOutQuadRightOut);
                inputcode.startAnimation(easeOutQuadRightOut);
                btnkirimcode.startAnimation(easeOutQuadRightOut);
                btnkembali.startAnimation(easeOutQuadRightOut);
                kirimulang.startAnimation(easeOutQuadRightOut);
                userb.setVisibility(View.GONE);
                inputcode.setVisibility(View.GONE);
                btnkirimcode.setVisibility(View.INVISIBLE);
                btnkembali.setVisibility(View.INVISIBLE);
                kirimulang.setVisibility(View.INVISIBLE);
            }
        });

        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).addToBackStack("tag").commit();
            }
        });

        loginskrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragtr = getFragmentManager().beginTransaction();
                fragtr.replace(R.id.fragmentcontainer, new LoginFragment()).addToBackStack("tag").commit();
            }
        });
        return view;
    }
}