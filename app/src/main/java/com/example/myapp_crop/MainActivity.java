package com.example.myapp_crop;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.IOException;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    int REQUEST_CODE = 99;
    ImageView scanPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scancam = findViewById(R.id.button);
        Button scanmedia=findViewById(R.id.button3);

        scanPic=findViewById(R.id.imageView);
        scanmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_MEDIA);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        scancam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_CAMERA);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == MainActivity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                scanPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}