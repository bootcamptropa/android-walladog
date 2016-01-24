package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.walladog.walladog.models.apiservices.AccessToken;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by hadock on 19/12/15.
 *
 */

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getName();

    @Bind(R.id.email)
    TextView userEmail;

    @Bind(R.id.password)
    TextView userPassword;

    @Bind(R.id.email_sign_in_button)
    Button btnLogin;

    private OnLoginClickListener mOnLoginClickListener=null;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View root = inflater.inflate(R.layout.fragment_login,container,false);
        ButterKnife.bind(this, root);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnLoginClickListener != null) {
                    mOnLoginClickListener.onLoginSubmit(userEmail.getText().toString(), userPassword.getText().toString(),root);
                }
                Log.v("RAMON", "click");
            }
        });

        return root;
    }

    public interface OnLoginClickListener {
        void onLoginSubmit(String username, String password,View currentView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnLoginClickListener = (OnLoginClickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginClickListener = null;
    }
}
