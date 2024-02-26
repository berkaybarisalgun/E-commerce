package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User,Integer>,CrudRepository<User,Integer> {
    @Query("select u FROM User u Where u.email=:email")
     User getUserByEmail(@Param("email") String email); //This section little bit complicated I gotta make sure that I fully Understand this part.

     Long countById(Integer id);

     @Query("SELECT u FROM User u where CONCAT(u.id,' ',u.email,' ',u.firstName,' ', u.lastName, ' ' ) LIKE %?1%")
     public Page<User> findAll(String keyword, Pageable pageable);

    @Query("update User u SET u.enabled=?2 WHERE u.id=?1")
    @Modifying
     void updateEnabledStatus(Integer id,boolean enabled);
}
