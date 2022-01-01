package com.example.apicalling.MainPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cssolutions.gocodesuserside.LoginModule.Model.SignUpModel;
import com.cssolutions.gocodesuserside.R;
import com.cssolutions.gocodesuserside.Utils.AcessDataDirectlyKt;
import com.cssolutions.gocodesuserside.Utils.Constants;
import com.cssolutions.gocodesuserside.Utils.InternetAvail;
import com.cssolutions.gocodesuserside.Utils.PreferenceHelper;
import com.cssolutions.gocodesuserside.Utils.WebApiClient;
import com.cssolutions.gocodesuserside.databinding.ActivitySignUpBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding mBinding;
    SignUpActivity mContext = SignUpActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_sign_up);
        onClick();
    }

    private void onClick() {
        mBinding.tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        mBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.edtUserName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter user name", Toast.LENGTH_SHORT).show();
                } else if (mBinding.edtEmail.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter email address", Toast.LENGTH_SHORT).show();
                } else if (mBinding.edtPhoneNo.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter Phone Number", Toast.LENGTH_SHORT).show();
                } else if (mBinding.edtPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (mBinding.edtConfirmPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please enter ConfirmPassword", Toast.LENGTH_SHORT).show();
                } else {
                    signUp();
                }
            }

            private void signUp() {
                if (InternetAvail.isInternetAvailable(mContext)) {
                    AcessDataDirectlyKt.showLoader(mContext);
                    WebApiClient.getInstance().SignUp(param()).enqueue(new Callback<SignUpModel>() {
                        @Override
                        public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                            AcessDataDirectlyKt.dismissLoader();
                            if (response.code() == 200) {
                                Log.d("success",response.message());
                                PreferenceHelper.putString(Constants.Name, String.valueOf(response.body().user.name));
                                PreferenceHelper.putString(Constants.Email, String.valueOf(response.body().user.email));
                                PreferenceHelper.putString(Constants.Password, String.valueOf(response.body().user.password));
                                PreferenceHelper.putString(Constants.Phone_no, String.valueOf(response.body().user.phone_no));
                                PreferenceHelper.putString(Constants.Verification_code, String.valueOf(response.body().user.verification_code));
                                Intent i = new Intent(mContext, VerificationCodesActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(mContext, "Something Went Wrong.", Toast.LENGTH_SHORT).show();
                                Log.d("unsuccess",response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<SignUpModel> call, Throwable t) {
                                AcessDataDirectlyKt.dismissLoader();
                                Toast.makeText(mContext, "" + t, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(mContext, "" + R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Map<String, String> param() {
        Map<String, String> params = new HashMap<>();
        params.put("name", mBinding.edtUserName.getText().toString());
        params.put("email", mBinding.edtEmail.getText().toString());
        params.put("password", mBinding.edtPassword.getText().toString());
        params.put("password_confirmation", mBinding.edtConfirmPassword.getText().toString());
        params.put("phone_no", mBinding.edtPhoneNo.getText().toString());
        params.put("category_id", "5");
        Log.d("param", params.toString());
        return params;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}