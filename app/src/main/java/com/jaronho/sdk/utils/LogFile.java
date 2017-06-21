package com.jaronho.sdk.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author:  jaron.ho
 * Date:    2017-06-21
 * Brief:   记录日志到文件
 */

public class LogFile {
	public static int FILE_MAX_SIZE = 1024 * 1024;  // 1M
	private static boolean mIsThreadRunning = false;
	private static Thread mThread = new Thread(new Runnable() {
		@Override
		public void run() {
			while (!mThread.isInterrupted() && !mLogMap.isEmpty()) {
				Set<Map.Entry<String, ConcurrentLinkedQueue<String>>> entrySet = mLogMap.entrySet();
				for (Map.Entry<String, ConcurrentLinkedQueue<String>> entry : entrySet) {
					String fileFullPath = entry.getKey();
					ConcurrentLinkedQueue<String> queue = entry.getValue();
					while (null != queue && !queue.isEmpty()) {
						String logString = queue.poll();
						writeToFile(fileFullPath, logString);
					}
				}
			}
		}
	});
	private static ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> mLogMap = new ConcurrentHashMap<>();
	private String mFileFullPath = "";

	private static void writeToFile(String fileFullPath, String logString) {
		File file = new File(fileFullPath);
		if (!file.exists()) {
			if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
				try {
					if (!file.createNewFile()) {
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return;
			}
		}
		try {
			long fileSize = file.length();
			boolean isAppend = true;
			if (fileSize >= FILE_MAX_SIZE) {
				fileSize = 0;
				isAppend = false;
			}
			FileWriter filerWriter = new FileWriter(file, isAppend);
			BufferedWriter bufWriter = new BufferedWriter(filerWriter);
			if (fileSize > 0) {
				bufWriter.newLine();
			}
			bufWriter.write(logString);
			bufWriter.close();
			filerWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (!mFileFullPath.isEmpty() && mLogMap.containsKey(mFileFullPath)) {
			mLogMap.remove(mFileFullPath);
		}
	}

	/**
	 * 功  能: 构造函数
	 * 参  数: context - 上下文活动
	 * 		   filePath - 日志文件路径,默认为:外部存储路径+包名,例:"/sdcard/com.demo.app/"
	 * 		   filePrefix - 日志文件名前缀,默认为"file"
	 * 返回值: 无
	 */
	public LogFile(Context context, String filePath, String filePrefix) {
		if (null == filePath || filePath.isEmpty()) {
			filePath = Environment.getExternalStorageDirectory().getPath() + "/" + context.getPackageName();
		}
		if (!filePath.endsWith("/")) {
			filePath += "/";
		}
		filePrefix = (null == filePrefix || filePrefix.isEmpty()) ? "file" : filePrefix;
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = new File(filePath);
				if (dir.exists() || dir.mkdirs()) {
					String date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date());
					String filename = filePrefix + "-" + date + "-" + System.currentTimeMillis() + ".log";
					mFileFullPath = filePath + filename;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功  能: 构造函数
	 * 参  数: 无
	 * 返回值: 无
	 */
	public LogFile(Context context) {
		this(context, "", "");
	}

	/**
	 * 功  能: 获取文件全路径
	 * 参  数: 无
	 * 返回值: String
	 */
	public String getFileFullPath() {
		return mFileFullPath;
	}

	/**
	 * 功  能: 记录
	 * 参  数: dateString - 日期字符串
	 * 		   text - 日志信息
	 * 返回值: 无
	 */
	public void record(String dateString, String text) {
		if (!mIsThreadRunning) {
			mIsThreadRunning = true;
			mThread.start();
		}
		if (!mFileFullPath.isEmpty()) {
			ConcurrentLinkedQueue<String> queue;
			if (mLogMap.containsKey(mFileFullPath)) {
				queue = mLogMap.get(mFileFullPath);
			} else {
				queue = new ConcurrentLinkedQueue<>();
				mLogMap.put(mFileFullPath, queue);
			}
			queue.add("[" + dateString + "] " + text);
		}
	}

	/**
	 * 功  能: 记录
	 * 参  数: text - 日志信息
	 * 返回值: 无
	 */
	public void record(String text) {
		record(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()), text);
	}

	/**
	 * 功  能: 删除
	 * 参  数: 无
	 * 返回值: 无
	 */
	public void delete() {
		if (!mFileFullPath.isEmpty()) {
			if (mLogMap.containsKey(mFileFullPath)) {
				mLogMap.remove(mFileFullPath);
			}
			File file = new File(mFileFullPath);
			if (file.exists()){
				file.delete();
			}
		}
	}
}
