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
    }

    public void run() {
        if (pushcount == 1) {
            firstnumber1 = ichijinum;
            maru = maru + "●";
        }
        if (pushcount == 2) {
            firstnumber2 = ichijinum;
            maru = maru + "●";
        }
        if (pushcount == 3) {
            firstnumber3 = ichijinum;
            maru = maru + "●";
        }
        if (pushcount == 4) {
            firstnumber4 = ichijinum;

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
        pushcount++;
        ichijinum = 1;
        run();
    }

    public void passwordnum2(View v) {
        pushcount++;
        ichijinum = 2;
        run();
    }

    public void passwordnum3(View v) {
        pushcount++;
        ichijinum = 3;
        run();
    }

    public void passwordnum4(View v) {
        pushcount++;
        ichijinum = 4;
        run();
    }

    public void passwordnum5(View v) {
        pushcount++;
        ichijinum = 5;
        run();
    }

    public void passwordnum6(View v) {
        pushcount++;
        ichijinum = 6;
        run();
    }

    public void passwordnum7(View v) {
        pushcount++;
        ichijinum = 7;
        run();
    }

    public void passwordnum8(View v) {
        pushcount++;
        ichijinum = 8;
        run();
    }

    public void passwordnum9(View v) {
        pushcount++;
        ichijinum = 9;
        run();
    }

    public void passwordnum0(View v) {
        pushcount++;
        ichijinum = 0;
        run();
    }
}
