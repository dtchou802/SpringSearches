package calstatela.cs5220.lab3.model.dao;

import java.util.List;

import calstatela.cs5220.lab3.model.Search;

public interface SearchDao {
    List<Search> getSearchesByDepartment(Integer departmentId);

    Search getSearchById(Integer searchId);

    Search saveSearch(Search search);

}

