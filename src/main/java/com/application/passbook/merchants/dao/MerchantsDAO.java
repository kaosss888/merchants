package com.application.passbook.merchants.dao;

import com.application.passbook.merchants.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Merchants DAO接口
 */
@Repository
public interface MerchantsDAO extends JpaRepository<Merchants, Integer> {

    Merchants findByName(String name);
}
