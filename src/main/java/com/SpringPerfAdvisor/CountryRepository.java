package com.SpringPerfAdvisor;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface CountryRepository extends JpaRepository<Country, Integer> {


	List<Country> findByCountryName(String countryName);

	@Query(value = "SELECT * FROM COUNTRY WHERE COUNTRY_NAME LIKE :cl", nativeQuery = true)
	List<Country> SelByDescrizioneLike(@Param("cl") String cl);
	
	//Query JPQL
	@Query(value = "FROM Country WHERE countryName LIKE :cl")
	List<Country> SelByDescrizioneLikeJPQL(@Param("cl") String cl);

	
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM COUNTRY WHERE COUNTRY_NAME = :cl", nativeQuery = true)
	void DelRowCountryName(@Param("cl") String cl);
	

}
