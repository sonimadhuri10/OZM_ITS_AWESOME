package com.example.ozmapp.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_profile, container, false);
        sd = new SessionManagment(getActivity());

        etEmail = (EditText)v.findViewById(R.id.etEmail);
        etName = (EditText)v.findViewById(R.id.etName);
        etMobile = (EditText)v.findViewById(R.id.etMobile);
        tvCashback =(TextView)v.findViewById(R.id.tvCashback);
        etMobile.setClickable(false);
        etMobile.setEnabled(false);

        etEmail.setText(sd.getEMAIL());
        etName.setText(sd.getNAME());
        etMobile.setText(sd.getMobile());

        tvCashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), CashbackActivity.class);
                startActivity(in);
                /*
                String text = "This is a test";
                String toNumber = "918962582883";
                // Replace with mobile phone number without +Sign or leading zeros, but with country code
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                startActivity(intent);
                */
            }
        });

        return  v ;
    }
}
