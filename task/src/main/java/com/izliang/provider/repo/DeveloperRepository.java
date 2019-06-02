package com.izliang.provider.repo;




import com.izliang.provider.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DeveloperRepository extends JpaRepository<Developer,Integer> {

    Developer findDeveloperByUsernameAndPassword(String username, String passord);

    Developer findDeveloperByUsername(String username);

    int countAllByUsername(String username);

    Developer findDeveloperByEamil(String email);

    Integer countAllByEamil(String email);

    Developer findDeveloperByPublicUploadLink(String publicUploadLink);

    Developer findDeveloperByPrivateSecret(String privateSecret);


    @Query(value = "select count (id) from Developer ")
    int countAll();
}
