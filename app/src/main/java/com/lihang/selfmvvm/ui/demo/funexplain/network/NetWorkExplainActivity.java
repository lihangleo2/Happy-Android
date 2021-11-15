package com.lihang.selfmvvm.ui.demo.funexplain.network;

import android.view.View;
import android.widget.RelativeLayout;

import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.PreferenceUtil;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.customview.tost.MyToast;
import com.lihang.selfmvvm.databinding.NetworkActivityBinding;

import java.io.File;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by leo
 * on 2021/9/26.
 */

/**
 * 【一、GET请求】
 * */
//    步骤1：
//    RetrofitApiService里
//    @GET("article/list/{curPage}/json")
//    Observable<ResponModel<List<DtBoxBean>>> getTaskList(@Path("curPage") int curPage,@Query("flag") int flag,@QueryMap HashMap<String, Object> map, @Header("Authorization") String authorization);
//    1.1、 @Path("curPage") int curPage 拼接在@GET("url")上的
//    1.2、 @Query("flag") int flag 单个参数上传，在参数不多的情况下可以@Query
//    1.3、 @QueryMap HashMap<String, Object> map 多个参数上传，可以用@QueryMap
//    1.4、 @Header("Authorization")String authorization 这个是头部信息，当要传入登录者token的传入

//    步骤2：
//    RepositoryImpl里
//      public MutableLiveData<Resource<List<BannerBean>>> getTaskList(int curPage, int flag, HashMap<String, Object> map, ParamsBuilder paramsBuilder) {
//              //一般authorization是登录者token，在登录的时候会存起来，只要获取就好了
//              String authorization = MyApplication.getLoginUser().getToken();
//              MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
//              return observeGo(getApiService().getTaskList(curPage,flag,map,authorization), liveData, paramsBuilder);
//        }

//    步骤3：
//    ViewModel(自己实现的ViewModel里)
//      public LiveData<Resource<List<BannerBean>>> getTaskList(int curPage, int flag, HashMap<String, Object> map,ParamsBuilder paramsBuilder) {
//              return getRepository().getTaskList(curPage,flag,map,paramsBuilder);
//       }


/**
 * 【二、POST请求】
 * 2.1、json请求
 * */
//      步骤1：
//      @Headers({"Content-type:application/json;charset=UTF-8"})
//      @POST("article/list/json")
//      Observable<ResponModel<List<BannerBean>>> addPatient(@Body RequestBody route);

//      步骤2：
//      public MutableLiveData<Resource<List<BannerBean>>> addPatient(String json, ParamsBuilder paramsBuilder) {
//              MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
//              return observeGo(getApiService().addPatient(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)), liveData, paramsBuilder);
//        }


/**
 * 【二、POST请求】
 * 2.2、键值对请求
 * */
//      步骤1：
//      @POST("user/login")
//      @FormUrlEncoded
//      Observable<ResponModel<List<BannerBean>>> addOther(@Field("title") String title,@FieldMap HashMap<String, Object> map);
//      2.2.1、@Field("title") String title 单个键值对上传
//      2.2.2、@FieldMap HashMap<String, Object> map  map的形式键值对上传

//      步骤2：
//      public MutableLiveData<Resource<List<BannerBean>>> addOther(String title,HashMap<String, Object> map, ParamsBuilder paramsBuilder) {
//              MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
//              return observeGo(getApiService().addOther(title,map), liveData, paramsBuilder);
//        }


/**
 * 图片上传的实例在EditorMessageActivity里都存在
 * 【三、单个图片上传（单个文件上传）】
 * */
//      步骤1：
//      @POST("sys/file/uploadFile")
//      @Multipart
//      Observable<ResponModel<String>> upLoadFile(@Part MultipartBody.Part file);

//      步骤2：
//      public MutableLiveData<Resource<String>> upLoadFile(String key, File file, ParamsBuilder paramsBuilder) {
//              MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
//              //这里是监听上传进度的辅助类UploadFileRequestBody
//              UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, liveData);
//              MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), uploadFileRequestBody);
//              return observeGo(getApiService().upLoadFile(body), liveData, paramsBuilder);
//        }


/**
 * 【四、多个图片上传（多个文件上传）】
 * */
//      步骤1：
//      @POST("sys/file/uploadFiles")
//      @Multipart
//      Observable<ResponModel<List<String>>> upLoadMoreFiles(@Part("type") RequestBody type, @PartMap Map<String, RequestBody> maps);

//      步骤2：
//      上传多张图片(进度监听)  多张图片进度监听，图片一张一张上传 所以用到了PictureProgressUtil工具类。用之前init初始数据，setProgress即可
//      public MutableLiveData<Resource<List<String>>> upLoadMoreFiles(String type, HashMap<String, File> files,ParamsBuilder paramsBuilder) {
//              MutableLiveData<Resource<List<String>>> liveData = new MutableLiveData<>();
//
//              Map<String, RequestBody> bodyMap = new HashMap<>();
//              for (int i = 0; i < files.size(); i++) {
//              File file = files.get(i);
//              UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, liveData);
//              bodyMap.put("files" + "\"; filename=\"" + file.getName(), uploadFileRequestBody);
//              }
//              //如果是传统的不带进度监听 只需要
////                bodyMap=PARAMS.manyFileToPartBody(files);
//              return upLoadFile(getApiService().upLoadMoreFiles(PARAMS.changeToRquestBody(type), bodyMap), liveData,paramsBuilder);
//        }


/**
 * 【五、PUT请求、其实和POST请求差不多，只不过把注解改成了PUT】
 * */
//      步骤1：
//      @Headers({"Content-type:application/json;charset=UTF-8"})
//      @PUT("article/list/json")
//      Observable<ResponModel<List<BannerBean>>> updateMedicine(@Body RequestBody route);


/**
 * 【六、DELETE请求】
 * 6.1、拼接url
 * */
//      步骤1：
//      @DELETE("patient/address/{addressId}")
//      Observable<ResponModel<String>> deleteAddress(@Path("addressId") int addressId);

/**
 * 【六、DELETE请求】
 * 6.2、DELETE的json请求
 * */
//      步骤1：
//      @Headers({"Content-type:application/json;charset=UTF-8"})
//      @HTTP(method = "DELETE", path = "patient/address/delete", hasBody = true)
//      Observable<ResponModel<Object>> deleteReport(@Body RequestBody route);


/**
 * 七、文件下载
 * 7.1、正常下载
 * */
//      步骤1：
//      @GET
//      @Streaming
//      //10以上用@streaming。不会造成oom，反正你用就是了
//      Observable<ResponseBody> downloadFile(@Url String url);

//      步骤2：
//      destDir：文件所在的路径
//      fileName：文件名
//      targetUrl：要下载的目标资源文件
//      public MutableLiveData<Resource<File>> downFile(String destDir, String fileName,String targetUrl) {
//              MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
//              return downLoadFile(getApiService().downloadFile(targetUrl), liveData, destDir, fileName);
//        }

/**
 * 七、文件下载
 * 7.2、断点下载 （断点下载的唯一限制是，要保证下载完成前后，是同一文件）
 * */
//      步骤1：
//      @GET
//      @Streaming
//      Observable<ResponseBody> downloadFile(@Url String url, @Header("RANGE") String range);

//      步骤2：
//      currentLength：之前已下载过的长度
//      public MutableLiveData<Resource<File>> downFile(String destDir, String fileName, long currentLength,String targetUrl) {
//              String range = "bytes=" + currentLength + "-";
//              MutableLiveData<Resource<File>> liveData = new MutableLiveData<>();
//              return downLoadFile(getApiService().downloadFile(targetUrl, range), liveData, destDir, fileName, currentLength);
//        }

public class NetWorkExplainActivity extends BaseActivity<NetViewModel, NetworkActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.network_activity;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shadowLayout_download_file:
                MyToast.show("文件下载都在这里哦~");
//                downLoadFile();
                break;
        }
    }

    private void downLoadFile() {
        String destDir = getFilesDir().getAbsolutePath().toString();
        mViewModel.downFile("url", destDir, "hiphopsVidel" + System.currentTimeMillis() + ".mp4").observe(this, resource -> {
            resource.handler(new OnCallback<File>() {
                @Override
                public void onSuccess(File data) {

                }

                @Override
                public void onFailure(int errorCode,String msg) {

                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onProgress(int precent, long total) {
                    super.onProgress(precent, total);


                }
            });
        });
    }
}
