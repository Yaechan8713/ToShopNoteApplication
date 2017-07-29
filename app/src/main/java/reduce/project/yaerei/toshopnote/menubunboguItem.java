package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "menubunboguItem")
public class menubunboguItem extends Model {
    @Column(name = "menubunboguname")
    public String menubunboguname;

    public menubunboguItem(){
        super();
    }
}
