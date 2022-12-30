package vincent.angkringanmbahsingo2.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vincent.angkringanmbahsingo2.API.API;
import vincent.angkringanmbahsingo2.API.APIInterface;
import vincent.angkringanmbahsingo2.ModelAPI.ResponseTransaksi;
import vincent.angkringanmbahsingo2.R;

public class SplashSelesaiFragment extends Fragment {

    Animation easeOutSineBottom, easeOutSineTopOut;
    TextView idtransaksi, subtotal, transfer, kirimbukti, kembali;
    CardView salinsub, salintf;
    ImageView gambarbukti;
    ConstraintLayout consanimate;
    APIInterface apiInterface;
    private ClipboardManager clipboardManager;
    static String currency = "Rp. %,d";

    public Uri ur;

    String dataIdTransaksi, dataTransfer;
    int dataSubtotal;
    public void setDataTransaksi(String dataIdTransaksi, int dataSubtotal, String dataTransfer) {
        this.dataIdTransaksi = dataIdTransaksi;
        this.dataSubtotal = dataSubtotal;
        this.dataTransfer = dataTransfer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_selesai, container, false);

        // Inisiasi komponen animasi
        consanimate = view.findViewById(R.id.consanimate);

        // Membuat animasi
        easeOutSineBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_bottom);
        easeOutSineTopOut = AnimationUtils.loadAnimation(getActivity(), R.anim.ease_out_sine_top_out);

        consanimate.startAnimation(easeOutSineBottom);
        idtransaksi = view.findViewById(R.id.ssfxtxtidtrs);
        subtotal = view.findViewById(R.id.ssfxtxtsubtotal);
        transfer = view.findViewById(R.id.ssfxtxttransfer);
        salinsub = view.findViewById(R.id.ssfxcardsub);
        salintf = view.findViewById(R.id.ssfxcardtf);
        kirimbukti = view.findViewById(R.id.ssfxbtnuploadimage);
        kembali = view.findViewById(R.id.ssfxbtnback);
        gambarbukti = view.findViewById(R.id.ssfximage);
        clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                consanimate.startAnimation(easeOutSineTopOut);
//                consanimate.setVisibility(View.GONE);
//                closeFragment();
//            }
//        }, 1500L); // Untuk mengatur waktu Splash Screen. 1000L = 1 detik

        salinsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipboardManager.setText(String.valueOf(subtotal));
                Toast.makeText(getActivity(), "Subtotal disalin", Toast.LENGTH_SHORT).show();
            }
        });

        salintf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipboardManager.setText(dataTransfer);
                Toast.makeText(getActivity(), "Nomor Rekening disalin", Toast.LENGTH_SHORT).show();
            }
        });

        kirimbukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImg();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consanimate.startAnimation(easeOutSineTopOut);
                consanimate.setVisibility(View.GONE);
                closeFragment();
            }
        });

        getData();
        return view;
    }

    private void getData(){
        idtransaksi.setText(dataIdTransaksi);
        subtotal.setText(String.format(currency, dataSubtotal));
        transfer.setText(dataTransfer);
    }

    private void closeFragment(){
        FragmentTransaction fragtr = getFragmentManager().beginTransaction().remove(this);
        fragtr.commit();
    }

    public void getImg() {
        final CharSequence[] opsiImg = {"Gallery", "Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih gambar dari");
        builder.setItems(opsiImg, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 0);
                    break;
                case 1:
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 1);
                    break;
            }
        });
        builder.create().show();
    }

    public void sendImageToDatabase(){
        String path = getRealPathFromUri(getContext(), ur);
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("gambarbukti", file.getName(), requestFile);
        HomeFragment hfg = new HomeFragment();
        apiInterface = API.getService().create(APIInterface.class);
        Call<ResponseTransaksi> riwayatCall = apiInterface.example(body);
        riwayatCall.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {

            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            ur = data.getData();
            gambarbukti.setImageURI(ur);
        }
    }

//    public static String getRealPathFromUri(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
}