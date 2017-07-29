package reduce.project.yaerei.toshopnote;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2017/05/13.
 */

@Table(name = "bookItem")
public class bookItem extends Model {
    @Column(name = "bookName")
    public String bookname;

    public bookItem(){
        super();
    }
}
