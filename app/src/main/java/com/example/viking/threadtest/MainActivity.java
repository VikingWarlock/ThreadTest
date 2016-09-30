package com.example.viking.threadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler threadHandler;
    TextView console;
    Button addButton;
    int threadCount;
    int totalMessageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console=(TextView)findViewById(R.id.console);
        if (console!=null){
            console.setMovementMethod(new ScrollingMovementMethod());
        }
        addButton=(Button)findViewById(R.id.add);
        totalMessageCount=0;
        threadHandler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                totalMessageCount++;
                String NewLine="Thread "+msg.what+" Total "+totalMessageCount;
                showLine(NewLine);
            }
        };
        if (addButton!=null){
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAction();
                }
            });

        }
    }

    private void showLine(String line){
        String origin=console.getText().toString();
        origin=line+"\n"+origin;
        console.setText(origin);
    }

    private void addAction(){
        threadCount++;
        showLine("The Thread "+threadCount+" Start");
        startNewThread();
    }


    private void startNewThread(){
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int ThreadIndex=threadCount;
                while (true){
                    threadHandler.sendEmptyMessage(ThreadIndex);
                    try{
                        Thread.sleep(5000);
                    }catch(InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }



}
