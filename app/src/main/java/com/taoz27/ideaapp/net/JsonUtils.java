package com.taoz27.ideaapp.net;

import com.google.gson.Gson;
import com.taoz27.ideaapp.models.BaseResponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by taoz27 on 2017/11/17.
 */

public class JsonUtils {
    public static <T> BaseResponse<T> fromJsonObject(String json, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(BaseResponse.class, new Class[]{clazz});
        Gson gson=new Gson();
        return gson.fromJson(json, type);
    }
    public static <T> BaseResponse<List<T>> fromJsonArray(String json, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(BaseResponse.class, new Type[]{listType});
        Gson gson=new Gson();
        return gson.fromJson(json, type);
    }
}
