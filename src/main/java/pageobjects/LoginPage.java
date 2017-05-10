package pageobjects;

import com.esotericsoftware.yamlbeans.YamlException;
import model.CheckPoint;
import model.TestCase;
import org.openqa.selenium.WebDriver;
import util.OperateElement;
import util.YamlRead;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class LoginPage {
    YamlRead yamlRead;
    OperateElement operateElement;
    protected WebDriver driver;

    /***
     * 默认构造函数
     * @param driver
     * @param path yaml配置参数
     */
    public LoginPage(WebDriver driver, String path) {
        this.driver = driver;
        yamlRead = new YamlRead(path);
        operateElement= new OperateElement(this.driver);
    }

    /***
     * 测试步骤
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public void operate() throws YamlException, FileNotFoundException {
        List list = (List) yamlRead.getYmal().get("testcase");
//        System.out.println(list);
        for(Object item: list){
            TestCase testCase = new TestCase();
            testCase.setFind_type((String) ((Map)item).get("find_type"));
            testCase.setElement_info((String) ((Map)item).get("element_info"));
            testCase.setText((String) ((Map)item).get("text"));
            testCase.setOperate_type((String) ((Map)item).get("operate_type"));
            operateElement.operate(testCase);
        }
    }

    /***
     * 检查点
     * @return
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public boolean checkpoint() throws YamlException, FileNotFoundException {
        List list = (List) yamlRead.getYmal().get("check");
//        System.out.println(list);
        for(Object item: list){
            CheckPoint checkPoint = new CheckPoint();
            checkPoint.setElement_info((String) ((Map)item).get("element_info"));
            checkPoint.setFind_type((String) ((Map)item).get("find_type"));
            if (!operateElement.checkElement(checkPoint)) {
                return false;
            }
        }
        return true;
    }

    /***
     * 个人信息页面
     * @return
     */
    public MyInfoPage myInfoPage() {
        System.out.println("进入到个人信息页面");
        return new MyInfoPage(this.driver,"/Myinfo.yaml");
    }
}
