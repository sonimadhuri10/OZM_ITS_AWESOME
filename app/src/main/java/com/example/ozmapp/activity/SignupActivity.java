package com.example.ozmapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ozmapp.R;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;

public class SignupActivity extends
        AppCompatActivity implements View.OnClickListener {

    LinearLayout llLogin;
    EditText etName, etMobile, etEmail, etPassword, etConfirmPassworsd;
    Button btnSignup;
    String name = "", email = "", mobile = "", password = "",
            confirmpassword = "", device_id;
    SessionManagment sd;
    ConnectionDetector cd;
    ProgressDialog pd;
    APIInterface apiInterface ;
    String EmialPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+com+";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        llLogin = (LinearLayout) findViewById(R.id.llLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        etName = (EditText) findViewById(R.id.etName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etpassword);
        etConfirmPassworsd = (EditText) findViewById(R.id.etConfirmpassword);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait....");

        llLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(SignupActivity.this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llLogin:
                Intent in = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
                break;
            case R.id.btnSignup:
                name = etName.getText().toString().trim();
                mobile = etMobile.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmpassword = etConfirmPassworsd.getText().toString().trim();

                if (name.equals("")) {
                    etName.setError("Please Enter Name");
                } else if (mobile.equals("")) {
                    etMobile.setError("Please Enter Mobile No");
                } else if (mobile.length() != 10) {
                    etMobile.setError("Please Enter Valid Mobile No");
                } else if (email.equals("")) {
                    etEmail.setError("Please Enter email address");
                } else if (!email.matches(EmialPattern)) {
                    etEmail.setError("Please Enter valid Email Address");
                } else if (password.equals("")) {
                    etPassword.setError("Please Enter Password");
                } else if (confirmpassword.equals("")) {
                    etConfirmPassworsd.setError("Please Enter Confirm Password");
                } else if (!password.equals(confirmpassword)) {
                    etConfirmPassworsd.setError("Your Password Does Not Match");
                } else {
                    signin(name, mobile, email, password, "user");
                }

                break;
            default:

                break;
        }
    }

    public void signin(String name, String phone, String ema, String pass,
                       String usertype) {
        try {
            JsonObject object=new JsonObject();
            object.addProperty("name",name);
            object.addProperty("phone",phone);
            object.addProperty("email",ema);
            object.addProperty("pass",pass);
            object.addProperty("usertype","user");

            pd.show();
            Call<SignupModel> call = apiInterface.getRegister(object);
            call.enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                    SignupModel resource = response.body();
                    pd.dismiss();
                    if (resource.result.equalsIgnoreCase("Success fully signup")) {
                        Toast.makeText(SignupActivity.this, "Signin Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                       } else if (resource.result.equalsIgnoreCase("user all ready resister")) {
                        Toast.makeText(SignupActivity.this, "Your mobile No or email already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<SignupModel> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(SignupActivity.this, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

}









