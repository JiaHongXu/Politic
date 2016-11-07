package politic.neu.com.politic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import politic.neu.com.politic.R;
import politic.neu.com.politic.fragment.LoginFragment;
import politic.neu.com.politic.fragment.SignUpFragment;
import politic.neu.com.politic.utils.ColorShades;
import politic.neu.com.politic.utils.DefaultUser;

public class LoginActivity extends BaseActivity {
    public static final int LOGIN_START = 1000;
    public static final int SIGNUP_START = 2000;
    public static final int LOGIN_SUCCESS = 1001;
    public static final int LOGIN_FAIL = 1002;
    public static final int SIGNUP_SUCCESS = 2001;
    public static final int SIGNUP_FAIL = 2002;
    @BindView(R.id.view_pager_container_login_activity)
    ViewPager mViewPager;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    // private ViewPager mViewPager;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;

    public static Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DefaultUser.setUpDefaultUser(this.getApplicationContext());
        if (DefaultUser.autoLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        initData();
        initView();
    }

    private void initData() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case LOGIN_START:
                        startWaitingDialog("正在登录...");
                        break;
                    case SIGNUP_START:
                        startWaitingDialog("正在注册...");
                        break;
                    case LOGIN_SUCCESS:
                        stopWaitingDialog();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        break;
                    case LOGIN_FAIL:
                        stopWaitingDialog();
                        showSnackBar(LoginActivity.this, "登录失败");
                        break;
                    case SIGNUP_SUCCESS:
                        stopWaitingDialog();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        break;
                    case SIGNUP_FAIL:
                        stopWaitingDialog();
                        showSnackBar(LoginActivity.this, "注册失败", "朕知道啦", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        break;
                }
            }
        };

        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(R.id.view_pager_container_login_activity);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_login_activity);
        tabLayout.setupWithViewPager(mViewPager);


    }

    private void initView() {
        final ColorShades shades = new ColorShades();
        final int[] colorBg = getResources().getIntArray(R.array.login_bg);
        applySelectedColor(colorBg[0]);
        final RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.bg_layout_login_activity);
        mViewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        shades.setFromColor(colorBg[position % colorBg.length])
                                .setToColor(colorBg[(position + 1) % colorBg.length])
                                .setShade(positionOffset);
                        rootLayout.setBackgroundColor(shades.generate());
                        applySelectedColor(shades.generate());
                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return loginFragment;
                case 1:
                    return signUpFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.login_activity_login);
                case 1:
                    return getResources().getString(R.string.signUp_activity_login);
            }
            return null;
        }
    }
}
