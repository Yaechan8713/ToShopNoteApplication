package reduce.project.yaerei.toshopnote;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by yaerei on 2017/06/24.
 */
public class TextsumDialogFragment extends DialogFragment {

    Intent intent;

    EditText sumedittext;

    int num1,num2,num3,num4,t;

    SharedPreferences pre;
    SharedPreferences.Editor editor;

        @Override
        public Dialog onCreateDialog(Bundle sumsaverInterfaceState){





            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contentview = inf.inflate(R.layout.sumdialog_setting, null);

            num1 = num2 = num3= num4 = 0;

            sumedittext = (EditText)contentview.findViewById(R.id.sumedittext);

            builder.setView(contentview);

            builder.setMessage("金額入力")
                    .setPositiveButton(R.string.kakutei, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //okボタンを押した後の処理

                            pre = getActivity().getSharedPreferences("inputnum1",Context.MODE_PRIVATE);
                            editor = pre.edit();
                            editor.putInt("inputnum1",num1);
                            editor.commit();
                            pre = getActivity().getSharedPreferences("inputnum2",Context.MODE_PRIVATE);
                            editor = pre.edit();
                            editor.putInt("inputnum2",num2);
                            editor.commit();
                            pre = getActivity().getSharedPreferences("inputnum3",Context.MODE_PRIVATE);
                            editor = pre.edit();
                            editor.putInt("inputnum3",num3);
                            editor.commit();
                            pre = getActivity().getSharedPreferences("inputnum3",Context.MODE_PRIVATE);
                            editor = pre.edit();
                            editor.putInt("inputnum3",num3);
                            editor.commit();

                        }
                    })

                    .setNegativeButton(R.string.chancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            num1 = num2 = num3= num4 = 0;
                            t++;
                        }
                    });


            return  builder.create();
        }

//    private Activity activity;
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity;
//    }

}

