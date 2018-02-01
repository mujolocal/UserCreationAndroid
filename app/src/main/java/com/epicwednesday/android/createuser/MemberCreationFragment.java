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
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by mjohnson on 1/11/18.
 * needs:
 *      memberCreation (create a new user)
 *      web access validation(check to see if there is an internet connection)
 *      tests
 *
 */

public class MemberCreationFragment extends Fragment {
    private static final  String TAG ="MemberCreationFragment";
//    private  Member mMember;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordConfimEditText;
    private Button mContinueButton;
    private TextView mInfoTextView;
    private ProgressBar mProgressBar;
    private CreateMemberWebPull mCreateMemberWebPull;
    private String results;


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
        mPasswordConfimEditText = view.findViewById(R.id.edit_text_fmc_password_confirm);
        mInfoTextView = view.findViewById(R.id.text_view_fmc_info);
        mProgressBar = view.findViewById(R.id.progress_bar_fmc);
//        mWebPullTask = new WebPullTask();

        mContinueButton = view.findViewById(R.id.button_fmc_membercreation);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfoTextView.setText("");
                mProgressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "onClick: ");
                clickablesEnabled(false);
                if(!passwordsAreTheSame()){
                    clickablesEnabled(true);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mInfoTextView.setText("Passwords are not the Same. Please re Enter them ");
                    mPasswordConfimEditText.setText("");
                    mPasswordEditText.setText("");
                }else if(mPasswordConfimEditText.getText().toString().length() < 4){
                    clickablesEnabled(true);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mInfoTextView.setText("Password too short please make greater than 3 characters");
                }else{
                    mCreateMemberWebPull = new CreateMemberWebPull();
                    CreateMemberWebPullAsyncTask createMemberWebPullAsyncTask = new CreateMemberWebPullAsyncTask();
                    createMemberWebPullAsyncTask.execute();
                }
            }
        });
        return view;
    }

    private Boolean passwordsAreTheSame(){
        Boolean bool = Boolean.FALSE;
        if ( (mPasswordConfimEditText.getText().toString() ).equals(mPasswordEditText.getText().toString() ) ){
            bool = Boolean.TRUE;
        }
        return bool;
    }
    private void clickablesEnabled(Boolean bool){
        mEmailEditText.setClickable(bool);
        mEmailEditText.setEnabled(bool );
        mPasswordEditText.setClickable(bool);
        mPasswordEditText.setEnabled(bool);
        mPasswordConfimEditText.setEnabled(bool);
        mPasswordConfimEditText.setClickable(bool);
        mContinueButton.setEnabled(bool);
        mContinueButton.setClickable(bool);
    }
    private class CreateMemberWebPullAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            mCreateMemberWebPull.nameAndPass(mEmailEditText.getText().toString()
                    ,mPasswordConfimEditText.getText().toString());
            mCreateMemberWebPull.createUser();
            Log.d(TAG, "doInBackground: ASYNC TAsk");
         return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            results = mCreateMemberWebPull.getResults();
            Log.d(TAG, "onPostExecute: "+results);
            clickablesEnabled(true);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
