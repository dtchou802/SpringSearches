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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import calstatela.cs5220.lab3.model.User;
import calstatela.cs5220.lab3.model.dao.UserDao;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> products(ModelMap models) {
        return userDao.getUsers();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer add(@RequestBody User user) {
    	user = userDao.saveUser(user);
        return user.getId();
    }
    
    @PatchMapping("/{id}")
    public void update2(@PathVariable Integer id, @RequestBody Map<String, Object> properties) {
        User user = userDao.getUser(id);
        for (String key : properties.keySet()) {
            switch (key) {
            case "name":
            	user.setName((String) properties.get(key));
                break;
            default:
            }
        }
        userDao.saveUser(user);
    }
    @PutMapping("/{id}")
    public void update1(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        user = userDao.saveUser(user);
    }
}
