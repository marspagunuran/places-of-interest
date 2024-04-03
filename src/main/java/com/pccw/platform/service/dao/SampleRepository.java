/** */
package com.pccw.platform.service.dao;

import com.pccw.platform.service.entity.SampleEntity;
import com.pccw.platform.service.persist.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/** @author dxiong */
public interface SampleRepository extends AbstractEntityRepository<SampleEntity> {

  @Query(
      "select e from #{#entityName} e where (:company_id is null or e.companyId = :company_id)"
          + " and ( :deleted is null or e.deleted = :deleted )")
  @Transactional(readOnly = true)
  Page<SampleEntity> search(
      @Param("company_id") String companyId, @Param("deleted") Boolean deleted, Pageable pageable);
}
