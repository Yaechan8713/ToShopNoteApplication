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

    int goukei,money,t,firstj,deletelist;

        @Override
        public Dialog onCreateDialog(Bundle sumsaverInterfaceState){



            deletelist = goukei = money = firstj = t =0;

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contentview = inf.inflate(R.layout.sumdialog_setting, null);


            SharedPreferences goukeinum = getActivity().getSharedPreferences("goukei", Context.MODE_PRIVATE);
            goukei = goukeinum.getInt("goukei", 0);

            SharedPreferences firstjnum = getActivity().getSharedPreferences("first",Context.MODE_PRIVATE);
            firstj = firstjnum.getInt("first",0);

            sumedittext = (EditText)contentview.findViewById(R.id.sumedittext);

            builder.setView(contentview);

            builder.setMessage("金額入力")
                    .setPositiveButton(R.string.kakutei, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //okボタンを押した後の処理

                                if (TextUtils.isEmpty(sumedittext.getText())) {
                                    firstj = 1;
                                    SharedPreferences firstjinputnum = getActivity().getSharedPreferences("first",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = firstjinputnum.edit();
                                    editor.putInt("first",firstj);
                                    editor.commit();
                                    money = Integer.valueOf(sumedittext.getText().toString());
                                    fragmentdialogruncode();
                                }

                            fragmentdialogruncode();

                            deletelist = 1;

                        }
                    })

                    .setNegativeButton(R.string.chancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            t++;
                        }
                    });

            SharedPreferences prelist = getActivity().getSharedPreferences("resetlist",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prelist.edit();
            editor.putInt("resetlist",deletelist);
            editor.commit();

            return  builder.create();
        }

//    private Activity activity;
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity;
//    }

    public void fragmentdialogruncode(){



        //合計金額保存

        Intent intent = new Intent(getActivity(),sumActivity.class);
        intent.putExtra("sum",money);
        startActivity(intent);
    }

}

