package EasyExcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class entityJD {
    @ExcelProperty("价格")
    private String price;
    @ExcelProperty("书籍名称")
    private String bookName;
    @ExcelProperty("图片地址")
    private String img;
    @ExcelProperty("商店名称")
    private String shopName;
    @ExcelProperty("所在省")
    private String province;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}