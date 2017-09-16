package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */

@Table(name = "kadennItem")
public class kadennItem extends Model {
    @Column(name = "kadennname")

    public String kadennname;

    public kadennItem() {
        super();
    }
}
