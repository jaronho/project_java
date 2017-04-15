/*************************************************
 * 权限
 */
    <!-- 添加读写权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <!-- 访问互联网权限 -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
/*************************************************
 * 自定义全局配置在Application中配置如下：
 */
	OkHttpUtil.init(this)
					.setConnectTimeout(30)//连接超时时间
					.setWriteTimeout(30)//写超时时间
					.setReadTimeout(30)//读超时时间
					.setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
					.setCacheLevel(CacheLevel.FIRST_LEVEL)//缓存等级
					.setCacheType(CacheType.FORCE_NETWORK)//缓存类型
					.setShowHttpLog(true)//显示请求日志
					.setShowLifecycleLog(false)//显示Activity销毁日志
					.setRetryOnConnectionFailure(false)//失败后不自动重连
					.setDownloadFileDir(downloadFileDir)//文件下载保存目录
					.addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
					.addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
					.setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
					.build();
/*************************************************
 * 获取网络请求客户端单例示例
 */
	//获取单例客户端（默认）
	// 方法一
	OkHttpUtil.getDefault(this)//绑定生命周期
						 .doGetSync(HttpInfo.Builder().setUrl(url).build());
	// 方法二
	OkHttpUtil.getDefault()//不绑定生命周期
						 .doGetSync(HttpInfo.Builder().setUrl(url).build());
/*************************************************
 * 取消指定请求 建议在视图中采用OkHttpUtil.getDefault(this)的方式进行请求绑定
 * 该方式会在Activity/Fragment销毁时自动取消当前视图下的所有请求；
 * 请求标识类型支持Object、String、Integer、Float、Double； 请求标识尽量保证唯一。
 */
	//*******请求时先绑定请求标识，根据该标识进行取消*******/
	//方法一：
	OkHttpUtil.Builder()
					.setReadTimeout(120)
					.build("请求标识")//绑定请求标识
					.doDownloadFileAsync(info);
	//方法二：
	OkHttpUtil.getDefault("请求标识")//绑定请求标识
				.doGetSync(info);
	//*******取消指定请求*******/ 
	OkHttpUtil.getDefault().cancelRequest("请求标识");
/*************************************************
 * 在Activity中同步调用示例
 */
    /**
     * 同步请求：由于不能在UI线程中进行网络请求操作，所以采用子线程方式
     */
    private void doHttpSync() {
        new Thread(()-> {
                HttpInfo info = HttpInfo.Builder()
                .setUrl(url)
                .addHead("head","test")//协议头参数设置
                .build();
                OkHttpUtil.getDefault(MainActivity.this).doGetSync(info);
                if (info.isSuccessful()) {
                    final String result = info.getRetDetail();
                    runOnUiThread(() -> {
                            resultTV.setText("同步请求：" + result);
                        }
                    );
                }
            }
        ).start();
    }
/*************************************************
 * 在Activity中异步调用示例
 */
	/**
     * 异步请求：回调方法可以直接操作UI
     */
    private void doHttpAsync() {
        OkHttpUtil.getDefault(MainActivity.this)
                .doGetAsync(
                HttpInfo.Builder().setUrl(url).build(),
                info -> {
                    if (info.isSuccessful()) {
                        String result = info.getRetDetail();
                        resultTV.setText("异步请求："+result);
                    }
                });
    }
/*************************************************
 * 在Activity上传图片示例
 */
	/**
     * 异步上传图片：显示上传进度
     */
    private void doUploadImg() {
        HttpInfo info = HttpInfo.Builder()
                        .setUrl(url)
                        .addUploadFile("file", filePathOne, new ProgressCallback() {
                            //onProgressMain为UI线程回调，可以直接操作UI
                            @Override
                            public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                                uploadProgressOne.setProgress(percent);
                                LogUtil.d(TAG, "上传进度：" + percent);
                            }
                        })
                        .build();
        OkHttpUtil.getDefault(this).doUploadFileAsync(info);
    }
/*************************************************
 * 在Activity断点下载文件示例
 */
	@OnClick({R.id.downloadBtn, R.id.pauseBtn, R.id.continueBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadBtn://下载
                download();
                break;
            case R.id.pauseBtn://暂停下载
                if(null != fileInfo)
                    fileInfo.setDownloadStatus(DownloadStatus.PAUSE);
                break;
            case R.id.continueBtn://继续下载
                download();
                break;
        }
    }
    private void download(){
        if(null == fileInfo)
            fileInfo = new DownloadFileInfo(url,"fileName",new ProgressCallback(){
                @Override
                public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                    downloadProgress.setProgress(percent);
                    tvResult.setText(percent+"%");
                    LogUtil.d(TAG, "下载进度：" + percent);
                }
                @Override
                public void onResponseMain(String filePath, HttpInfo info) {
                    if(info.isSuccessful()){
                        tvResult.setText(info.getRetDetail()+"\n下载状态："+fileInfo.getDownloadStatus());
                    }else{
                        Toast.makeText(DownloadBreakpointsActivity.this,info.getRetDetail(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        HttpInfo info = HttpInfo.Builder().addDownloadFile(fileInfo).build();
        OkHttpUtil.Builder().setReadTimeout(120).build(this).doDownloadFileAsync(info);
    }
/*************************************************
 * Cookie持久化示例 没有在Application中进行全局Cookie持久化配置时可以采用以下方式：
 */
	OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
            .setCacheLevel(FIRST_LEVEL)
            .setConnectTimeout(25).build(this);
	//一个okHttpUtil即为一个网络连接
	okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(url).build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        if (info.isSuccessful()) {
                            String result = info.getRetDetail();
                            resultTV.setText("异步请求："+result);
                        }
                    }
                });
/*************************************************/
