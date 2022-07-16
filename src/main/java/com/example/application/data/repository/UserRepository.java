package com.example.application.data.repository;

import com.example.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndRole(String username, String role);
    @Query("select i from User u " +
            "where lower(u.role) like lower(concat('%', :searchTerm, '%')) ")
    List<User> search(@Param("searchTerm") String searchTerm);
    //List<User> saveAll(List<User> collect);


}
