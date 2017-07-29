package reduce.project.yaerei.toshopnote;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int t;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t = 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.root_layout);

        switch(menu.getItemId()) {
            case R.id.homemenu:
                new AlertDialog
                        .Builder(MainActivity.this)
                        .setTitle("エラ―")
                        .setMessage("ジャンルを選択してください。")
                        .setIcon(R.drawable.chuui)
                        .setPositiveButton(
                                R.string.ok,
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which
                                    ){
                                        t++;
                                    }
                                }).show();
                return true;

            case R.id.menufood:
                Snackbar.make(layout,R.string.snack_txxt2,Snackbar.LENGTH_LONG).show();
                foodintent();
                return true;

            case R.id.menubunnbogu:
                Snackbar.make(layout,R.string.snack_txt3,Snackbar.LENGTH_LONG).show();
                bunnboguintent();
                return true;

            case R.id.menubook:
                Snackbar.make(layout,R.string.snack_txt4,Snackbar.LENGTH_LONG).show();
                bookintent();
                return true;

            case R.id.menukadenn:
                Snackbar.make(layout,R.string.snack_txt5,Snackbar.LENGTH_LONG).show();
                kadennintent();
                return true;

            case R.id.menuirui:
                Snackbar.make(layout,R.string.snack_txt6,Snackbar.LENGTH_LONG).show();
                iruiintent();
                return true;

            case R.id.menudish:
                Snackbar.make(layout,R.string.snack_txt7,Snackbar.LENGTH_LONG).show();
                dishintent();
                return true;

            case R.id.menukagu:
                Snackbar.make(layout,R.string.snack_txt8,Snackbar.LENGTH_LONG).show();
                kaguintent();
                return true;

            case R.id.menushokki:
                Snackbar.make(layout,R.string.snack_txt9,Snackbar.LENGTH_LONG).show();
                shokkiintent();
                return true;

            case R.id.menugoraku:
                Snackbar.make(layout,R.string.snack_txt10,Snackbar.LENGTH_LONG).show();
                gorakuintent();
                return true;

            case R.id.menusoft:
                Snackbar.make(layout,R.string.snack_txt11,Snackbar.LENGTH_LONG).show();
                softintent();
                return true;

            case R.id.menusonota:
                Snackbar.make(layout,R.string.snack_txt12,Snackbar.LENGTH_LONG).show();
                sonotaintent();
                return true;

        }
        return super.onOptionsItemSelected(menu);
    }

    public void foodintent(){
        intent = new Intent(this,menufoodActivity.class);
        startActivity(intent);
    }

    public void bunnboguintent(){
        intent = new Intent(this,menubunboguActivity.class);
        startActivity(intent);
    }

    public void bookintent(){
        intent = new Intent(this,bookmenuActivity.class);
        startActivity(intent);
    }

    public void kadennintent(){
        intent = new Intent(this,kadennmenuActivity.class);
        startActivity(intent);
    }

    public void iruiintent(){
        intent = new Intent(this,iruimenuActivity.class);
        startActivity(intent);

    }

    public void dishintent(){
        intent = new Intent(this,dishmenuActivity.class);
        startActivity(intent);

    }

    public void kaguintent(){
        intent = new Intent(this,kagumenuActivity.class);
        startActivity(intent);

    }

    public void shokkiintent(){
        intent = new Intent(this,shokkimenuActivity.class);
        startActivity(intent);
    }

    public void gorakuintent(){
        intent = new Intent(this,gorakumenuActivity.class);
        startActivity(intent);
    }

    public void softintent(){
        intent = new Intent(this,menusoftActivity.class);
        startActivity(intent);
    }

    public void sonotaintent(){
        intent = new Intent(this,sonotamenuActivity.class);
        startActivity(intent);
    }

















    public void sonotaonclick(View v){
        sonotaintent();
    }

    public void softonclick(View v){
        softintent();
    }

    public void gorakuonclick(View v){
        gorakuintent();
    }

    public void shokkionclick(View v){
        shokkiintent();
    }

    public void kaguonclick(View v){
        kaguintent();
    }

    public void dishonclick(View v){
        dishintent();
    }

    public void iruionclick(View v){
        iruiintent();
    }

    public void kadennonclick(View v){
        kadennintent();
    }

    public void bookonclick(View v){
        bookintent();
    }

    public void bunboguonclick(View v){
        bunnboguintent();
    }

    public void foodonclick(View v){
        foodintent();
    }
}
