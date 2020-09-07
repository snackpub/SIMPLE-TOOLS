package com.snackpub.core.activiti.config;

import org.activiti.engine.impl.cfg.IdGenerator;

public class ActivitiIdGenerator
  implements IdGenerator
{
  @Override
  public String getNextId()
  {
    return String.valueOf(UniqueIdUtil.genId());
  }
}
