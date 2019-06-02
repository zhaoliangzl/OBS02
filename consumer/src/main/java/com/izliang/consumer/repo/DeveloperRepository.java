package com.izliang.consumer.repo;





import com.izliang.consumer.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer,Integer> {

    Developer findDeveloperByUsernameAndPassword(String username, String passord);

    Developer findDeveloperByUsername(String username);

    int countAllByUsername(String username);

    Developer findDeveloperByEamil(String email);

    Integer countAllByEamil(String email);

    Developer findDeveloperByPublicUploadLink(String publicUploadLink);

    Developer findDeveloperByPrivateSecret(String privateSecret);

}
