package selenium;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


//公共方法封装
public class autopulic {


    WebDriver driver;

    //强制等待
    public void mandatorySleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //截取当前页面
    public void screenImg(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); //转换时间格式
        String time = dateFormat.format(Calendar.getInstance().getTime());
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File path = new File("E:\\JD爬虫\\"+time+".png");
        try {
            FileUtils.copyFile(file,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //chrome浏览器设置
    public WebDriver disableChrome(){
        //关闭浏览器弹窗
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        //后台运行浏览器
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        return driver;
    }

    //切换窗口
    public void switchLastHandle(WebDriver driver) {
        Set<String> allHandles = driver.getWindowHandles();
        ArrayList<String> lst = new ArrayList(allHandles);
        String handle = lst.get(lst.size() - 1);
        driver.switchTo().window(handle);
    }


    //显示等待点击
    public void getElementClick(WebDriverWait wait, By by,JavascriptExecutor js) {
        try {
            WebElement element = wait.until(new ExpectedCondition<WebElement>() {
                @NullableDecl
                @Override
                public WebElement apply( WebDriver webDriver) {
                    return webDriver.findElement(by);
                }
            });
            element.click();
            System.out.println("apply点击成功");
        }catch (Exception e){
            //screenImg();
            System.out.println("异常："+e);
            js.executeScript("arguments[0].click()",driver.findElement(by));
            System.out.println("apply点击失败，采用js点击："+driver.findElement(by));
        }
    }

    //邮件存草稿及发送公共方法
    public void mail(WebDriverWait wait,JavascriptExecutor js){
        driver.findElement(By.xpath("/html/body/div[1]/nav/div[1]/ul/li[2]")).click();
        System.out.println("写信定位成功");

        //尝试点击
        getElementClick(wait,By.className("nui-editableAddr-ipt"),js);
        driver.findElement(By.className("nui-editableAddr-ipt")).sendKeys("1259375229@qq.com");
        System.out.println("发送邮箱定位成功");
        driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[1]/section/header/div[2]/div[1]/div/div/input")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[1]/section/header/div[2]/div[1]/div/div/input")).sendKeys("公共方法");
        System.out.println("主题定位成功");

        driver.switchTo().frame(driver.findElement(By.className("APP-editor-iframe")));
        System.out.println("进入iframe");
        driver.findElement(By.className("nui-scroll")).click();
        driver.findElement(By.className("nui-scroll")).sendKeys("Liujx一定可以的");
        driver.switchTo().defaultContent();
        System.out.println("退出iframe");
    }

}
