package com.aiyaschool.aiya.message.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.ImageUtil;
import com.edmodo.cropper.CropImageView;

import java.io.File;

public class PhotoCropActivity extends AppCompatActivity {

    private static final String KEY_PATH = "path";
    private CropImageView cropImageView;
    private String srcPath;
    Button confirmButton;



//
//    @Override
//    protected void setToolBar() {
//        toolbar.setTitle(R.string.photosPreview);
//        srcPath = getIntent().getStringExtra(KEY_PATH);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_crop);
        confirmButton = (Button) findViewById(R.id.photo_crop_activity_complete);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        srcPath = getIntent().getStringExtra(KEY_PATH);
        if (TextUtils.isEmpty(srcPath)) {
            return;
        }
        Bitmap mBitmap= BitmapFactory.decodeFile(srcPath);
        int width=this.getResources().getDisplayMetrics().widthPixels;
        float factor = width / (float) mBitmap.getWidth();
        mBitmap = Bitmap.createScaledBitmap(mBitmap, width, (int) (mBitmap.getHeight() * factor), true);
        cropImageView.setImageBitmap(mBitmap);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropBitmap = cropImageView.getCroppedImage();
//            Bitmap cropBitmap = cropView.crop();
                File dir = new File(Environment.getExternalStorageDirectory() + "/ShootHzj/Pic");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = Environment.getExternalStorageDirectory() + "/ShootHzj/Pic/H." + System.currentTimeMillis() + ".jpg";
                boolean save = ImageUtil.saveBitmapToFile(cropBitmap, fileName);
                if (save) {
                    Intent data = new Intent();
                    data.putExtra("data", fileName);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_option, menu);
//        menu.findItem(R.id.action_done).setVisible(true);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_done) {
//            Bitmap cropBitmap = cropImageView.getCroppedImage();
////            Bitmap cropBitmap = cropView.crop();
//            String fileName = ConfigApi.getCachePath() + System.currentTimeMillis() + ".jpg";
//            boolean save = ImageUtil.saveBitmapToFile(cropBitmap, fileName);
//            if (save) {
//                Intent data = new Intent();
//                data.putExtra("data", fileName);
//                setResult(RESULT_OK, data);
//                finish();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
