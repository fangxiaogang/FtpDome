package com.socket.ftpdome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.socket.ftpdome.FTP.DeleteFileProgressListener;
import com.socket.ftpdome.FTP.DownLoadProgressListener;
import com.socket.ftpdome.FTP.UploadProgressListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String FTP_CONNECT_SUCCESSS = "ftp连接成功";
    public static final String FTP_CONNECT_FAIL = "ftp连接失败";
    public static final String FTP_DISCONNECT_SUCCESS = "ftp断开连接";
    public static final String FTP_FILE_NOTEXISTS = "ftp上文件不存在";

    public static final String FTP_UPLOAD_SUCCESS = "ftp文件上传成功";
    public static final String FTP_UPLOAD_FAIL = "ftp文件上传失败";
    public static final String FTP_UPLOAD_LOADING = "ftp文件正在上传";

    public static final String FTP_DOWN_LOADING = "ftp文件正在下载";
    public static final String FTP_DOWN_SUCCESS = "ftp文件下载成功";
    public static final String FTP_DOWN_FAIL = "ftp文件下载失败";

    public static final String FTP_DELETEFILE_SUCCESS = "ftp文件删除成功";
    public static final String FTP_DELETEFILE_FAIL = "ftp文件删除失败";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        Button buttontest = (Button) findViewById(R.id.test);
        buttontest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new FTP().openConnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        //上传功能
        //new FTP().uploadMultiFile为多文件上传
        //new FTP().uploadSingleFile为单文件上传
        Button buttonUpload = (Button) findViewById(R.id.button_upload);
        buttonUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // 上传
                        File file = new File("/mnt/sdcard/SCS/scs.db");
                        try {

                            //单文件上传
                            new FTP().uploadSingleFile(file, "",new UploadProgressListener(){

                                @Override
                                public void onUploadProgress(String currentStep,long uploadSize,File file) {
                                    // TODO Auto-generated method stub
                                    Log.d(TAG, currentStep);
                                    if(currentStep.equals(MainActivity.FTP_UPLOAD_SUCCESS)){
                                        Log.d(TAG, "-----shanchuan--successful");
                                    } else if(currentStep.equals(MainActivity.FTP_UPLOAD_LOADING)){
                                        long fize = file.length();
                                        float num = (float)uploadSize / (float)fize;
                                        int result = (int)(num * 100);
                                        Log.d(TAG, "-----shangchuan---"+result + "%");
                                    }
                                }
                            });
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        //下载功能
        Button buttonDown = (Button)findViewById(R.id.button_down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // 下载
                        try {

                            //单文件下载
                            new FTP().downloadSingleFile("/scs.db","/mnt/sdcard/download/","scs.db",new DownLoadProgressListener(){

                                @Override
                                public void onDownLoadProgress(String currentStep, long downProcess, File file) {
                                    Log.d(TAG, currentStep);
                                    if(currentStep.equals(MainActivity.FTP_DOWN_SUCCESS)){
                                        Log.d(TAG, "-----xiazai--successful");
                                    } else if(currentStep.equals(MainActivity.FTP_DOWN_LOADING)){
                                        Log.d(TAG, "-----xiazai---"+downProcess + "%");
                                    }
                                }

                            });

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        //删除功能
        Button buttonDelete = (Button)findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // 删除
                        try {

                            new FTP().deleteSingleFile("/scs.db",new DeleteFileProgressListener(){

                                @Override
                                public void onDeleteProgress(String currentStep) {
                                    Log.d(TAG, currentStep);
                                    if(currentStep.equals(MainActivity.FTP_DELETEFILE_SUCCESS)){
                                        Log.d(TAG, "-----shanchu--success");
                                    } else if(currentStep.equals(MainActivity.FTP_DELETEFILE_FAIL)){
                                        Log.d(TAG, "-----shanchu--fail");
                                    }
                                }

                            });

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

    }
}
