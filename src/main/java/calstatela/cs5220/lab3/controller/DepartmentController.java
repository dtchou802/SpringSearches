package calstatela.cs5220.lab3.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import calstatela.cs5220.lab3.model.Department;
import calstatela.cs5220.lab3.model.User;
import calstatela.cs5220.lab3.model.dao.DepartmentDao;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping
    public List<Department> departments(ModelMap models) {
        return departmentDao.getDepartments();
    }
    
    @PatchMapping("/{id}")
    public void update2(@PathVariable Integer id, @RequestBody Map<String, Object> properties) {
    	Department department = departmentDao.getDepartment(id);
        for (String key : properties.keySet()) {
            switch (key) {
            case "name":
            	department.setName((String) properties.get(key));
                break;
            default:
            }
        }
        departmentDao.saveDepartment(department);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer add(@RequestBody Department department) {
    	department = departmentDao.saveDepartment(department);
        return department.getId();
    }
}
