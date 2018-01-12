package com.epicwednesday.android.createuser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MemberCreationAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_creation);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_member_creation);
        if (fragment == null){
            fragment = new MemberCreationFragment();
            fragmentManager.beginTransaction().add(R.id.activity_member_creation,fragment).commit();
        }
    }
}
