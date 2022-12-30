package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.Dependencies.Backpressedlistener;
import vincent.angkringanmbahsingo2.MainActivity.MainHome;
import vincent.angkringanmbahsingo2.MainActivity.MainLogin;
import vincent.angkringanmbahsingo2.ModelAPI.DataItemLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;

public class ProfilFragment extends Fragment implements Backpressedlistener {

    Animation easeOutQuadRight, easeOutQuadRightOut;
    ScrollView scrollmain;
    public static Backpressedlistener backpressedlistener;
    LinearLayout logout;
    ImageView image, btnquit, btneditnomor, btneditalamat, btneditemail, btneditpass;
    TextView fpeditinfo, nama, nomortlp, alamat, username, email;
    Dialog dialog;

    HomeFragment hfg = new HomeFragment();
    String phonecode = "+62";
    APIInterface apiInterface;
    private List<DataItemLogin> dataUserProfil = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        scrollmain = view.findViewById(R.id.profxscrollmain);
        image = view.findViewById(R.id.profximageprofil);
        btnquit = view.findViewById(R.id.profxbtnquit);

        // TextView Info
        fpeditinfo = view.findViewById(R.id.profxtxtedit);
        nama = view.findViewById(R.id.profxnamaprofil);
        username = view.findViewById(R.id.profxusername);
        nomortlp = view.findViewById(R.id.profxnomortelp);
        alamat = view.findViewById(R.id.profxalamat);
        email = view.findViewById(R.id.profxemail);
        logout = view.findViewById(R.id.profxlogout);

        // Button Edit
        btneditnomor = view.findViewById(R.id.profxeditnomor);
        btneditalamat = view.findViewById(R.id.profxeditalamat);
        btneditemail = view.findViewById(R.id.profxeditemail);
        btneditpass = view.findViewById(R.id.profxeditpass);

        // Membuat animasi
        easeOutQuadRight = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right);
        easeOutQuadRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_quad_right_out);

        scrollmain.setVisibility(View.VISIBLE);
        scrollmain.startAnimation(easeOutQuadRight);

        fpeditinfo.setOnClickListener(view1 -> {
            showAlertEditinfo();
            dialog.show();
        });

        btneditnomor.setOnClickListener(view12 -> {
            showAlertNomor();
            dialog.show();
        });

        btneditalamat.setOnClickListener(view13 -> {
            showAlertAlamat();
            dialog.show();
        });

        btneditemail.setOnClickListener(view14 -> {
            showAlertEmail();
            dialog.show();
        });

        btneditpass.setOnClickListener(view15 -> {
            showAlertPassword();
            dialog.show();
        });

        btnquit.setOnClickListener(view16 -> {
            scrollmain.startAnimation(easeOutQuadRightOut);
            scrollmain.setVisibility(View.GONE);
            closeFragment();
        });

        logout.setOnClickListener(view17 -> {
            showAlertLogout();
            dialog.show();
        });

        nama.setText(hfg.teksnama.getText());
        setHasOptionsMenu(true);
        getDataLoginRetrofit();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Mengatur agar tombol back menjalankan kode yang ditentukan
            case android.R.id.home:
                // Kode yang akan dieksekusi ketika tombol back ditekan
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDataLoginRetrofit() {
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseLogin> loginCall = apiInterface.setDataHome(nama.getText().toString());
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                dataUserProfil = response.body().getData();
                username.setText(dataUserProfil.get(0).getUsername());
                nomortlp.setText("+62"+dataUserProfil.get(0).getNoHp());
                alamat.setText(dataUserProfil.get(0).getAlamat());
                email.setText(dataUserProfil.get(0).getEmail());
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAlertEditinfo(){
        Button editnama, edituser, editfoto;
        TextView eiteksnama, eiteksuser;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_editinfo,null);
        editnama = view.findViewById(R.id.alertxbtneditnama);
        edituser = view.findViewById(R.id.alertxbtneditusername);
        editfoto = view.findViewById(R.id.alertxbtneditfoto);
        eiteksnama = view.findViewById(R.id.alertxtxtnama);
        eiteksuser = view.findViewById(R.id.alertxtxtuser);
        eiteksnama.setText(nama.getText().toString());
        eiteksuser.setText(username.getText().toString());
        builder.setView(view);
        dialog = builder.create();
        editnama.setOnClickListener(view1 -> {
            EditText input;
            Button batal, kirim;
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            View view2 = getLayoutInflater().inflate(R.layout.alert_nama,null);
            input = view2.findViewById(R.id.alertxinputjumlah);
            batal = view2.findViewById(R.id.alertxbtnbatal);
            kirim = view2.findViewById(R.id.alertxbtnkirim);
            builder1.setView(view2);
            dialog = builder1.create();
            input.setText(nama.getText());
            kirim.setOnClickListener(view11 -> {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik Nama Anda");
                } else {
                    apiInterface = API.getService().create(APIInterface.class);
                    Call<ResponseLogin> loginCall = apiInterface.setUserNama(input.getText().toString(), username.getText().toString());
                    loginCall.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            dataUserProfil = response.body().getData();
                            nama.setText(dataUserProfil.get(0).getNamaLengkap());
                            eiteksnama.setText(dataUserProfil.get(0).getNamaLengkap());
                            hfg.teksnama.setText(dataUserProfil.get(0).getNamaLengkap());
                        }
                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            });
            batal.setOnClickListener(view112 -> dialog.dismiss());
            dialog.show();
        });
        edituser.setOnClickListener(view12 -> {
            EditText input;
            Button batal, kirim;
            AlertDialog.Builder builder12 = new AlertDialog.Builder(getActivity());
            View view2 = getLayoutInflater().inflate(R.layout.alert_username,null);
            input = view2.findViewById(R.id.alertxinputjumlah);
            batal = view2.findViewById(R.id.alertxbtnbatal);
            kirim = view2.findViewById(R.id.alertxbtnkirim);
            builder12.setView(view2);
            dialog = builder12.create();
            input.setText(username.getText());
            kirim.setOnClickListener(view1212 -> {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik Username Anda");
                } else {
                    apiInterface = API.getService().create(APIInterface.class);
                    Call<ResponseLogin> loginCall = apiInterface.setUserUsername(nama.getText().toString(), input.getText().toString());
                    loginCall.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            dataUserProfil = response.body().getData();
                            username.setText(dataUserProfil.get(0).getUsername());
                            eiteksuser.setText(dataUserProfil.get(0).getUsername());
                            hfg.teksuser.setText(dataUserProfil.get(0).getUsername());
                        }
                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            });
            batal.setOnClickListener(view121 -> dialog.dismiss());
            dialog.show();
        });
        editfoto.setOnClickListener(view13 -> {

        });
    }

    private void showAlertNomor(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_nomortlp,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        input.setText(nomortlp.getText());
        kirim.setOnClickListener(view1 -> {
            String replace1;
            if (input.getText().toString().isEmpty()){
                input.setError("Anda belum mengetik nomor telepon Anda");
            } else {
                replace1 = input.getText().toString();
                if (input.getText().toString().contains(phonecode)){
                    replace1 = replace1.replace(phonecode,"");
                    input.setText(replace1);
                }
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseLogin> loginCall = apiInterface.setUserNohp(nama.getText().toString(), input.getText().toString());
                loginCall.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        dataUserProfil = response.body().getData();
                        nomortlp.setText(phonecode+dataUserProfil.get(0).getNoHp());
                        hfg.teksnomor.setText(phonecode+dataUserProfil.get(0).getNoHp());
                    }
                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        batal.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void showAlertAlamat(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_alamat,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        input.setText(alamat.getText());
        kirim.setOnClickListener(view1 -> {
            if (input.getText().toString().isEmpty()){
                input.setError("Anda belum mengisi Alamat Anda");
            } else {
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseLogin> loginCall = apiInterface.setUserAlamat(nama.getText().toString(), input.getText().toString());
                loginCall.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        dataUserProfil = response.body().getData();
                        alamat.setText(dataUserProfil.get(0).getAlamat());
                        hfg.teksalamat.setText(dataUserProfil.get(0).getAlamat());
                    }
                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        batal.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void showAlertEmail(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_email,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        input.setText(email.getText());
        kirim.setOnClickListener(view1 -> {
            if (input.getText().toString().isEmpty()){
                input.setError("Anda belum mengisi E-Mail Anda");
            } else {
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseLogin> loginCall = apiInterface.setUserEmail(nama.getText().toString(), input.getText().toString());
                loginCall.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        dataUserProfil = response.body().getData();
                        email.setText(dataUserProfil.get(0).getEmail());
                        hfg.teksemail.setText(dataUserProfil.get(0).getEmail());
                    }
                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        batal.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void showAlertPassword(){
        EditText input;
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_password,null);
        input = view.findViewById(R.id.alertxinputjumlah);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        kirim.setOnClickListener(view1 -> {
            if (input.getText().toString().isEmpty()){
                input.setError("Anda belum mengetik password Anda");
            } else {
                apiInterface = API.getService().create(APIInterface.class);
                Call<ResponseLogin> loginCall = apiInterface.setUserPassword(nama.getText().toString(), input.getText().toString());
                loginCall.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        Toast.makeText(getActivity(), "Password berhasil diubah!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        batal.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void showAlertLogout(){
        Button batal, kirim;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_logout,null);
        batal = view.findViewById(R.id.alertxbtnbatal);
        kirim = view.findViewById(R.id.alertxbtnkirim);
        builder.setView(view);
        dialog = builder.create();
        batal.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), MainLogin.class));
            dialog.dismiss();
        });
        kirim.setOnClickListener(view12 -> dialog.dismiss());
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
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
        scrollmain.startAnimation(easeOutQuadRightOut);
        scrollmain.setVisibility(View.GONE);
        closeFragment();
    }
}