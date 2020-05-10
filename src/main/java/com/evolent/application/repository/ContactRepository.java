package com.evolent.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evolent.application.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
