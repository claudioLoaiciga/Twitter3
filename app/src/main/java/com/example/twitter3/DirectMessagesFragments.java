package com.example.twitter3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DirectMessagesFragments extends Fragment {
    //Variables view
    View view;
    //Constructor vacio
    public DirectMessagesFragments() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.direct_messages_fragment,container,false);//asociamos el fragment con su respectiva vista
        return view;
    }
}