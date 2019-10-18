package com.lihang.selfmvvm.retrofitwithrxjava.uploadutils;

import com.lihang.selfmvvm.bean.basebean.Resource;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by leo
 * on 2019/8/20.
 */
public class UploadFileRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    MutableLiveData liveData;

    public UploadFileRequestBody(File file, MutableLiveData liveData) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        this.liveData = liveData;
    }


    //同一key 不同文件
    public UploadFileRequestBody(String key, ArrayList<File> files, MutableLiveData liveData) {
        MultipartBody.Builder mBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                String fileKeyName = key;
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                mBuilder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeyName + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        this.mRequestBody = mBuilder.build();
        this.liveData = liveData;
    }


    //多文件上传，图片进度监听(不同key,不同图片)
    public UploadFileRequestBody(Map<String, File> fileMap,MutableLiveData liveData) {
        MultipartBody.Builder mBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (fileMap != null) {
            RequestBody fileBody = null;
            for (String key : fileMap.keySet()) {
                File file = fileMap.get(key);
                String fileKeyName = key;
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                mBuilder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeyName + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        this.mRequestBody = mBuilder.build();
        this.liveData = liveData;
    }


    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        //写入
        mRequestBody.writeTo(bufferedSink);

        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            if (liveData != null) {
                liveData.postValue(Resource.progress((int) (bytesWritten * 100 / contentLength()), contentLength()));
            }
        }
    }
}

