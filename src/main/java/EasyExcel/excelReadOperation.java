package EasyExcel;

import com.alibaba.excel.EasyExcel;
import org.testng.annotations.Test;

public class excelReadOperation {

    @Test(description = "读取excel文件并把读取内容再保存到excel")
    public void simpleRead() {
        String fileName = "E:\\JD爬虫\\1648477657613.xlsx";
        EasyExcel.read(fileName, entityJD.class, new DemoDataListener()).sheet().doRead();
    }
}
