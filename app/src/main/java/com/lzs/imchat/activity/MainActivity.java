package com.lzs.imchat.activity;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lzs.imchat.R;
import com.lzs.imchat.fragment.ContactFragment;
import com.lzs.imchat.fragment.NewsFragment;
import com.lzs.imchat.fragment.SettingFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton button_News,button_Constact,button_Setting;
    private NewsFragment fragment_News;
    private ContactFragment fragment_Contact;
    private SettingFragment fragment_Setting;
    private View currentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        init();
    }


    public void findView(){
        fragment_News= (NewsFragment) getFragmentManager().findFragmentById(R.id.fragment_news);
        fragment_Contact= (ContactFragment) getFragmentManager().findFragmentById(R.id.fragment_constact);
        fragment_Setting= (SettingFragment) getFragmentManager().findFragmentById(R.id.fragment_setting);

        button_News= (ImageButton) findViewById(R.id.buttom_news);
        button_Constact= (ImageButton) findViewById(R.id.buttom_constact);
        button_Setting= (ImageButton) findViewById(R.id.buttom_setting);

    }
    private void init() {
        button_News.setOnClickListener(this);
        button_Constact.setOnClickListener(this);
        button_Setting.setOnClickListener(this);
        button_News.performClick();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()){
            case R.id.buttom_news:
                transaction.hide(fragment_Contact);
                transaction.hide(fragment_Setting);
                transaction.show(fragment_News);
                break;
            case R.id.buttom_constact:
                transaction.hide(fragment_News);
                transaction.hide(fragment_Setting);
                transaction.show(fragment_Contact);
                break;
            case R.id.buttom_setting:
                transaction.show(fragment_Setting);
                transaction.hide(fragment_News);
                transaction.hide(fragment_Contact);
                break;
        }
        transaction.commit();
        setButton(v);
    }
    private void setButton(View v){
        if(currentButton!=null&&currentButton.getId()!=v.getId()){
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton=v;
    }
}
