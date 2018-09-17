## LBannerView 使用介绍

### 轮播控件千千万，为啥要自己再写一套？？？

#### 其一 是因为最近项目需求，小圆点再轮播图控件的底部，不是覆盖在图片上

#### 其二 也是项目需求，图片左右两边突出 类似效果一页看到左右突出（一页显示三张图，两边的图漏出一些图片）

#### 其三 就是这个框架了，哈哈

        
#### 自定义属性含义

    property | means
    
    
    
### Dependency

#### Step 1. Add the JitPack repository to your build file
    
```
        allprojects {
                repositories {
                    ...
                    maven { url 'https://jitpack.io' }
                }
            }
```
#### Step 2. Add the dependency

```
    dependencies {
       	        implementation 'com.github.fazhongxu:LBannerView:v0.1.0'
	}
```



#### 布局文件使用

    <json.xxl.com.lbannerview.BannerView
        android:layout_marginTop="20dp"
        android:id="@+id/banner_view_below"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:bottomColor="@color/transparent"
        app:dotGravity="center"
        app:dotIndicatorFocus="@color/colorPrimary"
        app:dotIndicatorNormal="@color/colorGray"
        app:widthProportion="8"
        app:heightProportion="3"
        app:bulge="true"
        app:bulgeDistance="30"
        app:bottomType="belowPic"
        app:dotSize="10dp" />
        

### 效果图


<img src="https://github.com/fazhongxu/IBannerView/blob/master/images/screenshot.png" width="80%" height="30%">


<img src="https://github.com/fazhongxu/IBannerView/blob/master/images/screenshot_below.png" width="50%" height="50%" margin:0 auto>

