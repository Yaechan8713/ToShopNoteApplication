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
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;

/**
 * Created by yaerei on 2017/08/10.
 */
public class googlemaprunActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap_main);

        if(ContextCompat.checkSelfPermission(this,permission,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},REQUEST_PERMISSION);

        }else{
            locationStart();
        }
    }

    private void locationStart(){
        Log.d("debug","locationStart()");

//        LocationManager　インスタンス作成
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsEnabled){
//        GPSを設定するようにする。
            Intent settingIntent = new Intent(Settings,ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingIntent);
            Log.d("debug","not gpsEnable,startActivity");
        }else{
            Log.d("debug","gpsEnabled");
        }

        if(ContextCompat.checkSelfPermission(this,permission,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},REQUEST_PERMISSION);

            Log.d("debug","checkSelfPermission false");
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,50,this);

//        結果の受け取り
        @Override
        public void onRequestPermissionResult(int requestCode, String[] permissions,int[] grantResults){
            if(requestCode == 1000){
//                使用が許可された
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("debug","checkSelfPermission true");

                    locationStart();
                    return;
                }else {
//                    それでも拒否された時の対応
                    new AlertDialog.Builder(googlemaprunActivity.this)
                            .setTitle("")
                            .setMessage("これ以上なにもできません")
                            .setPositiveButton(
                                    R.string.ok,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            t++;
                                        }
                                    }
                            ).show();

                }
            }
        }

        @Override
        public void onStatusChanged(String provider,int status, Bundle extras){
            switch (status){
                case LocationProvider.AVAILABLE:
                    Log.d("debug","LocationProvider.AVILABLE");
                    break;

                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug","LocationProvider.OUT_OF_SERVICE");
                    break;

                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug","LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }

        @Override
        public void onLocationChanged(Location location){
            TextView location1textView,location2textView;

            //緯度の表示
            location1textView = (TextView)findViewById(R.id.location1textView);
            location1textView.setText("Latitude" + location.getLaitude());

            //経度の表示
            location2textView = (TextView)findViewById(R.id.loccation2textView);
            location2textView.setText("Latitude" + location.getLongitude());
        }


        @Override
        public void onProviderEnabled(String provider){

        }

        @Override
        public void onProviderDisabled(String provider){

        }
    }
}


