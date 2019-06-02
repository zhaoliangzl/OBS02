package com.izliang.provider.repo;



import com.izliang.provider.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findAdminByUsernameAndPassword(String username, String password);
}
