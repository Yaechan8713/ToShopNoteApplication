package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "sonotaItem")
public class sonotaItem extends Model {
    @Column(name = "sonotaname")
    public String sonotaname;

    public sonotaItem() {
        super();
    }
}
