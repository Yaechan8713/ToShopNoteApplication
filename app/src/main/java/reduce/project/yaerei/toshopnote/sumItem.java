package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/27.
 */

@Table(name="sumItems")
public class sumItem extends Model {
    @Column(name="sumName")
    public String sumname;

    public sumItem(){
        super();
    }

}
