package vincent.angkringanmbahsingo2.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
import vincent.angkringanmbahsingo2.ModelAPI.DataItemLogin;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseLogin;
import vincent.angkringanmbahsingo2.R;

public class ProfilFragment extends Fragment {

    Animation easeOutQuadRight, easeOutQuadRightOut;
    ScrollView scrollmain;
    ImageView image, btnquit, btneditnomor, btneditalamat, btneditemail, btneditpass;
    TextView fpeditinfo, nama, nomortlp, alamat, username, email, logout;
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

        fpeditinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertEditinfo();
                dialog.show();
            }
        });

        btneditnomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertNomor();
                dialog.show();
            }
        });

        btneditalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertAlamat();
                dialog.show();
            }
        });

        btneditemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertEmail();
                dialog.show();
            }
        });

        btneditpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertPassword();
                dialog.show();
            }
        });

        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollmain.startAnimation(easeOutQuadRightOut);
                scrollmain.setVisibility(View.GONE);
                closeFragment();
            }
        });

        nama.setText(hfg.teksnama.getText());
        getDataLoginRetrofit();
        return view;
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
        editnama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input;
                Button batal, kirim;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view2 = getLayoutInflater().inflate(R.layout.alert_nama,null);
                input = view2.findViewById(R.id.alertxinputjumlah);
                batal = view2.findViewById(R.id.alertxbtnbatal);
                kirim = view2.findViewById(R.id.alertxbtnkirim);
                builder.setView(view2);
                dialog = builder.create();
                input.setText(nama.getText());
                kirim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                    }
                });
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input;
                Button batal, kirim;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view2 = getLayoutInflater().inflate(R.layout.alert_username,null);
                input = view2.findViewById(R.id.alertxinputjumlah);
                batal = view2.findViewById(R.id.alertxbtnbatal);
                kirim = view2.findViewById(R.id.alertxbtnkirim);
                builder.setView(view2);
                dialog = builder.create();
                input.setText(username.getText());
                kirim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                                    hfg.teksemail.setText(dataUserProfil.get(0).getUsername());
                                }
                                @Override
                                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                                    Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                    }
                });
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        editfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
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
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        }
                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        }
                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().isEmpty()){
                    input.setError("Anda belum mengetik password Anda");
                } else {
                    apiInterface = API.getService().create(APIInterface.class);
                    Call<ResponseLogin> loginCall = apiInterface.setUserPassword(nama.getText().toString(), input.getText().toString());
                    loginCall.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            Toast.makeText(getActivity(), "Passowrd berhasil diubah!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(getActivity(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
    }
}