package com.pccw.platform.service.controller;

import com.pccw.platform.service.dto.AdminCreateSampleRequest;
import com.pccw.platform.service.dto.UpdateSampleRequest;
import com.pccw.platform.service.model.Paging;
import com.pccw.platform.service.model.SampleDataModel;
import com.pccw.platform.service.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "${server.rest.base:}/admin/samples/v1",
    produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin", description = "Sherpa Agent Access Only")
@RequiresRoles(
    logical = Logical.OR,
    value = {"ADMIN", "SUPER_ADMIN", "USER"})
public class AdminSampleController {

  @Autowired private SampleService service;

  @Operation(summary = "Search entities")
  @GetMapping()
  public Paging<SampleDataModel> search(
      @RequestParam(required = false) String companyId,
      @RequestParam(required = false) Boolean deleted,
      @RequestParam(required = false, defaultValue = Paging.PAGE_START_INDEX_STR) int page,
      @RequestParam(required = false, defaultValue = Paging.PAGE_SIZE_DEFAULT_STR) int size) {
    return service.search(companyId, deleted, Paging.toPageable(page, size));
  }

  @Operation(summary = "Find a entity details")
  @GetMapping(path = "/{id}")
  public SampleDataModel findOne(@PathVariable("id") String id) {
    return service.findOne(id);
  }

  @Operation(summary = "Delete a entity")
  @DeleteMapping(path = "/{id}")
  public SampleDataModel delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

  @Operation(summary = "Update a entity")
  @PatchMapping(path = "/{id}")
  public SampleDataModel update(@PathVariable("id") String id, @Valid UpdateSampleRequest request) {
    return service.update(id, request);
  }

  @Operation(summary = "create a entity")
  @PostMapping
  public SampleDataModel create(@Valid AdminCreateSampleRequest request) {
    return service.create(request.getCompanyId(), request);
  }
}
