package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "iruiItem")
public class iruiItem extends Model {
    @Column(name = "iruiname")
    public String iruiname;

    public iruiItem() {
        super();
    }
}
