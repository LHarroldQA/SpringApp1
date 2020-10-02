package com.qa.app.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.app.persistence.domain.Band;

@Repository
public interface BandRepo extends JpaRepository<Band,Long>{

}
