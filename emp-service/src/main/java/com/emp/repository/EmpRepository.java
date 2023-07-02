package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.EmpEntity;

@Repository
public interface EmpRepository extends JpaRepository<EmpEntity, Integer>{

}
