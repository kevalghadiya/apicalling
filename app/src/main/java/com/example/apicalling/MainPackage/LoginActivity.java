package com.example.apicalling.MainPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cssolutions.gocodesuserside.DashBoard.Activity.MainActivity;
import com.cssolutions.gocodesuserside.LoginModule.Model.LoginModel;
import com.cssolutions.gocodesuserside.R;
import com.cssolutions.gocodesuserside.Utils.AcessDataDirectlyKt;
import com.cssolutions.gocodesuserside.Utils.Constants;
import com.cssolutions.gocodesuserside.Utils.DialogUtils;
import com.cssolutions.gocodesuserside.Utils.InternetAvail;
import com.cssolutions.gocodesuserside.Utils.PreferenceHelper;
import com.cssolutions.gocodesuserside.Utils.WebApiClient;
import com.cssolutions.gocodesuserside.databinding.ActivityLoginBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements DialogUtils.AlertDialogListener {
    ActivityLoginBinding mBinding;
    LoginActivity context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(context, R.layout.activity_login);
        onClick();
    }

    private void onClick() {
        mBinding.tvSignUP.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.edtEmail.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(context, "Please enter valid Email", Toast.LENGTH_SHORT).show();
                }else if(mBinding.edtPassword.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(context, "Please enter valid Password", Toast.LENGTH_SHORT).show();
                }else
                    LogIn();
            }

            private void LogIn() {
                if (InternetAvail.isInternetAvailable(context)){
                    AcessDataDirectlyKt.showLoader(context);
                    WebApiClient.getInstance().Login(param()).enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            AcessDataDirectlyKt.dismissLoader();
                            if (response.code() ==200){
                                Toast.makeText(context, "Congratulation, You have logged in Successfully", Toast.LENGTH_LONG).show();
                                Log.d("Success",response.message());
                                PreferenceHelper.putBoolean(Constants.IS_LOGIN,true);
                                PreferenceHelper.putString(Constants.Id,String.valueOf(response.body().user.id));
                                PreferenceHelper.putString(Constants.Token,String.valueOf(response.body().token));
                                PreferenceHelper.putString(Constants.Name,String.valueOf(response.body().user.name));
                                PreferenceHelper.putString(Constants.Email,String.valueOf(response.body().user.email));
                                PreferenceHelper.putString(Constants.Password,String.valueOf(response.body().user.password));
                                PreferenceHelper.putString(Constants.Profile_Image,String.valueOf(response.body().user.profile_image));
                                PreferenceHelper.putString(Constants.Phone_no,String.valueOf(response.body().user.phone_no));
                                PreferenceHelper.putString(Constants.Address,String.valueOf(response.body().user.address));
                                PreferenceHelper.putString(Constants.Is_Verified,String.valueOf(response.body().user.is_verified));
                                PreferenceHelper.putString(Constants.Verification_code,String.valueOf(response.body().user.verification_code));
                                PreferenceHelper.putString(Constants.Latitude,String.valueOf(response.body().user.latitude));
                                PreferenceHelper.putString(Constants.Longitude,String.valueOf(response.body().user.longitude));

                                startActivity(new Intent(context, MainActivity.class));
                                finish();

                            }else {
                                Toast.makeText(context, "Something Went Wrong.", Toast.LENGTH_SHORT).show();
                                Log.d("unsuccess",response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            AcessDataDirectlyKt.dismissLoader();
                            Toast.makeText(context, "" + t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(context, "check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Map<String, String> param() {
        Map<String, String> params = new HashMap<>();
        params.put("email", mBinding.edtEmail.getText().toString());
        params.put("password", mBinding.edtPassword.getText().toString());
        Log.d("param", params.toString());
        return params;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPositiveClick(int from) {

    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}