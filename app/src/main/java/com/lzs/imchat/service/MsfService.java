package com.lzs.imchat.service;



import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Session;
import org.jivesoftware.smack.packet.Message.Type;

import java.net.DatagramSocket;
import java.net.SocketException;
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
    private ConnectionConfiguration config;
    private final IBinder binder = new MyBinder();
    /**
     * openfire服务器address
     */
    private final static String server = "10.108.136.59";

    public void onCreate() {
        super.onCreate();
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
        try {
            //connection = new XMPPConnection(server);
            //connection.connect();
            /** 5222是openfire服务器默认的通信端口，你可以登录http://192.168.8.32:9090/到管理员控制台查看客户端到服务器端口 */
            config = new ConnectionConfiguration(server, 5222);

            /** 是否启用压缩 */
            config.setCompressionEnabled(true);
            /** 是否启用安全验证 */
            config.setSASLAuthenticationEnabled(false);
            /** 是否启用调试 */
            config.setDebuggerEnabled(false);
            //允许自动连接
            config.setReconnectionAllowed(true);
            // 允许登陆成功后更新在线状态
            config.setSendPresence(true);

            /** 创建connection链接 */
            connection = new XMPPConnection(config);
            /** 建立连接 */
            connection.connect();
            login();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        try {
            connection.login("lzs", "123");
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("在线");
        connection.sendPacket(presence);
       }

    public List<Map<String, Object>> getRoster() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Roster roster = connection.getRoster();
        Collection<RosterEntry> rosterEntiry = roster.getEntries();
        Iterator<RosterEntry> iter = rosterEntiry.iterator();
        while (iter.hasNext()) {
            RosterEntry entry = iter.next();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("info", entry);
            data.add(map);
        }
        return data;
    }

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


    class MyMessageListeners implements MessageListener {
        public void processMessage(Chat chat, Message message) {
            try {
                /** 发送消息 */
                chat.sendMessage("dingding……" + message.getBody());
            } catch (XMPPException e) {
                e.printStackTrace();
            }
            /** 接收消息 */

        }
    }
}


