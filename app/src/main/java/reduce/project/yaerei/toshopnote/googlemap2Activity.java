package reduce.project.yaerei.toshopnote;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.activeandroid.util.Log;

/**
 * Created by yaerei on 2017/08/14.
 */
public class googlemap2Activity extends AppCompatActivity {

    private final int REQUEST_PERMISSION = 10;
    String toast;

    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_googlemap2_main);

        Log.d("googlemap2Activity","onCreate()");

        if(Build.VERSION.SDK_INT >= 9){
            checkper();
        }else{
            locationActivity();
        }
    }

    //位置情報許可の確認
    public void checkper(){
        //すでに許可している場合
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationActivity();
        }
        //拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    //許可を求める
    private void requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(googlemap2Activity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION);
        }else{
            toast = "許可されないとGoogleMapが使えません。";
            Toast.makeText(this,toast,Toast.LENGTH_SHORT);

        }
    }

    //結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        //使用が許可された
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            locationActivity();
            return;
        }else{
            //それでも拒否された時の対応
            toast = "これ以上何もできません。";
            Toast.makeText(this,toast,Toast.LENGTH_SHORT);
        }

    }
//        IntentでLocation

    public void locationActivity(){
        Intent intent = new Intent(this,googlemaprunActivity.class);
        startActivity(intent);
    }

}
