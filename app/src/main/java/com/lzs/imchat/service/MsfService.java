package com.lzs.imchat.service;



import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lzs.imchat.ImApplication;
import com.lzs.imchat.listener.FriendsPacketListener;
import com.lzs.imchat.util.XmppConnectionManager;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 2016/3/18.
 */
public class MsfService extends Service{
    private XMPPConnection connection;
    private XmppConnectionManager connectionManager;
    private ConnectionConfiguration config;
    private final IBinder binder = new MyBinder();
    private FriendsPacketListener friendsPacketListener;
    /**
     * openfire服务器address
     */
    private final static String server = "10.108.136.59";

    public void onCreate() {
        super.onCreate();
        connectionManager = XmppConnectionManager.getInstance();
        initXMPPTask();

    }
    private void initXMPPTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    init();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void init() {
        connection = connectionManager.init();
        login();

    }

    public void login() {
        try { 
            /** 建立连接 */
            connection.connect();
            connection.login("lzs", "123");
            if (connection.isAuthenticated()) {
                friendsPacketListener=new FriendsPacketListener(this);
                PacketFilter filter = new AndFilter(new PacketTypeFilter(Presence.class));
                connection.addPacketListener(friendsPacketListener, filter);
                Presence presence = new Presence(Presence.Type.available);
                presence.setStatus("在线");
                connection.sendPacket(presence);
                ImApplication.xmppConnection = connection;
            }
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public List<Map<String, Object>> getRoster() {
//        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//        Roster roster = connection.getRoster();
//        Collection<RosterEntry> rosterEntiry = roster.getEntries();
//        Iterator<RosterEntry> iter = rosterEntiry.iterator();
//        while (iter.hasNext()) {
//            RosterEntry entry = iter.next();
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("info", entry);
//            data.add(map);
//        }
//        return data;
//    }

    public void destory() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public class MyBinder extends Binder {
        public MsfService getService() {
            return MsfService.this;
        }
    }


//    class MyMessageListeners implements MessageListener {
//        public void processMessage(Chat chat, Message message) {
//            try {
//                /** 发送消息 */
//                chat.sendMessage("dingding……" + message.getBody());
//            } catch (XMPPException e) {
//                e.printStackTrace();
//            }
//            /** 接收消息 */
//
//        }
//    }
}


