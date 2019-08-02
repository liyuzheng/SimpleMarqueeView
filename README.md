Textview android:ellipsize="marquee" often dose not work,so I build this lib.

first:

implementation 'li.y.z:simplemarqueeviewlib:1.0.6'

second:

 <li.yz.simplemarqueeviewlib.SimpleMarqueeView
 
            android:layout_width="match_parent"
            
            android:layout_height="wrap_content"
            
            android:id="@+id/text1"
            
            android:layout_marginTop="100dp"
            
            android:background="#000000"
            
            app:text="Hello World! This is a Simple Marquee View. You can use it instead of textviewmarquee"
            
            app:textColor="@color/colorPrimary"/>

attrs:

app:delay="1500" //animation delay

app:margin_txt="133dp" //between two texts margin

app:textSize="12sp" 

app:textColor="@color/colorPrimary"

app:shadow_width="12dp" //shadow,if background is not color , that dose not work

app:speed="12"   //the system marquee textview is 12L

app:textStyle="bold"


It's not support Spannable now.
