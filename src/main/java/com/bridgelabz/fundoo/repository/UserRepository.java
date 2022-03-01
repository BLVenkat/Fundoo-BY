package com.bridgelabz.fundoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
}
