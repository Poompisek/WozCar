package com.example.trainee1.wozcar.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trainee1.wozcar.BuildConfig;
import com.example.trainee1.wozcar.Network.Model.ApiResponse;
import com.example.trainee1.wozcar.Network.ConnectionService;
import com.example.trainee1.wozcar.Network.Model.Prediction;
import com.example.trainee1.wozcar.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private ImageView imgV_camera;
    private Uri uri;
    private Bitmap bm;
    private ImageButton btn_submit;
    private MultipartBody.Part file1 = null;
    private String userChoosenTask;
    private Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isEnableLog();

        imgV_camera = (ImageView) findViewById(R.id.imgV_camera);
        btn_submit = (ImageButton) findViewById(R.id.imgB_submit);

        imgV_camera.setOnClickListener(onClickListener);
        btn_submit.setOnClickListener(onClickListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imgV_camera.setImageBitmap(bm);
        imgV_camera.setRotation(90);
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File imgFile1 = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/IMG_Image01.jpg");
        FileOutputStream fo;
        try {
            imgFile1.createNewFile();

            fo = new FileOutputStream(imgFile1);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgV_camera.setImageBitmap(thumbnail);
        imgV_camera.setRotation(90);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(MainActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void callImage() {

        File imgFile1 = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/IMG_Image01.jpg");
        if (imgFile1.exists()) {
            RequestBody reqFile1 = RequestBody.create(MediaType.parse("upload "), imgFile1);
            file1 = MultipartBody.Part.createFormData("image", imgFile1.getName(), reqFile1);
        }

        Call<ApiResponse> call = ConnectionService.getInstance().getApiService().getPrediction(file1);
        call.enqueue(claimRequestCallBack);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == imgV_camera) {
                selectImage();
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File f = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/IMG_Image01.jpg");
//                uri = Uri.fromFile(f);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(Intent.createChooser(intent
//                        , "Take a pict ure with"), REQUEST_CAMERA);
            }

            if (v == btn_submit) {

                if (null != imgV_camera.getDrawable()) {
                    callImage();
                } else {
                    Toast.makeText(getApplicationContext()
                            , "No picture", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    final Callback<ApiResponse> claimRequestCallBack = new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if (response.isSuccessful()) {
                ApiResponse apiResponse = response.body();

                List<Prediction> list = apiResponse.getPredictions();

                Toast.makeText(getApplicationContext()
                        , "Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext()
                        , "unSuccessful", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, final Throwable t) {
            Timber.d("Fail");
            Toast.makeText(getApplicationContext()
                    , "Fail", Toast.LENGTH_SHORT).show();
        }
    };

    private boolean isEnableLog() {
        if (BuildConfig.DEBUG) {
            return true;
        }
        return false;
    }

}
