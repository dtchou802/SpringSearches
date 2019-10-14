package calstatela.cs5220.lab3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import calstatela.cs5220.lab3.model.Search;
import calstatela.cs5220.lab3.model.User;
import calstatela.cs5220.lab3.model.dao.SearchDao;
import calstatela.cs5220.lab3.model.dao.UserDao;

@RestController
@RequestMapping("/searches")
public class SearchController {

    @Autowired
    private SearchDao searchDao;
    @Autowired
    private UserDao userDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Search createSearch(@RequestBody Search search) {
        Search searchRet = searchDao.saveSearch(search);
        return searchRet;
    }

    @GetMapping("/{id}")
    public Search searchesById(@PathVariable Integer id) {
        return searchDao.getSearchById(id);
    }

    @GetMapping("/department/{id}")
    public List<Search> searchesDepartment(@PathVariable Integer id) {
    	List<Search> searchList= searchDao.getSearchesByDepartment(id);
    	return searchList;
    }

    @PostMapping("/committeeMem")
    public User addCommittee(@RequestParam("SearchId") int SearchId, @RequestParam("UserId") int UserId) {
    	Search search = searchDao.getSearchById(SearchId);
    	User user = userDao.getUser(UserId);
    	search.getCommitteeMembers().add(user);
    	Search searchRet = searchDao.saveSearch(search);
    	return user;
    }

    @DeleteMapping("/committeeMem")
    public void deleteCommittee(@RequestParam("SearchId") int SearchId, @RequestParam("UserId") int UserId) {
        Search search = searchDao.getSearchById(SearchId);
        User user = userDao.getUser(UserId);
        search.getCommitteeMembers().remove(user);
        searchDao.saveSearch(search);
    }
}
