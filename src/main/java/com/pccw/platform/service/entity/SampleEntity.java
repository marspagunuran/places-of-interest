/** */
package com.pccw.platform.service.entity;

import com.pccw.platform.service.persist.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** @author dxiong */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "CONSOLE_CORE_SAMPLE_ENTITY",
    indexes = {
      @Index(columnList = "key_name", unique = true),
      @Index(columnList = "entity_id", unique = true),
      @Index(columnList = "company_id", unique = false),
    })
public class SampleEntity extends AbstractEntity {

  @Column(name = "company_id", nullable = false)
  private String companyId;

  @Column(name = "name")
  private String name;

  @Column(name = "description", columnDefinition = "text", length = 2000)
  private String description;
}
