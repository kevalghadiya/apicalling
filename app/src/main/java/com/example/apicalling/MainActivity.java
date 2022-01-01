package com.example.apicalling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.apicalling.MainPackage.AdapterProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
MainActivity context=MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProfile();
    }

    private void setProfile() {
        //      AdapterProfileBinding mBinding;
        ArrayList<ModelClass> mData = new ArrayList<>();
        ModelClass obj = new ModelClass(binding.edtName.getText().toString(), binding.edtSurName.getText().toString(),R.drawable.ic_launcher_background);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        mData.add(obj);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvProfile.setLayoutManager(layoutManager);
        AdapterProfile adapterProfile = new AdapterProfile(mData, context);
        binding.rvProfile.setAdapter(adapterProfile);
    }

}