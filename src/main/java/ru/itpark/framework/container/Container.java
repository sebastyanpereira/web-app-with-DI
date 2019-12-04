package ru.itpark.framework.container;

import javax.naming.NamingException;
import java.util.Map;

public interface Container {
  Map<Class, Object> init() throws NamingException;
}
