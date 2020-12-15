package com.example.ozmapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etpassword ,etMobile,etForgotNo;
    TextView tvForgot ;
    String email ="",password="",mobile="",forgotNo ="";
    Button btnlogin , btnLoginViaOtp, btnGetOtp , btnVerify;
    LinearLayout llSignup , llLoginNormal ,llLoginOtp ,llForgot ;
    SessionManagment sd;
    ConnectionDetector cd;
    APIInterface apiInterface;
    ProgressDialog pd ;
    String EmialPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+com+";
    Animation slideUp , fadein;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        etEmail=(EditText)findViewById(R.id.etEmail);
        etpassword=(EditText)findViewById(R.id.etPassword);
        etMobile=(EditText)findViewById(R.id.etMobile);
        etForgotNo=(EditText)findViewById(R.id.etforgotMobile);
        btnlogin=(Button)findViewById(R.id.btnLogin);
        btnLoginViaOtp=(Button)findViewById(R.id.btnLoginViaOTp);
        btnGetOtp=(Button)findViewById(R.id.btnGetOtp);
        btnVerify=(Button)findViewById(R.id.btnVerifyNo);
        llSignup=(LinearLayout)findViewById(R.id.llSignup);
        llLoginNormal=(LinearLayout)findViewById(R.id.llLoginNormal);
        llLoginOtp=(LinearLayout)findViewById(R.id.llLoginOtp);
        llForgot=(LinearLayout)findViewById(R.id.llForgot);
        tvForgot=(TextView)findViewById(R.id.tvForgotPassword);

        sd = new SessionManagment(this);
        cd = new ConnectionDetector(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        slideUp =    AnimationUtils.loadAnimation(this, R.anim.slide_up_dialog);
        fadein =    AnimationUtils.loadAnimation(this, R.anim.fadein);
        permission();

        llSignup.setOnClickListener(this);
        llSignup.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
        btnLoginViaOtp.setOnClickListener(this);
        btnGetOtp.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        tvForgot.setOnClickListener(this);

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(LoginActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llSignup:
                Intent in = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case R.id.btnLogin:
                email=etEmail.getText().toString().trim();
                password = etpassword.getText().toString().trim();

                if (email.equals("")) {
                    etEmail.setError("Please Enter Email Address or Mobile No");
                } else if(email.matches("[a-zA-Z]+")){
                    etEmail.setError("Please Enter valid Email Address or Mobile No");
                }else if (email.contains("@") && !email.matches(EmialPattern)) {
                    etEmail.setError("Please Enter Valid Email Address");
                } else if(email.matches("[0-9]+") && email.length()!=10){
                    etEmail.setError("Please Enter Valid Mobile No");
                }else if(password.equals("")){
                    etpassword.setError("Please Enter Password");
                }else{
                    login(email,password);
                }
                break;
            case R.id.btnLoginViaOTp:

                llLoginNormal.setVisibility(View.GONE);
                llLoginOtp.startAnimation(slideUp);
                llLoginOtp.setVisibility(View.VISIBLE);

                break;
            case R.id.btnGetOtp:
                mobile=etMobile.getText().toString().trim();

                if (mobile.equals("")) {
                    etMobile.setError("Please Enter Mobile No");
                } else if(mobile.length()!= 10) {
                    etMobile.setError("Please Enter 10 digit Mobile No");
                }else{
                   // otp(mobile);
                }
                break;
            case R.id.tvForgotPassword:
                llLoginNormal.setVisibility(View.GONE);
                llForgot.startAnimation(slideUp);
                llForgot.setVisibility(View.VISIBLE);
                break;
            case R.id.btnVerifyNo:
                forgotNo = etForgotNo.getText().toString().trim();
                if (forgotNo.equals("")) {
                    etForgotNo.setError("Please Enter Email Address or Mobile No");
                } else if(forgotNo.matches("[a-zA-Z]+")){
                    etForgotNo.setError("Please Enter valid Email Address or Mobile No");
                }else if (forgotNo.contains("@") && !forgotNo.matches(EmialPattern)) {
                    etForgotNo.setError("Please Enter Valid Email Address");
                } else if(forgotNo.matches("[0-9]+") && forgotNo.length()!=10      ){
                    etForgotNo.setError("Please Enter Valid Mobile No");
                }else{

                    verifyNumber(forgotNo);
                }
                break;
            default:
                break;
        }
    }

/*
    public void otp(final String mobile) {
        try{
            pd.show();
            Call<SignupModel> call = apiInterface.getOtpVerify(mobile,sd.getFCM());
            call.enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                    SignupModel resource = response.body();
                    pd.dismiss();
                    if (resource.success.equals("Send otp!")) {
                        Toast.makeText(LoginActivity.this, "Send Otp Successfully", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(LoginActivity.this, OtpActivity.class);
                        in.putExtra("mobile",mobile);
                        in.putExtra("otp",resource.otp);
                        in.putExtra("userid",resource.userdata.get(0).id);
                        in.putExtra("apikey",resource.userdata.get(0).api_token);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                        finish();
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    }else{
                        Toast.makeText(LoginActivity.this, "Sorry , This No Is Not Register With Us", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<SignupModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }


    }
*/

    public void login(String email, String password) {
        try{
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("phone",email);
            object.addProperty("pass",password);

            Call<LoginModel> call = apiInterface.getLogin(object);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                    LoginModel resource = response.body();
                    pd.dismiss();

                    if (resource.status.equalsIgnoreCase("1")) {
                        Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        sd.setLOGIN_STATUS("true");
                        sd.setUSER_ID(resource.mainlist.get(0)._id);
                        sd.setSETNAME(resource.mainlist.get(0).name);
                        sd.setSETEMAIL(resource.mainlist.get(0).email);
                        sd.setMobile(resource.mainlist.get(0).phone);

                        Intent in1 = new Intent(LoginActivity.this,DrawerActivity.class);
                        startActivity(in1);
                        finish();
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);

                    } else if (resource.status.equalsIgnoreCase("2")) {
                        Toast.makeText(LoginActivity.this, "Wrong Email Address & Password", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    public void verifyNumber(final String mobile) {
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
                        Intent in1 = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                        in1.putExtra("otp",resource.mainlist.get(0).otp);
                        in1.putExtra("id",resource.mainlist.get(0)._id);
                        in1.putExtra("email",resource.mainlist.get(0).email);
                        in1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in1);
                        finish();
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    }else{
                        Toast.makeText(LoginActivity.this, "email or mobile no is not registered", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }


    public void permission() {
        if (Build.VERSION.SDK_INT < 23) {
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                        },
                        3);
            } else {
            }
        }
    }

}
