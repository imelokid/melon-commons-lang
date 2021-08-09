package cn.melonkid.commons.lang.parser;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * 占位符解析器
 *
 * @author imelonkid
 * @date 2021/08/07 11:52
 **/
public class PlaceholdParser {

    /** 美元符 */
    public static String DOLLER_FLAG = "$";

    /** 左大括号 */
    public static String BRACKET_LEFT = "{";

    /** 右大括号 */
    public static String BRACKET_RIGHT = "}";

    /**
     * 解析占位符 并将占位符替换为传入的参数
     * <p>
     * 如 我有三样东西，分别是：${test}，${test1}
     * 约定： 1. 占位符做括号和右括号之间只能是英文字母，不能包含其他符号
     * 2. 没有找到占位符参数的，不进行替换
     *
     * @param sourceStr 原始字符串
     * @param params    参数集合
     * @return  替换占位符后的字符串
     */
    public static String parseAndReplaceByName(String sourceStr, Map<String, String> params) {
        if (StringUtils.isBlank(sourceStr)) {
            return sourceStr;
        }
        if (params == null || params.size() < 1) {
            return sourceStr;
        }

        // 字符串必须同时保函左右大括号
        if (!(sourceStr.contains(DOLLER_FLAG) && sourceStr.contains(BRACKET_LEFT)
            && sourceStr.contains(BRACKET_RIGHT))) {
            return sourceStr;
        }

        StringBuilder sb = new StringBuilder();
        char[] sourceStrArr = sourceStr.toCharArray();

        for (int i = 0; i < sourceStrArr.length; i++) {
            char c1 = sourceStrArr[i];
            char c2 = 0;
            if (i < sourceStrArr.length - 1) {
                c2 = sourceStrArr[i + 1];
            }

            if (!(c1 == DOLLER_FLAG.charAt(0) && c2 == BRACKET_LEFT.charAt(0))) {
                sb.append(c1);
                continue;
            }

            // 发现左占位符
            if (c1 == DOLLER_FLAG.charAt(0) && c2 == BRACKET_LEFT.charAt(0)) {
                // 已经走到最后一个占位符了，但是这个占位符不完整
                if (i + 2 >= sourceStrArr.length) {
                    sb.append(DOLLER_FLAG).append(BRACKET_LEFT);
                    return sb.toString();
                }

                // 获取KEY
                StringBuilder paramKey = new StringBuilder();
                for (int j = i + 2; j < sourceStrArr.length; j++) {
                    char c3 = sourceStrArr[j];

                    if (c3 != BRACKET_RIGHT.charAt(0)) {
                        paramKey.append(sourceStrArr[j]);
                        continue;
                    }

                    // 空KEY 这种原样保持了吧
                    if (paramKey.length() == 0) {
                        sb.append(DOLLER_FLAG).append(BRACKET_LEFT).append(BRACKET_RIGHT);
                        i = j;
                        break;
                    }

                    // 获取参数
                    String param = params.get(paramKey.toString());
                    if (param == null) {
                        sb.append(DOLLER_FLAG).append(BRACKET_LEFT).append(paramKey)
                            .append(BRACKET_RIGHT);
                        i = j;
                        break;
                    }

                    // 直接将占位符替换为参数
                    sb.append(param);
                    i = j;
                    break;
                }
            }
        }

        return sb.toString();
    }

    /**
     * 解析占位符 并将占位符替换为传入的参数
     * 如 我有三样东西，分别是：{}，{}，{}
     *
     * @param sourceStr 原始字符串
     * @param params    参数列表
     * @return  替换占位符之后的字符串
     */
    public static String parse(String sourceStr, String... params) {
        if (StringUtils.isBlank(sourceStr)) {
            return sourceStr;
        }

        // 字符串必须同时保函左右大括号
        if (!(sourceStr.contains(BRACKET_LEFT) && sourceStr.contains(BRACKET_RIGHT))) {
            return sourceStr;
        }

        StringBuilder sb = new StringBuilder();
        char[] sourceStrArr = sourceStr.toCharArray();

        int targetCn = 0;

        for (int i = 0; i < sourceStrArr.length; i++) {
            // 获取左右大括号
            char left = sourceStrArr[i];
            char right = 0;
            if (i < sourceStrArr.length - 1) {
                right = sourceStrArr[i + 1];
            }

            // 命中
            if (BRACKET_LEFT.charAt(0) == left && BRACKET_RIGHT.charAt(0) == right) {
                sb.append(generateParam(targetCn++, params));
                i++;
                continue;
            }
            sb.append(left);
        }

        return sb.toString();
    }

    /**
     * 获取当前占位符对应的参数
     *
     * @param targetCn
     *     目标占位符位置
     * @param params
     *     参数列表
     * @return 占位符对应的参数
     */
    private static String generateParam(int targetCn, String[] params) {
        if (params == null || params.length < 1) {
            return "";
        }

        if (targetCn >= params.length) {
            return "";
        }

        return params[targetCn];
    }

    public static void main(String[] args) {

    }
}
