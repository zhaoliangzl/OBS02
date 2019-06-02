package com.izliang.consumer.repo;




import com.izliang.consumer.model.UploadInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UploadInfoRepository extends JpaRepository<UploadInfo,Integer> {

    UploadInfo findUploadInfoByUploadSecret(String uploadSecret);

    List<UploadInfo> findUploadInfoByMd5(String md5);


}
