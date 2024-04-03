package com.pccw.platform.service.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.pccw.platform.service.entity.SampleEntity;
import com.pccw.platform.service.model.SampleDataModel;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class SampleServiceTest {

  @Test
  void renders() {
    SampleService service = new SampleService();
    List<SampleDataModel> renders = service.renders(List.of(new SampleEntity()));
    assertThat(renders).isNotNull();
    SampleDataModel render = service.render(new SampleEntity());
    assertThat(render).isNotNull();
  }
}
