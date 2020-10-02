package com.qa.app.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.app.persistence.domain.Guitarist;

@Repository
public interface GuitaristRepo extends JpaRepository<Guitarist,Long>{
	}
