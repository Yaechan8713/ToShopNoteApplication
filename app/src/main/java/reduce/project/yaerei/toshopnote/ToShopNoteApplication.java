package reduce.project.yaerei.toshopnote;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by yaerei on 2017/08/30.
 */
public class ToShopNoteApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
