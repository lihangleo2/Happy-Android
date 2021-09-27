package com.lihang.selfmvvm.morefunction.apkupdate;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


import com.lihang.selfmvvm.base.bean.EventBusBean;
import com.lihang.selfmvvm.bean.ProgressBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import androidx.core.content.FileProvider;


/**
 * 更新APP
 */
public class DownLoadService extends Service {

    private DownloadManager manager;
    private DownloadCompleteReceiver receiver;
    private String url;
    private String DOWNLOADPATH = "/demo/apk/";
    private long mYdownId;

    private void initDownManager() {
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        down.setAllowedOverRoaming(false);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        down.setMimeType(mimeString);
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        down.setVisibleInDownloadsUi(true);
//        down.setDestinationInExternalPublicDir(DOWNLOADPATH, "demo.apk");
        //适配android10的
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "demo.apk");
        down.setDestinationUri(Uri.fromFile(file));

        down.setTitle("demo");
        mYdownId = manager.enqueue(down);
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        mHandler = new Handler();
        mHandler.post(progressRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        url = intent.getStringExtra("downloadurl");
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "demo.apk");
        if (file.exists()) {
            deleteFileWithPath(file.getPath());
        } else {
            file.mkdir();
        }

        try {
            initDownManager();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent0 = new Intent(Intent.ACTION_VIEW, uri);
                intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent0);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.removeCallbacks(progressRunnable);
            EventBus.getDefault().post(new EventBusBean(99));
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (manager.getUriForDownloadedFile(downId) != null) {
                    installAPK(context, getRealFilePath(context, manager.getUriForDownloadedFile(downId)));
                } else {
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                }
                DownLoadService.this.stopSelf();
            }
        }

        private void installAPK(Context context, String path) {
            File file = new File(path);
            if (file.exists()) {
                openFile(file, context);
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void openFile(File var0, Context var1) {
        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".provider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        } else {
            var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
        }
        try {
            var1.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(var1, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }
    }

    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    public static boolean deleteFileWithPath(String filePath) {
        SecurityManager checker = new SecurityManager();
        File f = new File(filePath);
        checker.checkDelete(filePath);
        if (f.isFile()) {
            f.delete();
            return true;
        }
        return false;
    }


    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     *
     * @param downloadId
     * @return
     */
    private int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = manager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }


    private Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            int[] bytesAndStatus = getBytesAndStatus(mYdownId);
            Log.e("我是不是走这里了呢", bytesAndStatus[0] + "=========" + bytesAndStatus[1] + "============" + bytesAndStatus[2]);
            ProgressBean progressBean = new ProgressBean();
            progressBean.setProgress(bytesAndStatus[0]);
            progressBean.setMax(bytesAndStatus[1]);
            EventBus.getDefault().post(progressBean);
            mHandler.postDelayed(this, 500);
        }
    };

    private Handler mHandler;

}
