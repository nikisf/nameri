package com.softuni.repository;

import com.softuni.model.entity.Job;
import com.softuni.model.entity.RealEstate;
import com.softuni.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, String> {



    List<RealEstate> findAllByUsername(String userId);

}
