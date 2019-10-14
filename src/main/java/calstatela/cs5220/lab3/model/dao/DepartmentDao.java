package calstatela.cs5220.lab3.model.dao;

import java.util.List;

import calstatela.cs5220.lab3.model.Department;

public interface DepartmentDao {

	Department getDepartment(Integer id);

    List<Department> getDepartments();

    Department saveDepartment(Department department);
}

