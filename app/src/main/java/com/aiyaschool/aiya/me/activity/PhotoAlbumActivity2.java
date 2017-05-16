package com.aiyaschool.aiya.me.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.me.bean.ImagePathItem;
import com.aiyaschool.aiya.multi_image_selector.MultiImageSelector;
import com.aiyaschool.aiya.multi_image_selector.MultiImageSelectorFragment;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PhotoAlbumActivity2 extends AppCompatActivity {

    private final static String TAG = "PhotoAlbumActivity2";

    private String dateNow;
    private boolean isEdit;

    private TextView mTvBack,mTvPhotoEdit;

    private RecyclerView mRvPhotoAlbum;
    private RvAlbumAdapter rvAlbumAdapter;

    private List<String> mLtTime;
    private List<List<String>> mLtAlbum;
    private ArrayList<String> photoList;
    private ArrayList<String> mSelectPath;

    //创建一个集合来记录那些被选中的图片编号  最开始想用新的bean photo 修改数据库时报错  放弃
    private List<List<Integer>> mSelectedNumber;

    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    private SQLiteDatabase db;
    private RvAlbumAdapter.GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album2);
        initView();
        initData();

    }

    private void initView(){
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvPhotoEdit = (TextView) findViewById(R.id.tv_photo_edit);
        mRvPhotoAlbum = (RecyclerView) findViewById(R.id.rv_photo_album);
        rvAlbumAdapter = new RvAlbumAdapter();
        mRvPhotoAlbum.setAdapter(rvAlbumAdapter);
        mRvPhotoAlbum.setLayoutManager(new LinearLayoutManager(this));

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent();
                intent.putExtra("flag", "me");
                setResult(RESULT_OK,intent);
                finish();


            }
        });

        mTvPhotoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(55555);
                if(isEdit == false){
                    clearSelected();
                    isEdit = true;
                    mTvPhotoEdit.setText("删除");
                }else{
                    //TODO  将选中的图片移除
                    for(int i=0;i<mLtAlbum.get(0).size();i++){
                        Log.d(TAG, "onClick: "+mLtAlbum.get(0).get(i));
                    }
                    for(int i=0;i<mSelectedNumber.size();i++){
                        for(int j=0;j<mSelectedNumber.get(i).size();j++){
                            int position = mSelectedNumber.get(i).get(j);
                            Log.d(TAG, "position "+position);
                            Log.d(TAG, "size "+mLtAlbum.get(i).size());
                            Log.d(TAG, "get(position) "+mLtAlbum.get(i).get(position));
                            mLtAlbum.get(i).remove(position);
                            mLtAlbum.get(i).add(position,"");
                        }
                    }
                    for(int i=0;i<mLtAlbum.size();i++){
                        Iterator<String> it = mLtAlbum.get(i).iterator();
                        while(it.hasNext()){
                            String str = it.next();
                            System.out.println(str);
                            if(str.equals("")){
                                it.remove();
                            }
                        }
                    }
                    Log.d(TAG, "onClick: "+mLtAlbum.get(0).size());

                    clearSelected();
                    isEdit = false;
                    mTvPhotoEdit.setText("编辑");

                }

                rvAlbumAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        mLtTime = new ArrayList<>();
        mLtAlbum = new ArrayList<>();
        mSelectedNumber = new ArrayList<>();
        db = Connector.getDatabase();
        List<ImagePathItem> list;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateNow = df.format(new Date());
        System.out.println("DataSupport.isExist(ImagePathItem.class)" + DataSupport.isExist(ImagePathItem.class));
        if (DataSupport.isExist(ImagePathItem.class)) {
            list = DataSupport.findAll(ImagePathItem.class);
            System.out.println("initdata" + list.size());
            for (ImagePathItem imagePathItem : list) {
                String date = imagePathItem.getDate();
                List<String> path = imagePathItem.getImagePath();
                if (!date.equals(dateNow)) {
                    boolean a = path.remove(R.drawable.uploadpic_226x226 + "");
                    System.out.println("boolean"+a);
                    if (path.size() == 0) {
                        continue;
                    }
                }
                mLtTime.add(imagePathItem.getDate());
                mLtAlbum.add(path);
                mSelectedNumber.add(new ArrayList<Integer>());
                System.out.println("imagePathItem.getImagePath()" + imagePathItem.getImagePath());
            }
            DataSupport.deleteAll(ImagePathItem.class);
        }
        //将数据库中保存的数据放到mLtTime和mLtAlbum
        if(mLtTime.size()==0){
            mLtTime.add(0,dateNow);
            photoList = new ArrayList<>();
            photoList.add(R.drawable.uploadpic_226x226+"");
            mLtAlbum.add(0,photoList);
            mSelectedNumber.add(0,new ArrayList<Integer>());
        }else {
            if(!mLtTime.get(0).equals(dateNow)){
                mLtTime.add(0,dateNow);
                photoList = new ArrayList<>();
                photoList.add(R.drawable.uploadpic_226x226+"");
                mLtAlbum.add(0,photoList);
                mSelectedNumber.add(0,new ArrayList<Integer>());
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(isEdit){
            isEdit = false;
            mTvPhotoEdit.setText("编辑");
            clearSelected();
            rvAlbumAdapter.notifyDataSetChanged();
            return;
        }
        Log.d(TAG, "onBackPressed: "+mSelectedNumber.get(0).size());
        saveData();
        finish();
    }

    private void clearSelected(){
        for(int i=0;i<mSelectedNumber.size();i++){
            mSelectedNumber.get(i).clear();
        }
    }
    private void backToMain(){
        saveData();
        Intent intent = new Intent(PhotoAlbumActivity2.this,MainActivity.class);
        intent.putExtra("Flag","Me");
        setResult(RESULT_OK, intent);
        //startActivity(intent);
        finish();
    }
    private void saveData(){
        List<ImagePathItem> list = new ArrayList<>();
        for(int i=0;i<mLtTime.size();i++){
            ImagePathItem imagePathItem = new ImagePathItem();
            imagePathItem.setDate(mLtTime.get(i));
            imagePathItem.setImagePath(mLtAlbum.get(i));
            System.out.println("imagePathItem.setImagePath(mLtAlbum.get(i))"+mLtAlbum.get(i));
            list.add(imagePathItem);
        }
        DataSupport.saveAll(list);

        System.out.println("DataSupport.isExist(ImagePathItem.class)"+DataSupport.isExist(ImagePathItem.class));

    }




    class RvAlbumAdapter extends RecyclerView.Adapter<RvAlbumAdapter.AlbumViewHolder>{

        @Override
        public RvAlbumAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(PhotoAlbumActivity2.this).inflate(R.layout.photo_album_item,parent,false);
            AlbumViewHolder albumViewHolder = new AlbumViewHolder(view);
            return albumViewHolder;
        }

        @Override
        public void onBindViewHolder(final RvAlbumAdapter.AlbumViewHolder holder, final int position1) {

            holder.mTvDay.setText(mLtTime.get(position1));
            gridViewAdapter = new GridViewAdapter(PhotoAlbumActivity2.this, R.layout.grid_view_item,mLtAlbum.get(position1));
            holder.mGvPhoto.setAdapter(gridViewAdapter);
            holder.mGvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    String i = R.drawable.uploadpic_226x226+"";
                    String j = mLtAlbum.get(position1).get(position);
                    if(position1==0&&position==mLtAlbum.get(position1).size()-1&&i.equals(j)){
                        clearSelected();
                        isEdit = false;
                        //此时增加图片
                        String choiceMode = "multi";
                        pickImage(choiceMode);


                    }else{
                        //如果isEdit是true，checkmark选中   如果isEdit是false 此时查看大图
                        ImageView checkmark = (ImageView) view.findViewById(R.id.checkmark);
                        if(isEdit){
                            if(mSelectedNumber.get(position1).contains(position)){
                                checkmark.setImageResource(R.drawable.mis_btn_unselected);
                                mSelectedNumber.get(position1).remove(position);
                            }else{
                                checkmark.setImageResource(R.drawable.mis_btn_selected);
                                mSelectedNumber.get(position1).add(position);
                            }
                            //TODO  
                            System.out.println(77777);
                        }else{

                        }

                    }


                }



            });
        }

        @Override
        public int getItemCount() {
            return mLtTime.size();
        }

        class AlbumViewHolder extends RecyclerView.ViewHolder{

            private TextView mTvDay;
            private GridView mGvPhoto;
            public AlbumViewHolder(View itemView) {
                super(itemView);
                mTvDay = (TextView) itemView.findViewById(R.id.tv_day);
                mGvPhoto = (GridView) itemView.findViewById(R.id.grid_photo_album);

            }
        }
        class GridViewAdapter extends ArrayAdapter<String> {

            private Context mContext;
            private int layoutResourceId;
            private List<String> mGridData = new ArrayList<>();


            public GridViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
                super(context, resource, objects);
                this.mContext = context;
                this.layoutResourceId = resource;
                this.mGridData = objects;

            }



            public void setGridData(ArrayList<String> mGridData){
                this.mGridData = mGridData;
                notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                if(mGridData.size()>9){
                    return 9;
                }else{
                    return mGridData.size();
                }

            }


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ViewHolder holder;

                if (convertView == null) {
                    LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                    convertView = inflater.inflate(layoutResourceId, parent, false);
                    holder = new ViewHolder();
                    holder.imageView = (ImageView) convertView.findViewById(R.id.imgview_item);
                    holder.checkmark = (ImageView) convertView.findViewById(R.id.checkmark);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                String item = mGridData.get(position);

                File imageFile = new File(item);
                if (imageFile.exists()) {
                    // 显示图片
                    Picasso.with(mContext)
                            .load(imageFile)
                            .placeholder(R.drawable.mis_default_error)
                            .tag(MultiImageSelectorFragment.TAG)
                            .resize(226, 226)
                            .centerCrop()
                            .into(holder.imageView);
                    if(isEdit){
                        holder.checkmark.setVisibility(View.VISIBLE);
                    }
                }else{
                    holder.imageView.setImageResource(R.drawable.uploadpic_226x226);
                }

                return convertView;
            }

            private class ViewHolder{
                ImageView imageView;
                ImageView checkmark;
            }
        }
    }

    private void pickImage(String choiceMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else {
            boolean showCamera = false;
            int maxNum = 9;

            MultiImageSelector selector = MultiImageSelector.create(PhotoAlbumActivity2.this);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            if (choiceMode.equals("single")) {
                selector.single();
            } else {
                selector.multi();
            }
            selector.origin(mSelectPath);
            selector.start(PhotoAlbumActivity2.this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PhotoAlbumActivity2.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                String choiceMode = "multi";
                pickImage(choiceMode);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if(mLtAlbum.get(0).size()==10){
                    return;
                }
                for(String p: mSelectPath){
                    mLtAlbum.get(0).add(0,p);
                    rvAlbumAdapter.notifyDataSetChanged();
                }

            }
        }
    }
}
