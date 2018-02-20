package reduce.project.yaerei.toshopnote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.util.Calendar;
import java.util.List;

/**
 * Created by yaerei on 2017/05/05.
 */
public class kadennmenuActivity extends AppCompatActivity {
    ListView listiew;
    Intent intent;
    EditText editText, kadensumedittext;
    Spinner spinner;
    TextView kadennsumtextView;
    int t, spint, deletint, onclickint, kadennsum;
    TextView mode;
    ArrayAdapter<String> adapter;
    String monoedit, spinstr, monototal;
    SharedPreferences kadennpre;
    SharedPreferences.Editor kadenneditor;
    int year,oldyear,day,oldday,month,oldmonth;
    Calendar calendar = Calendar.getInstance();
    EditText kadennresurchedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kadenn);

        mode = (TextView)findViewById(R.id.mode);
        kadennresurchedit = (EditText)findViewById(R.id.kadennresurchedit);
        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        kadensumedittext = (EditText) findViewById(R.id.kadensumedittext);
        kadennsumtextView = (TextView) findViewById(R.id.kadennsumtextView);

        year = oldyear = day = oldday = month = oldmonth = 0;

        firststring();
        modetext(deletint);

        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH) + 1;

        SharedPrefarences();

        oldyear = kadennpre.getInt("kadennyear",0);

        oldmonth = kadennpre.getInt("kadennmonth",0);

        oldday = kadennpre.getInt("kadennmonth",0);

        deletint = t = spint = onclickint = kadennsum = 0;

        if((year == oldyear && day == oldday && month == oldmonth)) {

            kadennpre = getSharedPreferences("kadennsum", Context.MODE_PRIVATE);
            kadennsum = kadennpre.getInt("kadennsum", 0);

        }
        hyouji();

        spint = spinner.getSelectedItemPosition();
        spinstr = (String) spinner.getSelectedItem();


        listiew.setAdapter(adapter);

        listiew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item;

                if (deletint == 0) {
                    item = (String) adapter.getItem(position);
//                        adapter.insert(item, position);
//                        adapter.remove(item);

                    new AlertDialog
                            .Builder(kadennmenuActivity.this)
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
                    final ArrayAdapter adapter = (ArrayAdapter) listiew.getAdapter();
                    item = (String) adapter.getItem(position);

                    new AlertDialog
                            .Builder(kadennmenuActivity.this)
                            .setTitle(R.string.delete)
                            .setMessage("次の項目を削除しますか？\n\n" + item)
                            .setPositiveButton(
                                    R.string.delete,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            derateItem(item);

                                            adapter.remove(item);

                                            Toast.makeText(kadennmenuActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
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

        List<kadennItem> items;
        items = new Select().from(kadennItem.class).execute();
        for (kadennItem item : items) {
            adapter.insert(item.kadennname, 0);
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

    public void insertItem(String kadenninsert) {
        kadennItem item = new kadennItem();
        item.kadennname = kadenninsert;
        item.save();
    }

    public void derateItem(String kadenndelate) {
        kadennItem item = new kadennItem();
        item = new Select().from(kadennItem.class).where("kadennname =?", kadenndelate).executeSingle();
        item.delete();
    }

    public void newint(String kadennnew) {
        kadennItem item = new kadennItem();
        item = new Select().from(kadennItem.class).where("kadennname =?", kadennnew).executeSingle();
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
                dayinput();
                finish();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menufood:
                dayinput();
                finish();
                intent = new Intent(this, menufoodActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubunnbogu:
                dayinput();
                finish();
                intent = new Intent(this, menubunboguActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubook:
                dayinput();
                finish();
                intent = new Intent(this, bookmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menukadenn:
                dayinput();
                new AlertDialog
                        .Builder(kadennmenuActivity.this)
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

            case R.id.menuirui:
                dayinput();
                finish();
                intent = new Intent(this, iruimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menudish:
                dayinput();
                finish();
                intent = new Intent(this, dishmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menukagu:
                dayinput();
                finish();
                intent = new Intent(this, kagumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menushokki:
                dayinput();
                finish();
                intent = new Intent(this, shokkimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menugoraku:
                dayinput();
                finish();
                intent = new Intent(this, gorakumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusoft:
                dayinput();
                finish();
                intent = new Intent(this, menusoftActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusonota:
                dayinput();
                finish();
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
                    .Builder(kadennmenuActivity.this)
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
                    .Builder(kadennmenuActivity.this)
                    .setTitle(R.string.input)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;
                                    modetext(deletint);
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

    public void delete() {
        if (deletint == 0) {
            String deletemode = getString(R.string.delete) + "モード";

            new AlertDialog
                    .Builder(kadennmenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(deletemode + "にしますか？")
                    .setPositiveButton(

                            deletemode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //ダイアログのOKボタンを押したときの処理
                                    deletint = 1;
                                    modetext(deletint);
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
                    .Builder(kadennmenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;
                                    modetext(deletint);
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
    public void resurch(View v){
        if(deletint == 0){

            String resurchstring = kadennresurchedit.getText().toString();

            kadennItem item = new Select().from(kadennItem.class).where("kadennname = ?",resurchstring).executeSingle();

            String resurchdeishitem,resurchkekka;

            resurchkekka = "検索結果";

            if(item == null){
                new AlertDialog.Builder(kadennmenuActivity.this)
                        .setTitle(resurchkekka)
                        .setMessage("検索結果が見つかりませんでした。")
                        .setPositiveButton(
                                R.string.kakuninn,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        edittextfirst();
                                    }
                                }
                        ).show();

                return;
            }else{
                resurchdeishitem = item.kadennname;

                new AlertDialog.Builder(kadennmenuActivity.this)
                        .setTitle(resurchkekka)
                        .setMessage("検索結果が見つかりました！\n\n欲しい物：" + resurchdeishitem)
                        .setPositiveButton(
                                R.string.ok,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        edittextfirst();
                                    }
                                }
                        ).show();
            }
        }else if(deletint == 1){
            new AlertDialog.Builder(kadennmenuActivity.this)
                    .setTitle("エラー")
                    .setIcon(R.drawable.chuui)
                    .setMessage("削除モードになっています。\nワード検索は追加モードでないとできません。\n追加モードに変化しますか？")
                    .setPositiveButton(
                            R.string.tuika,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deletint = 0;
                                    modetext(deletint);
                                }
                            }
                    ).setNeutralButton(
                    R.string.chancel,

                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int t;
                            t = 0;
                            t++;
                        }
                    }
            ).show();
        }
    }

    public void edittextfirst(){
        kadennresurchedit.setText("");
    }

    public void modetext(int tt){
        if(tt == 0){
            mode.setText("追加モード");
        }else if(tt == 1){
            mode.setText("削除モード");
        }
    }

    public void firststring() {
        monoedit = spinstr = monototal = "";
        editText.setText(monoedit);
    }

    public void goukeiintent() {

        int kadennsum1;

        if (kadensumedittext.getText().toString().equals("")) {
            kadennsum1 = 0;

            return;
        }

        kadennsum1 = Integer.valueOf(kadensumedittext.getText().toString());

        if(kadennsum1 < 0){

            kadennsum1 = 0;
            Toast.makeText(this,"負の値は計算できません。",Toast.LENGTH_SHORT).show();
        }

        kadennsum = kadennsum + kadennsum1;

        hyouji();

        kadensumedittext.setText("");
        editText.setText("");

        dayinput();

        intent = new Intent(this, sumActivity.class);
        intent.putExtra("sum", kadennsum);
        startActivity(intent);

        kadennsum = kadennsum1 = 0;
    }


    public void hyouji() {

        kadennsumtextView.setText("");

        kadennpre = getSharedPreferences("kadennsum", Context.MODE_PRIVATE);
        kadenneditor = kadennpre.edit();
        kadenneditor.putInt("kadennsum", kadennsum);
        kadenneditor.commit();

        String texthyouji;

        if (kadennsum <= 999999999) {
            texthyouji = "合計金額は" + kadennsum + "円です。";
        } else if (kadennsum < 0) {
            kadennsum = 0;
            texthyouji = "エラーです。";
        } else {
            kadennsum = 0;
            texthyouji = "エラーです。合計金額が大き過ぎです。";
        }

        kadennsumtextView.setText(texthyouji);
    }

    public void intentbutton(View v) {
        goukeiintent();
    }

    public void dayinput(){

        SharedPrefarences();

        kadenneditor = kadennpre.edit();

        kadenneditor.putInt("year",year);
        kadenneditor.putInt("month",month);
        kadenneditor.putInt("day",day);

        kadenneditor.commit();



    }

    public void SharedPrefarences(){

        kadennpre = getSharedPreferences("kadennyear",Context.MODE_PRIVATE);
        kadennpre = getSharedPreferences("kadennmonth",Context.MODE_PRIVATE);
        kadennpre = getSharedPreferences("kadennday",Context.MODE_PRIVATE);

    }


}
