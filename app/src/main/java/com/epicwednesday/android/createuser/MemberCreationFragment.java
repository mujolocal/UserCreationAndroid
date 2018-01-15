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
    private WebPullTask mWebPullTask;
    private ProgressBar mProgressBar;
    private CreateMemberWebPush mCreateMemberWebPush;

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

                if(!passwordsAreTheSame()){
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mInfoTextView.setText("Passwords are not the Same. Please re Enter them ");
                    mPasswordConfimEditText.setText("");
                    mPasswordEditText.setText("");
                }else if(mPasswordConfimEditText.getText().toString().length() < 4){
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mInfoTextView.setText("Password too short please make greater than 3 characters");
                }else{
                    mWebPullTask = new WebPullTask();
                    mWebPullTask.execute();
                }
            }
        });
        return view;
    }
    private class WebPullTask extends AsyncTask<Void,Void,Void>{
        private static final String Tag = "WebPullTask";
        private EmailValidationWebPull mEmailValidationWebPull;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickablesEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            mEmailValidationWebPull = new EmailValidationWebPull(mEmailEditText.getText().toString());
            mEmailValidationWebPull.validateEmail();
            if(mEmailValidationWebPull.getEmailIsValidBoolean()){
                mCreateMemberWebPush = new CreateMemberWebPush(
                        mEmailEditText.getText().toString(),
                        mPasswordConfimEditText.getText().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: ");
            super.onPostExecute(aVoid);
            if(!mEmailValidationWebPull.getEmailIsValidBoolean()){
                mInfoTextView.setText("Email Already Used, please choose another");
            }
            mProgressBar.setVisibility(View.INVISIBLE);
            clickablesEnabled(true);
        }
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
}
