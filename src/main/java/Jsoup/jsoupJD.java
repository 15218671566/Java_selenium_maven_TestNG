package Jsoup;

import EasyExcel.entityJD;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class jsoupJD {

    @Test
    public List<entityJD> crawlerJava() throws Exception {

        Document document = Jsoup.connect("https://search.jd.com/Search?keyword=Java&enc=utf-8&wq=Java&pvid=13c2d73b1f894d4cb90398a72422c951").get();

        ArrayList<entityJD> list = new ArrayList<>();

        Element j_goodsList = document.getElementById("J_goodsList");
        Elements li = j_goodsList.getElementsByTag("li");

        for (Element el : li){
            //价格
            String price = el.getElementsByClass("p-price").text();
            //文字
            String bookName = el.select("div.p-name > a > em").text();
            //图片
            String img = el.select("div.p-img> a > img").attr("data-lazy-img");
            //店名称
            String shopName = el.select("div.p-shopnum > a").attr("title");
            //所在省
            String province = el.select("div.p-stock.hide").attr("data-province");

            entityJD entityJD = new entityJD();
            entityJD.setPrice(price);
            entityJD.setBookName(bookName);
            entityJD.setImg(img);
            entityJD.setShopName(shopName);
            entityJD.setProvince(province);
            list.add(entityJD);
        }

        return list;
    }


    @Test(description = "下载JD图片到本地")
    public void downloadJava() throws Exception {

        File file = new File("e://JD爬虫");
        if (!file.exists()) {
            file.mkdir();
        }

        Document document = Jsoup.connect("https://search.jd.com/Search?keyword=java&enc=utf-8&wq=java&pvid=81085ff4f9db4bd4aebbe8866fa40eab").get();

        Element j_goodsList = document.getElementById("J_goodsList");
        Elements li = j_goodsList.getElementsByTag("li");

        for (Element el : li){
            //文字
            String bookName = el.select("div.p-name > a > em").text();
            //图片
            String img = el.select("div.p-img> a > img").attr("data-lazy-img");

            Random ran = new Random();
            if (bookName.contains("/")){
                bookName = ran.nextInt(1000) + "";
            }


            //获取图片链接后，保存图片至本地
            String newFileName = bookName + ".jpg";
            OutputStream os = new FileOutputStream(file.getPath() + "//" + newFileName);

            System.out.println(img);
            URL url2 = new URL("https:"+img);
            URLConnection con = url2.openConnection();
            InputStream is = con.getInputStream();

            int len;
            byte[] bs = new byte[1024];
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();

        }

    }



}
