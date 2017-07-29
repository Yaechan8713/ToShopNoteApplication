package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */

@Table(name = "gorakuItem")
public class gorakuItem extends Model {
    @Column(name = "gorakuname")
    public String gorakuname;

    public gorakuItem(){
        super();
    }
}
