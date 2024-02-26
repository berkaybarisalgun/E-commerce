package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)//as a default it checks h2 database that's why we use it
@Rollback(false)//in order to keep the commmit in (waiting) place
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin=entityManager.find(Role.class,1);//It finds the role that has primary key=1
        User  userMj=new User("michalejordan@gmail.com","1996finals","Michael","Jordan");
        userMj.addRole(roleAdmin);
        User savedUser=repo.save(userMj);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles(){
        User messi=new User("messi@gmail.com","messi","Lionel","Messi");
        Role roleEditor=new Role(3);
        Role roleAssistant=new Role(5);

        messi.addRole(roleEditor);
        messi.addRole(roleAssistant);

        User savedUser=repo.save(messi);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers=repo.findAll();// we used  Iterable is the super type of List. So it's very normal to return Iterable instead of List.
        listUsers.forEach(user -> System.out.println(user));
    }
    @Test
    public void testGetUserById(){
    User userMj=repo.findById(1).get();
        System.out.println(userMj);
    //assertThat(userMj).isNotNull();
    }
    @Test
    public void testUpdateUserDetails(){
        User userMj=repo.findById(1).get();
        userMj.setEnabled(true);
        userMj.setEmail("Iamsuckatbasketball@gmail.com");
        repo.save(userMj);
        System.out.println(userMj);
    }
    @Test
    public void testUpdateUserRoles(){
        User userMj=repo.findById(2).get();
        Role roleEditor=new Role(3);
        Role roleSalesperson=new Role(2);
        userMj.getRoles().remove(roleEditor);//check if I can also write 3 instead of role editor
        userMj.addRole(roleSalesperson);
        repo.save(userMj);

    }
    @Test
    public void testDeleteUser(){
        Integer userId=2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
        String email="Iamsuckatbasketball@gmail.com";
        User user = repo.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id=28;
        Long countById=repo.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser(){
        Integer id=6;
        repo.updateEnabledStatus(id,false);
    }


    @Test
    public void testEnableUser(){
        Integer id=6;
        repo.updateEnabledStatus(id,true);
    }

    @Test
    public void testListFirstPage(){
        int pageNumber=0;
        int pageSize=4;

        Pageable pageable=PageRequest.of(pageNumber,pageSize);

        Page<User> page=repo.findAll(pageable);

        List<User> listUsers=page.getContent();

        listUsers.forEach(user-> System.out.println(user));

        assertThat(listUsers.size()).isEqualTo(pageSize);


    }

    @Test
    public void testSearchUsers(){
        String keyword="bruce";

        int pageNumber=0;
        int pageSize=4;

        Pageable pageable=PageRequest.of(pageNumber,pageSize);

        Page<User> page=repo.findAll(keyword,pageable);

        List<User> listUsers=page.getContent();

        listUsers.forEach(user-> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);


    }







}
