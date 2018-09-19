package json.xxl.com.bannerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import json.xxl.com.lbannerview.BannerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bannberPictures = intArrayOf(R.mipmap.ic_banner_001, R.mipmap.ic_banner_002,
                R.mipmap.ic_banner_003, R.mipmap.ic_banner_004, R.mipmap.ic_banner_005)

        val bannerDes = arrayOf("2018","dream","music","spring","mysql")

        banner_view.post {  // //设置了宽高比 所以需要post 否则拿不到宽度 显示不出图片 或则显示的图片高度不对
            kotlin.run {
                banner_view.setAdapter(object : BannerAdapter(){
                    override fun getView(position: Int, convertView: View?): View {
                        val imageView  = ImageView(this@MainActivity)
                        Glide.with(this@MainActivity)
                                .load(bannberPictures.get(position))
                                .into(imageView)
                        return imageView
                    }

                    override fun getCount(): Int {
                        return bannberPictures.size
                    }

                    override fun getBannerDes(position: Int): String {
                        return bannerDes.get(position)
                    }

                })
            }

        }

        banner_view_below.post{
            kotlin.run {
                banner_view_below.setAdapter(object : BannerAdapter() {
                    override fun getView(position: Int, convertView: View?): View {
                        val imageView = ImageView(this@MainActivity)
                        Glide.with(this@MainActivity)
                                .load(bannberPictures.get(position))
                                .into(imageView)
                        return imageView
                    }
                    override fun getCount(): Int {
                        return bannberPictures.size
                    }

                })
            }
        }

        jump_to_banner.setOnClickListener {
            val intent = Intent(this@MainActivity,BannerActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
