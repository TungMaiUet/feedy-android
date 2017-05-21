package com.example.tungmai.feedy.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.fragment.FragmentLogin;
import com.example.tungmai.feedy.fragment.RegisterFragment;
import com.example.tungmai.feedy.models.ItemPrepare;
import com.example.tungmai.feedy.splite.Database;

import java.util.ArrayList;

/**
 * Created by TungMai on 3/12/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private FragmentLogin fragmentLogin;
    private RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentLogin = new FragmentLogin();
        registerFragment = new RegisterFragment();
        initFragmentLogin();
//        Database database = new Database(this);
//        ArrayList<ItemPrepare> temp=database.getDataPrepare();
//        Log.e("test",temp.size()+"");
    }

    public void initFragmentLogin() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentLogin);
        fragmentTransaction.commit();
    }

    public void initFragmentRegister() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_login_register_come, R.anim.anim_login_register_gone);

        fragmentTransaction.replace(R.id.frame_layout, registerFragment);
        fragmentTransaction.commit();
    }

    public void changeFragmentLogin() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_register_login_gone, R.anim.anim_login_register_come);

        fragmentTransaction.replace(R.id.frame_layout, fragmentLogin);
        fragmentTransaction.commit();
    }
}
