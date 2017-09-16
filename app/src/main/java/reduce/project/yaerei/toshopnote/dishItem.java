package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/05/22.
 */

@Table(name = "dishItem")
public class dishItem extends Model {
    @Column(name = "dishName")
    public String dishname;

    public dishItem() {
        super();
    }

}
