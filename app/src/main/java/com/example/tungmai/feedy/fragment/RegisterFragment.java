package com.example.tungmai.feedy.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.activity.LoginActivity;
import com.example.tungmai.feedy.api.ConnectSever;
import com.example.tungmai.feedy.asynctask.PostLoadingDataAsyncTask;
import com.example.tungmai.feedy.custom.CustomImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by TungMai on 3/12/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {

    public static final int RESULT_LOAD_IMG = 32;
    private static final String TAG = "RegisterFragment";
    private static final int WHAT_REGISTER = 876;
    private static final String ACCOUNT_EXISTS = "Account is exists";
    private View view;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private RadioButton rbtMale;
    private RadioButton rbtFemale;
    private EditText edtBirthday;
    private String imageUser;
    private TextView tvAddImage;
    private ImageView ivImage;
    private Button btnRegister;
    private TextView tvBack;

    private Uri imageUriUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        edtName = (EditText) view.findViewById(R.id.edt_username);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        edtBirthday = (EditText) view.findViewById(R.id.edt_birthday);

        tvAddImage = (TextView) view.findViewById(R.id.tv_add_image);
        ivImage = (ImageView) view.findViewById(R.id.iv_image);
        tvAddImage.setOnClickListener(this);

        btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        rbtMale = (RadioButton) view.findViewById(R.id.rdb_male);
        rbtFemale = (RadioButton) view.findViewById(R.id.rdb_female);
        rbtMale.setChecked(true);

        tvBack = (TextView) view.findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                boolean gender;
                if (rbtMale.isChecked()) {
                    gender = true;
                } else {
                    gender = false;
                }
//                Log.e(TAG,gender+"");
                String birthday = edtBirthday.getText().toString();
                String image;
                if (imageUriUser == null) {
                    image = null;
                } else
                    image = imageUriUser.getPath();
//                Log.e(TAG,image);
//                    image = CustomImage.imageToBase64(imageUriUser.getPath());

                if (email.trim().isEmpty() || name.trim().isEmpty() || password.trim().isEmpty() || birthday.trim().isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), "Bạn nhập thiếu", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> map = new HashMap<>();
                map.put("name_user", name);
                map.put("email", email);
                map.put("password", password);
                map.put("gender", gender + "");
                map.put("birth", birthday);
                HashMap<String, String> mapFile = new HashMap<>();
                if (image == null) {
                    mapFile = null;
                } else
                    mapFile.put("profileimage", image);
//                mapFile.put()

                PostLoadingDataAsyncTask loginAsyncTask = new PostLoadingDataAsyncTask(WHAT_REGISTER, handler, getActivity(), ConnectSever.LINK_SERVER_REGISTER);
                loginAsyncTask.execute(map, mapFile);

                break;
            case R.id.tv_add_image:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;

            case R.id.tv_back:
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.changeFragmentLogin();
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            if (msg.what == WHAT_REGISTER) {
                Log.e(TAG, result);
                result = result.substring(1, result.length() - 1);
//                result = result.substring(0, result.length());
                if (result.equals(ACCOUNT_EXISTS)) {
//                    Log.e(TAG,result);
                    Toast.makeText(getActivity().getBaseContext(), "Tài khoản đã được sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Vui lòng xác nhận gmail", Toast.LENGTH_SHORT).show();
                    LoginActivity loginActivity = (LoginActivity) getActivity();
                    loginActivity.changeFragmentLogin();
                    return;
                }

            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            imageUriUser = data.getData();
            Log.e(TAG,imageUriUser.toString());
//            Log.e(TAG, CustomImage.base64Image(imageUriUser.getPath()));
//            ivImage.setImageBitmap(CustomImage.base64ToImage(CustomImage.imageToBase64(imageUriUser.getPath())));
            CustomImage.decodeFile(imageUriUser.getPath(),ivImage);
        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
