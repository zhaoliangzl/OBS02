package com.izliang.provider.repo;




import com.izliang.provider.model.tmp.TmpForSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmpForSessionRepository extends JpaRepository<TmpForSession,Integer> {

    TmpForSession findFirstByEmailOrderByIdDesc(String email);

}
