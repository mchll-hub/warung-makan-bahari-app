package com.enigmacamp.wmb.util;

import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
   private final Validator validator;

   public void validate(Object object){
       Set<ConstraintViolation<Object>> result = validator.validate(object);
       if (!result.isEmpty()){
           throw new ConstraintViolationException(result);
       }
   }
}
