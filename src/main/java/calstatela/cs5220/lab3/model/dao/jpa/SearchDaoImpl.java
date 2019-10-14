package calstatela.cs5220.lab3.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import calstatela.cs5220.lab3.model.Search;
import calstatela.cs5220.lab3.model.dao.SearchDao;

@Repository
public class SearchDaoImpl implements SearchDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Search getSearchById(Integer id) {
        return entityManager.find(Search.class, id);
    }

    @Override
    public List<Search> getSearchesByDepartment(Integer departmentId) {
        return entityManager.createQuery("from Search s where s.department.id = :departmentId", Search.class)
                .setParameter("departmentId", departmentId)
        		.getResultList();
    }

    @Override
    @Transactional
    public Search saveSearch(Search search) {
        return entityManager.merge(search);
    }

}
