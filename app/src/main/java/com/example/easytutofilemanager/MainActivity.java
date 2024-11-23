package com.example.easytutofilemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在Activity创建时直接检查权限
        if (checkPermission()) {
            // 如果权限已授予，直接跳转到 FileListActivity
            Intent intent = new Intent(MainActivity.this, FileListActivity.class);
            String path = Environment.getExternalStorageDirectory().getPath();
            intent.putExtra("path", path);
            startActivity(intent);
        } else {
            // 如果没有权限，直接请求权限
            requestPermission();
        }

    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else{
        ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
// 异步检查权限
        }


    }
}