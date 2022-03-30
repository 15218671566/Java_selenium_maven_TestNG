package selenium;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

/*TestNG参数化*/
public class variableProvider {

    @DataProvider(name = "providerLogin")
    public Object[][] providerLogin(){
        return new Object[][] {{ "163邮箱账号","163邮箱密码" }};
    }



    @DataProvider(name = "provideData")
    public Object[][] provideData(Method method) {
        Object[][] result = null;
        if (method.getName().equals("addGroup")) {
            result = new Object[][] {{ "addGroup分组" }};
        } else if (method.getName().equals("addNoGroupName")) {
            result = new Object[][] {{"addNoGroupName联系人"}};
        }
        return result;

    }

}
