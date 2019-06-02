package com.izliang.provider.repo;



import com.izliang.provider.model.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzRepository extends JpaRepository<Clazz,Integer> {

    @Query(value = "select count (id) from Clazz")
    int countAll();

}
