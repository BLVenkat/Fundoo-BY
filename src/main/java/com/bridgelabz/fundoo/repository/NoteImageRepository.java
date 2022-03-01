package com.bridgelabz.fundoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.entity.NoteImage;

@Repository
public interface NoteImageRepository extends JpaRepository<NoteImage, Long> {

}
