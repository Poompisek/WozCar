package com.example.trainee1.wozcar.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.trainee1.wozcar.R;

import java.io.File;

public class SubmitActivity extends AppCompatActivity {

    ImageView imgShow;
    ListView lv_Detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        findViewById();

        setImg();


    }
    private void findViewById(){
        imgShow = (ImageView)findViewById(R.id.imgV_show);
        lv_Detail = (ListView)findViewById(R.id.listV_Detail);
    }

    private void setImg() {
        File imgFile = new File(Environment.getExternalStorageDirectory(),"DCIM/Camera/IMG_Image01.jpg");

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgShow.setRotation(90);
            imgShow.setImageBitmap(myBitmap);
        }
    }


}
