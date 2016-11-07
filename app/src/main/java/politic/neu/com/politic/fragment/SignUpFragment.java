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

import static politic.neu.com.politic.activity.LoginActivity.SIGNUP_FAIL;
import static politic.neu.com.politic.activity.LoginActivity.SIGNUP_START;
import static politic.neu.com.politic.activity.LoginActivity.handler;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private View view;
    private Button signBtn;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        init();
        return view;
    }

    private void init() {
        signBtn = (Button) view.findViewById(R.id.btn_signUp_signUp);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = handler.obtainMessage();
                m.what = SIGNUP_START;
                handler.sendMessage(m);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        DefaultUser.login("20144786", "徐嘉宏", "1");
                        Message m = handler.obtainMessage();
                        m.what = SIGNUP_FAIL;
                        handler.sendMessage(m);
                    }
                }.start();
            }
        });
    }

}
