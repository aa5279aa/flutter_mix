package com.lxl;

import android.Manifest;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;

public class DecibelActivity extends Activity implements View.OnClickListener {
    private MicroPhoneThread microPhone = new MicroPhoneThread();  //线程用于实时录制周围声音
    private static final String TAG = "AudioRecord";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    public boolean istrue = true;

    //    private MediaRecorder mARecorder;    //麦克风控制
//    private File mAudiofile, mSampleDir;  //录音文件保存
    //    private ImageView iv_record_wave_left, iv_record_wave_right;
    private AnimationDrawable ad_left, ad_right;
    private TextView textView1;
    private Button button;
    private MHandler mHandler = new MHandler();

    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    textView1.setText(msg.obj.toString());
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.decibe_layout);
        init();
    }

    private void init() {
        //初始化左侧动态动画控件
//        iv_record_wave_left = findViewById(R.id.iv_record_wave_left);
//        iv_record_wave_right = findViewById(R.id.iv_record_wave_right);
//        ad_left = (AnimationDrawable) iv_record_wave_left.getBackground();
//        ad_right = (AnimationDrawable) iv_record_wave_right.getBackground();
//        ad_left.start();
//        ad_right.start();
        button = findViewById(R.id.tv_title);
        textView1 = findViewById(R.id.textView1);
        button.setOnClickListener(this);
    }

    AudioRecord mAudioRecord;

    @Override
    public void onClick(View v) {
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        microPhone.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //录音获取麦克风声音
        String recordAudio = Manifest.permission.RECORD_AUDIO;
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;

//        Permissions.RECORD_AUDIO;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{recordAudio, writeExternalStorage}, 1);
        }

    }

    //返回功能
    public void onBack(View v) {
        DecibelActivity.this.finish();
    }

    class MicroPhoneThread extends Thread {       //测试当前分贝值通知UI修改

        @Override
        public void run() {
            mAudioRecord.startRecording();
            while (istrue) {

                short[] buffer = new short[BUFFER_SIZE];
                //r是实际读取的数据长度，一般而言r会小于buffersize
                int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                long v = 0;
                // 将 buffer 内容取出，进行平方和运算
                for (int i = 0; i < buffer.length; i++) {
                    v += buffer[i] * buffer[i];
                }
                // 平方和除以数据总长度，得到音量大小。
                double mean = v / (double) r;
                double volume = 10 * Math.log10(mean);
                Log.d(TAG, "分贝值:" + volume);
                DecimalFormat df = new DecimalFormat("#.00");
                String str = df.format(volume);
                mHandler.sendMessage(mHandler.obtainMessage(1, str));
                recordDB(str);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void recordDB(String volume) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "record.txt");
        String currentTime = DateUtil.getCurrentTime();
        IOHelper.writerStrByCodeToFile(file, "utf-8", true, currentTime + "=" + volume + "\n");
    }
}
