package com.example.mnet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mnet.databinding.FragmentFirstBinding;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.concurrent.Executor;

public class FirstFragment extends Fragment implements MHandler{

    private FragmentFirstBinding binding;
    private SharedPreferences mpref;
    private LinkedList ml;

    @Override
    public void fArr(String js) {
       // System.out.println("hlo");
       // System.out.println(js);

        mpref.edit().putString("json",js).commit();
        JSONObject mj= null;
        try {
            mj = new JSONObject(js);

        JSONArray getArray = mj.getJSONObject("networks").getJSONArray("applicable");

        ml = new LinkedList<String>();

        for(int i = 0; i < getArray.length(); i++)
        {
            JSONObject objects = getArray.getJSONObject(i);
            ml.add(objects.getString("code"));
        }

        }catch(Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentFirstBinding.inflate(inflater, container, false);
        mpref= getActivity().getSharedPreferences("mp",Context.MODE_PRIVATE);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Downloading json....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        Executor executor = new DirectExecutor();
        executor.execute(new JDownload(getActivity(),progressDialog,this));






        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ml.isEmpty()){
                    TextInputLayout mt = binding.getRoot().findViewById(R.id.oidis);
                    String ms = mt.getEditText().getText().toString();
                    if (ml.contains(ms.toUpperCase())){
                     mpref.edit().putString("scode",ms.toUpperCase()).commit();
                  //   System.out.println("hello"+mpref.getString("scode",""));
                     NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                    }
                }}
        });



    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}