package com.lihang.selfmvvm.utils;

import com.lihang.selfmvvm.retrofitwithrxjava.uploadutils.UploadFileRequestBody;

import java.io.File;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MultipartBody;

/**
 * Created by leo
 * on 2019/11/7.
 */
public class MultipartBodyUtils {
    public static MultipartBody.Part getBody(MutableLiveData liveData, Map<String, File> files) {
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(files, liveData);
        MultipartBody.Part body = MultipartBody.Part.create(uploadFileRequestBody);
        return body;
    }
}
