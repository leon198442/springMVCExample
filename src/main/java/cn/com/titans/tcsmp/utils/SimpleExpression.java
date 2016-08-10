package cn.com.titans.tcsmp.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;


public class SimpleExpression implements Criterion{  
    
    private String fieldName;       //属性名  
    private Object value;           //对应值  
    private Operator operator;      //计算符  
  
    protected SimpleExpression(String fieldName, Object value, Operator operator) {  
        this.fieldName = fieldName;  
        this.value = value;  
        this.operator = operator;  
    }  
  
    public String getFieldName() {  
        return fieldName;  
    }  
    public Object getValue() {  
        return value;  
    }  
    public Operator getOperator() {  
        return operator;  
    }  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,  
            CriteriaBuilder builder) {  
        Path expression = null;  
        if(fieldName.contains(".")){  
            String[] names = StringUtils.split(fieldName, ".");  
            expression = root.get(names[0]);  
            for (int i = 1; i < names.length; i++) {  
                expression = expression.get(names[i]);  
            }  
        }else{  
            expression = root.get(fieldName);  
        }  
          
        switch (operator) {  
        case EQ:  
            return builder.equal(expression, value);  
        case NE:  
            return builder.notEqual(expression, value);  
        case LIKE:  
            return builder.like((Expression<String>) expression, "%" + value + "%");  
        case LT:  
            return builder.lessThan(expression, (Comparable) value);  
        case GT:  
            return builder.greaterThan(expression, (Comparable) value);  
        case LTE:  
            return builder.lessThanOrEqualTo(expression, (Comparable) value);  
        case GTE:  
            return builder.greaterThanOrEqualTo(expression, (Comparable) value);  
        default:  
            return null;  
        }  
    }

	@Override
	public String toSql() {
		StringBuffer sql = new StringBuffer();
		sql.append(" "+fieldName);
        switch (operator) {  
        case EQ:  
        	sql.append("=");
        	sql.append(value);
        	break;
        case NE:  
        	sql.append("<>");
        	sql.append(value);
        	break;
        case LIKE:  
        	sql.append(" LIKE ");
        	sql.append("%" + value + "%");
        	break;
        case LT:  
        	sql.append("<");
        	sql.append(value);
        	break;
        case GT:  
        	sql.append(">");
        	sql.append(value);
        	break;
        case LTE:  
        	sql.append("<=");
        	sql.append(value); 
        	break;
        case GTE:  
        	sql.append(">=");
        	sql.append(value);
        	break;
        default:  
            return null;  
        } 
        sql.append(" ");
		return sql.toString();
	}  
      
}  
