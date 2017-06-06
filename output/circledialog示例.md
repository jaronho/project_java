https://github.com/mylhyl/Android-CircleDialog

使用

简单的对话框
	new CircleDialog.Builder(this)
			.setTitle("标题")
			.setText("提示框")
			.setPositive("确定", null)
			.show();
			
选择对话框
	final String[] items = {"拍照", "从相册选择", "小视频"};
	new CircleDialog.Builder(this)
			.configDialog(new ConfigDialog() {
				@Override
				public void onConfig(DialogParams params) {
					//增加弹出动画
					params.animStyle = R.style.dialogWindowAnim;
				}
			})
			.setTitle("标题")
			.setTitleColor(Color.BLUE)
			.setItems(items, new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				}
			})
			.setNegative("取消", null)
			.configNegative(new ConfigButton() {
				@Override
				public void onConfig(ButtonParams params) {
					//取消按钮字体颜色
					params.textColor = Color.RED;
				}
			})
			.show();
			
#说明

此库自动将px转换百分比，由于 Dialog 布局一般只有微调，暂时只支持textSize，height，padding
默认字体大小;Title、message、button、padding 的px在设计稿为 1080 * 1920 的尺寸
也可自己定义，只需在manifest.xml中加入如下格式
        <meta-data android:name="design_width" android:value="1200"/>
        <meta-data android:name="design_height" android:value="1920"/>
#注意

继承基类BaseCircleDialog背景为透明，自定义layout时按需求设置背景


			