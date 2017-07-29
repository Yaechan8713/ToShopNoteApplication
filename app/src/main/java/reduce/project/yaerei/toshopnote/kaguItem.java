package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "kaguItem")
public class kaguItem extends Model {
    @Column(name = "kaguname")

    public String kaguname;

    public kaguItem(){
        super();
    }
}
