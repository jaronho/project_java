/*************************************************
 * Ȩ��
 */
    <!-- ��Ӷ�дȨ�� -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <!-- ���ʻ�����Ȩ�� -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
/*************************************************
 * �Զ���ȫ��������Application���������£�
 */
	OkHttpUtil.init(this)
					.setConnectTimeout(30)//���ӳ�ʱʱ��
					.setWriteTimeout(30)//д��ʱʱ��
					.setReadTimeout(30)//����ʱʱ��
					.setMaxCacheSize(10 * 1024 * 1024)//����ռ��С
					.setCacheLevel(CacheLevel.FIRST_LEVEL)//����ȼ�
					.setCacheType(CacheType.FORCE_NETWORK)//��������
					.setShowHttpLog(true)//��ʾ������־
					.setShowLifecycleLog(false)//��ʾActivity������־
					.setRetryOnConnectionFailure(false)//ʧ�ܺ��Զ�����
					.setDownloadFileDir(downloadFileDir)//�ļ����ر���Ŀ¼
					.addResultInterceptor(HttpInterceptor.ResultInterceptor)//������������
					.addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//������·�쳣������
					.setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//�־û�cookie
					.build();
/*************************************************
 * ��ȡ��������ͻ��˵���ʾ��
 */
	//��ȡ�����ͻ��ˣ�Ĭ�ϣ�
	// ����һ
	OkHttpUtil.getDefault(this)//����������
						 .doGetSync(HttpInfo.Builder().setUrl(url).build());
	// ������
	OkHttpUtil.getDefault()//������������
						 .doGetSync(HttpInfo.Builder().setUrl(url).build());
/*************************************************
 * ȡ��ָ������ ��������ͼ�в���OkHttpUtil.getDefault(this)�ķ�ʽ���������
 * �÷�ʽ����Activity/Fragment����ʱ�Զ�ȡ����ǰ��ͼ�µ���������
 * �����ʶ����֧��Object��String��Integer��Float��Double�� �����ʶ������֤Ψһ��
 */
	//*******����ʱ�Ȱ������ʶ�����ݸñ�ʶ����ȡ��*******/
	//����һ��
	OkHttpUtil.Builder()
					.setReadTimeout(120)
					.build("�����ʶ")//�������ʶ
					.doDownloadFileAsync(info);
	//��������
	OkHttpUtil.getDefault("�����ʶ")//�������ʶ
				.doGetSync(info);
	//*******ȡ��ָ������*******/ 
	OkHttpUtil.getDefault().cancelRequest("�����ʶ");
/*************************************************
 * ��Activity��ͬ������ʾ��
 */
    /**
     * ͬ���������ڲ�����UI�߳��н�������������������Բ������̷߳�ʽ
     */
    private void doHttpSync() {
        new Thread(()-> {
                HttpInfo info = HttpInfo.Builder()
                .setUrl(url)
                .addHead("head","test")//Э��ͷ��������
                .build();
                OkHttpUtil.getDefault(MainActivity.this).doGetSync(info);
                if (info.isSuccessful()) {
                    final String result = info.getRetDetail();
                    runOnUiThread(() -> {
                            resultTV.setText("ͬ������" + result);
                        }
                    );
                }
            }
        ).start();
    }
/*************************************************
 * ��Activity���첽����ʾ��
 */
	/**
     * �첽���󣺻ص���������ֱ�Ӳ���UI
     */
    private void doHttpAsync() {
        OkHttpUtil.getDefault(MainActivity.this)
                .doGetAsync(
                HttpInfo.Builder().setUrl(url).build(),
                info -> {
                    if (info.isSuccessful()) {
                        String result = info.getRetDetail();
                        resultTV.setText("�첽����"+result);
                    }
                });
    }
/*************************************************
 * ��Activity�ϴ�ͼƬʾ��
 */
	/**
     * �첽�ϴ�ͼƬ����ʾ�ϴ�����
     */
    private void doUploadImg() {
        HttpInfo info = HttpInfo.Builder()
                        .setUrl(url)
                        .addUploadFile("file", filePathOne, new ProgressCallback() {
                            //onProgressMainΪUI�̻߳ص�������ֱ�Ӳ���UI
                            @Override
                            public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                                uploadProgressOne.setProgress(percent);
                                LogUtil.d(TAG, "�ϴ����ȣ�" + percent);
                            }
                        })
                        .build();
        OkHttpUtil.getDefault(this).doUploadFileAsync(info);
    }
/*************************************************
 * ��Activity�ϵ������ļ�ʾ��
 */
	@OnClick({R.id.downloadBtn, R.id.pauseBtn, R.id.continueBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadBtn://����
                download();
                break;
            case R.id.pauseBtn://��ͣ����
                if(null != fileInfo)
                    fileInfo.setDownloadStatus(DownloadStatus.PAUSE);
                break;
            case R.id.continueBtn://��������
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
                    LogUtil.d(TAG, "���ؽ��ȣ�" + percent);
                }
                @Override
                public void onResponseMain(String filePath, HttpInfo info) {
                    if(info.isSuccessful()){
                        tvResult.setText(info.getRetDetail()+"\n����״̬��"+fileInfo.getDownloadStatus());
                    }else{
                        Toast.makeText(DownloadBreakpointsActivity.this,info.getRetDetail(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        HttpInfo info = HttpInfo.Builder().addDownloadFile(fileInfo).build();
        OkHttpUtil.Builder().setReadTimeout(120).build(this).doDownloadFileAsync(info);
    }
/*************************************************
 * Cookie�־û�ʾ�� û����Application�н���ȫ��Cookie�־û�����ʱ���Բ������·�ʽ��
 */
	OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
            .setCacheLevel(FIRST_LEVEL)
            .setConnectTimeout(25).build(this);
	//һ��okHttpUtil��Ϊһ����������
	okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(url).build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        if (info.isSuccessful()) {
                            String result = info.getRetDetail();
                            resultTV.setText("�첽����"+result);
                        }
                    }
                });
/*************************************************/
