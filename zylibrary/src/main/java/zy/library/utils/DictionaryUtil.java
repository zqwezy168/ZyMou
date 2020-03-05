package zy.library.utils;

/**
 * 描述：字典转化工具类
 * 创建人：Justin
 * 创建时间：2016/10/14 17:35
 * 修改人：
 * 修改时间：2016/10/14 17:35
 * 修改备注：
 */

public class DictionaryUtil {

    public static String getMaritalStatus(int marital) {
        switch (marital) {
            case 1:
                return "已婚";
            case 2:
                return "未婚";
            case 3:
                return "丧偶";
            case 4:
                return "离异";
            default:
                return "未婚";
        }
    }


    public static String getEqptLevel(String level) {
        switch (level) {
            case "1":
                return "一级";
            case "2":
                return "二级";
            case "3":
                return "三级";
            default:
                return "三级";
        }
    }
}
