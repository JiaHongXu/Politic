package politic.neu.com.politic.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 307A on 2016/11/6.
 *
 * 登录或注册成功后建立全局持有的一个单例用户
 */

public class DefaultUser {

    public static final String USER_PREF = "USER_PREF";
    public static final String USER_NAME_PREF = "USER_NAME";
    public static final String USER_ID_PREF = "USER_ID";
    public static final String USER_VIP_PREF = "USER_VIP";

    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;

    public static void setUpDefaultUser(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 登录或注册成功进入程序 写入User信息 保存在Preference里
     * @param userId 用户Id
     * @param userName 用户昵称
     * @param isVip 是否付费用户 是-"0"  否-"1"
     */
    public static void login(String userId, String userName, String isVip) {
        setUserId(userId);
        setUserName(userName);
        setIsVip(isVip.equals("1"));
    }

    /**
     * 清空本地用户数据
     */
    public static void logout() {
        editor.clear().commit();
    }

    /**
     * 检测是否有用户数据 判断是否自动登录
     * @return 是否自动登录
     */
    public static boolean autoLogin() {
        return  !userId().equals("") && !userName().equals("");
    }

    private static void setUserId(String userId) {
        editor.putString(USER_ID_PREF, userId).commit();
    }

    public static String userId() {
        return sharedPreferences.getString(USER_ID_PREF, "");
    }

    private static void setUserName(String userName) {
        editor.putString(USER_NAME_PREF, userName).commit();
    }

    public static String userName() {
        return sharedPreferences.getString(USER_NAME_PREF, "");
    }

    public static void setIsVip(boolean isVip) {
        editor.putString(USER_VIP_PREF, isVip?"1":"0").commit();
    }

    public static boolean isVip() {
        return sharedPreferences.getString(USER_ID_PREF, "0").equals("1");
    }
}
