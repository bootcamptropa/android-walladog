package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.walladog.walladog.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by hadock on 19/12/15.
 *
 */

public class SigninFragment extends Fragment {

    private static final String TAG = SigninFragment.class.getName();

    @Bind(R.id.email)
    TextView userEmail;

    @Bind(R.id.password)
    TextView userPassword;

    @Bind(R.id.email_sign_in_button)
    Button btnLogin;

    private OnSigninClickListener mOnSigninClickListener=null;

    public static SigninFragment newInstance() {
        SigninFragment fragment = new SigninFragment();
        return fragment;
    }

    public SigninFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View root = inflater.inflate(R.layout.fragment_signin,container,false);
        ButterKnife.bind(this, root);

        //TODO implement for siginin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSigninClickListener != null) {
                    mOnSigninClickListener.onSigninSubmit(userEmail.getText().toString(), userPassword.getText().toString(), root);
                }
                Log.v("RAMON", "click");
            }
        });

        return root;
    }

    public interface OnSigninClickListener {
        void onSigninSubmit(String username, String password, View currentView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnSigninClickListener = (OnSigninClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnSigninClickListener = null;
    }
}
