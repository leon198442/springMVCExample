package cn.com.titans.tcsmp.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.Entity;

public class JPAUtil {

	
    @SuppressWarnings("unchecked")
	public static <T> Class<T> getEntityClass(Class<T> entity, int index){
        if (null == entity) return null;
        if (Object.class.equals(entity)) return (Class<T>)Object.class;
       
        ParameterizedType type = (ParameterizedType)entity.getGenericSuperclass();
        if (null == type) return (Class<T>)Object.class;
        Type[] types = type.getActualTypeArguments();
        if (null == types || types.length == 0) return (Class<T>)Object.class;
        if (index > 0) {
            return (Class<T>)(index < types.length ? types[index] : types[types.length - 1]);
        } else {
            return (Class<T>)types[0];
        }
    }
    public static <T> Class<T> getEntityClass(Class<T> entity){
        return (Class<T>)getEntityClass(entity, 0);
    }
    
    /** 
     * 获取实体的名称 
     * @param <T> 
     * @param entityClass 实体类 
     * @return 
     */  
    public static <T> String getEntityName(Class<T> entityClass) {  
        String entityname = entityClass.getSimpleName();  
        Entity entity = entityClass.getAnnotation(Entity.class);  
        if(entity.name() != null && !"".equals(entity.name())) {  
            entityname = entity.name();  
        }  
        return entityname;  
    }  
}
