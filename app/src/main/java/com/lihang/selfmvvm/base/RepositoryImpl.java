package com.lihang.selfmvvm.base;

import com.leo.utilspro.utils.GsonUtil;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.User;
import com.lihang.selfmvvm.bean.HomeFatherBean;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.base.bean.Resource;
import com.lihang.selfmvvm.common.PARAMS;
import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.base.retrofitwithrxjava.uploadutils.UploadFileRequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by leo
 * on 2019/10/15.
 * 这是所有的网络请求所在的位置
 */
public class RepositoryImpl extends BaseModel {

    /*** For Exmaple - - - - - - - - - - - - - -- - - -- -- - - -- - - ---------------------------*/

    //获取 banner列表
    public MutableLiveData<Resource<List<BannerBean>>> getBannerList() {
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getBanner(), liveData);
    }

    //获取首页文章
    public MutableLiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<HomeFatherBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getHomeArticles(curPage), liveData, paramsBuilder);
    }



    /**
     * 一、GET请求所有实例都在
     * */
    public MutableLiveData<Resource<List<BannerBean>>> getTaskList(int curPage, int flag, HashMap<String, Object> map,ParamsBuilder paramsBuilder) {
        //一般authorization是登录者token，在登录的时候会存起来，只要获取就好了
        String authorization = MyApplication.getLoginUser().getToken();
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getTaskList(curPage,flag,map,authorization), liveData, paramsBuilder);
    }

    /**
     * 二、POST请求
     * 2.1、json请求
     * */
    public MutableLiveData<Resource<List<BannerBean>>> addPatient(String json, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().addPatient(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)), liveData, paramsBuilder);
    }


    /**
     * 二、POST请求
     * 2.2、键值对请求
     * */
    public MutableLiveData<Resource<List<BannerBean>>> addOther(String title,HashMap<String, Object> map, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().addOther(title,map), liveData, paramsBuilder);
    }

    /**
     * 三、单个图片上传(文件上传)
     * */
    public MutableLiveData<Resource<String>> upLoadFile(String key, File file, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        //这里是监听上传进度的辅助类UploadFileRequestBody
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, liveData);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), uploadFileRequestBody);
        return observeGo(getApiService().upLoadFile(body), liveData, paramsBuilder);
    }

    /**
     * 四、多个图片上传(多个文件上传)
     * */
    //上传多张图片(进度监听)  多张图片进度监听，图片一张一张上传 所以用到了PictureProgressUtil工具类。用之前init初始数据，setProgress即可
    public MutableLiveData<Resource<List<String>>> upLoadMoreFiles(String type, HashMap<String, File> files,ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<String>>> liveData = new MutableLiveData<>();

        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, liveData);
            bodyMap.put("files" + "\"; filename=\"" + file.getName(), uploadFileRequestBody);
        }
        //如果是传统的不带进度监听 只需要
//        bodyMap=PARAMS.manyFileToPartBody(files);
        return upLoadFile(getApiService().upLoadMoreFiles(PARAMS.changeToRquestBody(type), bodyMap), liveData,paramsBuilder);
    }









    /**
     * 七、文件下载
     * 7.1、正常下载
     * */
    public MutableLiveData<Resource<File>> downFile(String destDir, String fileName,String targetUrl) {
        MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
        return downLoadFile(getApiService().downloadFile(targetUrl), liveData, destDir, fileName);
    }

    /**
     * 七、文件下载
     * 7.2、断点下载
     * */
    public MutableLiveData<Resource<File>> downFile(String destDir, String fileName, long currentLength,String targetUrl) {
        String range = "bytes=" + currentLength + "-";
        MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
        return downLoadFile(getApiService().downloadFile(targetUrl, range), liveData, destDir, fileName, currentLength);
    }






}
