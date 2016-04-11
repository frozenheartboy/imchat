package com.lzs.imchat.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzs.imchat.ImApplication;
import com.lzs.imchat.bean.List_item;
import com.lzs.imchat.service.MsfService;
import com.lzs.imchat.util.Const;
import com.lzs.imchat.util.ToastUtil;
import com.lzs.imchat.view.TitleBarView;
import com.lzs.imchat.R;
import com.lzs.imchat.adapter.ConstactAdapter;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ContactFragment extends Fragment {
    private Context context;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private ListView listView = null;
    private List<List_item> list;
    private FriendsOnlineStatusReceiver friendsOnlineStatusReceiver;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initData();
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        friendsOnlineStatusReceiver=new FriendsOnlineStatusReceiver();
        IntentFilter intentFilter=new IntentFilter(Const.ACTION_FRIENDS_ONLINE_STATUS_CHANGE);
        context.registerReceiver(friendsOnlineStatusReceiver, intentFilter);
        mBaseView = inflater.inflate(R.layout.fragment_constact_father, container, false);
        findView();
        return mBaseView;

    }

    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
        mTitleBarView.setTitleText(R.string.constacts);//标题
        mTitleBarView.setTitleLeft("刷新");//左按钮-刷新好友
        mTitleBarView.setTitleRight("添加");//右按钮-添加好友
        mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                initData();
            }
        });
        listView = (ListView) mBaseView.findViewById(R.id.list);
        if(ImApplication.xmppConnection==null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                }
            }, 1000);
        }else{
            initData();
        }
    }

    void initData() {
        if (ImApplication.xmppConnection != null) {
            getData();
            listView.setAdapter(new ConstactAdapter(context, list));
        }
    }

    public void getData() {
        XMPPConnection conn = ImApplication.xmppConnection;
        list = new ArrayList<List_item>();//在线
        List<List_item> ListNotOnline = new ArrayList<List_item>();//不在线
        Roster roster = conn.getRoster();
        Collection<RosterEntry> rosterEntiry = roster.getEntries();
        for (RosterEntry entry : rosterEntiry) {
            Presence presence = roster.getPresence(entry.getUser());
            List_item listitem = new List_item();
            listitem.setUsername(entry.getName());
            if (!TextUtils.isEmpty(presence.getStatus())) {
                listitem.setMood(presence.getStatus());
            } else {
                listitem.setMood("离线");
            }
            if (presence.isAvailable()) {//在线
                listitem.setOnline_status("1");
                list.add(listitem);
            } else {//下线
                listitem.setOnline_status("0");
                ListNotOnline.add(listitem);
            }
        }
        list.addAll(ListNotOnline);

    }
    class FriendsOnlineStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            String from = intent.getStringExtra("from");
            int status = intent.getIntExtra("status", 0);
            if (!TextUtils.isEmpty(from)) {
                if (status == 1) {
                    ToastUtil.showShortToast(context, from + "上线了");
                } else if (status == 0) {
                    ToastUtil.showShortToast(context, from + "下线了");
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                }
            }, 1000);
        }

    }

}
