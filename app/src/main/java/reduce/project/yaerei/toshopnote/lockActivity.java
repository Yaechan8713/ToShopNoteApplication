package reduce.project.yaerei.toshopnote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by yaerei on 2017/07/08.
 */
public class lockActivity extends AppCompatActivity {

    Intent intent;

    int firstjudge,passwordcount;
    Random random = new Random();

    TextView passwordtextView;

    SharedPreferences pre;


    Button button1, button2, button3, button4, button5, button6, button7, button8;

    int money1, money2, money3, money4, money5,sum, money6, money7, money8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        passwordtextView = (TextView) findViewById(R.id.passwordtextView);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);

        firstjudge = 0;

        pre = getSharedPreferences("lockfirst", Context.MODE_PRIVATE);
        firstjudge = pre.getInt("lockfirst", 0);

        firstrun();

    }

    public void firstrun(){

        intent = getIntent();
        sum = intent.getIntExtra("sum",0);

        passwordcount = 0;
        if (firstjudge == 1) {

            int intentnum;


            int ten = 10;

            intentnum = random.nextInt(ten);

            if (intentnum == 0 || intentnum == 1 || intentnum == 2 || intentnum == 3 || intentnum == 4 || intentnum == 5 || intentnum == 6 || intentnum == 7 || intentnum == 8) {

                if (intentnum == 0) {//4725

                    money1 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money2 = pre.getInt("inputnum3", 0);

                    money3 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money4 = pre.getInt("inputnum1", 0);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum4", 0);

                    money6 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum2", 0);

                    money8 = random.nextInt(ten);

                } else if (intentnum == 1) {//7351

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money1 = pre.getInt("inputnum4", 0);

                    money2 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money3 = pre.getInt("inputnum2", 0);

                    money4 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum3", 0);

                    money6 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum1", 0);

                    money8 = random.nextInt(ten);


                } else if (intentnum == 2) {//8176

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money1 = pre.getInt("inputnum2", 0);

                    money2 = random.nextInt(ten);

                    money3 = random.nextInt(ten);

                    money4 = random.nextInt(ten);

                    money5 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money6 = pre.getInt("inputnum4", 0);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum3", 0);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money8 = pre.getInt("inputnum1", 0);

                } else if (intentnum == 3) {//5467
                    money1 = random.nextInt(ten);

                    money2 = random.nextInt(ten);

                    money3 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money4 = pre.getInt("inputnum2", 0);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum1", 0);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money6 = pre.getInt("inputnum3", 0);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum4", 0);

                    money8 = random.nextInt(ten);

                } else if (intentnum == 4) {//3726

                    money1 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money2 = pre.getInt("inputnum3", 0);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money3 = pre.getInt("inputnum1", 0);

                    money4 = random.nextInt(ten);

                    money5 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money6 = pre.getInt("inputnum4", 0);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum2", 0);

                } else if (intentnum == 5) {//6574

                    money1 = random.nextInt(ten);

                    money2 = random.nextInt(ten);

                    money3 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money4 = pre.getInt("inputnum4", 0);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum2", 0);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money6 = pre.getInt("inputnum1", 0);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum3", 0);

                    money8 = random.nextInt(ten);

                } else if (intentnum == 6) {//8713

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money1 = pre.getInt("inputnum3", 0);

                    money2 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money3 = pre.getInt("inputnum4", 0);

                    money4 = random.nextInt(ten);

                    money5 = random.nextInt(ten);

                    money6 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum2", 0);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money8 = pre.getInt("inputnum1", 0);

                } else if (intentnum == 7) {//4381

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money1 = pre.getInt("inputnum4", 0);

                    money2 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money3 = pre.getInt("inputnum2", 0);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money4 = pre.getInt("inputnum1", 0);

                    money5 = random.nextInt(ten);

                    money6 = random.nextInt(ten);

                    money7 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money8 = pre.getInt("inputnum3", 0);

                } else {//5372

                    money1 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                    money2 = pre.getInt("inputnum4", 0);

                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                    money3 = pre.getInt("inputnum2", 0);

                    money4 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                    money5 = pre.getInt("inputnum1", 0);

                    money6 = random.nextInt(ten);

                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                    money7 = pre.getInt("inputnum3", 0);

                    money8 = random.nextInt(ten);

                }

            } else {//8564

                money1 = random.nextInt(ten);

                money2 = random.nextInt(ten);

                money3 = random.nextInt(ten);

                pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                money4 = pre.getInt("inputnum4", 0);

                pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                money5 = pre.getInt("inputnum2", 0);

                pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                money6 = pre.getInt("inputnum3", 0);

                money7 = random.nextInt(ten);

                pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                money8 = pre.getInt("inputnum1", 0);
            }
        } else {

            money1 = money2 = money3 = money4 = money5 = money6 = money7 = money8 = 0;

            intent = new Intent(this, lockfirstActivity.class);
            startActivity(intent);
        }

        button1.setText(money1 + "");

        button2.setText(money2 + "");

        button3.setText(money3 + "");

        button4.setText(money4 + "");

        button5.setText(money5 + "");

        button6.setText(money6 + "");

        button7.setText(money7 + "");

        button8.setText(money8 + "");
    }

    public void passwordrun(int ps){
        int num1,num2,num3,num4,ps1,ps2,ps3,ps4;
        num1 = num2 = num3 = num4 = 0;

        pre = getSharedPreferences("inputnum1",Context.MODE_PRIVATE);
        ps1 = pre.getInt("inputnum1",0);

        pre =getSharedPreferences("inputnum2",Context.MODE_PRIVATE);
        ps2 = pre.getInt("inputnum2",0);

        pre = getSharedPreferences("inputnum3",Context.MODE_PRIVATE);
        ps3 = pre.getInt("inputnum3",0);

        pre = getSharedPreferences("inputnum4",Context.MODE_PRIVATE);
        ps4 = pre.getInt("inputnum4",0);

        if(passwordcount == 0){
            num1 = ps;
            passwordcount++;
        }
        if(passwordcount == 1){
            num2 = ps;
            passwordcount++;
        }else if(passwordcount == 2){
            num3 = ps;
            passwordcount++;
        }else{
            num4 = ps;
            passwordcount = 0;
        }

        if(num1 == ps1 && num2 == ps2 && num3 == ps3 && num4 == ps4){

            intent = new Intent(this,sumActivity.class);
            intent.putExtra("sum2",sum);
            startActivity(intent);

        }else{
            new AlertDialog.Builder(lockActivity.this)
                    .setTitle("エラー")
                    .setIcon(R.drawable.chuui)
                    .setMessage("パスワードが違います。")
                    .setPositiveButton(
                            R.string.ryoukai,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    firstrun();
                                }
                            }
                    ).show();
        }
    }
}
