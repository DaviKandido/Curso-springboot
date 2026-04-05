package br.com.cursospringboot.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  /**
   * Copia as propriedades não-nulas do objeto de origem para o objeto de destino.
   * <p>
   * Este método usa o {@link BeanUtils} do Spring para copiar as propriedades da origem para o destino.
   * Ele só copia as propriedades que não são nulas no objeto de origem.
   * <p>
   * Este método é útil para copiar propriedades não-nulas de um objeto para outro, como ao criar um novo objeto a partir de um objeto existente.
   * @param source o objeto de origem
   * @param target o objeto de destino
   */
  public static void copyNonNullProperties(Object source, Object target) {
      BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);

    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>(); 

    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }
  
}
