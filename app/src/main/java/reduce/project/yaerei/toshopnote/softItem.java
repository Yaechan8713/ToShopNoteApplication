package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/06/10.
 */
@Table(name = "softItem")
public class softItem extends Model {
    @Column(name = "softname")
    public String softname;

    public softItem(){
        super();
    }

}
