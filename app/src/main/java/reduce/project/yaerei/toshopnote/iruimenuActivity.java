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
public class iruimenuActivity extends AppCompatActivity {
    ListView listiew;
    Intent intent;
    EditText editText, iruisumedittext,iruiresurchedit;
    Spinner spinner;
    int t, spint, deletint, onclickint, iruisum;
    ArrayAdapter<String> adapter;
    TextView iruisumtextView,mode;
    String monoedit, spinstr, monototal;
    SharedPreferences iruipre;
    SharedPreferences.Editor iruieditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irui);

        iruiresurchedit = (EditText)findViewById(R.id.iruiresurchedit);
        mode = (TextView)findViewById(R.id.mode);
        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        iruisumedittext = (EditText) findViewById(R.id.iruisumedittext);
        iruisumtextView = (TextView) findViewById(R.id.iruisumtextView);

        firststring();
        deletint = t = spint = onclickint = iruisum = 0;


        iruipre = getSharedPreferences("iruisum", Context.MODE_PRIVATE);
        iruisum = iruipre.getInt("iruisum", 0);

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
                            .Builder(iruimenuActivity.this)
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
                            .Builder(iruimenuActivity.this)
                            .setTitle(R.string.delete)
                            .setMessage("次の項目を削除しますか？\n\n" + item)
                            .setPositiveButton(
                                    R.string.delete,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            derateItem(item);

                                            adapter.remove(item);

                                            Toast.makeText(iruimenuActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
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

        List<iruiItem> items;
        items = new Select().from(iruiItem.class).execute();
        for (iruiItem item : items) {
            adapter.insert(item.iruiname, 0);
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
                new AlertDialog
                        .Builder(iruimenuActivity.this)
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
                finish();
                intent = new Intent(this, gorakumenuActivity.class);
                startActivity(intent);
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

    public void input() {

        if (deletint == 0) {
            if (TextUtils.isEmpty(editText.getText())) {
                monototal = spinner.getSelectedItem().toString();
            } else {
                monototal = editText.getText().toString();
            }

            new AlertDialog
                    .Builder(iruimenuActivity.this)
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
                    .Builder(iruimenuActivity.this)
                    .setTitle(R.string.input)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;
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

            String resurchstring = iruiresurchedit.getText().toString();

            iruiItem item = new Select().from(gorakuItem.class).where("iruiname = ?",resurchstring).executeSingle();

            String resurchdeishitem,resurchkekka;

            resurchkekka = "検索結果";

            if(item == null){
                new AlertDialog.Builder(iruimenuActivity.this)
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
                resurchdeishitem = item.iruiname;

                new AlertDialog.Builder(iruimenuActivity.this)
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
            new AlertDialog.Builder(iruimenuActivity.this)
                    .setTitle("エラー")
                    .setIcon(R.drawable.chuui)
                    .setMessage("削除モードになっています。\nワード検索は追加モードでないとできません。\n追加モードに変化しますか？")
                    .setPositiveButton(
                            R.string.tuika,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deletint = 0;
                                    modetext();
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
        iruiresurchedit.setText("");
    }

    public void modetext(){
        if(deletint == 0){
            mode.setText("追加モード");
        }else if(deletint == 1){
            mode.setText("削除モード");
        }
    }


    public void delete() {
        if (deletint == 0) {
            String deletemode = getString(R.string.delete) + "モード";

            new AlertDialog
                    .Builder(iruimenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(deletemode + "にしますか？")
                    .setPositiveButton(

                            deletemode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //ダイアログのOKボタンを押したときの処理
                                    deletint = 1;
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
                    .Builder(iruimenuActivity.this)
                    .setTitle(R.string.delete)
                    .setMessage(inputmode + "にしますか？")
                    .setPositiveButton(

                            inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletint = 0;
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

        int iruisum1;

        if (iruisumedittext.getText().toString().equals("")) {
            iruisum1 = 0;

            return;
        }

        iruisum1 = Integer.valueOf(iruisumedittext.getText().toString());

        iruisum = iruisum + iruisum1;

        iruisumedittext.setText("");

        hyouji();

        iruipre = getSharedPreferences("iruisum", Context.MODE_PRIVATE);
        iruieditor = iruipre.edit();
        iruieditor.putInt("iruisum", iruisum);
        iruieditor.commit();

        intent = new Intent(this, sumActivity.class);
        intent.putExtra("sum", iruisum);
        startActivity(intent);

        iruisum = iruisum1 = 0;
    }

    public void hyouji() {
        if (iruisum <= 999999999) {
            iruisumtextView.setText("合計金額は" + iruisum + "円です。");
        } else if (iruisum < 0) {
            iruisum = 0;
            iruisumtextView.setText("エラーです。");
        } else {
            iruisum = 0;
            iruisumtextView.setText("エラーです。合計金額が大き過ぎです。");
        }
    }

    public void intentbutton(View v) {
        goukeiintent();
    }
}
