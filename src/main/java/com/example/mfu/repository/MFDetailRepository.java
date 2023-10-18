package com.example.mfu.repository;

import com.example.mfu.entity.MFDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MFDetailRepository extends JpaRepository<MFDetail, Long> {

    Optional<MFDetail> findMFDetailBySchemaId(Long schemaId);

}
