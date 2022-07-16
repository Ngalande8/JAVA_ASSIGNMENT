package com.example.application.data.repository;

import com.example.application.data.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("select i from Issue i " +
            "where lower(i.equipName) like lower(concat('%', :searchTerm, '%')) ")
    List<Issue> search(@Param("searchTerm") String searchTerm);

    //List<Equipment> saveAll(List<Issue> collect);
}
