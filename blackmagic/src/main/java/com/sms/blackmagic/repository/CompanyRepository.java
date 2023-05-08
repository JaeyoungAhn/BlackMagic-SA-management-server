package com.sms.blackmagic.repository;

import com.sms.blackmagic.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyId(Integer companyId);
}
