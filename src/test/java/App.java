import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.service.IEmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:mvc.xml")
public class App {
    @Autowired
    private IEmployeeService employeeService;

    @Test
    public void test() {

        for (int i = 0; i < 15; i++) {
            Employee e = new Employee();
            e.setUsername("爸爸");
            e.setRealname("father");
            e.setPassword("1");
            e.setTel("007");
            e.setEmail("e");
            e.setHireDate(new Date());
            e.setState(true);
            e.setAdmin(true);
            employeeService.saveOrUpdate(e);
        }
    }
}
