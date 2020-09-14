package com.example.vamsi.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

//import com.vidyo.VidyoClient.Connector.ConnectorPkg;
//import com.vidyo.VidyoClient.Connector.Connector;

//public class VideoChat extends AppCompatActivity implements Connector.IConnect {
//
//    private Connector vc;
//    private FrameLayout videoFrame;
//    private String const apiToken = "cHJvdmlzaW9uAHNlZ2F1bHRAY2U5ODAyLnZpZHlvLmlvADYzNzY3Mzk0ODkxAAAxYTA1MDRhNTQ2MmRjMDhlMjhlMDg4MjZmMTNhNWJlZWY2OTc0NjAzMTcxYTNmN2ZiZTA4NjczYmMyNGY2ZTE2ZWZhNTk3NjliZDYwZTUyY2ZiZGI3NWMwYzAyZWFiOTk=";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_chat);
//
//        ConnectorPkg.setApplicationUIContext(this);
//        ConnectorPkg.initialize();
//        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);
//    }
//
//    public void Start(View v) {
//        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 2, "r", "", 0);
//        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
//    }
//
//    public void Connect(View v) {
//        vc.connect("prod.vidyo.io", apiToken, 'DemoUser' , "DemoRoom", this);
//    }
//
//    public void Disconnect(View v) {
//        vc.disconnect();
//    }
//
//    public void onSuccess() {}
//
//    public void onFailure(Connector.ConnectorFailReason reason) {}
//
//    public void onDisconnected(Connector.ConnectorDisconnectReason reason) {}
//}