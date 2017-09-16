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
public class sonotamenuActivity extends AppCompatActivity {
    ListView listiew;
    Intent intent;
    EditText editText, sonotaedittext;
    Spinner spinner;
    int t, spint, deletint, onclickint, sonotasum;
    ArrayAdapter<String> adapter;
    TextView sonotatextView;
    SharedPreferences sonotapre;
    SharedPreferences.Editor sonotaeditor;
    String monoedit, spinstr, monototal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonota);
        listiew = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.edittext);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        sonotaedittext = (EditText) findViewById(R.id.sonotaedittext);
        sonotatextView = (TextView) findViewById(R.id.sonotatextView);

        firststring();

        deletint = t = spint = onclickint = sonotasum = 0;

        sonotapre = getSharedPreferences("sonotasum", Context.MODE_PRIVATE);
        sonotasum = sonotapre.getInt("sonotasum", 0);

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
                            .Builder(sonotamenuActivity.this)
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
                            .Builder(sonotamenuActivity.this)
                            .setTitle(R.string.delete)
                            .setMessage("次の項目を削除しますか？\n\n" + item)
                            .setPositiveButton(
                                    R.string.delete,

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            derateItem(item);

                                            adapter.remove(item);

                                            Toast.makeText(sonotamenuActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
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

        List<sonotaItem> items;
        items = new Select().from(sonotaItem.class).execute();
        for (sonotaItem item : items) {
            adapter.insert(item.sonotaname, 0);
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

    public void insertItem(String sontoainsert) {
        sonotaItem item = new sonotaItem();
        item.sonotaname = sontoainsert;
        item.save();
    }

    public void derateItem(String sonotadelate) {
        sonotaItem item = new sonotaItem();
        item = new Select().from(sonotaItem.class).where("sonotaname =?", sonotadelate).executeSingle();
        item.delete();
    }

    public void newint(String sonotanew) {
        sonotaItem item = new sonotaItem();
        item = new Select().from(sonotaItem.class).where("sonotaname =?", sonotanew).executeSingle();
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
                        .Builder(sonotamenuActivity.this)
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
                    .Builder(sonotamenuActivity.this)
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
                    .Builder(sonotamenuActivity.this)
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
                    .Builder(sonotamenuActivity.this)
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
                    .Builder(sonotamenuActivity.this)
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
        int sonotasum1;

        sonotasum1 = Integer.valueOf(sonotaedittext.getText().toString());

        if (sonotaedittext.getText().toString().equals("")) {
            sonotasum1 = 0;

            return;
        }

        sonotasum = sonotasum + sonotasum1;

        sonotaedittext.setText("");

        sonotapre = getSharedPreferences("sonotasum", Context.MODE_PRIVATE);
        sonotaeditor = sonotapre.edit();
        sonotaeditor.putInt("sonotasum", sonotasum);
        sonotaeditor.commit();

        hyouji();

        intent = new Intent(this, sumActivity.class);
        intent.putExtra("sum", sonotasum);
        startActivity(intent);
    }

    public void hyouji() {

        String texthyouji;

        if (sonotasum <= 999999999) {
            texthyouji = "合計金額は" + sonotasum + "円です。";
        } else if (sonotasum < 0) {
            sonotasum = 0;
            texthyouji = "エラーです。";
        } else {
            sonotasum = 0;
            texthyouji = "エラーです。合計金額が大き過ぎです。";
        }

        sonotatextView.setText(texthyouji);

    }

    public void intentbutton(View v) {
        goukeiintent();
    }
}
