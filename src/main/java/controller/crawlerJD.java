package controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.net.URLConnection;


public class crawlerJD {


    autopulic au = new autopulic();
    //打印日志
    private static Logger logger = Logger.getLogger(crawlerJD.class);
    /*新建驱动并屏蔽浏览器通知
    ChromeOptions chromeOptions = au.enableChrome();
    WebDriver driver = new ChromeDriver(chromeOptions);*/

    WebDriver driver = au.enableChrome2();

    //显示等待20秒
    WebDriverWait wait = new WebDriverWait(driver, 20);
    Actions actions = new Actions(driver);
    Random random = new Random();
    //执行js代码
    JavascriptExecutor js = (JavascriptExecutor) driver;


    //登录网站
    @BeforeClass
    public void loginJD() {



        System.setProperty("webserver.chrome.driver", "\\src\\chromedriver.exe");

        driver.get("https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E6%89%8B%E6%9C%BA&pvid=9e3b11d8029a4c3cab1e5af15eedfdd9");
        driver.manage().deleteAllCookies();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        logger.info("JD手机页面打开成功---------------------");
        au.mandatorySleep(5000);
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        au.mandatorySleep(5000);
        System.out.println(driver.getPageSource());

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

        //定位文字
        List<WebElement> text = driver.findElements(By.cssSelector("ul > li> div > div.p-name.p-name-type-2 > a > em"));
        logger.info("手机个数：" + text.size());
        List<WebElement> img = driver.findElements(By.cssSelector("ul > li > div > div.p-img > a > img"));
        logger.info("手机图片个数："+img.size());
        System.out.println();

        if (text.size() != 0 && img.size() != 0) {
            for (int a = 0; a < text.size(); a++) {

                String name = text.get(a).getText();
                System.out.println(name);
                String src = img.get(a).getAttribute("src");
                System.out.println(src);

                if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(src)){

                    System.out.println("可以执行爬虫");
                    System.out.println("----------------------------------------------------------------------------");

                    String newFileName = name + ".jpg";
                    OutputStream os = new FileOutputStream(file.getPath() + "\\" + newFileName);

                    URL url = new URL(src);
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
    }

}
