package com.lzs.imchat.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzs.imchat.R;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment{
	private Context Message_Context;
	private View mBaseView;
	private String userid;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Message_Context=getActivity();
		return inflater.inflate(R.layout.fragment_newsmessage,container,false);
	}
}
