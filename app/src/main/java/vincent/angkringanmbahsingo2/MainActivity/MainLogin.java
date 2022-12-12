package vincent.angkringanmbahsingo2.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import vincent.angkringanmbahsingo2.Fragments.LoginFragment;
import vincent.angkringanmbahsingo2.Fragments.RegisterFragment;
import vincent.angkringanmbahsingo2.Fragments.WelcomeSplashFragment;
import vincent.angkringanmbahsingo2.R;
import vincent.angkringanmbahsingo2.Fragments.WelcomeFragment;

public class MainLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        FragmentTransaction fragtr = getSupportFragmentManager().beginTransaction();
        fragtr.add(R.id.fragmentcontainer, new WelcomeSplashFragment());
        fragtr.commit();
    }

    // Fungsi ketika tombol Back Button di klik
    @Override
    public void onBackPressed() {
        if(WelcomeFragment.backpressedlistener!=null){
            WelcomeFragment.backpressedlistener.onBackPressed();
        } else if(LoginFragment.backpressedlistener!=null){
            LoginFragment.backpressedlistener.onBackPressed();
        } else if(RegisterFragment.backpressedlistener!=null){
            RegisterFragment.backpressedlistener.onBackPressed();
        }
    }
}