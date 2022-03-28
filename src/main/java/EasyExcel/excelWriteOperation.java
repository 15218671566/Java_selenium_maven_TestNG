package EasyExcel;

import Jsoup.jsoupJD;
import com.alibaba.excel.EasyExcel;
import org.testng.annotations.Test;

public class excelWriteOperation {


    @Test(description = "把jsoup爬取到的数据进行保存到excel")
    public void simpleWrite() throws Exception {
        String fileName = "e://JD爬虫//" + System.currentTimeMillis() + ".xlsx";
        System.out.println(fileName);
        EasyExcel.write(fileName, entityJD.class).sheet("Liujx测试模板").doWrite(new jsoupJD().crawlerJava());
    }
}
