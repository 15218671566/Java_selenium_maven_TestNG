package controller;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class tenCenOperation {
    autopulic au = new autopulic();
    //打印日志
    private static Logger logger = Logger.getLogger(tenCenOperation.class);
    //新建驱动并屏蔽浏览器通知
    WebDriver driver = au.enableChrome2();
    //显示等待20秒
    WebDriverWait wait = new WebDriverWait(driver, 20);
    //执行js代码
    JavascriptExecutor js = (JavascriptExecutor)driver;


    //登录网站
    @BeforeClass
    public void openTenCen() {
        System.setProperty("webserver.chrome.driver", "\\src\\chromedriver.exe");

        driver.get("https://www.baidu.com/");
        driver.manage().deleteAllCookies();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        logger.info("登录操作已完成---------------------");
    }

    //退出驱动
    @AfterClass
    public void quitTenCen() {
        driver.quit();
    }

    @Test(priority = 1,description = "进入腾讯课堂",enabled = true)
    public void intoTencen(){
        //百度句柄
        String baiduHandle = driver.getWindowHandle();

        WebElement kw = driver.findElement(By.id("kw"));
        kw.click();
        kw.sendKeys("腾讯课堂");

        driver.findElement(By.id("su")).click();

        System.out.println("百度句柄："+baiduHandle);
        driver.findElement(By.partialLinkText("-综合性在线终身学习平台")).click();

        au.switchLastHandle(driver);
        String tencenHandle = driver.getWindowHandle();
        System.out.println("腾讯句柄："+tencenHandle);

        driver.findElement(By.id("js_login")).click();

        au.mandatorySleep(3000);
        driver.switchTo().frame(2);
        driver.findElement(By.id("switcher_plogin")).click();

        WebElement u = driver.findElement(By.id("u"));
        u.click();
        u.sendKeys("腾讯课堂账号");

        WebElement p = driver.findElement(By.id("p"));
        p.click();
        p.sendKeys("腾讯课堂密码");

        driver.findElement(By.id("login_button")).click();
        driver.switchTo().defaultContent();
        System.out.println("登录成功");
    }

    @Test(priority = 2,description = "搜索Java自动化测试")
    public void search(){
        WebElement js_keyword = driver.findElement(By.id("js_keyword"));
        js_keyword.click();
        js_keyword.sendKeys("Java自动化测试");

        driver.findElement(By.cssSelector("i.icon-font.i-search-bold")).click();
    }
}
