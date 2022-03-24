package controller;

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
import java.util.concurrent.TimeUnit;


//公共方法封装
public class autopulic {


    WebDriver driver;


    //登录163邮箱
    public void login(WebDriver driver){
        driver.switchTo().frame(driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/div/div[4]/div[1]/div[1]/iframe")));
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("15218671566");

        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("asd5325608");


        driver.findElement(By.id("dologin")).click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    //强制等待
    public void mandatorySleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //显示等待
    public void showWait(WebDriverWait wait,WebElement element,JavascriptExecutor js){
        try{
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            System.out.println("显示等待点击成功："+element);
        }catch (Exception e){
            js.executeScript("arguments[0].click()",element);
            System.out.println("异常："+e);
            System.out.println("显示等待点击失败，采用js点击："+element);
        }

    }

    //截取当前页面
    public void screenImg(WebDriver driver){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); //转换时间格式
        String time = dateFormat.format(Calendar.getInstance().getTime());
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File path = new File("d:\\截图"+time+".png");
        try {
            FileUtils.copyFile(file,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //禁止浏览器弹窗提醒
    public ChromeOptions enableChrome(){
        // 创建HashMap类的一个对象
        Map<String, Object> prefs = new HashMap<String, Object>();
        // 设置提醒的设置，2表示block
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        return options;
        //在 Chrome driver传入options设置
    }


    public WebDriver enableChrome2(){
        // 创建HashMap类的一个对象
        Map<String, Object> prefs = new HashMap<String, Object>();
        // 设置提醒的设置，2表示block
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        return driver;
        //在 Chrome driver传入options设置
    }

    //切换窗口
    public void switchLastHandle(WebDriver driver) {
        //获取当前打开窗口的所有句柄
        Set<String> allHandles = driver.getWindowHandles();
        ArrayList<String> lst = new ArrayList(allHandles);
        String handle = lst.get(lst.size() - 1);
        driver.switchTo().window(handle);
    }

    public WebElement getElement(long timeOutInSecond, By by) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        WebElement element = wait.until(new ExpectedCondition<WebElement>() {
            @NullableDecl
            @Override
            public WebElement apply(@NullableDecl WebDriver webDriver) {
                return webDriver.findElement(by);
            }
        });

        return element;
    }
}
