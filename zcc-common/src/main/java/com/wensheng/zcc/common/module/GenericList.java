package com.wensheng.zcc.common.module;

import java.util.ArrayList;

/**
 * @author chenwei on 4/11/19
 * @project miniapp-backend
 */
public class GenericList <T> extends ArrayList<T>
{
  private Class<T> genericType;

  public GenericList(Class<T> c)
  {
    this.genericType = c;
  }

  public Class<T> getGenericType()
  {
    return genericType;
  }
}
