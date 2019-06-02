package com.izliang.provider.repo;




import com.izliang.provider.model.ObsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OBSInfoRepository extends JpaRepository<ObsInfo,Integer> {

    int countAllByAccessKeyIdAndSecretAccessKeyAndClazzId(String ak, String sk, int clazzId);

    List<ObsInfo> findObsInfosByUserId(int userId);

    Page<ObsInfo> findPageObsInfosByUserId(int userId, Pageable pageable);

    List<ObsInfo> findObsInfosByUserIdAndAvailableIsTrue(int userId);

}
