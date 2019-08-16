# SimpleMarqueeView [ ![Download](https://api.bintray.com/packages/liyz/lyzlib/SimpleMarqueeView/images/download.svg?version=1.1.2) ](https://bintray.com/liyz/lyzlib/SimpleMarqueeView/1.1.2/link)

###
目前实现的跑马灯效果都是添加view,并且用view动画实现，会占用大量系统资源。

本跑马灯实现简单，与系统textview实现效果相同。采用value动画实现，性能好，满足基本文本滚动效果。

### 效果图(图片录制失真，实际效果流畅）

<img src="/resources/simple.gif" style="width: 30%;">

#### Gradle:

    implementation 'li.y.z:simplemarqueeviewlib:1.1.2'

#### 属性

| Attribute 属性          | Description 描述 | 
|:---				     |:---| 
| delay         | 动画执行间隔 | 
| margin_txt         | 两行文字间隔 | 
| textSize         | 文字大小 | 
| textColor         | 文字颜色 | 
| shadow_width         | 两端阴影宽度，背景不是颜色时失效 | 
| speed         | 滚动速度，12为textview跑马灯速度 |
| textStyle        | 文字样式 |
| gravity        | 非跑马灯样式时，文字位置 【暂时只支持start|center_horizontal】 |

#### XML

    <li.yz.simplemarqueeviewlib.SimpleMarqueeView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/text1"
            android:layout_marginTop="100dp"
            android:background="#000000"
            app:text="Hello World! This is a Simple Marquee View. You can use it instead of textviewmarquee"
            app:textColor="@color/colorPrimary"/>

### 已知问题
暂不支持Spannable
由于不想引入viewmodel，目前页面不显示状态动画依然执行，如果需要暂停、恢复，请调用pause()、resume()方法

### 关于我

[GitHub: liyuzheng](https://github.com/liyuzheng)  

[个人邮箱: yuzheng_li@live.cn]
