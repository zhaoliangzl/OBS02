package com.izliang.provider.repo;



import com.izliang.provider.model.Loger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggerRepository extends JpaRepository<Loger,Integer> {

    @Query(nativeQuery=true,value =
            "select * from zl_obs_dev_logger l " +
            "where l.time<:time and l.dev_id = :id order by time DESC limit 7"
    )
    List<Loger> findRecentlyLogByTimeById(@Param("time")long time, @Param("id")int id);

}
