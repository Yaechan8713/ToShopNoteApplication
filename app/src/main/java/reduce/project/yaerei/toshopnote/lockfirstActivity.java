package reduce.project.yaerei.toshopnote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yaerei on 2017/08/31.
 */
public class lockfirstActivity extends AppCompatActivity {

    int firstnumber1, firstnumber2, firstnumber3, firstnumber4, ichijinum, pushcount;

    TextView textView;

    String maru;

    SharedPreferences pre;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockfirst);
        textView = (TextView) findViewById(R.id.passwordtextView);

        textView.setText("パスワードを設定してください。");
        firstnumber1 = firstnumber2 = firstnumber3 = firstnumber4 = pushcount = ichijinum = 0;
        maru = "";

        int lockfirst = 0;

        pre = getSharedPreferences("lockfirst",Context.MODE_PRIVATE);
        lockfirst = pre.getInt("lockfirst",0);

        if(lockfirst == 0){
            run();
        }else{
            intent();
        }
    }

    public void run(int t) {

        pushcount++;

        if (pushcount == 1) {
            firstnumber1 = t;
            maru = maru + "●";
        }
        if (pushcount == 2) {
            firstnumber2 = t;
            maru = maru + "●";
        }
        if (pushcount == 3) {
            firstnumber3 = t;
            maru = maru + "●";
        }
        if (pushcount == 4) {
            firstnumber4 = t;

            new AlertDialog
                    .Builder(lockfirstActivity.this)
                    .setTitle("パスワード保存")
                    .setMessage("パスワードを保存しますか？")
                    .setPositiveButton(
                            R.string.ok,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    pre = getSharedPreferences("inputnum1", Context.MODE_PRIVATE);
                                    editor = pre.edit();
                                    editor.putInt("inputnum1", firstnumber1);
                                    editor.commit();

                                    pre = getSharedPreferences("inputnum2", Context.MODE_PRIVATE);
                                    editor = pre.edit();
                                    editor.putInt("inputnum2", firstnumber2);
                                    editor.commit();

                                    pre = getSharedPreferences("inputnum3", Context.MODE_PRIVATE);
                                    editor = pre.edit();
                                    editor.putInt("inputnum3", firstnumber3);
                                    editor.commit();

                                    pre = getSharedPreferences("inputnum4", Context.MODE_PRIVATE);
                                    editor = pre.edit();
                                    editor.putInt("inputnum4", firstnumber4);
                                    editor.commit();

                                    int a = 1;

                                    pre = getSharedPreferences("lockfirst", Context.MODE_PRIVATE);
                                    editor = pre.edit();
                                    editor.putInt("lockfirst", a);
                                    editor.commit();
                                    maru = "";
                                    intent();
                                }
                            })
                    .setNeutralButton(
                            R.string.chancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    maru = "";
                                }
                            }
                    ).show();
        }


        textView.setText(maru);

    }

    public void intent() {
        Intent intent = new Intent(this, lockActivity.class);
        startActivity(intent);
    }

    public void passwordnum1(View v) {
        run(1);
    }

    public void passwordnum2(View v) {
        run(2);
    }

    public void passwordnum3(View v) {
        run(3);
    }

    public void passwordnum4(View v) {
        run(4);
    }

    public void passwordnum5(View v) {
        run(5);
    }

    public void passwordnum6(View v) {
        run(6);
    }

    public void passwordnum7(View v) {
        run(7);
    }

    public void passwordnum8(View v) {
        run(8);
    }

    public void passwordnum9(View v) {
        run(9);
    }

    public void passwordnum0(View v) {
        run(0);
    }
}
