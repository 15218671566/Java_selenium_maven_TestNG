package selenium;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Random;

/*TestNG参数化*/
public class variableProvider {

    @DataProvider(name = "providerLogin")
    public Object[][] providerLogin(){
        return new Object[][] {{ "账号","密码" }};
    }



    @DataProvider(name = "provideData")
    public Object[][] provideData(Method method) {
        Object[][] result = null;
        if (method.getName().equals("addGroup")) {
            //result = new Object[][] {{ "addGroup分组" }};
            result = new Object[][]{{"Liujx" + new Random().nextInt(100)}};
        } else if (method.getName().equals("addNoGroupName")) {
            //result = new Object[][] {{"addNoGroupName联系人"}};
            result = new Object[][]{{"姓名"+new Random().nextInt(100)}};
        }
        return result;

    }

}
