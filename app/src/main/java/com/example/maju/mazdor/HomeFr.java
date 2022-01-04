package com.example.maju.mazdor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.maju.mazdor.Activites.ChatActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFr extends Fragment {


    public HomeFr() {
        // Required empty public constructor
    }

    LinearLayout PostJOb, ViewJOB;

    @Override

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container, false);

        PostJOb= (LinearLayout)view.findViewById(R.id.addpost);
        PostJOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                posjob();

            }
        });

        ViewJOB= (LinearLayout)view.findViewById(R.id.viewpost);
        ViewJOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewJob();

            }
        });



        return view;
    }

    public void posjob(){
        Intent intent = new Intent(getActivity(),Job_post.class);
        startActivity(intent);
    }
    public void viewJob(){
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
    }


}
