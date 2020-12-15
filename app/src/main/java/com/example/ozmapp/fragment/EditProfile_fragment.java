package com.example.ozmapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.CashbackActivity;
import com.example.ozmapp.comman.SessionManagment;

public class EditProfile_fragment extends Fragment {

    EditText etName , etMobile , etEmail ;
    TextView tvCashback ;
    SessionManagment sd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_profile, container, false);

        sd = new SessionManagment(getActivity());

        etEmail = (EditText)v.findViewById(R.id.etEmail);
        etName = (EditText)v.findViewById(R.id.etName);
        etMobile = (EditText)v.findViewById(R.id.etMobile);
        tvCashback =(TextView)v.findViewById(R.id.tvCashback);

        etEmail.setText(sd.getEMAIL());
        etName.setText(sd.getNAME());
        etMobile.setText(sd.getMobile());

        tvCashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), CashbackActivity.class);
                startActivity(in);
            }
        });


        return  v ;
    }
}
