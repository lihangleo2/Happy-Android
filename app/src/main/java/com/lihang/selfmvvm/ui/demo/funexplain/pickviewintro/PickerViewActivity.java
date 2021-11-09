package com.lihang.selfmvvm.ui.demo.funexplain.pickviewintro;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.leo.utilspro.utils.GsonUtil;
import com.leo.utilspro.utils.ToastUtils;
import com.leo.utilspro.utils.threadpool.ThreadManager;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.bean.CardBean;
import com.lihang.selfmvvm.bean.JsonBean;
import com.lihang.selfmvvm.bean.ProvicenBean;
import com.lihang.selfmvvm.databinding.PickerActivityBinding;
import com.lihang.selfmvvm.utils.GetJsonDataUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leo
 * on 2021/11/9.
 */
public class PickerViewActivity extends BaseActivity<NormalViewModel, PickerActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.picker_activity;
    }

    @Override
    protected void processLogic() {
        initSexPickerView();
        initTimePickerView();

        //先初始化地址信息，然后再初始化cityPickerView
        initJsonData();
    }


    //城市选择
    private OptionsPickerView cityPickerView;//城市选择器
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void initCityPickerView() {
        cityPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String provicen = options1Items.get(options1).getPickerViewText();
                String city = options2Items.get(options1).get(options2);
                String area = options3Items.get(options1).get(options2).get(options3);
                String address = "";
                if (provicen.equals(city)) {
                    address = city + "  " + area;
                } else {
                    address = provicen + "  " + city + "  " + area;
                }
                binding.txtAddress.setText(address);

            }
        })
                .setSelectOptions(8, 0, 0)
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvFinish = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvOk = (TextView) v.findViewById(R.id.tv_ok);
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cityPickerView.returnData();
                                cityPickerView.dismiss();
                            }
                        });

                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cityPickerView.dismiss();
                            }
                        });


                    }
                })
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        cityPickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器

    }


    private void initJsonData() {
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                /**
                 * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
                 * 关键逻辑在于循环体
                 *
                 * */
                String JsonData = GetJsonDataUtil.getJson(PickerViewActivity.this, "province.json");//获取assets目录下的json文件数据
                ProvicenBean provicenBean = parseData(JsonData);
                ArrayList<JsonBean> jsonBean = provicenBean.getData();//用Gson 转成实体

                /**
                 * 添加省份数据
                 *
                 * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
                 * PickerView会通过getPickerViewText方法获取字符串显示出来。
                 */
                options1Items = jsonBean;

                for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                    ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                    for (int c = 0; c < jsonBean.get(i).getChilds().size(); c++) {//遍历该省份的所有城市
                        String CityName = jsonBean.get(i).getChilds().get(c).getValue();
                        CityList.add(CityName);//添加城市
                        ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                        if (jsonBean.get(i).getChilds().get(c).getChilds() == null
                                || jsonBean.get(i).getChilds().get(c).getChilds().size() == 0) {
                            City_AreaList.add("");
                        } else {
                            for (int j = 0; j < jsonBean.get(i).getChilds().get(c).getChilds().size(); j++) {
                                City_AreaList.add(jsonBean.get(i).getChilds().get(c).getChilds().get(j).getValue());
                            }
//                            City_AreaList.addAll(jsonBean.get(i).getChilds().get(c).getChilds().get(0).getValue());
                        }
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                    }

                    /**
                     * 添加城市数据
                     */
                    options2Items.add(CityList);

                    /**
                     * 添加地区数据
                     */
                    options3Items.add(Province_AreaList);
                }


                binding.relativeAddress.post(new Runnable() {
                    @Override
                    public void run() {
                        initCityPickerView();
                    }
                });
            }
        });
    }

    public ProvicenBean parseData(String result) {//Gson 解析
        ProvicenBean provicenBean = GsonUtil.deser(result, ProvicenBean.class);
        return provicenBean;
    }


    //时间选择
    TimePickerView pvTime;

    private void initTimePickerView() {
        Calendar selectedDate = Calendar.getInstance();
        //如果要选特定的时间点，在这里填写。不填的话，默认就是当天的数据
//        selectedDate.set(1990, 1, 1);
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat formatYear = new SimpleDateFormat("yyyy-MM-dd");
                String timeStr = formatYear.format(date);
                binding.txtBorn.setText(timeStr);
            }
        })
                .setLayoutRes(R.layout.pickerview_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvFinish = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvOk = (TextView) v.findViewById(R.id.tv_ok);
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });

                        tvFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setDate(selectedDate)
                .build();
    }


    //条件选择 (性别选择)
    OptionsPickerView pvCustomOptions;
    ArrayList<CardBean> cardItem = new ArrayList<>();

    private void initSexPickerView() {
        cardItem.add(new CardBean(0, "男"));
        cardItem.add(new CardBean(1, "女"));
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                binding.txtSex.setText(tx);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvFinish = (TextView) v.findViewById(R.id.tv_finish);
                final TextView tvOk = (TextView) v.findViewById(R.id.tv_ok);
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    }
                });

                tvFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.dismiss();
                    }
                });
            }
        })
                .isDialog(true)
                .setOutSideCancelable(true)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_sex:
                //条件选择点击(性别选择)
                pvCustomOptions.show();
                break;

            case R.id.relative_born:
                //时间选择点击
                pvTime.show();
                break;

            case R.id.relative_address:
                //城市选择点击
                cityPickerView.show();
                break;
        }
    }
}
