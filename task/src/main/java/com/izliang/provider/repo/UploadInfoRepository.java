package com.izliang.provider.repo;



import com.izliang.provider.model.UploadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadInfoRepository extends JpaRepository<UploadInfo,Integer> {

    UploadInfo findUploadInfoByUploadSecret(String uploadSecret);

    List<UploadInfo> findUploadInfoByMd5(String md5);

}
