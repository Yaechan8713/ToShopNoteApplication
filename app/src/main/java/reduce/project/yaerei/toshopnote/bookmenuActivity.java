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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by yaerei on 2017/05/05.
 */
public class bookmenuActivity extends AppCompatActivity {

    ListView listiew;
    Intent intent;
    EditText editText, sumedittext,resurchedit;
    Spinner spinner;
    int spint, deletint, onclickint, goukei, booksum, t, resetlist, money, oldsum;
    ArrayAdapter<String> adapter, sumadapter;
    String monoedit, spinstr, monototal;
    TextView modetextview, bookmoneytextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_book);

        resurchedit = (EditText)findViewById(R.id.resurchedit);
        sumedittext = (EditText) findViewById(R.id.sumedittext);
        modetextview = (TextView) findViewById(R.id.mode);
        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        bookmoneytextView = (TextView) findViewById(R.id.booktextView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        sumadapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        firststring();

        SharedPreferences moneyhyouji = getSharedPreferences("oldbooksum", Context.MODE_PRIVATE);
        money = moneyhyouji.getInt("oldbooksum", 0);

        oldsum = money;

        if (oldsum <= 999999999) {
            bookmoneytextView.setText("合計金額は" + oldsum + "円です。");
        } else if (oldsum < 0) {
            oldsum = 0;
            bookmoneytextView.setText("エラーです。");
        } else {
            oldsum = 0;
            bookmoneytextView.setText("エラーです。合計金額が大き過ぎです。");
        }

        SharedPreferences preoldsum = getSharedPreferences("oldbooksum", Context.MODE_PRIVATE);
        SharedPreferences.Editor preoldsumeditor = preoldsum.edit();
        preoldsumeditor.putInt("oldbooksum", money);
        preoldsumeditor.commit();

        t = goukei = deletint = spint = onclickint = resetlist = booksum = money = oldsum = 0;


        SharedPreferences premoney = getSharedPreferences("money", Context.MODE_PRIVATE);
        SharedPreferences.Editor preeditor = premoney.edit();
        preeditor.putInt("money", money);
        preeditor.commit();

        modetext("追加モード");

        spint = spinner.getSelectedItemPosition();
        spinstr = (String) spinner.getSelectedItem();

        listiew.setAdapter(adapter);

        listiew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final ArrayAdapter adapter = (ArrayAdapter) listiew.getAdapter();

                final String item = (String) adapter.getItem(position);

                if (deletint == 0) {


//                    保存したリストをダイアログ表示するための処理
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

//                    削除したいリストを確認するための
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
                                    })
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

//       FoatactionActionボタンを押したときの処理
        FloatingActionButton foatactionbutton = (FloatingActionButton) findViewById(R.id.foatactionbutton);

        foatactionbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //このFoatactionActionボタンが押された時の処理
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

    public void resurch(View v) {

        if(deletint == 0){

            String resurch = resurchedit.getText().toString();

            bookItem item = new Select().from(bookItem.class).where("bookName = ?", resurch).executeSingle();

            String resurchitem, resurchkekka;

            resurchkekka = "検索結果";

            if (item == null) {

                new AlertDialog.Builder(bookmenuActivity.this)
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
            } else {

                resurchitem = item.bookname;
                ;

                new AlertDialog.Builder(bookmenuActivity.this)
                        .setTitle(resurchkekka)
                        .setMessage("検索結果が見つかりました！\n\n欲しい物：" + resurchitem)
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
            new AlertDialog.Builder(bookmenuActivity.this)
                    .setTitle("エラー")
                    .setIcon(R.drawable.chuui)
                    .setMessage("削除モードになっています。\nワード検索は追加モードでないとできません。\n追加モードに変更しますか？")
                    .setPositiveButton(
                            R.string.inputmode,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    deletint = 0;
                                    modetext("追加モード");
                                }
                            }
                    ).setNeutralButton(

                    R.string.chancel,

                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int t = 0;

                            t++;
                        }
                    }
            ).show();
        }else{
            new AlertDialog.Builder(bookmenuActivity.this)
                    .setTitle("エラー")
                    .setMessage("エラーです。")
                    .setIcon(R.drawable.chuui)
                    .setPositiveButton(

                            R.string.kakuninn,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int t = 0;

                                    t++;
                                }
                            }
                    ).show();
        }
    }

    public void edittextfirst(){
        resurchedit.setText("");
    }

    public void touchedit(View v){
        resurchedit.setText("");
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
//                ホームのジャンルのレイアウトを開く
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menufood:
                finish();
//                食べ物のジャンルのレイアウトを開く
                intent = new Intent(this, menufoodActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubunnbogu:
                finish();
//                文房具のジャンルのレイアウトを開く
                intent = new Intent(this, menubunboguActivity.class);
                startActivity(intent);
                return true;

            case R.id.menubook:
//                エラーメッセージの表示
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
                finish();
//                家電のジャンルのレイアウトを開く
                intent = new Intent(this, kadennmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menuirui:
                finish();
//                衣類のジャンルのレイアウトを開く
                intent = new Intent(this, iruimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menudish:
                finish();
//                洗剤のジャンルのレイアウトを開く
                intent = new Intent(this, dishmenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menukagu:
                finish();
//                家具のジャンルのレイアウトを開く
                intent = new Intent(this, kagumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menushokki:
                finish();
//                食器のジャンルのレイアウトを開く
                intent = new Intent(this, shokkimenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menugoraku:
                finish();
//                娯楽のジャンルのレイアウトを開く
                intent = new Intent(this, gorakumenuActivity.class);
                startActivity(intent);
                return true;
            case R.id.menusoft:
                finish();
//                ソフトウェアのジャンルのレイアウトを開く
                intent = new Intent(this, menusoftActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusonota:
                finish();
//                その他のジャンルのレイアウトを開く
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

                                    modetext("追加モード");
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

    public void modetext(String modestr) {
        modetextview.setText(modestr);
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
//                                    deletint = 1;
                                    modetext("削除モード");

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
//                                    deletint = 0;
                                    modetext("追加モード");
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

        int booksummoney;

        if (sumedittext.getText().toString().equals("")) {
            //sumedittextにデータが入ってなかった場合
            booksummoney = 0;
            return;
        }

        booksummoney = Integer.valueOf(sumedittext.getText().toString());

        money = money + booksummoney;

        sumedittext.setText("");
        SharedPreferences preoldsum = getSharedPreferences("oldbooksum", Context.MODE_PRIVATE);
        SharedPreferences.Editor preoldsumeditor = preoldsum.edit();
        preoldsumeditor.putInt("oldbooksum", money);
        preoldsumeditor.commit();

        if (oldsum <= 999999999) {
            bookmoneytextView.setText("合計金額は" + oldsum + "円です。");
        } else if (oldsum < 0) {
            oldsum = 0;
            bookmoneytextView.setText("エラーです。");
        } else {
            oldsum = 0;
            bookmoneytextView.setText("エラーです。合計金額が大き過ぎです。");
        }
        intent = new Intent(this, lockActivity.class);
        intent.putExtra("sum", money);
        startActivity(intent);


        money = 0;
    }

    public void intentbutton(View v) {

        goukeiintent();

    }


}
