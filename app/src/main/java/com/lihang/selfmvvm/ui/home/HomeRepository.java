package com.lihang.selfmvvm.ui.home;

import com.lihang.selfmvvm.base.BaseModel;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.bean.basebean.Resource;
import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.retrofitwithrxjava.uploadutils.UploadFileRequestBody;
import com.lihang.selfmvvm.utils.MultipartBodyUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by leo
 * on 2019/10/15.
 */
public class HomeRepository extends BaseModel {


    public MutableLiveData<Resource<List<BannerBean>>> getBannerList() {
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getBanner(), liveData);
    }

    public MutableLiveData<Resource<File>> downFile(String destDir, String fileName) {
        MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
        return downLoadFile(getApiService().downloadFile(SystemConst.QQ_APK), liveData, destDir, fileName);
    }

    //断点下载
    public MutableLiveData<Resource<File>> downFile(String destDir, String fileName, long currentLength) {
        String range = "bytes=" + currentLength + "-";
        MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
        return downLoadFile(getApiService().downloadFile(SystemConst.QQ_APK, range), liveData, destDir, fileName, currentLength);
    }

    //上传文件
    public MutableLiveData<Resource<String>> upLoad(HashMap<String, String> map, Map<String, File> files) {
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
        return upLoadFile(getApiService().uploadPic(map, MultipartBodyUtils.getBody(liveData, files)), liveData);
    }


}
