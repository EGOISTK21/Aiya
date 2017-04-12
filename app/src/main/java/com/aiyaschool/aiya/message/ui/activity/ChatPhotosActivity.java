package com.aiyaschool.aiya.message.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.adapter.ChatPhotosPagerAdapter;
import com.tencent.TIMElem;
import com.tencent.TIMImageElem;

import java.util.ArrayList;

public class ChatPhotosActivity extends AppCompatActivity {

    private TIMImageElem nowShowing;
    private ArrayList<TIMElem> list;
    private int index;
    private TextView progressTextView;
    private ViewPager viewPager;
    private ChatPhotosPagerAdapter adapter;
    private ArrayList<String> imgList;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_photos);
        Intent i = getIntent();
        imgList = (ArrayList<String>) i.getSerializableExtra("list");
        index = i.getIntExtra("now",0);

        progressTextView = (TextView) findViewById(R.id.photo_progress_showText);
        viewPager =(ViewPager) findViewById(R.id.pager);


        size = imgList.size();
        progressTextView.setText(index+1+"/"+size);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressTextView.setText(position+1+"/"+size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
