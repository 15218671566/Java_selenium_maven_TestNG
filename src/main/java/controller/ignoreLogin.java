package controller;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ignoreLogin {

    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webserver.chrome.driver", "\\src\\chromedriver.exe");
        driver = new ChromeDriver();

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void testLoginWithCookie() {
        driver.get("https://mail.163.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            Cookie c1 = new Cookie(cookie.getName(), cookie.getValue());
            driver.manage().addCookie(c1);
            System.out.println(cookie.getName() + "\t" + cookie.getValue());
        }
        Cookie cookie1 = new Cookie("", "");
        driver.manage().addCookie(cookie1);
        Cookie cookie2 = new Cookie(".", "");
        driver.manage().addCookie(cookie2);
        Cookie cookie3 = new Cookie("", "");
        driver.manage().addCookie(cookie3);
        driver.manage().window().maximize();
        autopulic au = new autopulic();
        au.mandatorySleep(5000);
        driver.navigate().refresh();
        Set<Cookie> cookies1 = driver.manage().getCookies();
        for (Cookie cookie8 : cookies1) {
            System.out.println("cookie："+cookie8.getName() + "-------" + cookie8.getValue());
        }
        au.mandatorySleep(5000);

    }


}
