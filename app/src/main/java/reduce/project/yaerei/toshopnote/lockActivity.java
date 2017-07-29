package reduce.project.yaerei.toshopnote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by yaerei on 2017/07/08.
 */
public class lockActivity extends AppCompatActivity {

//    CustomView cView;

    private LinearLayout.LayoutParams blp;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout varlayout = new LinearLayout(this);
        button = (Button)findViewById(R.id.button);
        setContentView(varlayout);

        Intent intent = new Intent(this,sumActivity.class);
        startActivity(intent);

        float scale = getResources().getDisplayMetrics().density;
        int buttonWitdh = (int)(250*scale);
        int buttonHeight = (int)(100*scale);

        blp = new LinearLayout.LayoutParams(buttonWitdh,buttonHeight);
        int margins = (int)(20*scale);
        blp.setMargins((int)(5*scale),(int)(50*scale),(int)(50*scale),(int)(20*scale));
        button.setLayoutParams(blp);










    }
}