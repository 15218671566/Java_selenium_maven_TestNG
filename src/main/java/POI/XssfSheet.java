package POI;

import org.apache.poi.xssf.usermodel.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XssfSheet {
    @Test(description = "比较excel内两个sheet不一致的数据")
    public void contrastExcel() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("E:\\JD爬虫\\1648477684343.xlsx"));
        XSSFSheet sheet1 = xssfWorkbook.getSheetAt(0);
        XSSFSheet sheet2 = xssfWorkbook.getSheetAt(1);
        XSSFWorkbook xssfWorkbook1 = new XSSFWorkbook();
        XSSFSheet sheet3 = xssfWorkbook1.createSheet("sheet3");
        XSSFRow row;
        XSSFCell cell;
        ArrayList<String> onlyIdList = new ArrayList<>();

        int lastRowNum = sheet2.getLastRowNum()+1;
        int lastCellNum = sheet2.getRow(0).getLastCellNum();
        for (int i = 0; i < lastRowNum; i++) {
            onlyIdList.add(sheet2.getRow(i).getCell(0).toString());
        }
        String id;
        for (int rowId = 0; rowId < lastRowNum; rowId++) {

            ArrayList<String> sheet1List = new ArrayList<>();
            ArrayList<String> sheet2List = new ArrayList<>();
            row = sheet3.createRow(rowId);

            for (int cellId = 0; cellId < lastCellNum; cellId++) {
                cell = row.createCell(cellId);

                id = sheet1.getRow(rowId).getCell(0).toString();
                boolean flag = onlyIdList.contains(id);
                //System.out.println("布尔值：" + contains);
                if (flag) {
                    sheet1List.add(sheet1.getRow(rowId).getCell(cellId).toString());
                    sheet2List.add(sheet2.getRow(rowId).getCell(cellId).toString());
                }else {
                    //写入sheet3
                    //System.out.println("============不相等");
                    cell.setCellValue(sheet1.getRow(rowId).getCell(cellId).toString());
                }
            }
            for (int cellId1 = 0; cellId1 < lastCellNum; cellId1++) {
                if (!sheet1List.equals(sheet2List)){
                    cell = row.createCell(cellId1);
                    cell.setCellValue(sheet1List.get(cellId1));
                }
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\JD爬虫\\Liujx写入数据.xlsx"));
        xssfWorkbook1.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("数据写入成功");
    }


    @Test(description = "保存excel内的图片并命名")
    public void saveImg() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("E:\\JD爬虫\\Liujx_picture.xlsx"));
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        List<XSSFPictureData> picList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        List<XSSFShape> shapes = sheetAt.getDrawingPatriarch().getShapes();
        System.out.println("包含图片的行数为："+shapes.size());
        for (int i = 0 ; i < shapes.size(); i++){
            XSSFCell cell = sheetAt.getRow(i+1).getCell(0);

            String s = cell.toString();
            System.out.println(s);
            String[] split = s.split("\\.");
            System.out.println("length:"+split.length);
            String splitID = "";
            if (split.length > 0){
                splitID = split[0];
                System.out.println("唯一id："+splitID);
            }
            idList.add(splitID);

            XSSFShape xssfShape = shapes.get(i);
            //图片所在的位置
            XSSFClientAnchor anchor1 = (XSSFClientAnchor) xssfShape.getAnchor();
            int row1 = anchor1.getRow1();
            short col1 = anchor1.getCol1();
            System.out.println("图片所在行列为："+row1+","+col1);

            if (xssfShape instanceof XSSFPicture) {
                XSSFPicture pic = (XSSFPicture) xssfShape;
                XSSFPictureData picData = pic.getPictureData();
                picList.add(picData);
            }
            System.out.println();//换行

        }
        //保存图片
        FileOutputStream out;
        for (int a = 0; a < shapes.size(); a++){
            XSSFPictureData xssfPictureData = picList.get(a);
            byte[] data = xssfPictureData.getData();
            out = new FileOutputStream("E:\\JD爬虫\\"+idList.get(a)+ ".jpeg");
            out.write(data);
        }
    }
}
