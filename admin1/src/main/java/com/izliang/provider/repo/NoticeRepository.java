package com.izliang.provider.repo;



import com.izliang.provider.model.Loger;
import com.izliang.provider.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Integer> {

    Page<Notice> findAllByEnableIsTrue(Pageable pageable);


    @Query(value = "select count (id) from Notice ")
    int countAll();
}
