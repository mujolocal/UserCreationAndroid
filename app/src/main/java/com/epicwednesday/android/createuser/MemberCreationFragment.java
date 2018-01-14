package com.epicwednesday.android.createuser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mjohnson on 1/11/18.
 */

public class MemberCreationFragment extends Fragment {
    private static final  String TAG ="MemberCreationFragment";
    private  Member mMember;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mContinueButton;
    private TextView mInfoTextView;
    private WebPullTask mWebPullTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_creation,container,false);
        mEmailEditText = view.findViewById(R.id.edit_text_fmc_email);
        mPasswordEditText = view.findViewById(R.id.edit_text_fmc_password);
        mInfoTextView = view.findViewById(R.id.text_view_fmc_info);
        mWebPullTask = new WebPullTask();
        mContinueButton = view.findViewById(R.id.button_fmc_membercreation);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 1");
                mWebPullTask.execute();
            }
        });



        return view;
    }
    private class WebPullTask extends AsyncTask<Void,Void,Void>{
        private static final String Tag = "WebPullTask";
        private WebPull mWebPull;
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: 2");
            mWebPull = new WebPull("m@g.com","112233231231");
            mWebPull.fetchItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: 3");
            super.onPostExecute(aVoid);
            mInfoTextView.setText(mWebPull.getJsonString());
        }
    }
}
