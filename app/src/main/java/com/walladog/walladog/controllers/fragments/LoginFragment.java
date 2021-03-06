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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.walladog.walladog.R;
import com.walladog.walladog.models.apiservices.AccessToken;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;

import java.io.UnsupportedEncodingException;

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
                    try {
                        mOnLoginClickListener.onLoginSubmit(userEmail.getText().toString(), userPassword.getText().toString(),root);
                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return root;
    }

    public interface OnLoginClickListener {
        void onLoginSubmit(String username, String password,View currentView) throws UnsupportedEncodingException;
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
