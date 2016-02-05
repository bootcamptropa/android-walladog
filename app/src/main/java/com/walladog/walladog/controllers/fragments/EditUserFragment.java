package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.models.UserData;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDUsersService;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EditUserFragment extends Fragment {

    private final static String TAG = EditUserFragment.class.getName();

    private static final String ARG_USERDATA = "ARG_USERDATA";
    private UserData mUserdata;

    AutoCompleteTextView mUsername,mFirstname,mLastname,mPassword,mPassword2,mAvatarUrl;
    TextView mUsernameTitle;
    Button mBtnSave;
    CircularImageView mAvatar;

    public EditUserFragment() {

    }

    public static EditUserFragment newInstance(UserData userData) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERDATA, (Serializable) userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserdata = (UserData) getArguments().getSerializable(ARG_USERDATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_user, container, false);

        mUsername = (AutoCompleteTextView) v.findViewById(R.id.edit_user_username);
        mFirstname = (AutoCompleteTextView) v.findViewById(R.id.edit_user_firstname);
        mLastname = (AutoCompleteTextView) v.findViewById(R.id.edit_user_lastname);
        mPassword = (AutoCompleteTextView) v.findViewById(R.id.edit_user_password);
        mPassword2 = (AutoCompleteTextView) v.findViewById(R.id.edit_user_password2);
        mAvatarUrl = (AutoCompleteTextView) v.findViewById(R.id.edit_user_avatarurl);
        mBtnSave = (Button) v.findViewById(R.id.btn_update_user);
        mAvatar = (CircularImageView) v.findViewById(R.id.useredit_avatar);
        mUsernameTitle = (TextView) v.findViewById(R.id.edit_user_titleusername);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFrom()){
                    try {
                        saveUserData(mUserdata);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        loadDataModel();

        return v;
    }

    public void loadDataModel(){
        if(mUserdata!=null) {
            mUsername.setText(mUserdata.getUsername());
            mFirstname.setText(mUserdata.getFirst_name());
            mLastname.setText(mUserdata.getLast_name());
            mAvatarUrl.setText(mUserdata.getAvatar_url());
            mUsernameTitle.setText(mUserdata.getUsername());
            Picasso.with(getContext()).load(mUserdata.getAvatar_url()).into(mAvatar);
        }
    }

    private Boolean validateFrom(){
        if(mUsername.length()<3 || mUsername.length()==0){
            snakeMsg("Nombre demasiado corto o vacío");
            return false;
        }

        mUserdata.setFirst_name(mFirstname.getText().toString());
        mUserdata.setLast_name(mLastname.getText().toString());

        return true;
    }

    private void saveUserData(UserData data) throws UnsupportedEncodingException {
        ServiceGeneratorOAuth.createService(WDUsersService.class).updateUser(String.valueOf(mUserdata.getId()),data).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Response<UserData> response, Retrofit retrofit) {
                Log.v(TAG, "Usuario actualizado con éxito");
                snakeMsg("Datos actualizados!");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v(TAG,"Error al actualizar");
                snakeMsg("Error al actualizar!");
            }
        });
    }

    private void snakeMsg(String s){
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
    }
}
