package reduce.project.yaerei.toshopnote;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.util.Date;

/**
 * Created by yaerei on 2017/08/10.
 */

public class googlemaprunActivity extends AppCompatActivity {

    //Fused Location Provider API
    private FusedLocationProviderClient fusedLocationProViderClient;

    //Location Settings APIS
    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location location;

    private String lastUpdateTime;
    private Boolean requestLocationUpdates;
    private static final int REQUEST_CHECK_STTINGS = 0x1;
    private int priority = 0;
    TextView textView;
    String textLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap_main);

        fusedLocationProViderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        createLocaClb();
        createLR();
        buidLSR();

        textView = (TextView) findViewById(R.id.textView1);
        textLog = "onCreate()\n";
        textView.setText(textLog);

        //測位開始
        Button buttonStart = (Button) findViewById(R.id.buttonstart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLU();
            }
        });

        //測位終了
        Button buttonstop = (Button) findViewById(R.id.buttonstop);
        buttonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLU();
            }
        });
    }

    //locationのコールバックを受け取る

    private void createLocaClb() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                location = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };
    }

    private void updateLocationUI() {
        //getLastLocation()からの情報がある場合のみ
         if (location != null) {
             textLog += "----------UpdateLocation----------\n";
             textLog += "Latitude" + String.valueOf(location.getLatitude()) + "\n";
             textLog += "Latitude=" + String.valueOf(location.getLongitude()) + "\n";
             textLog += "Accuracy=" + String.valueOf(location.getAccuracy()) + "\n";
             textLog += "Altitude=" + String.valueOf(location.getAltitude()) + "\n";
             textLog += "Speed=" + String.valueOf(location.getSpeed()) + "\n";
             textLog += "Bearing" + String.valueOf(location.getBearing()) + "\n";
             textLog += "Time=" + String.valueOf(lastUpdateTime) + "\n";

             textView.setText(textLog);
         }
    }




    private void createLR() {
        locationRequest = new LocationRequest();

        priority = 0;

        if(priority == 0){
//            高い精度の位置情報を取得したい場合
//            インターバルを例えば5000msecに設定すればマップアプリの様なリアルタイム測位となる
//            主に精度重視の為、GPSが優先的に使われる
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }else if(priority == 1){
//            バッテリー消費を抑えたい場合、精度は100m悪くなる
//            主にwifi、電話網での位置情報が主となる
//            この設定の例としては、setInterval(1時間).setFastestInterval(1分)
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }else if(priority == 2){
//            バッテリー消費を抑えたい場合、精度は10km悪くなる
            locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        }else{
//            受け身的な位置情報取得アプリが自ら測位せず、他のアプリで得られた位置情報は入手できる
            locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
        }
        //
        //
        //
        //
        locationRequest.setInterval(60000);
        //
        //
        locationRequest.setFastestInterval(5000);


    }
}



