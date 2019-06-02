package com.izliang.provider.repo;




import com.izliang.provider.model.Helper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HelperRepository extends JpaRepository<Helper,Integer> {

    List<Helper> findAllByHelperClassId(int helperClassId);

    void deleteAllByHelperClassId(int helperClassId);

    @Query(value = "select count (id) from Helper ")
    int countAll();
}
