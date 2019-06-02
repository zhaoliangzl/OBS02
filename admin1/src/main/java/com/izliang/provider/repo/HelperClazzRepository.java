package com.izliang.provider.repo;




import com.izliang.provider.model.HelperClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HelperClazzRepository extends JpaRepository<HelperClass,Integer> {

    @Query(value = "select count (id) from HelperClass")
    int countAll();

}
