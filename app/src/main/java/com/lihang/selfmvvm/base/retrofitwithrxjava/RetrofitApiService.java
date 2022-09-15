package com.lihang.selfmvvm.base.retrofitwithrxjava;


import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.User;
import com.lihang.selfmvvm.bean.HomeFatherBean;
import com.lihang.selfmvvm.base.bean.ResponModel;
import com.lihang.selfmvvm.common.SystemConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by leo
 * on 2019/8/14.
 * Retrofit 接口请求配置都在这
 */
public interface RetrofitApiService {

    /*
    针对那些不安规律走的后台服务器，直接将ResponModel返回出去
     */
    @GET("banner/json")
    Observable<ResponModel> getAllResult();




    /** For Example ----------------------------------------------------------------------------*/
    //wanAndroid的，轮播banner的接口
    @GET("banner/json")
    Observable<ResponModel<List<BannerBean>>> getBanner();

    //首页文章,curPage拼接。从0开始
    @GET("article/list/{curPage}/json")
    Observable<ResponModel<HomeFatherBean>> getHomeArticles(@Path("curPage") int curPage);


    /**
     * 一、GET请求的所有事例
     */
    @GET("article/list/{curPage}/json")
    Observable<ResponModel<List<BannerBean>>> getTaskList(@Path("curPage") int curPage, @Query("flag") int flag, @QueryMap HashMap<String, Object> map, @Header("Authorization") String authorization);

    /**
     * 二、POST请求
     * 2.1、json请求
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("article/list/json")
    Observable<ResponModel<List<BannerBean>>> addPatient(@Body RequestBody route);

    /**
     * 二、POST请求
     * 2.2、键值对请求
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<ResponModel<List<BannerBean>>> addOther(@Field("title") String title, @FieldMap HashMap<String, Object> map);

    /**
     * 三、单张图片上传(单个文件上传)
     */
    @POST("sys/file/uploadFile")
    @Multipart
    Observable<ResponModel<String>> upLoadFile(@Part MultipartBody.Part file);

    /**
     * 四、多张图片上传(多个文件上传)
     */
    @POST("sys/file/uploadFiles")
    @Multipart
    Observable<ResponModel<List<String>>> upLoadMoreFiles(@Part("type") RequestBody type, @PartMap Map<String, RequestBody> maps);

    /**
     * 五、PUT请求，其实和POST请求一样，只不过用PUT注解
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @PUT("article/list/json")
    Observable<ResponModel<List<BannerBean>>> updateMedicine(@Body RequestBody route);


    /**
     * 六、DELETE请求
     * 6.1、拼在url上
     * */
    @DELETE("patient/address/{addressId}")
    Observable<ResponModel<String>> deleteAddress(@Path("addressId") int addressId);

    /**
     * 六、DELETE请求
     * 6.2、DELETE的json请求
     * */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @HTTP(method = "DELETE", path = "patient/address/delete", hasBody = true)
    Observable<ResponModel<Object>> deleteReport(@Body RequestBody route);


    /**
     * 七、文件下载
     * 7.1、正常下载
     * */
    @GET
    @Streaming
    //10以上用@streaming。不会造成oom，反正你用就是了
    Observable<ResponseBody> downloadFile(@Url String url);


    /**
     * 七、文件下载
     * 7.2、断点下载
     * */
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url, @Header("RANGE") String range);

}
