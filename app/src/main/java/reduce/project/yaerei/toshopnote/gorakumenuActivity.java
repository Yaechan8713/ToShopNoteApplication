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

import java.util.List;

/**
 * Created by yaerei on 2017/05/05.
 */
public class gorakumenuActivity extends AppCompatActivity {
    ListView listiew;
    Intent intent;
    EditText editText, sumedittextgoraku;
    Spinner spinner;
    int t, spint, deletint, onclickint, gorakusum;
    EditText gorakuresurchedit;
    ArrayAdapter<String> adapter;
    TextView gorakusumtextView,mode;
    SharedPreferences gorakupre;
    SharedPreferences.Editor gorakueditor;
    String monoedit, spinstr, monototal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goraku);

        gorakuresurchedit = (EditText)findViewById(R.id.gorakuresurchedit);
        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        gorakusumtextView = (TextView) findViewById(R.id.gorakusumtextView);
        mode = (TextView)findViewById(R.id.mode);
        sumedittextgoraku = (EditText) findViewById(R.id.gorakusumedittext);

        firststring();

        modetext("追加モード");

        deletint = t = spint = onclickint = gorakusum = 0;

        gorakupre = getSharedPreferences("gorakusum", Context.MODE_PRIVATE);


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
                            .Builder(gorakumenuActivity.this)
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
                            .Builder(gorakumenuActivity.this)
                            .setTitle(R.string.delete)
                            .setMessage("次の項目を削除しますか？\n\n" + item)
                            .setPositiveButton(
                                    R.string.delete,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            derateItem(item);

                                            adapter.remove(item);

                                            Toast.makeText(gorakumenuActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
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

        List<gorakuItem> items;
        items = new Select().from(gorakuItem.class).execute();
        for (gorakuItem item : items) {
            adapter.insert(item.gorakuname, 0);
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

    public void insertItem(String gorakuinsert) {
        gorakuItem item = new gorakuItem();
        item.gorakuname = gorakuinsert;
        item.save();
    }

    public void derateItem(String gorakudelate) {
        gorakuItem item = new gorakuItem();
        item = new Select().from(gorakuItem.class).where("gorakuname =?", gorakudelate).executeSingle();
        item.delete();
    }

    public void newint(String gorakunew) {
        gorakuItem item = new gorakuItem();
        item = new Select().from(gorakuItem.class).where("gorakuname =?", gorakunew).executeSingle();
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
                finish();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menufood:
                finish();
                intent = new Intent(this, menufoodActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubunnbogu:
                finish();
                intent = new Intent(this, menubunboguActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubook:
                finish();
                intent = new Intent(this, bookmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menukadenn:
                finish();
                intent = new Intent(this, kadennmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuirui:
                finish();
                intent = new Intent(this, iruimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menudish:
                finish();
                intent = new Intent(this, dishmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menukagu:
                finish();
                intent = new Intent(this, kagumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menushokki:
                finish();
                intent = new Intent(this, shokkimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menugoraku:
                new AlertDialog
                        .Builder(gorakumenuActivity.this)
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

            case R.id.menusoft:
                finish();
                intent = new Intent(this, menusoftActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusonota:
                finish();
                intent = new Intent(this, sonotamenuActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(menu);
    }

    public void modetext(String modestr){
        mode.setText(modestr);
    }

    public void resurch(View v){
        if(deletint == 0){

            String resurchstring = gorakuresurchedit.getText().toString();

            gorakuItem item = new Select().from(gorakuItem.class).where("gorakuname = ?",resurchstring).executeSingle();

            String resurchdeishitem,resurchkekka;

            resurchkekka = "検索結果";

            if(item == null){
                new AlertDialog.Builder(gorakumenuActivity.this)
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
                resurchdeishitem = item.gorakuname;

                new AlertDialog.Builder(gorakumenuActivity.this)
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
            new AlertDialog.Builder(gorakumenuActivity.this)
                    .setTitle("エラー")
                    .setIcon(R.drawable.chuui)
                    .setMessage("削除モードになっています。\nワード検索は追加モードでないとできません。\n追加モードに変化しますか？")
                    .setPositiveButton(
                            R.string.tuika,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deletint = 0;
                                    firststring();
                                    modetext(R.string.tuika + "モード");
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
        gorakuresurchedit.setText("");
    }

    public void input() {

        if (deletint == 0) {
            if (TextUtils.isEmpty(editText.getText())) {
                monototal = spinner.getSelectedItem().toString();
            } else {
                monototal = editText.getText().toString();
            }

            new AlertDialog
                    .Builder(gorakumenuActivity.this)
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
            final String inputmode = getString(R.string.input) + "モード";
            new AlertDialog
                    .Builder(gorakumenuActivity.this)
                    .setTitle(R.string.input)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    firststring();
                                    modetext(inputmode);
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
            final String deletemode = getString(R.string.delete) + "モード";

            new AlertDialog
                    .Builder(gorakumenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(deletemode + "にしますか？")
                    .setPositiveButton(

                            deletemode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //ダイアログのOKボタンを押したときの処理
//                                    deletint = 1;
                                    modetext(deletemode);
                                    firststring();
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
            final String inputmode = getString(R.string.input) + "モード";
            new AlertDialog
                    .Builder(gorakumenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;
                                    firststring();
                                    modetext(inputmode);
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
        int gorakuintentsum;

        if (sumedittextgoraku.getText().toString().equals("")) {
            gorakuintentsum = 0;

            return;
        }

        gorakuintentsum = Integer.valueOf(sumedittextgoraku.getText().toString());

        gorakusum = gorakusum + gorakuintentsum;

        sumedittextgoraku.setText("");
        gorakupre = getSharedPreferences("gorakusum", Context.MODE_PRIVATE);
        gorakueditor = gorakupre.edit();
        gorakueditor.putInt("gorakusum", gorakusum);
        gorakueditor.commit();

        gorakusumtextView.setText("合計金額は" + gorakusum + "円です。");

        intent = new Intent(this, lockActivity.class);
        intent.putExtra("sum", gorakusum);
        startActivity(intent);

        gorakuintentsum = gorakusum = 0;
    }

    public void intentbutton(View v) {
        goukeiintent();
    }
}
