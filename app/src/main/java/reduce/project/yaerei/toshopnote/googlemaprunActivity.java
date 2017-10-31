package reduce.project.yaerei.toshopnote;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by yaerei on 2017/08/10.
 */

public class googlemaprunActivity extends AppCompatActivity {

    private static final int REQUEST_CHECK_STTINGS = 0x1;
    TextView textView;
    String textLog;
    //Fused Location Provider API
    private FusedLocationProviderClient fusedLocationClient;
    //Location Settings APIS
    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location location;
    private String lastUpdateTime;
    private Boolean requestingLocationUpdates;
    private int priority = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
//                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());
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

        if (priority == 0) {
//            高い精度の位置情報を取得したい場合
//            インターバルを例えば5000msecに設定すればマップアプリの様なリアルタイム測位となる
//            主に精度重視の為、GPSが優先的に使われる
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else if (priority == 1) {
//            バッテリー消費を抑えたい場合、精度は100m悪くなる
//            主にwifi、電話網での位置情報が主となる
//            この設定の例としては、setInterval(1時間).setFastestInterval(1分)
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        } else if (priority == 2) {
//            バッテリー消費を抑えたい場合、精度は10km悪くなる
            locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        } else {
//            受け身的な位置情報取得アプリが自ら測位せず、他のアプリで得られた位置情報は入手できる
            locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
        }
        //アップで都のインターバル期間設定
        //このインターバルは測位データがない場合はアップデートしない
        //状況によってはこの時間より長くこともある
        //他に同様のアプリが短いインターバルでアップデートされているとそれに影響されて短くなることもある
        //単位：msec
        locationRequest.setInterval(60000);
        //このインターバル時間は正確。これより早いアップデートはしない
        //単位：msec
        locationRequest.setFastestInterval(5000);
    }

    private void buidLSR() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datta) {
        switch (requestCode) {
            case REQUEST_CHECK_STTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d("debug", "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("debug", "User chose not to make required location  settings changes");
                        requestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void startLU() {
        settingsClient.checkLocationSettings(locationSettingsRequest).addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.i("debug", "All location settings are satisfied");

//                パーミッションの確認
                if (ActivityCompat.checkSelfPermission(googlemaprunActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(googlemaprunActivity.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
//                LocationSettingsStatusCodes locationsettingsCode;
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("dubug", "Location settings are not satisfied Attempting to upgrade" + "location settings");

                        try {
                            ResolvableApiException rae = (ResolvableApiException) e;
                            rae.startResolutionForResult(googlemaprunActivity.this, REQUEST_CHECK_STTINGS);
                        } catch (IntentSender.SendIntentException sie) {
                            Log.i("debug", "PendingIntent unable execute request.");
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String error = "Location settings are inadequate, and can't be" + " fixed here.Fix in Settings.";
                        Log.e("debug", error);
                        Toast.makeText(googlemaprunActivity.this, error, Toast.LENGTH_LONG).show();
                        requestingLocationUpdates = false;

                }
            }
        });

        requestingLocationUpdates = true;
    }

    private void stopLU() {
        textLog += "onStop()\n";
        textView.setText(textLog);

        if (!requestingLocationUpdates) {
            Log.d("debug", "stopLocationUpdates: updates never requested, no-op.");

            return;
        }

        fusedLocationClient.removeLocationUpdates(locationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                requestingLocationUpdates = false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        バッテリー消費を鑑みLocation requestを止める
        stopLU();
    }
}



