package com.zhy.http.okhttp.callback;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by Vector
 * on 2017/2/14 0014.
 */

/**
 * 不能以内部类的形式调用
 *
 * @param <T>
 */
public abstract class OuterCallback<T> extends Callback<T> {
    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {

        Class clz1 = this.getClass();//this指的是在Client中真正new 的对象（ new BookDaoImpl()）
        //得到它的泛型父类
        Type type = clz1.getGenericSuperclass();

        //3.将type转化为ParameterizedType,因为它可以取<>中的数据
        ParameterizedType pt = (ParameterizedType) type;

        //4.得到<T>的具体取值
        Class<T> mClazz = (Class<T>) pt.getActualTypeArguments()[0];
        String string = response.body().string();
        return JSON.parseObject(string, mClazz);
    }
}
