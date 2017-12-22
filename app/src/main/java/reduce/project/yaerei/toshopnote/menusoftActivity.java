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
public class menusoftActivity extends AppCompatActivity {
    ListView listiew;
    Intent intent;
    EditText editText, softsumeditText,softresurch;
    Spinner spinner;
    TextView softsumtextView,mode;
    int t, spint, deletint, onclickint, softsum;
    ArrayAdapter<String> adapter;
    String monoedit, spinstr, monototal;
    SharedPreferences softpre;
    SharedPreferences.Editor softeditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft);

        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        softsumeditText = (EditText) findViewById(R.id.softsumeditText);
        softsumtextView = (TextView) findViewById(R.id.softsumtextView);
        mode = (TextView)findViewById(R.id.mode);
        softresurch = (EditText)findViewById(R.id.softresurch);

        firststring();

        deletint = t = spint = softsum = onclickint = 0;

        softpre = getSharedPreferences("softsum", Context.MODE_PRIVATE);
        softsum = softpre.getInt("softsum", 0);

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
                            .Builder(menusoftActivity.this)
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
                            .Builder(menusoftActivity.this)
                            .setTitle(R.string.delete)
                            .setMessage("次の項目を削除しますか？\n\n" + item)
                            .setPositiveButton(
                                    R.string.delete,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            derateItem(item);

                                            adapter.remove(item);

                                            Toast.makeText(menusoftActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
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

        List<softItem> items;
        items = new Select().from(softItem.class).execute();
        for (softItem item : items) {
            adapter.insert(item.softname, 0);
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

    public void insertItem(String softinsert) {
        softItem item = new softItem();
        item.softname = softinsert;
        item.save();
    }

    public void derateItem(String softdelate) {
        softItem item = new softItem();
        item = new Select().from(softItem.class).where("softname =?", softdelate).executeSingle();
        item.delete();
    }

    public void newint(String softnew) {
        softItem item = new softItem();
        item = new Select().from(softItem.class).where("softname =?", softnew).executeSingle();
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
                finish();
                intent = new Intent(this, gorakumenuActivity.class);
                startActivity(intent);
                return true;

            case R.id.menusoft:
                new AlertDialog
                        .Builder(menusoftActivity.this)
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
                    .Builder(menusoftActivity.this)
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
                    .Builder(menusoftActivity.this)
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

    public void delete() {
        if (deletint == 0) {
            String deletemode = getString(R.string.delete) + "モード";

            new AlertDialog
                    .Builder(menusoftActivity.this)
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
                    .Builder(menusoftActivity.this)
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
        int softsum1;

        if (softsumeditText.getText().toString().equals("")) {
            softsum1 = 0;

            return;
        }

        softsum1 = Integer.valueOf(softsumeditText.getText().toString());

        softsum = softsum + softsum1;

        softsumeditText.setText("");

        softpre = getSharedPreferences("softsum", Context.MODE_PRIVATE);
        softeditor = softpre.edit();
        softeditor.putInt("softsum", softsum);
        softeditor.commit();

        hyouji();
        intent = new Intent(this, sumActivity.class);
        intent.putExtra("sum", softsum);
        startActivity(intent);
    }

    public void hyouji() {

        String texthyouji;

        if (softsum <= 999999999) {
            texthyouji = "合計金額は" + softsum + "円です。";
        } else if (softsum < 0) {
            softsum = 0;
            texthyouji = "エラーです。";
        } else {
            softsum = 0;
            texthyouji = "エラーです。合計金額が大き過ぎです。";
        }

        softsumtextView.setText(texthyouji);

    }

    public void intentbutton(View v) {
        goukeiintent();
    }

    public void resurch(View v){
        if(deletint == 0){

            String resurchstring = softresurch.getText().toString();

            softItem item = new Select().from(softItem.class).where("softname = ?",resurchstring).executeSingle();

            String resurchdeishitem,resurchkekka;

            resurchkekka = "検索結果";

            if(item == null){
                new AlertDialog.Builder(menusoftActivity.this)
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
                resurchdeishitem = item.softname;

                new AlertDialog.Builder(menusoftActivity.this)
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

//            Context context = kagumenuActivity;
        }else if(deletint == 1){
            new AlertDialog.Builder(menusoftActivity.this)
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
        softresurch.setText("");
    }

    public void modetext(){
        if(deletint == 0){
            mode.setText("追加モード");
        }else if(deletint == 1){
            mode.setText("削除モード");
        }
    }
}
