package com.lihang.selfmvvm.common;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by leo on 2017/9/13.
 * 键值对上传类
 */

public class PARAMS {
    public static String pageSize = "10";

    public static HashMap<String, String> gankPost(String url, String desc, String who, String type, String debug) {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("desc", desc);
        map.put("who", who);
        map.put("type", type);
        map.put("debug", debug);
        return map;
    }





    public static RequestBody changeToRquestBody(String param) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), param);
    }

    public static MultipartBody.Part changeToMutiPartBody(String key, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        return body;
    }

    //不同key 不同图片
    public static Map<String, RequestBody> manyFileToPartBody(Map<String, File> fileMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : fileMap.keySet()) {
            requestBodyMap.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), fileMap.get(key)));
        }
        return requestBodyMap;
    }

    //同一key 多张图片
    public static Map<String, RequestBody> manyFileToPartBody(String key, ArrayList<File> files) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            requestBodyMap.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i)));
        }
        return requestBodyMap;
    }


}
