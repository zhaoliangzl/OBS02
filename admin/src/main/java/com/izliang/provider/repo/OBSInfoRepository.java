package com.izliang.provider.repo;




import com.izliang.provider.model.ObsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OBSInfoRepository extends JpaRepository<ObsInfo,Integer> {

    int countAllByAccessKeyIdAndSecretAccessKeyAndClazzId(String ak, String sk, int clazzId);

    List<ObsInfo> findObsInfosByUserId(int userId);

    Page<ObsInfo> findPageObsInfosByUserId(int userId, Pageable pageable);

    List<ObsInfo> findObsInfosByUserIdAndAvailableIsTrue(int userId);

    int countAllByClazzId(int classId);

    /*
    * obs所有的存储空间
    * */
    @Query(value = "select sum (sharpingSize) from ObsInfo ")
    long findAllSaveSize();

    //obs已经使用的的存储空间
    @Query(value = "select sum (useSize) from ObsInfo ")
    long findAllUsedSize();

    //obs总的上行流量
    @Query(value = "select sum (downflow) from ObsInfo ")
    long findAllFlowSie();

    //obs已经使用的下行流量
    @Query(value = "select sum (downedFlow) from ObsInfo ")
    long findAllUseFlowSize();

    //obs可用数量
    @Query(value = "select count (id) from ObsInfo d where d.obsInfoStatus = 3 and d.available = true ")
    int findAllEnbaleNum();



}
