/** */
package com.pccw.platform.service.service;

import com.pccw.platform.service.dao.SampleRepository;
import com.pccw.platform.service.dto.CreateSampleRequest;
import com.pccw.platform.service.entity.SampleEntity;
import com.pccw.platform.service.model.AppProperties;
import com.pccw.platform.service.model.Paging;
import com.pccw.platform.service.model.SampleDataModel;
import com.pccw.platform.service.persist.AbstractEntityRepository;
import com.pccw.platform.service.utils.DataMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author dxiong */
@Service
@Slf4j
public class SampleService
    extends com.pccw.platform.service.persist.AbstractService<SampleDataModel, SampleEntity> {

  @Autowired private SampleRepository repo;

  @Autowired private AppProperties config;

  @Transactional(readOnly = true)
  public Paging<SampleDataModel> search(String companyId, Boolean deleted, Pageable pageable) {
    Page<SampleEntity> entities = repo.search(companyId, deleted, pageable);
    return Paging.toPaging(this.renders(entities.getContent()), entities.getPageable());
  }

  @Override
  public SampleEntity findEntityById(String id) {
    return this.assertEntityExist(
        this.repo::findByEntityIdOrKeyName, id, "Can't find a entity via id=" + id);
  }

  @Transactional
  public SampleDataModel create(String companyId, CreateSampleRequest request) {
    SampleEntity entity = new SampleEntity();
    entity.setName(request.getName());
    entity.setDescription(request.getDescription());
    entity.setCompanyId(companyId);
    this.onCreate(entity);

    entity = repo.save(entity);

    return this.render(entity);
  }

  @Override
  public List<SampleDataModel> renders(List<SampleEntity> entities) {
    return entities.stream()
        .map(Mappers.getMapper(DataMapper.class)::map)
        .collect(Collectors.toList());
  }

  @Override
  public SampleDataModel render(SampleEntity entity) {
    return Mappers.getMapper(DataMapper.class).map(entity);
  }

  @Override
  public AbstractEntityRepository<SampleEntity> getDao() {
    return this.repo;
  }
}
