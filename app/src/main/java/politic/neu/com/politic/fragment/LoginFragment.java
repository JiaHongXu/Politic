package politic.neu.com.politic.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import politic.neu.com.politic.R;
import politic.neu.com.politic.utils.DefaultUser;

import static politic.neu.com.politic.activity.LoginActivity.LOGIN_START;
import static politic.neu.com.politic.activity.LoginActivity.LOGIN_SUCCESS;
import static politic.neu.com.politic.activity.LoginActivity.handler;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private View view;
    private Button loginBtn;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init();
        return view;
    }

    private void init() {
        loginBtn = (Button) view.findViewById(R.id.btn_login_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = handler.obtainMessage();
                m.what = LOGIN_START;
                handler.sendMessage(m);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000);
                            DefaultUser.login("20144786", "徐嘉宏", "1");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Message m = handler.obtainMessage();
                        m.what = LOGIN_SUCCESS;
                        handler.sendMessage(m);
                    }
                }.start();
            }
        });
    }
}
