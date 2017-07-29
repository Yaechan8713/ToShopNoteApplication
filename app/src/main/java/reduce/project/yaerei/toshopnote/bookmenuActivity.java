package reduce.project.yaerei.toshopnote;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by yaerei on 2017/05/05.
 */
public class bookmenuActivity extends AppCompatActivity {

    ListView listiew;
    Intent intent;
    EditText editText,sumedittext;
    Spinner spinner;
    int spint, deletint, onclickint, goukei, booksum, t, resetlist, money, oldsum;
    ArrayAdapter<String> adapter, sumadapter;
    String monoedit, spinstr, monototal;
    TextView modetextview, bookmoneytextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        sumedittext = (EditText)findViewById(R.id.sumedittext);
        modetextview = (TextView) findViewById(R.id.mode);
        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        bookmoneytextView = (TextView) findViewById(R.id.booktextView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        sumadapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        firststring();

        SharedPreferences moneyhyouji = getSharedPreferences("money", Context.MODE_PRIVATE);
        money = moneyhyouji.getInt("oldbooksum", 0);

        oldsumhyoji();

        SharedPreferences preoldsum = getSharedPreferences("oldbooksum", Context.MODE_PRIVATE);
        SharedPreferences.Editor preoldsumeditor = preoldsum.edit();
        preoldsumeditor.putInt("oldbooksum", money);
        preoldsumeditor.commit();

        t = goukei = deletint = spint = onclickint = resetlist = booksum = money = oldsum = 0;


        SharedPreferences premoney = getSharedPreferences("money", Context.MODE_PRIVATE);
        SharedPreferences.Editor preeditor = premoney.edit();
        preeditor.putInt("money", money);
        preeditor.commit();

        modetext();

        spint = spinner.getSelectedItemPosition();
        spinstr = (String) spinner.getSelectedItem();

        listiew.setAdapter(adapter);

        listiew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final ArrayAdapter adapter = (ArrayAdapter) listiew.getAdapter();

                final String item = (String) adapter.getItem(position);

                if (deletint == 0) {


                    new AlertDialog
                            .Builder(bookmenuActivity.this)
                            .setTitle(R.string.monobasho)
                            .setMessage(item)
                            .setPositiveButton(
                                    R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which
                                        ) {
                                            t++;
                                        }
                                    }).show();


                } else if (deletint == 1) {

                    new AlertDialog
                            .Builder(bookmenuActivity.this)
                            .setTitle(R.string.delete)
                            .setMessage("次の項目を削除しますか？\n\n" + item)
                            .setPositiveButton(
                                    R.string.delete,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            derateItem(item);

                                            adapter.remove(item);

                                            Toast.makeText(bookmenuActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                }

            }
        });

        listiew.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter adapter = (ArrayAdapter) listiew.getAdapter();

                final String item = (String) adapter.getItem(position);
                adapter.insert(item, position);
//                adapter.remove(item);

                return false;
            }
        });

        List<bookItem> items;
        items = new Select().from(bookItem.class).execute();
        for (bookItem item : items) {
            adapter.insert(item.bookname, 0);
        }

        FloatingActionButton foatactionbutton = (FloatingActionButton) findViewById(R.id.foatactionbutton);

        foatactionbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //このfoatactionbuttonが押された時の処理
                input();
            }
        });

        FloatingActionButton deletefoatactionbutton = (FloatingActionButton) findViewById(R.id.deletefoatactionbutton);

        deletefoatactionbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete();
            }
        });


    }

    public void insertItem(String bookinsert) {
        bookItem item = new bookItem();
        item.bookname = bookinsert;
        item.save();
    }

    public void derateItem(String bookdelate) {
        bookItem item = new bookItem();
        item = new Select().from(bookItem.class).where("bookname =?", bookdelate).executeSingle();
        item.delete();
    }

    public void oldsumhyoji() {
        Intent intent = getIntent();
        oldsum = intent.getIntExtra("sum", 0);

        oldsum = oldsum + money;

        if (oldsum <= 999999999) {
            bookmoneytextView.setText("合計金額は" + oldsum + "円です。");
        } else if (oldsum < 0) {
            oldsum = 0;
            bookmoneytextView.setText("エラーです。");
        } else {
            oldsum = 0;
            bookmoneytextView.setText("エラーです。合計金額が大き過ぎです。");
        }

    }

    public void newint(String booknew) {
        bookItem item = new bookItem();
        item = new Select().from(bookItem.class).where("bookname =?", booknew).executeSingle();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.root_layout);

        switch (menu.getItemId()) {
            case R.id.homemenu:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menufood:
                intent = new Intent(this, menufoodActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubunnbogu:
                intent = new Intent(this, menubunboguActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubook:
                new AlertDialog
                        .Builder(bookmenuActivity.this)
                        .setTitle("エラ―")
                        .setMessage("ジャンルが同じです。\n他のジャンルを選択してください。")
                        .setIcon(R.drawable.chuui)
                        .setPositiveButton(
                                R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which
                                    ) {
                                        t++;
                                    }
                                }).show();
                return true;

            case R.id.menukadenn:
                intent = new Intent(this, kadennmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuirui:
                intent = new Intent(this, iruimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menudish:
                intent = new Intent(this, dishmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menukagu:
                intent = new Intent(this, kagumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menushokki:
                intent = new Intent(this, shokkimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menugoraku:
                intent = new Intent(this, gorakumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusoft:
                intent = new Intent(this, menusoftActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusonota:
                intent = new Intent(this, sonotamenuActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(menu);
    }

    public void input() {

        if (deletint == 0) {
            if (TextUtils.isEmpty(editText.getText())) {
                monototal = spinner.getSelectedItem().toString();
            } else {
                monototal = editText.getText().toString();
            }

            new AlertDialog
                    .Builder(bookmenuActivity.this)
                    .setTitle("追加")
                    .setMessage("次の項目を追加しますか？\n\n項目：" + monototal)
                    .setPositiveButton(
                            R.string.tuika,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog, int which
                                ) {
                                    adapter.add(monototal);
                                    insertItem(monototal);
                                    firststring();

                                }
                            })
                    .setNeutralButton(
                            R.string.chancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog, int which
                                ) {
                                    //                                firststring();
                                    t++;
                                }
                            }).show();

        }
        if (deletint == 1) {
            String inputmode = getString(R.string.input) + "モード";
            new AlertDialog
                    .Builder(bookmenuActivity.this)
                    .setTitle(R.string.input)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;

                                    modetext();
                                }
                            }
                    )
                    .setNeutralButton(
                            R.string.chancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    t++;
                                }
                            }
                    ).show();
        }
    }

    public void modetext() {
        if (deletint == 0) {
            modetextview.setText("追加モード");
        } else if (deletint == 1) {
            modetextview.setText("削除モード");
        }
    }

    public void delete() {
        if (deletint == 0) {
            String deletemode = getString(R.string.delete) + "モード";

            new AlertDialog
                    .Builder(bookmenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(deletemode + "にしますか？")
                    .setPositiveButton(

                            deletemode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //ダイアログのOKボタンを押したときの処理
                                    deletint = 1;
                                    modetext();

                                }
                            }
                    )
                    .setNeutralButton(
                            R.string.chancel,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //ダイアログのキャンセルボタンを押したときの処理
                                    t++;
                                }
                            }
                    ).show();

        } else if (deletint == 1) {
            String inputmode = getString(R.string.input) + "モード";
            new AlertDialog
                    .Builder(bookmenuActivity.this)
                    .setTitle(R.string.input)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;
                                    modetext();
                                }
                            }
                    )
                    .setNeutralButton(
                            R.string.chancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    t++;
                                }
                            }
                    ).show();
        }

    }

    public void firststring() {
        monoedit = spinstr = monototal = "";
        editText.setText(monoedit);
    }


    public void goukeiintent() {

        money = Integer.valueOf(sumedittext.getText().toString());

        if(sumedittext.getText().toString().equals("")){
            //sumedittextにデータが入ってなかった場合
            money = 0;
            return;
        }
        sumedittext.setText("");
        SharedPreferences preoldsum = getSharedPreferences("oldbooksum", Context.MODE_PRIVATE);
        SharedPreferences.Editor preoldsumeditor = preoldsum.edit();
        preoldsumeditor.putInt("oldbooksum", money);
        preoldsumeditor.commit();


        bookmoneytextView.setText("合計金額は" + money + "円です。");

        intent = new Intent(this,sumActivity.class);
        intent.putExtra("sum",money);
        startActivity(intent);

    }

    public void intentbutton(View v) {

        goukeiintent();
    }


}
