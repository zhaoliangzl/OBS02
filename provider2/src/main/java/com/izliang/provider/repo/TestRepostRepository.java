package com.izliang.provider.repo;


import com.izliang.provider.model.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepostRepository extends JpaRepository<TestReport,Integer> {

}
