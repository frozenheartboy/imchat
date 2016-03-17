package com.lzs.imchat.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzs.imchat.R;

/**
 * Created by L on 2016/3/17.
 */
public class ContactFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact,container,false);
    }
}
