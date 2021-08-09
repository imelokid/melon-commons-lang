package cn.melonkid.commons.lang.parser;

import com.google.gson.Gson;

/**
 * json格式化工具
 * base google gson
 *
 * @author imelonkid
 * @date 2021/08/09 10:14
 **/
public class JSONUtil {

    /** SERIALIZER */
    public static Gson SERIALIZER = new Gson();

    /**
     * <p><pre>{@code
     *  class BagOfPrimitives {
     *   private int value1 = 1;
     *   private String value2 = "abc";
     *   private transient int value3 = 3;
     *   BagOfPrimitives() {
     *     // no-args constructor
     *   }
     * }
     *
     * // Serialization
     * BagOfPrimitives obj = new BagOfPrimitives();
     * Gson gson = new Gson();
     * String json = gson.toJson(obj);
     *
     * // ==> json is {"value1":1,"value2":"abc"}
     * }
     *
     * @param obj source
     * @return ret
     */
    public static <T> String toJSONString(T obj) {
        return SERIALIZER.toJson(obj);
    }

    /**
     * from jsonString to obj
     *
     * @param jsonString from String
     * @param classOfT   target obj
     * @return target obj
     */
    public static <T> T fromJSON(String jsonString, Class<T> classOfT) {
        return SERIALIZER.fromJson(jsonString, classOfT);
    }
}
