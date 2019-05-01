package com.example.twitter3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimelineFragment extends Fragment { //Extender a clase Fragment

    //Variables
    View view;
    //Construtor Vacio
    public TimelineFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.timeline_fragment,container,false);//asociamos el fragment con su respectiva vista
        return view;
    }
}
