package com.example.ozmapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ozmapp.R;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.LoginModel;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    static EditText et1, et2, et3, et4, et5;
    ImageView imgOtp;
    static String e1 = "", e2 = "", e3 = "", e4 = "", e5 = "", finalstring = "",
            msg = "", one = "", two = "", three = "", four = "", five = "",email="";
    static ProgressDialog pd;
    ConnectionDetector cd;
    static APIInterface apiInterface;
    SessionManagment sd;
    LinearLayout llotp , llReset ;
    String otp="",id="";
    Button btnReset ;
    String password = "",confirmpassword = "" ,token = "";
    EditText etpassword , etConfirmPassword;
    Animation slideUp ;
    TextView tvHeading , tvResend ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_layout);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);

        imgOtp = (ImageView) findViewById(R.id.imgMove);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        tvResend = (TextView) findViewById(R.id.tvResend);
        etpassword=(EditText)findViewById(R.id.etNewPassword);
        etConfirmPassword=(EditText)findViewById(R.id.etConfirmpassword);
        btnReset=(Button)findViewById(R.id.btnResetpassword);
        llotp=(LinearLayout)findViewById(R.id.llotp);
        llReset=(LinearLayout)findViewById(R.id.llReset);

        sd = new SessionManagment(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        slideUp =    AnimationUtils.loadAnimation(this, R.anim.slide_up_dialog);

        tvHeading.setText("Please Enter OTP");

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
        }

        et1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et1.getText().toString().length() == 1) {
                    et2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et2.getText().toString().length() == 1) {
                    et3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et3.getText().toString().length() == 1) {
                    et4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et4.getText().toString().length() == 1) {
                    et5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et5.getText().toString().length() == 1) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        imgOtp.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        tvResend.setOnClickListener(this);

        Intent in = getIntent();
        otp =   in.getStringExtra("otp");
        id =   in.getStringExtra("id");
        email =   in.getStringExtra("email");

    }

    public static void recivedSms(String message) {

        msg = message.substring(49, 55);
        one = msg.substring(0, 1);
        two = msg.substring(1, 2);
        three = msg.substring(2, 3);
        four = msg.substring(3, 4);
        five = msg.substring(4, 5);

        et1.setText(one);
        et2.setText(two);
        et3.setText(three);
        et4.setText(four);
        et5.setText(five);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgMove:
                e1 = et1.getText().toString().trim();
                e2 = et2.getText().toString().trim();
                e3 = et3.getText().toString().trim();
                e4 = et4.getText().toString().trim();
                e5 = et5.getText().toString().trim();

                finalstring = e1 + e2 + e3 + e4 + e5;
                if (e1.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Fill Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (e2.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Fill Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (e3.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Fill Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (e4.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Fill Complete OTP", Toast.LENGTH_SHORT).show();
                } else if (e5.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Fill Complete OTP", Toast.LENGTH_SHORT).show();
                } else {
                    if(finalstring.equals(otp)){
                        llotp.setVisibility(View.GONE);
                        llReset.startAnimation(slideUp);
                        llReset.setVisibility(View.VISIBLE);
                        tvHeading.setText("Reset Your Password");
                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, "Your otp Does Not Match", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnResetpassword:

                password = etpassword.getText().toString().trim();
                confirmpassword =  etConfirmPassword.getText().toString().trim() ;

                if(password.equals("")){
                    etpassword.setError("Please Enter New Password");
                }else if(confirmpassword.equals("")){
                    etConfirmPassword.setError("Please Enter Confirm Password");
                }else if(!password.equals(confirmpassword)){
                    etConfirmPassword.setError("Your Password Does Not Match");
                }else{
                    verifyNumber(id,password);
                }

                break;
            case R.id.tvResend:
                ResendOtp(email);
                break;
            default:

                break;
        }
    }


    public void verifyNumber(final String id , String password) {
        try{
            pd.show();

            JsonObject object=new JsonObject();
            object.addProperty("_id",id);
            object.addProperty("password",password);

            Call<SignupModel> call = apiInterface.resetPassword(object);
            call.enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                    SignupModel resource = response.body();
                    pd.dismiss();
                    if (resource.result.equals("success fully change password")) {
                        Toast.makeText(ForgotPasswordActivity.this, "Your Password Has Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        startActivity(in);
                        finish();
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, "Sorry , This No Is Not Register With Us", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<SignupModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }


    }

    public void ResendOtp(final String mobile) {
        try{
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("email",mobile);

            Call<LoginModel> call = apiInterface.getForgot(object);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                    LoginModel resource = response.body();
                    pd.dismiss();
                    if(resource.status.equals("1")){
                        Toast.makeText(ForgotPasswordActivity.this, "Successfully sent otp", Toast.LENGTH_SHORT).show();
                        otp =   resource.mainlist.get(0).otp ;

                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, "Email or mobile no is not registered", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }



}

