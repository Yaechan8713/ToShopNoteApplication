package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "menufoodItem")
public class menufoodItem extends Model {
    @Column(name = "menufoodname")
    public String menufoodname;

    public menufoodItem(){
        super();
    }
}
