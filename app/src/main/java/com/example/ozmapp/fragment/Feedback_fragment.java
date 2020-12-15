package com.example.ozmapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.LoginActivity;
import com.example.ozmapp.activity.SignupActivity;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.LoginModel;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class Feedback_fragment extends Fragment implements View.OnClickListener {

    RadioGroup radioGroup ;
    RadioButton rdOffer , rdRequest , rdother ;
    EditText etImprove ; ;
    Button btnSubmit ;
    String feedback_type  = "", improve="";
    APIInterface apiInterface ;
    ProgressDialog pd ;
    SessionManagment sd;
    ConnectionDetector cd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feedback_layout, container, false);

        radioGroup =(RadioGroup)v.findViewById(R.id.radiogroup);
        rdOffer =(RadioButton) v.findViewById(R.id.rdOffer);
        rdRequest =(RadioButton)v.findViewById(R.id.rdRequestapp);
        rdother =(RadioButton)v.findViewById(R.id.rdOther);
        btnSubmit=(Button)v.findViewById(R.id.btnfeedback);
        etImprove=(EditText) v.findViewById(R.id.etImprove);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        cd = new ConnectionDetector(getActivity());
        sd = new SessionManagment(getActivity());

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(), "Please check your internet connection...", Toast.LENGTH_SHORT).show();
        }

        rdOffer.setOnClickListener(this);
        rdRequest.setOnClickListener(this);
        rdother.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

     /*   radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rdOffer) {

                } else if(checkedId == R.id.rdRequestapp){

                }else if(checkedId == R.id.rdOther) {

                }else{

                }
           }

        });
*/

        return  v;
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btnfeedback:


              improve = etImprove.getText().toString();
              int selectedId = radioGroup.getCheckedRadioButtonId();

              if(selectedId == rdOffer.getId()) {
                  feedback_type = "Offer Feedback";
              } else if(selectedId == rdRequest.getId()) {
                  feedback_type = "Request an App";
              }else if(selectedId == rdother.getId()) {
                  feedback_type = "Other Feedback";
              }else{
                  feedback_type = "Offer Feedback";
              }


              if(feedback_type.equals("")) {
                  Toast.makeText(getActivity(),"Please Select Feedback Type",Toast.LENGTH_LONG).show();
              }else if(improve.equals("")){
                  Toast.makeText(getActivity(),"Please Enter Required Field",Toast.LENGTH_LONG).show();
              }else{
                  feedback(feedback_type,improve);
              }

              break;
          default:

              break;
      }
    }

    public void feedback(String feedback, String comment) {
        try {
            pd.show();
            Date currentTime;
            currentTime = Calendar.getInstance().getTime();

            JsonObject object=new JsonObject();
            object.addProperty("feedbackType",feedback);
            object.addProperty("reviewComment",comment);
            object.addProperty("userName",sd.getNAME());
            object.addProperty("phoneNumber",sd.getMobile());
            object.addProperty("submitTimeStamp",currentTime.toString());
            object.addProperty("uid",sd.getUSER_ID());


            Call<LoginModel> call = apiInterface.getFeedback(object);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                    LoginModel resource = response.body();
                    pd.dismiss();
                    if (resource.status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Your feedback has successfully submitted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "try after sometimes", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    pd.dismiss();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

}
