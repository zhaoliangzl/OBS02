package com.izliang.consumer.repo;





import com.izliang.consumer.model.ObsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OBSInfoRepository extends JpaRepository<ObsInfo,Integer> {


}
