package com.subzero.trafficflow.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.response.UserResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 登陆页面
 * Created by hui on 2016/3/8.
 */
public class LoginActivity extends BaseActivity {

    private EditText login_user_account;
    private EditText login_user_passwd;
    private Button btn_login;
    private String mUserName;
    private String mUserPasswd;
    private UserResponse response;
    private UserResponse userResponse;


    @Override
    protected void initView() {

         /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        if (PreferenceDB.getInstance().islogin() && !TextUtils.isEmpty(PreferenceDB.getInstance().getUserAccesstoken())) {
            Intent okLogined = new Intent();
            okLogined.setClass(LoginActivity.this, MainActivity.class);
            startActivity(okLogined);
            LoginActivity.this.finish();
        }
    }

    @Override
    protected void initSystemBar() {

    }

    @Override
    protected void initData() {

        login_user_account = (EditText) findViewById(R.id.login_user_account);
        login_user_passwd = (EditText) findViewById(R.id.login_user_passwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName = login_user_account.getText().toString().trim();
                mUserPasswd = login_user_passwd.getText().toString().trim();

                if (null == mUserName || mUserName.length() == 0) {
                    ToastUtil.showNormalToast("请输入账号");
                    login_user_account.requestFocus();
                } else if (null == mUserPasswd || mUserPasswd.length() == 0) {
                    ToastUtil.showNormalToast("请输入密码");
                    login_user_passwd.requestFocus();
                } else {
                    //执行登陆操作
                    LoginAction();
                }

            }
        });
    }

    private void LoginAction() {
        String url = UrlConstants.BASE + UrlConstants.LOGIN_IN;
        OkHttpUtils.post().
                url(url).
                addParams("cols","").
                addParams("license", UrlConstants.license).
                addParams("userId", mUserName).
                addParams("password", mUserPasswd).
                build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        LogUtil.e("result", "请求失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        String res = JsonUtil.getFieldValue(response, "result");
                        userResponse = JsonUtil.parseJsonToBean(res, UserResponse.class);

                        LogUtil.e("result", res);

                        if (userResponse.isSuccess()) {
                            LogUtil.e("result", "登陆成功");
                            LogUtil.e("token", userResponse.getUser().getToken());

                            PreferenceDB.getInstance().cleanUser();
                            PreferenceDB.getInstance().setUserAccesstoken(userResponse.getUser().getToken());
                            PreferenceDB.getInstance().setIslogin(true);
                            PreferenceDB.getInstance().setUserId(userResponse.getUser().getUSER_ID());
                            PreferenceDB.getInstance().setGLJGBS(userResponse.getUser().getGLJGBS());
                            PreferenceDB.getInstance().setGLJGMC(userResponse.getUser().getGLJGMC());
                            PreferenceDB.getInstance().setUserName(userResponse.getUser().getUSER_NAME());
                            PreferenceDB.getInstance().setMAIL(userResponse.getUser().getMAIL());
                            PreferenceDB.getInstance().setSEX(userResponse.getUser().getSEX());

                            //跳转
                            Intent okLogined = new Intent();
                            okLogined.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(okLogined);
                            LoginActivity.this.finish();

                        } else {
                            ToastUtil.showNormalToast(userResponse.getError());
                        }
                    }
                });
    }


}
