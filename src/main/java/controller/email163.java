package controller;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class email163 {

    autopulic au = new autopulic();
    //打印日志
    private static Logger logger = Logger.getLogger(email163.class);

    WebDriver driver = au.enableChrome2();

    //显示等待20秒
    WebDriverWait wait = new WebDriverWait(driver, 20);
    Actions actions = new Actions(driver);
    Random random = new Random();
    //执行js代码
    JavascriptExecutor js = (JavascriptExecutor)driver;


    //登录网站
    @BeforeClass
    public void openEmail() {
        System.setProperty("webserver.chrome.driver", "\\src\\chromedriver.exe");

        driver.get("https://mail.163.com/");
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //退出驱动
    @AfterClass
    public void quitEmail() {
        driver.quit();
    }

    @Test(priority = 1, description = "登录163邮箱", enabled = true)
    public void login163(){
        driver.switchTo().frame(driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/div/div[4]/div[1]/div[1]/iframe")));
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("163邮箱账号");

        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("163邮箱密码");

        driver.findElement(By.id("dologin")).click();
        logger.info("登录操作已完成---------------------");

    }

    @Test(priority = 2, description = "删除草稿箱邮件", enabled = true)
    public void deleteDraft() {
        //尝试点击
        au.getElementClick(wait,By.cssSelector("ul.js-component-tree.nui-tree > li:nth-child(6) > div"),js);
        //定位邮件
        int ck0 = driver.findElements(By.className("ck0")).size() - 1;
        logger.info("草稿箱邮件数：" + ck0);
        actions.clickAndHold(driver.findElements(By.className("ck0")).get(ck0)).perform();
        actions.contextClick(driver.findElements(By.className("ck0")).get(ck0)).perform();

        //点击删除
        int size1 = driver.findElements(By.className("nui-menu-item-link")).size();
        driver.findElements(By.className("nui-menu-item-link")).get(size1 - 1).click();
        logger.info("草稿箱删除操作完成---------------------------");
    }

    @Test(priority = 3, description = "存草稿箱操作", enabled = true)
    public void saveDraft() {

        driver.findElement(By.id("_mail_tabitem_0_133")).click();

        au.mail(wait,js);

        //草稿箱
        List<WebElement> box = driver.findElements(By.cssSelector("div.js-component-toolbar.nui-toolbar > div:nth-child(3) > div"));
        box.get(box.size()-1).click();

        //取消
        List<WebElement> cancel = driver.findElements(By.cssSelector("div.js-component-toolbar.nui-toolbar > div:nth-child(4) > div"));
        cancel.get(cancel.size()-1).click();
        logger.info("邮件存草稿操作已完成------------------------------");
    }

    @Test(priority = 4, description = "删除已发送邮件", enabled = true)
    public void deleteSendEmail() {
        driver.navigate().refresh();

        driver.findElement(By.id("_mail_tabitem_0_133")).click();
        //尝试点击
        au.getElementClick(wait,By.cssSelector("ul.js-component-tree.nui-tree > li:nth-child(7) > div[hidefocus='hidefocus']"),js);

        //定位已发送邮件
        int ck1 = driver.findElements(By.cssSelector("div.nl0.hA0.ck0")).size() - 1;
        logger.info("已发送邮件条数：" + ck1 + "-----------------");
        actions.clickAndHold(driver.findElements(By.cssSelector("div.nl0.hA0.ck0")).get(0)).perform();
        actions.contextClick(driver.findElements(By.cssSelector("div.nl0.hA0.ck0")).get(0)).perform();

        //点击删除邮件
        int size1 = driver.findElements(By.className("nui-menu-item-link")).size();
        driver.findElements(By.className("nui-menu-item-link")).get(size1 - 1).click();

        logger.info("已发送邮件删除操作完成------------------------------");

    }

    @Test(priority = 5, description = "发送邮件", enabled = true)
    public void sendEmail() {
        driver.findElement(By.id("_mail_tabitem_0_133")).click();
        au.mail(wait,js);
        driver.findElement(By.cssSelector("footer.jp0 > div:first-child > span.nui-btn-text")).click();

        logger.info("发送邮件操作已完成------------------------------");
    }



    @Test(priority = 6, description = "新增联系人(不加分组)")
    public void addNotGroupName() {
        driver.findElement(By.id("_mail_tabitem_1_134")).click();

        By by = By.cssSelector("div.js-component-button.nui-mainBtn.nui-btn.nui-btn-hasIcon.nui-mainBtn-hasIcon > span.nui-btn-text");
        au.getElementClick(wait,by,js);

        //再次点击
        driver.navigate().refresh();
        js.executeScript("arguments[0].click()",driver.findElement(By.cssSelector("div.js-component-button.nui-mainBtn.nui-btn.nui-btn-hasIcon.nui-mainBtn-hasIcon > span.nui-btn-text")));

        //姓名
        WebElement name = driver.findElement(By.id("input_N"));
        name.clear();
        name.sendKeys("姓名"+random.nextInt(100));

        //电子邮箱
        List<WebElement> elements = driver.findElements(By.cssSelector("dl.ou0 > dd.is0 > div.js-component-input.nui-ipt > input.nui-ipt-input"));
        elements.get(1).click();
        elements.get(1).sendKeys("123456789"+random.nextInt(100)+"@qq.com");

        //手机号码
        elements.get(2).click();
        elements.get(2).sendKeys(random.nextInt(100)+"12345600");

        //备注
        driver.findElement(By.id("input_DETAIL")).click();
        driver.findElement(By.id("input_DETAIL")).sendKeys("随便添加测试咯");

        driver.findElement(By.cssSelector("div.nui-msgbox-ft-btns > div.js-component-button.nui-mainBtn.nui-btn > span.nui-btn-text")).click();


    }

    @Test(priority = 7, description = "新增分组")
    public void addGroup() {
        driver.findElement(By.id("_mail_tabitem_1_134")).click();
        driver.navigate().refresh();
        //点击新建联系人
        driver.findElement(By.cssSelector("div.js-component-button.nui-mainBtn.nui-btn.nui-btn-hasIcon.nui-mainBtn-hasIcon  > span.nui-btn-text")).click();

        //点击选择分组
        driver.findElement(By.cssSelector("div.js-component-button.ef0.nui-btn > span.nui-btn-text")).click();

        //点击新建分组
        driver.findElement(By.cssSelector("div.js-component-button.dX0.nui-btn > span.nui-btn-text")).click();

        //输入分组名称
        List<WebElement> elements1 = driver.findElements(By.cssSelector("div.js-component-input.nui-ipt > input.nui-ipt-input"));
        elements1.get(elements1.size() - 1).sendKeys("Liujx" + random.nextInt(100));

        //保存分组
        driver.findElements(By.cssSelector("div.cW0 > div.js-component-button.nui-btn > span.nui-btn-text")).get(0).click();

        //关闭创建分组窗口
        driver.findElements(By.cssSelector("div.js-component-button.nui-mainBtn.nui-btn >span.nui-btn-text")).get(1).click();
        List<WebElement> elements = driver.findElements(By.cssSelector("div.js-component-button.nui-btn > span.nui-btn-text"));
        elements.get(elements.size() - 1).click();
    }

    @Test(priority = 8, description = "新增联系人(需加分组)")
    public void addGroupName() {
        //尝试点击
        au.getElementClick(wait,By.id("_mail_tabitem_1_134"),js);
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("_mail_tabitem_1_134"))).click();
        driver.navigate().refresh();
        driver.findElement(By.cssSelector("div.js-component-button.nui-mainBtn.nui-btn.nui-btn-hasIcon.nui-mainBtn-hasIcon > span.nui-btn-text")).click();

        //姓名
        WebElement name = driver.findElement(By.id("input_N"));
        name.clear();
        name.sendKeys("姓名" + random.nextInt(100));

        //电子邮箱
        List<WebElement> elements = driver.findElements(By.cssSelector("dl.ou0 > dd.is0 > div.js-component-input.nui-ipt > input.nui-ipt-input"));
        elements.get(1).click();
        elements.get(1).sendKeys("123456789" + random.nextInt(100) + "@qq.com");

        //手机号码
        elements.get(2).click();
        elements.get(2).sendKeys(random.nextInt(100) + "12345600");

        //备注
        driver.findElement(By.id("input_DETAIL")).click();
        driver.findElement(By.id("input_DETAIL")).sendKeys("随便添加测试咯");

        //进入分组选择页面
        driver.findElement(By.cssSelector("div.js-component-button.ef0.nui-btn > span.nui-btn-text")).click();

        //选择分组
        List<WebElement> elements1 = driver.findElements(By.cssSelector("span.nui-fIBlock.rM0"));
        elements1.get(0).click();

        //点击保存已选分组
        driver.findElement(By.cssSelector("div.nui-msgbox-ft-btns > div.js-component-button.nui-mainBtn.nui-btn > span.nui-btn-text")).click();

        //点击保存联系人
        driver.findElement(By.cssSelector("div.nui-msgbox-ft-btns > div.js-component-button.nui-mainBtn.nui-btn > span.nui-btn-text")).click();
    }

    @Test(priority = 9,description = "删除第一个联系人",enabled = true)
    public void deleteName(){
        //定位通讯录
        driver.findElement(By.id("_mail_tabitem_1_134")).click();
        //定位所有联系人
        driver.findElement(By.cssSelector("div.js-component-component.nui-tree-item-label.nui-tree-item-label-selected")).click();

        //定位需要删除的联系人,然后删除
        driver.findElement(By.cssSelector("tbody.nui-table-body > tr:nth-child(1) >td:nth-child(2) >span > span > b")).click();
        driver.findElement(By.cssSelector("div.nui-toolbar-item:nth-child(2) > div.js-component-button.nui-btn:nth-child(3)")).click();
        driver.findElements(By.cssSelector("div.js-component-button.nui-mainBtn.nui-btn > span.nui-btn-text")).get(1).click();
        logger.info("删除联系人操作已完成");

    }

    @Test(priority = 10,description = "删除第一个分组",enabled = true)
    public void deleteGroup(){

        //定位通讯录
        driver.findElement(By.id("_mail_tabitem_1_134")).click();

        //定位分组
        int size = driver.findElements(By.cssSelector("ul.js-component-tree.nui-tree > li.js-component-tree.gw0.nui-tree-item-isUnfold.nui-tree-item:nth-of-type(6)")).size();
        if (size == 0 ){
            return;
        }

        //定位许需要删除的分组，然后进行删除
        driver.findElement(By.cssSelector("ul.js-component-tree.nui-tree > li.js-component-tree.gw0.nui-tree-item-isUnfold.nui-tree-item:nth-of-type(6)")).click();
        driver.findElement(By.cssSelector("div.nui-toolbar-item > div.js-component-component  > div:nth-child(2)")).click();
        driver.findElement(By.cssSelector("div.nui-msgbox-ft-btns > div.js-component-button.nui-mainBtn.nui-btn")).click();
        logger.info("删除分组操作已完成");
    }

    @Test(priority = 11, description = "退出163账号",enabled = true)
    public void quit163() {
        driver.navigate().refresh();
        driver.findElement(By.id("spnUid")).click();
        driver.findElement(By.id("_mail_component_78_78")).click();

    }
}