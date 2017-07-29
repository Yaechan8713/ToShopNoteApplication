package reduce.project.yaerei.toshopnote;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yaerei on 2017/06/20.
 */
public class sumActivity extends AppCompatActivity {


    TextView sumtextView;

    SharedPreferences pref;
    ArrayAdapter<String> adapter;

    int sum,oldsum,t,year,month,day,oldyear,oldmonth,oldday;

    ListView listView;

    ArrayList<String> arrayList = new ArrayList<String>();
    Calendar obj_cd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

//        Intent intent = new Intent(this,lockActivity.class);
//        startActivity(intent);
        pref =getSharedPreferences("shop",Context.MODE_PRIVATE);

        sum = t = year = month = day = oldyear = oldday = oldmonth = oldsum = 0;

        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);

        sumtextView = (TextView)findViewById(R.id.textView);

        listView = (ListView)findViewById(R.id.listView);



        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = (String)adapter.getItem(position);
                adapter.insert(item,position);
                adapter.remove(item);
            }

        });






        List<sumItem>items;
        items = new Select().from(sumItem.class).execute();
        for(sumItem item:items){
            adapter.insert(item.sumname,0);
        }
        daterun();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //起動中のアプリを開いた時の処理

        String str = "確認";


        new AlertDialog.Builder(sumActivity.this)
                .setTitle("Good")
                .setMessage("成功！！")
                .setPositiveButton(
                        str,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                t++;
                            }
                        }
                );

    }



    public void resetsum(View v){
        reset();
    }

    public void hyoujiandclock(){

//        String f = t + "秒";

        sumtextView.setText("合計" + oldsum + "円");



    }

    public void insertItem(String suminsert){
        sumItem item = new sumItem();
        item.sumname = suminsert;;
        item.save();
    }

    public void derateItem(String sumderate){
        sumItem item = new sumItem();
        item = new Select().from(sumItem.class).where("sumname=?",sumderate).executeSingle();
        item.delete();
    }

    public void newint(String sumnewint){
        sumItem item = new sumItem();
        item = new Select().from(sumItem.class).where("sumname=?",sumnewint).executeSingle();
    }

    public void daterun(){

        oldyear = pref.getInt("year", 0);

        oldmonth = pref.getInt("month", 0);

        oldday = pref.getInt("day", 0);

        oldsum = pref.getInt("goukeisum", 0);



        year = obj_cd.get(Calendar.YEAR);
        month = obj_cd.get(Calendar.MONTH) + 1;
        day = obj_cd.get(Calendar.DATE);


        if((oldyear == year && oldmonth == month && oldday == day) || oldyear == 0 || oldmonth == 0 || oldday == 0){
            Intent intent = getIntent();
            sum = intent.getIntExtra("sum", 0);
            oldsum = oldsum + sum;
        }else{
            dayriset();
        }

        hyoujiandclock();

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("goukeisum",oldsum);
        editor.commit();
    }

    public void reset(){


        t =  sum = oldsum = 0;


        hyoujiandclock();


        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("goukeisum",oldsum);
        editor.commit();
    }



    public void dayriset(){

        SharedPreferences.Editor yeareditor = pref.edit();
        yeareditor.putInt("year",year);
        yeareditor.commit();


        SharedPreferences.Editor motheditor = pref.edit();
        motheditor.putInt("month",month);
        motheditor.commit();


        SharedPreferences.Editor dayeditor = pref.edit();
        dayeditor.putInt("day",day);
        dayeditor.commit();


        reset();
    }

}
