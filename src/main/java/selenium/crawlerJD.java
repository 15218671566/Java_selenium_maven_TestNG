package selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.net.URLConnection;

//爬取京东图片及文字
public class crawlerJD {


    autopulic au = new autopulic();
    //打印日志
    private static Logger logger = Logger.getLogger(crawlerJD.class);

    WebDriver driver = au.enableChrome2();
    //执行js代码
    JavascriptExecutor js = (JavascriptExecutor) driver;


    //登录网站
    @BeforeClass
    public void loginJD() {

        System.setProperty("webserver.chrome.driver", "\\src\\chromedriver.exe");

        driver.get("https://search.jd.com/Search?keyword=Java&enc=utf-8&wq=Java&pvid=13c2d73b1f894d4cb90398a72422c951");
        driver.manage().deleteAllCookies();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        logger.info("JD手机页面打开成功---------------------");
        au.mandatorySleep(5000);
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

    }

    //退出驱动
    @AfterClass
    public void quitChrome() {
        driver.quit();
    }


    @Test(priority = 1)
    public void downloadJD() throws IOException {
        File file = new File("e://JD爬虫");

        if (!file.exists()) {

            file.mkdir();

        }

        WebElement j_goodsList = driver.findElement(By.id("J_goodsList"));
        List<WebElement> elements = j_goodsList.findElements(By.cssSelector("ul.gl-warp.clearfix > li"));

        for (WebElement a : elements){
            String img = a.findElement(By.cssSelector("div.p-img> a > img")).getAttribute("data-lazy-img");
            String bookName = a.findElement(By.cssSelector("div.p-name > a > em")).getText();
            Random ran = new Random();
            if (bookName.contains("/")){
                bookName = ran.nextInt(1000) + "";
            }

            String newFileName = bookName + ".jpg";
            OutputStream os = new FileOutputStream(file.getPath() + "\\" + newFileName);

            URL url = new URL(img);
            URLConnection con = url.openConnection();
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
