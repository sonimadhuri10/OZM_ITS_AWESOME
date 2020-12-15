package com.example.ozmapp.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.CashbackActivity;
import com.example.ozmapp.adapter.Cashback_adapter;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.LoginModel;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.model.cashapp_model;
import com.example.ozmapp.model.cashback_model;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

public class cashback_fragemnt extends Fragment implements View.OnClickListener {

    Spinner spinner ;
    TextView tvHeading , etDate ;
    Button btncashbackapply ;
    EditText etAmount ;
    ImageView imgBill ;
    SessionManagment sd;
    ConnectionDetector cd;
    ProgressDialog pd ;
    APIInterface apiInterface ;
    ArrayList<String> al ;
    DatePickerDialog picker;
    private int PICK_IMAGE_REQUEST = 1;
    String baseimg = "",date="",amount="",application="" ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cashback_layout, container, false);

        al = new ArrayList<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(getActivity());
        sd = new SessionManagment(getActivity());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        tvHeading = (TextView)v.findViewById(R.id.tvHeading);
        spinner = (Spinner)v.findViewById(R.id.spApp);
        etAmount = (EditText) v.findViewById(R.id.etAmount);
        etDate = (TextView) v.findViewById(R.id.etDate);
        imgBill=(ImageView)v.findViewById(R.id.imgBill);
        btncashbackapply=(Button) v.findViewById(R.id.btncashbackapply);

        getCashapp();

        etDate.setOnClickListener(this);
        imgBill.setOnClickListener(this);
        btncashbackapply.setOnClickListener(this);

        return v;
    }



    public void getCashapp() {
        try {
            JsonObject object=new JsonObject();
            Call<ArrayList<cashapp_model>> call = apiInterface.getcashapp(object);
            call.enqueue(new Callback<ArrayList<cashapp_model>>() {
                @Override
                public void onResponse(Call<ArrayList<cashapp_model>> call, retrofit2.Response<ArrayList<cashapp_model>> response) {
                    ArrayList<cashapp_model> resource = response.body();
                    tvHeading.setText(resource.get(0).cashback_message);
                    al.addAll(resource.get(0).app_list) ;

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                }
                @Override
                public void onFailure(Call<ArrayList<cashapp_model>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etDate:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.imgBill:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE_REQUEST);
                break;
            case R.id.btncashbackapply:

                amount=etAmount.getText().toString();
                date=etDate.getText().toString();
                application = spinner.getSelectedItem().toString();

                if(application.equals("")){
                    Toast.makeText(getActivity(),"Please select application",Toast.LENGTH_LONG).show();
                }else if(amount.equals("")){
                    Toast.makeText(getActivity(),"Please enter amount",Toast.LENGTH_LONG).show();
                }else if(date.equals("")){
                    Toast.makeText(getActivity(),"Please enter order date",Toast.LENGTH_LONG).show();
                }else if(baseimg.equals("")){
                    Toast.makeText(getActivity(),"Please upload your bill",Toast.LENGTH_LONG).show();
                }else{
                cash(application,amount,date);
                }

                break;
            default:

                break;
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imgBill.setImageBitmap(bitmap);
                baseimg = encodeImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cash(String app, String amount , String dat) {
        try {
            pd.show();

            JsonObject object=new JsonObject();
            object.addProperty("application",app);
            object.addProperty("amount",amount);
            object.addProperty("orderDate",dat);
            object.addProperty("approved","pending");
            object.addProperty("phoneNumber",sd.getMobile());
            object.addProperty("displayName",sd.getNAME());
            object.addProperty("image",baseimg);
            object.addProperty("uid",sd.getUSER_ID());

            Call<SignupModel> call = apiInterface.applycashback(object);
            call.enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                    SignupModel resource = response.body();
                    pd.dismiss();
                    if (resource.status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Successfully sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "try after sometimes", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<SignupModel> call, Throwable t) {
                    pd.dismiss();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

}
