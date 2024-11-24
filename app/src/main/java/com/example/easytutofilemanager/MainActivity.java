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
import android.util.Log;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialButton deleteBtn = findViewById(R.id.delete_btn);
        MaterialButton storageBtn = findViewById(R.id.storage_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //permission allowed
//                    Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                    String path = Environment.getExternalStorageDirectory().getPath();
                    Log.d("StoragePath", "根目录路径: " + path);

                    // 构建 DCIM 文件夹路径
                    File dcimFolder = new File(path, "DCIM");


                    if (dcimFolder.exists() && dcimFolder.isDirectory()) {
                        Log.d("DCIM", "DCIM 文件夹存在!");
                        // Get all files and subfolders in the DCIM folder
                        deleteFilesInDirectory(dcimFolder);
                    }
//                    startActivity(intent);
                }else{
                    //permission not allowed
                    requestPermission();

                }
            }
        });

        storageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //permission allowed
                    Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                    String path = Environment.getExternalStorageDirectory().getPath();
                    intent.putExtra("path",path);
                    startActivity(intent);
                }else{
                    //permission not allowed
                    requestPermission();

                }
            }
        });

    }

    // Recursive method to delete files and keep directories
    private void deleteFilesInDirectory(File directory) {
        // Get all files and subdirectories in the current directory
        File[] files = directory.listFiles();

        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // If it's a directory, recursively call the method to handle its contents
                    deleteFilesInDirectory(file);
                    Log.d("DCIM", "保留文件夹: " + file.getName());
                } else {
                    // If it's a file, delete it
                    boolean deleted = file.delete();
                    if (deleted) {
                        Log.d("DCIM", "已删除文件: " + file.getName());
                    } else {
                        Log.d("DCIM", "无法删除文件: " + file.getName());
                    }
                }
            }
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
        }else
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }
}