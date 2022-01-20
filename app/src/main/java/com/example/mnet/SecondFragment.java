package com.example.mnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mnet.databinding.FragmentSecondBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private SharedPreferences mpref;
    private LinkedList<String> ml;
    private JSONObject pm;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        mpref = getActivity().getSharedPreferences("mp", Context.MODE_PRIVATE);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readJ();

        LinearLayout mll = new LinearLayout(this.getContext());
        mll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mll.setOrientation(LinearLayout.VERTICAL);
        mll.setId(R.id.mll);

        for(String el: ml){


            TextInputLayout textInputLayout =  new TextInputLayout(new ContextThemeWrapper(getContext(), R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
            textInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
           // textInputLayout.setHintTextAppearance(R.style.Base_Widget_MaterialComponents_TextInputLayout_TextInputLayout);
            textInputLayout.setBoxBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
            textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
//Must use context of textInputLayout
            TextInputEditText editText = new TextInputEditText(textInputLayout.getContext());
            editText.setHint(el);
            textInputLayout.addView(editText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            mll.addView(textInputLayout);
        }

        binding.getRoot().addView(mll);






        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> mm = new LinkedHashMap<>();
                LinearLayout mll = binding.getRoot().findViewById(R.id.mll);
                for (int i = 0; i < mll.getChildCount(); i++) {
                    TextInputLayout v = (TextInputLayout) mll.getChildAt(i);
                    mm.put(ml.get(i), v.getEditText().getText().toString());
                }

                InfoCard mc = new InfoCard(mm,pm);
                //NavHostFragment.findNavController(SecondFragment.this)
                //        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    public void readJ(){
        JSONObject mj= null;
        try {
            mj = new JSONObject(mpref.getString("json",""));

            JSONArray getArray = mj.getJSONObject("networks").getJSONArray("applicable");

            ml = new LinkedList<String>();

            for(int i = 0; i < getArray.length(); i++)
            {
                JSONObject objects = getArray.getJSONObject(i);
                if(objects.getString("code").equals(mpref.getString("scode",""))) {
                    pm = objects;
                    JSONArray el = objects.getJSONArray("inputElements");
                    for(int j = 0; j < el.length(); j++) {
                        JSONObject me = el.getJSONObject(j);
                        ml.add(me.getString("name"));
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }




    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}