package EasyExcel;

import com.alibaba.excel.EasyExcel;

import java.util.List;

/**
 * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
 **/
public class DemoDAO {
    public void save(List<entityJD> list) {

        String fileName = "e://JD爬虫//" + System.currentTimeMillis() + ".xlsx";
        System.out.println(fileName);
        EasyExcel.write(fileName, entityJD.class).sheet("Liujx模板1").doWrite(list);
    }
}