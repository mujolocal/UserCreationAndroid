package com.epicwednesday.android.createuser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mjohnson on 1/11/18.
 */

public class MemberCreationFragment extends Fragment {
    private  Member mMember;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mContinueButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_creation,container,false);
        return view;
    }
}
