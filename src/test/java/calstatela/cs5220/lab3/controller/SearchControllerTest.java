package calstatela.cs5220.lab3.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import calstatela.cs5220.lab3.model.Search;
import calstatela.cs5220.lab3.model.User;
import calstatela.cs5220.lab3.model.dao.SearchDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SearchControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private SearchDao searchDao;

    @Test
    @Transactional
    public void createSearch() throws Exception {
        mockMvc.perform(
                post("/searches")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"id\": 1,\"position\": \"Associate Professor\", \"committeeChair\":{\"id\":3}, \"departmentId\":{\"id\":5}, \"committeeMembers\":[{\"id\":2},{\"id\":4}]}")
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.position").value("Associate Professor"))
                .andExpect(jsonPath("$.committeeChair.id").value(3))
                // @JsonProperty("departmentId") on search model
                .andExpect(jsonPath("$.departmentId").value(5))
                .andExpect(jsonPath("$.committeeMembers[0].id").value(2))
                .andExpect(jsonPath("$.committeeMembers[1].id").value(4));
    }

    @Test
    public void searchesById() throws Exception{
        int searchId = 6;
        Search search = searchDao.getSearchById(searchId);
        mockMvc.perform(
                get("/searches/" + search.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(search.getId()))
                .andExpect(jsonPath("$.position").value(search.getPosition()))
                .andExpect(jsonPath("$.committeeChair.id").value(search.getCommitteeChair().getId()))
                .andExpect(jsonPath("$.departmentId").value(search.getDepartment().getId()))
                .andExpect(jsonPath("$.committeeMembers[0].id").value(search.getCommitteeMembers().get(0).getId()));
    }

    @Test
    public void searchesByDepartment()throws Exception {
        int departmentId = 5;
        List<Search> search = new ArrayList<Search>();
        search = searchDao.getSearchesByDepartment(departmentId);

        MvcResult result =mockMvc.perform(
                get("/searches/department/" + departmentId)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].departmentId").value(search.get(0).getDepartment().getId()))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();

        ObjectMapper obj = new ObjectMapper();
        String searchToJson = "";
        try{
            searchToJson = obj.writeValueAsString(search);
        }
        catch (IOException error){
            error.printStackTrace();
        }
        assertEquals(searchToJson, responseJson);
    }

    //using param
    @Test
    public void deleteCommittee() throws Exception {
        int SearchId = 6;
        int UserId = 2;

        mockMvc.perform(
                delete("/searches/committeeMem")
                    .param("SearchId",Integer.toString(SearchId))
                    .param("UserId",Integer.toString(UserId))
               )
                .andExpect(status().is2xxSuccessful());

        //Just to make the committee members was actually removed
        List<User> committeeMembers = searchDao.getSearchById(SearchId).getCommitteeMembers();
        boolean isPresent = false;
        for(User mem : committeeMembers){
            if(mem.getId()==UserId)
                isPresent= true;
        }
        assertFalse(isPresent);
    }

    @Test
    @Transactional
    public void addCommittee() throws Exception {
        mockMvc.perform(
                post("/searches/committeeMem")
                        .param("UserId","2")
                        .param("SearchId","6")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(2));
    }
}

//since we are testing we didn't want to add to real database