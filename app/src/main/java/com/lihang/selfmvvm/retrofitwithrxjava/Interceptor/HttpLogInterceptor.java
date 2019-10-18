package com.lihang.selfmvvm.retrofitwithrxjava.Interceptor;

import android.text.TextUtils;


import com.lihang.selfmvvm.utils.LogUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by leo
 * on 2019/8/19.
 */
public class HttpLogInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        StringBuffer sbf = new StringBuffer();
        Request request = chain.request();

        RequestBody requestBody = request.body();
        String body = null;
        try {
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                body = buffer.clone().readString(charset);
                if (!TextUtils.isEmpty(body)) {
                    //如果是图片上传调用URLDecoder会报错，即使tryCache都没用，what!!!
                    String netUrl = request.url().toString();
                    body = URLDecoder.decode(body, "utf-8");
                }
            }
        } catch (IOException e) {
            LogUtils.i("上传文件或者，下载文件", "取消打印");
        }
        sbf.append(" \n请求方式：==> " + request.method())
                .append("\nurl：" + request.url())
                .append("\n请求头：" + request.headers())
                .append("\n请求参数: " + body);

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        String rBody;

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);

        try {
            if (!TextUtils.isEmpty(rBody)) {
                rBody = decodeUnicode(rBody);
            }
        } catch (Exception e) {
            rBody = "";
            LogUtils.i("上传文件或者，下载文件", "取消打印");
        }

        sbf.append("\n收到响应: code ==> " + response.code())
                .append("\nResponse: " + rBody);
        LogUtils.i("网络请求", sbf.toString());
        return response;
    }


    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
