package com.izliang.provider.repo;



import com.izliang.provider.model.Admin;
import com.izliang.provider.model.Loger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
public interface LoggerRepository extends JpaRepository<Loger,Integer> {

}
