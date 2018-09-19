package json.xxl.com.bannerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import json.xxl.com.lbannerview.BannerAdapter;
import json.xxl.com.lbannerview.BannerView;


/**
 * Created by xxl on 2018/9/19.
 * <p>
 * Description :
 */

public class BannerActivity extends AppCompatActivity {


    private String[] bannerDes = new String[]{"2018", "dream", "music", "spring", "mysql"};
    private int[] bannberPictures = new int[]{R.mipmap.ic_banner_001, R.mipmap.ic_banner_002,
            R.mipmap.ic_banner_003, R.mipmap.ic_banner_004, R.mipmap.ic_banner_005};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        final BannerView bannerView = findViewById(R.id.banner_view);
        final BannerView belowBannerView = findViewById(R.id.banner_view_below);

        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView imageView = new ImageView(BannerActivity.this);
                Glide.with(BannerActivity.this).load(bannberPictures[position]).into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return bannberPictures.length;
            }
            @Override
            public String getBannerDes(int position) {
                return bannerDes[position];
            }
        });

        // 如果 布局文件里面设置了  app:widthProportion="8"  app:heightProportion="3"  宽高比 不是直接固定的高度
       /* bannerView.post(new Runnable() {   // post 方式
            @Override
            public void run() {
                bannerView.setAdapter(new BannerAdapter() {
                    @Override
                    public View getView(int position, View convertView) {
                        ImageView imageView = new ImageView(BannerActivity.this);
                        Glide.with(BannerActivity.this).load(bannberPictures[position]).into(imageView);
                        return imageView;
                    }

                    @Override
                    public int getCount() {
                        return bannberPictures.length;
                    }

                    @Override
                    public String getBannerDes(int position) {
                        return bannerDes[position];
                    }
                });
            }
        });
        */
        belowBannerView.post(new Runnable() {  // 如果布局里宽高比例没设置 是直接写死的高度 则不用post延时
            @Override
            public void run() {
                belowBannerView.setAdapter(new BannerAdapter() {   //指示点在banner图片下方 左右两边设置突出
                    @Override
                    public View getView(int position, View convertView) {
                        ImageView imageView = new ImageView(BannerActivity.this);
                        Glide.with(BannerActivity.this).load(bannberPictures[position]).into(imageView);
                        return imageView;
                    }

                    @Override
                    public int getCount() {
                        return bannberPictures.length;
                    }
                });
            }
        });

        findViewById(R.id.jump_to_kotlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerActivity.this,MainActivity.class));
                finish();
            }
        });

    }


}
