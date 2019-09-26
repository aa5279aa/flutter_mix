package com.lxl;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lxl.fragment.TestFragment;

public class MainActivity extends FragmentActivity {

    TestFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(0x01000000, 0x01000000);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragment = new TestFragment();
//                fragmentTransaction.add(Window.ID_ANDROID_CONTENT, fragment, "test");
//                fragmentTransaction.commitAllowingStateLoss();
                addFragment(getSupportFragmentManager(), fragment, "test");

            }
        });
    }

    public static void addFragment(FragmentManager supportFragmentManager, Fragment baseDialogFragment, String tag) {
        addFragment(supportFragmentManager, baseDialogFragment, Window.ID_ANDROID_CONTENT, tag);
    }

    public static void addFragment(FragmentManager supportFragmentManager, Fragment baseDialogFragment, int content, String tag) {
        try {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.common_anim_fragment_in, R.anim.common_anim_fragment_out, R.anim.common_anim_fragment_close_in, R.anim.common_anim_fragment_close_out);
            Fragment fragment = supportFragmentManager.findFragmentById(content);
            if (fragment != null) {
                if (fragment instanceof FragmentManager.OnBackStackChangedListener) {
                    supportFragmentManager.addOnBackStackChangedListener((FragmentManager.OnBackStackChangedListener) fragment);
                }
                transaction.hide(fragment);
            }
            transaction.add(content, baseDialogFragment, tag);
            transaction.addToBackStack(tag);
            transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragment != null && fragment.isVisible()) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commitAllowingStateLoss();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("lxltest", "");
    }
}
