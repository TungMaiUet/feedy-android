package com.example.tungmai.feedy.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.HomeActivity;
import com.example.tungmai.feedy.activity.LoginActivity;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.GetLoadingDataAsyncTask;
import com.example.tungmai.feedy.asynctask.PostLoadingDataAsyncTask;
import com.example.tungmai.feedy.models.User;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by TungMai on 3/12/2017.
 */

public class FragmentLogin extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentLogin";
    private static final int WHAT_LOGIN = 423;
    private static final String LOGIN_FAIL = "Ko dc";
    private static final String LOGIN_VERITY = "Don't verity";
    private static final int WHAT_FORGET_PASSWORD = 421;
    public static final String INTENT_USER = "intent user";
    public static final String INTENT_ITEM_BLOG = "inten item blog";
    public static final int REQUEST_CODE_LOGIN = 9237;


    private View view;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private CheckBox chbSave;
    private TextView tvForget;
    //    private LoginButton loginButton;
    private TextView tvRegister;

    private SaveSharedPreference saveSharedPreference;
//    private CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
//        sharedPreferences = getActivity().getSharedPreferences("my_account", getActivity().MODE_PRIVATE);
        saveSharedPreference = new SaveSharedPreference();

        Log.e(TAG, "bbb");
        if (saveSharedPreference.getUser(getActivity().getBaseContext()) != null) {
            Log.e(TAG, "aa");
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            User user = saveSharedPreference.getUser(getActivity().getBaseContext());
            intent.putExtra(INTENT_USER, user);
            startActivityForResult(intent, REQUEST_CODE_LOGIN);
        }

//        if(saveSharedPreference.getUserName(getActivity()/))
        initViews();
        return view;
    }


    private void initViews() {
        edtEmail = (EditText) view.findViewById(R.id.edt_username);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);

        edtEmail.setText(saveSharedPreference.getUserName(getActivity().getBaseContext()));
        edtPassword.setText(saveSharedPreference.getPassword(getActivity().getBaseContext()));

//        if (!edtEmail.getText().toString().trim().equals("") && !edtPassword.getText().toString().trim().equals("")) {
//            HashMap<String, String> mapLogin = new HashMap<>();
//            mapLogin.put("email", edtEmail.getText().toString());
//            mapLogin.put("password", edtPassword.getText().toString());
//            GetLoadingDataAsyncTask loginAsyncTask = new GetLoadingDataAsyncTask(handler, getActivity(), WHAT_LOGIN);
//            loginAsyncTask.execute(ConnectSever.LINK_SERVER_LOGIN + "/" + edtEmail.getText().toString() + "/" + edtPassword.getText().toString());
//        }

        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        chbSave = (CheckBox) view.findViewById(R.id.checkbox);
//        chbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    saveSharedPreference.setUserName(getActivity().getBaseContext(),edtEmail.getText().toString(),edtPassword.getText().toString());
//////                    Log.e(TAG, "aaaa");
//                    SharedPreferences.Editor edit = sharedPreferences.edit();
//                    edit.putString("username", edtEmail.getText().toString());
//                    edit.putString("password", edtPassword.getText().toString());
//                    edit.commit();
//                }
//            }
//        });

        tvForget = (TextView) view.findViewById(R.id.tv_forget_password);
        tvForget.setOnClickListener(this);
        tvRegister = (TextView) view.findViewById(R.id.tv_creat_account);
        tvRegister.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), "Bạn nhập không đủ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (chbSave.isChecked()) {
                    saveSharedPreference.setUserName(getActivity().getBaseContext(), edtEmail.getText().toString(), edtPassword.getText().toString());
                }
                HashMap<String, String> mapLogin = new HashMap<>();
                mapLogin.put("email", email);
                mapLogin.put("password", password);
                GetLoadingDataAsyncTask loginAsyncTask = new GetLoadingDataAsyncTask(handler, getActivity(), WHAT_LOGIN);
                loginAsyncTask.execute(ConnectSever.LINK_SERVER_LOGIN + "/" + email + "/" + password);

                break;
            case R.id.tv_forget_password:
                String emailForget = edtEmail.getText().toString();
                if (emailForget.trim().isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), "Bạn chưa nhập email để lấy lại mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> mapLoginForget = new HashMap<>();
                mapLoginForget.put("email", emailForget);
                PostLoadingDataAsyncTask forgetAsyncTask = new PostLoadingDataAsyncTask(WHAT_FORGET_PASSWORD, handler, getActivity(), ConnectSever.LINK_SERVER_LOGIN_FORGET);
                forgetAsyncTask.execute(mapLoginForget, null);
                break;

            case R.id.tv_creat_account:
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.initFragmentRegister();

                break;

        }
    }

    private Handler handler = new Handler() {
        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
//            Log.e(TAG, result + "a");
            if (msg.what == WHAT_LOGIN) {
                result = result.substring(1, result.length() - 1);
//                result = result.substring(0, result.length());
//                Log.e(TAG, result);
                if (result.equals(LOGIN_FAIL)) {
//                    Log.e(TAG,result);
                    Toast.makeText(getActivity().getBaseContext(), "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result.equals(LOGIN_VERITY)) {
                    Toast.makeText(getActivity().getBaseContext(), "Tài khoản của bạn chưa được xác nhận", Toast.LENGTH_SHORT).show();
                    return;
                } else {
//                    Log.e(TAG, result);
                    try {
//                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObject = new JSONObject(result);
                        String id = jsonObject.getString("_id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        boolean gender = jsonObject.getBoolean("gender");
                        String urlImage = jsonObject.getString("image");
                        String birth = jsonObject.getString("birth");
                        User user = new User(id, name, email, gender, birth, urlImage);

                        if (chbSave.isChecked())
                            saveSharedPreference.setUser(getActivity().getBaseContext(), user);

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.putExtra(INTENT_USER, user);
                        startActivityForResult(intent, REQUEST_CODE_LOGIN);
//                        Log.e(TAG,user.getImageUser());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (msg.what == WHAT_FORGET_PASSWORD) {
                result = result.substring(1, result.length() - 1);
//                result = result.substring(0, result.length());
                String message;
                if (result.equals(LOGIN_FAIL)) {
//                    Log.e(TAG,result);
                    message = "Không có tài khoản yêu cầu";
                } else
                    message = "Vui lòng vào email để lấy lại mật khẩu";
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Quên mật khẩu");
                dialog.setMessage(message);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
//                Log.e(TAG, result);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            saveSharedPreference.logOut(getActivity().getBaseContext());
        }
    }

    class SaveSharedPreference {
        static final String PREF_USER_NAME = "username";
        static final String PREF_PASSWORD = "password";
        static final String PREF_USER = "user-pref";

        public SharedPreferences getSharedPreferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public void setUserName(Context ctx, String userName, String password) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_USER_NAME, userName);
            editor.putString(PREF_PASSWORD, password);
            editor.commit();
        }

        public void setUser(Context ctx, User user) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            editor.putString(PREF_USER, json);
            editor.commit();
        }

        public String getUserName(Context ctx) {
            return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
        }

        public String getPassword(Context ctx) {
            return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
        }

        public User getUser(Context ctx) {
            Gson gson = new Gson();
            String json = getSharedPreferences(ctx).getString(PREF_USER, "");
            Log.e(TAG, json + "cc");
            User obj = gson.fromJson(json, User.class);
            return obj;
        }

        public void logOut(Context ctx) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            Log.e(TAG, "logout");
            editor.clear();
            editor.commit();
        }
    }
}
