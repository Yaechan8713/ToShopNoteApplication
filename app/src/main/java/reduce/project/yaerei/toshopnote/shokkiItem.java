package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "shokkiItem")
public class shokkiItem extends Model {
    @Column(name = "shokkiname")
    public String shokkiname;

    public shokkiItem() {
        super();
    }
}
