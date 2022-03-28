package Listener;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class TestNGListener extends TestListenerAdapter {
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        super.onTestSuccess(iTestResult);
        TestNGRetry testNGRetry = (TestNGRetry)iTestResult.getMethod().getRetryAnalyzer();
        testNGRetry.reSetCount();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        super.onTestFailure(iTestResult);
        // 每次dataProvider中的参数跑完一次，就重置一次当前的重跑次数，恢复到默认值，保证每个失败的用例都能重跑设置的次数。
        TestNGRetry testNGRetry = (TestNGRetry)iTestResult.getMethod().getRetryAnalyzer();
        testNGRetry.reSetCount();
    }
}
