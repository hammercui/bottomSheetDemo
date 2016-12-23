# BottomSheet的简单使用

>BottomSheet是Android在v23.2版本新推出的组件，实现类似于ios的ActionSheet组件的效果，算是终于推出的官方版本，统一百花齐放的各自实现。

以下讲解简单的使用。
## 首先 引入依赖

```
 compile 'com.android.support:appcompat-v7:25.0.0'
    compile 'com.android.support:design:25.0.0'
    compile 'com.android.support:recyclerview-v7:25.0.0'
```
我喜欢使用lmbda表达式，所以，sdk换成java8

```
android{
  ...
  defaultConfig{
    ...
    jackOptions {
            enabled true
        }
  }
  
  compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

## 使用BottomSheetBehavior
底部弹出view,可以滑动显示或者隐藏

注意
`bottomSheetBehavior.setState（）`来设置状态
一共有五种状态

```
 /**
     * The bottom sheet is dragging.拖拽中，几乎不用
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * The bottom sheet is settling.几乎不用
     */
    public static final int STATE_SETTLING = 2;

    /**
     * The bottom sheet is expanded.展开
     */
    public static final int STATE_EXPANDED = 3;

    /**
     * The bottom sheet is collapsed.关闭
     */
    public static final int STATE_COLLAPSED = 4;

    /**
     * The bottom sheet is hidden.是否隐藏
     */
    public static final int STATE_HIDDEN = 5;

```

代码示例：

```
LinearLayout llBottomLayout = (LinearLayout)this.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);  //修改状态
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("测试","newState:"+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //Log.d("测试","slideOffset:"+slideOffset);
            }
        });
       
```

R.id.bottom_sheet的xml如下
注意` app:behavior_peekHeight="0dp"`就能使BottomSheet完全隐藏

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/bottom_sheet"
    android:layout_width="match_parent"
    android:layout_height="340dp"
    android:orientation="vertical"
    android:background="@android:color/darker_gray"
    app:behavior_hideable="true"
    app:behavior_peekHeight="0dp"
    app:layout_behavior="android.support.design.widget.BottomSheetBehavior"
    >

    <TextView
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:layout_marginLeft="40dp"
        android:gravity="center_vertical"
        android:textColor="#000000"
        android:text="button 1"/>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:layout_marginLeft="40dp"
        android:gravity="center_vertical"
        android:textColor="#000000"
        android:text="button_2"/>
</LinearLayout>

```

## 使用BottomSheetDialog

这个应该是使用频率最高的情况，支持点击空白关闭，支持自定义ContentView.

```
bottomSheetDialog = new BottomSheetDialog(this);

        //初始化contentview
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.bottom_dialog,null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());//添加基础的item动画
        recyclerView.setVerticalScrollBarEnabled(true);
        List<String> data = new ArrayList<>();
        for (int i=0;i<30;i++){
            data.add("按钮"+i);
        }

        recyclerView.setAdapter(new BottomListAdapter(this,data,(view,position)->{
            Log.d("测试","点击了position："+position);
            Toast.makeText(this,"点击了position："+position,Toast.LENGTH_SHORT).show();
            bottomSheetDialog.hide();
        }));

        //contentview添加到dialog
        bottomSheetDialog.setContentView(recyclerView);
        //获得dialog的behavior
        bottomSheetDialogBehavior = BottomSheetBehavior.from((View)recyclerView.getParent());
```

**但是有个一bug**

当我们关闭dialog，再次`showDialog`之后，只会变阴影，并不会出现view,推测是这是的`BottomSheetBehavior`的`state`仍然是`STATE_COLLAPSED`状态，我们需要手动设置为`STATE_EXPANDED`状态。

首先获得`BottomSheetDialog`的`BottomSheetBehavior`

```
 //contentview添加到dialog
        bottomSheetDialog.setContentView(recyclerView);
        //获得dialog的behavior
        bottomSheetDialogBehavior = BottomSheetBehavior.from((View)recyclerView.getParent());
```

然后在`showDialog`函数里

```
 bottomSheetDialog.show();
                bottomSheetDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
```


