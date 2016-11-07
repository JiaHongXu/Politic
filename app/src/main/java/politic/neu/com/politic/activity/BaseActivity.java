package politic.neu.com.politic.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 大家可以参考这个BaseActivity里的方法
 * 新建的Activity都继承于这个类, 许多方法可以直接调用
 * 该项目的编码规范以及工具类的使用方法可参考LoginActivity
 *
 * 命名规范
 * --变量命名(驼峰)
 * Button -> btn : loginBtn
 * TextView -> tv : emailTv
 * Image -> img : iconImg
 * ImageButton -> imgBtn : submitImgBtn
 * ImageView -> iv : iconIv
 * CheckBox -> chk : confirmChk
 * Tab -> tab : titleTab
 *
 * --常量命名
 * 字母全部大写 单词之间下划线_分隔 例 长等待时间 WAITING_DURATION_LONG
 *
 * --xml控件id的命名
 * 例如 LoginActivity 的提交按钮
 * 控件类型_控件功能_Activity或Fragment btn_submit_login
 *
 * --方法命名
 * initXXX() 初始化
 * isXXX() 返回值
 * getXXX() 取某个值
 * processXXX() 处理XXX
 * displayXXX() showXXX() 显示XXX
 *
 * --尺寸数据 见 res/values/dimens.xml
 *
 * --颜色数据 见 res/values/colors.xml
 * 调试时可以自定义色值
 */
public class BaseActivity extends AppCompatActivity {
    private SystemBarTintManager mTintManager;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        initUI();
        initData();
    }

    /**
     * 在这里 初始化界面UI
     */
    private void initUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 在这里 初始化数据 比如xxx = new XXX()等的初始化变量的操作
     */
    private void initData() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);

        progressDialog = new ProgressDialog(this);
    }

    protected Context getContext() {
        return context;
    }

    /**
     * 启动带进度条的等待对话框
     */
    protected final void startWaitingDialog() {
        progressDialog.show();
    }

    /**
     * 启动带进度条的等待对话框
     * @param msg 提示语
     */
    protected final void startWaitingDialog(String msg) {
        progressDialog.setMessage(msg);
        startWaitingDialog();
    }

    /**
     * 让上面弹出的等待对话框消失
     */
    protected final void stopWaitingDialog() {
        progressDialog.dismiss();
    }

    /**
     * 显示带消息的SnackBar(无按钮)
     * @param activity 该SnackBar所在的activity
     * @param msg 显示的SnackBar里的消息
     */
    protected final void showSnackBar(Context activity, String msg) {
        showSnackBar(activity, msg, null, null);
    }

    /**
     * 显示带消息的SnackBar(带按钮)
     * @param activity 该SnackBar所在的activity
     * @param msg 显示的SnackBar里的消息
     * @param actionString SnackBar按钮文字
     * @param listener SnackBar按钮的点击监听器
     */
    protected final void showSnackBar(Context activity, String msg, String actionString, View.OnClickListener listener) {
        View rootView = ((Activity)activity).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);

        if (listener != null) {
            snackbar.setAction(actionString, listener);
        }
        snackbar.show();
    }

    /**
     * 设置状态栏颜色，在onPageScrolled里进行背景颜色一样的设置值。
     * @param color 颜色
     */
    protected final void applySelectedColor(int color) {
        mTintManager.setTintColor(color);
    }

    /**
     * 获取当前activity的根视图View
     * @param context 当前上下文
     * @return 返回根视图
     */
    protected final View getRootView(Activity context) {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
