package com.navinival.navinivalplugin.AndroidNative;

/**
 * Created by strea on 2016/09/29.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.content.Intent;
import android.widget.Toast;

//GPS関係のimport

import com.unity3d.player.UnityPlayerActivity;

public class NavinivalNative extends UnityPlayerActivity{

    private final int REQUEST_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(NavinivalNative.this, BeaconService.class));//Beaconサービス起動

        //GPS用バージョンチェック
        if(Build.VERSION.SDK_INT >= 23){
            checkPermission();
        }
        else{
            locationActivity();
        }


    }

    public void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationActivity();
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(NavinivalNative.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
        }
        else {
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_PERMISSION);

        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationActivity();
                return;

            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "実行にはGPSが必要です", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    // Intent でLocation
    private void locationActivity() {
        Intent intent = new Intent(getApplication(), LocationActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onPause();

    }
}

