package vincent.angkringanmbahsingo2.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import vincent.angkringanmbahsingo2.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(decor.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainLogin.class));
                finish();
            }
        }, 1500L); // Untuk mengatur waktu Splash Screen. 1000L = 1 detik
    }
}