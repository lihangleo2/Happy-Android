@[toc]
# Happy-Android
使用玩Android api。实现一个RxJava + Retrofit + MVVM的项目。通俗易懂
* okhttp+retrofit+rxjava+mvvm
* rxlifecycle解决RxJava内存泄漏
* banner
* glide
* svg
* 沉浸式状态栏
* cookieJar
* eventbus
* dataBinding
* aop实现登录校验（只要是重复代码，建议使用aop解决）
* 增加异步初始化，优化启动速度（相关代码在launchstater包）


<details>
<summary>关于Happy-Android</summary>
<pre><code>
持续更新中
</code></pre>
</details>

从零开始搭建MVVM架构系列文章（持续更新）:  
[Android从零开始搭建MVVM架构（1）————DataBinding](https://juejin.im/post/5d89d9f8f265da03f2340e2b)  
[Android从零开始搭建MVVM架构（2）————ViewModel](https://juejin.im/post/5d9c333cf265da5b8a515abb)  
[Android从零开始搭建MVVM架构（3）————LiveData](https://juejin.im/post/5d9d8f756fb9a04dd8591b8e)  
[Android从零开始搭建MVVM架构（4）————Room（从入门到进阶）](https://juejin.im/post/5d9fdacaf265da5bb86ac12c)  
[Android从零开始搭建MVVM架构（5）————Lifecycles](https://juejin.im/post/5da41d55f265da5bb977d15e)  
[Android从零开始搭建MVVM架构（6）————使用玩Android API带你搭建MVVM框架（初级篇）](https://juejin.im/post/5da6c0acf265da5bbb1e4df7)  
[Android从零开始搭建MVVM架构（7） ———— 使用玩Android API带你搭建MVVM框架（终极篇）](https://juejin.im/post/5da90c54f265da5b932e7960)


项目说明：
* lis下的tbs和jniLibs下的libtbs.so是腾讯X5WbeView不要的so库，使WebView加载更快捷
* 【base】里除了EventBusBean(有利于EventBus的判断)外其他都是MVVM封装的代码
* 【bean】放项目的bean解析对象
* 【ui】代码等存放包
* 【utils】项目本地用的utils
* 【morefunction】1、launchstater(启动器，提高启动性能)；2、apkupdate(apk更新)

* diooto 第三方，仿微信小图到大图转场动画支持手势操作(部分被作者修改)
* imagepicker 第三图片选择器，已被作者修改适配全面屏等(内部带简单剪裁)
* ucrop 第三方剪裁控件，更高级的剪裁。被作者修改适配全面屏等
* 【项目适配】使用的是screenMatch插件，原理是最小宽度限定符