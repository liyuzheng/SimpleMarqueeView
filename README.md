# SimpleMarqueeView [ ![Download](https://api.bintray.com/packages/liyz/lyzlib/SimpleMarqueeView/images/download.svg?version=1.0.6) ](https://bintray.com/liyz/lyzlib/SimpleMarqueeView/1.0.6/link)


### 效果图

<img src="/resources/simple.gif" style="width: 30%;">

#### Gradle:

    implementation 'li.y.z:simplemarqueeviewlib:1.0.6'

#### 属性

| Attribute 属性          | Description 描述 | 
|:---				     |:---| 
| delay         | 一行文字动画执行时间 | 
| margin_txt         | 两行文字翻页时间间隔 | 
| textSize         | 文字大小 | 
| textColor         | 文字颜色 | 
| shadow_width         | 文字位置:left、center、right | 
| speed         | 单行设置 |
| textStyle        | 动画滚动方向:bottom_to_top、top_to_bottom、right_to_left、left_to_right |

#### XML

    <li.yz.simplemarqueeviewlib.SimpleMarqueeView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/text1"
            android:layout_marginTop="100dp"
            android:background="#000000"
            app:text="Hello World! This is a Simple Marquee View. You can use it instead of textviewmarquee"
            app:textColor="@color/colorPrimary"/>


### 关于我

[GitHub: liyuzheng](https://github.com/liyuzheng)  

[个人邮箱: yuzheng_li@live.cn]
