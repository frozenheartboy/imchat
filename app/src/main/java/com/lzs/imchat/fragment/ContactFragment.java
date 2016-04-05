package com.lzs.imchat.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzs.imchat.service.MsfService;
import com.lzs.imchat.view.TitleBarView;
import com.lzs.imchat.R;
import com.lzs.imchat.adapter.ConstactAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2016/3/17.
 */
public class ContactFragment extends Fragment {
    private Context context;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private ListView listView=null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();
        mBaseView=inflater.inflate(R.layout.fragment_constact_father, container,false);
        listView=(ListView) mBaseView.findViewById(R.id.list);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new ConstactAdapter(context, list));
        return mBaseView;

    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
        for (int i = 0; i <10 ; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.mipmap.ic_launcher);
            map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }

}
