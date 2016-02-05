package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

    @Bind(R.id.reg_email)
    TextView userEmail;

    @Bind(R.id.reg_password)
    TextView userPassword;

    @Bind(R.id.reg_password2)
    TextView userPassword2;

    @Bind(R.id.reg_button)
    Button btnLogin;

    @Bind(R.id.register_username)
    TextView userName;

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
                if (mOnSigninClickListener != null && validateForm()) {
                    mOnSigninClickListener.onSigninSubmit(userEmail.getText().toString(), userPassword.getText().toString(), root,userEmail.getText().toString());
                }
            }
        });

        return root;
    }

    public interface OnSigninClickListener {
        void onSigninSubmit(String username, String password, View currentView, String email);
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

    private Boolean validateForm(){
        if(userEmail.getText().length()<4 || userEmail.getText().length()==0){
            snakeMsg("Error en el nombre de usuario");
            return false;
        }
        /*if((userPassword.getText().toString()!=userPassword2.getText().toString()) || userPassword.toString().length()<3){
            snakeMsg("Password erroneo");
            return false;
        }*/
        if(userName.length()<3){
            snakeMsg("Nombre de usuario muy corto");
            return false;
        }

        return true;
    }

    private void snakeMsg(String s){
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
    }
}
