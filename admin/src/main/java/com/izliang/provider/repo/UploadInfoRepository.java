package com.izliang.provider.repo;



import com.izliang.provider.model.UploadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadInfoRepository extends JpaRepository<UploadInfo,Integer> {

    UploadInfo findUploadInfoByUploadSecret(String uploadSecret);

    List<UploadInfo> findUploadInfoByMd5(String md5);

    @Query(value = "select count (id) from UploadInfo")
    int findAllObsInfoNum();

    @Query(nativeQuery=true,value="select count(*) from (select count(uploadinfo0_.id) a from zl_obs_upload_info as uploadinfo0_ group by uploadinfo0_.md5) a")
    int findReadFileNum();

}
