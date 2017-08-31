package reduce.project.yaerei.toshopnote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by yaerei on 2017/07/08.
 */
public class lockActivity extends AppCompatActivity {

    Intent intent;

    int firstjudge;
    Random random = new Random();

    SharedPreferences pre;

    int money1, money2, money3, money4, money5, money6, money7, money8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstjudge = 0;

        pre = getSharedPreferences("lockfirst",Context.MODE_PRIVATE);
        firstjudge = pre.getInt("lockfirst",0);

        if(firstjudge == 1) {

            int intentnum;


            int ten = 10;

            intentnum = random.nextInt(ten);

            if (intentnum == 0 || intentnum == 1 || intentnum == 2 || intentnum == 3 || intentnum == 4 || intentnum == 5 || intentnum == 6 || intentnum == 7 || intentnum == 8 || intentnum == 9 || intentnum == 10) {
                if (intentnum == 0) {//4725

                    money1 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3",Context.MODE_PRIVATE);
                    money2 = pre.getInt("inputnum3",0);

                    money3 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum1",Context.MODE_PRIVATE);
                    money4 = pre.getInt("inputnum1",0);

                    pre = getSharedPreferences("inputnum4",Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum4",0);

                    money6 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2",Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum2",0);

                    money8 = random.nextInt(ten);

                } else if (intentnum == 1) {//7351

                    pre = getSharedPreferences("inputnum4",Context.MODE_PRIVATE);
                    money1 = pre.getInt("inputnum4",0);

                    money2 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2",Context.MODE_PRIVATE);
                    money3 = pre.getInt("inputnum2",0);

                    money4 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3",Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum3",0);

                    money6 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum1",Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum1",0);

                    money8 = random.nextInt(ten);


                } else if (intentnum == 2) {//8176

                } else if (intentnum == 3) {
                    intent = new Intent(this, lockpart4Activity.class);
                } else if (intentnum == 4) {
                    intent = new Intent(this, lockpart5Activity.class);
                } else if (intentnum == 5) {
                    intent = new Intent(this, lockpart6Activity.class);
                } else if (intentnum == 6) {
                    intent = new Intent(this, lockpart7Activity.class);
                } else if (intentnum == 7) {
                    intent = new Intent(this, lockpart8Activity.class);
                } else if (intentnum == 8) {
                    intent = new Intent(this, lockpart9Activity.class);
                } else {
                    intent = new Intent(this, lockpart10Activity.class);
                }

            } else {
                intent = new Intent(this, lockpart10Activity.class);
            }
        }else{
            intent = new Intent(this,lockfirstActivity.class);
        }
        startActivity(intent);
    }
}














