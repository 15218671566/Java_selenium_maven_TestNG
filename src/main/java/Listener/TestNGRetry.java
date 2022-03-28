package Listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestNGRetry  implements IRetryAnalyzer {
    private int maxRetryCount = 2;
    private int currentRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (currentRetryCount<=maxRetryCount){
            currentRetryCount++;
            return true;
        }
        return false;
    }
    public void reSetCount(){
        currentRetryCount=1;
    }
}
