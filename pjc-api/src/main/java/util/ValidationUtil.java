package util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class ValidationUtil {


	public static <T> boolean contains( T item, List<T> items ){
		for( T row : items ){
			if(row.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNull(Object object) {
		return object == null;
	}

	
	public static boolean isEmpty(Object value) {
		if( value instanceof String ){
			return isEmpty( (String) value );
		} if( value instanceof Integer ){
			return isEmpty( (Integer) value );
		} if( value instanceof Double ){
			return isEmpty( (Double) value );
		} if( value instanceof Long ){
			return isEmpty( (Long) value );
		} if( value instanceof Date ){
			return isEmpty( (Date) value );
		} if( value instanceof Time ){
			return isEmpty( (Time) value );
		} if( value instanceof Timestamp ){
			return isEmpty( (Timestamp) value );
		}						
		return isNull(value);
	}
	
	public static boolean isEmpty( List<?> value ){
		return isNull( value ) || value.isEmpty();
	}
	
	public static boolean isEmpty(String value) {
		return isNull(value) || value.trim().equals("") || value.trim().isEmpty() || value.equalsIgnoreCase("null");
	}

	public static boolean isEmpty(Integer value) {
		return isNull(value) || value.equals(0);
	}

	public static boolean isEmpty(Double value) {
		return isNull(value) || value.equals(0.0);
	}	

	public static boolean isEmpty(Long value) {
		return isNull(value) || value.equals(0);
	}

	public static boolean isEmpty(Date value) {
		return isNull(value) || value.equals(0);
	}

	public static boolean isEmpty(Time value) {
		if(!isNull(value)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(value.getTime());
			return ( calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0 );
		}
		return true;
	}

	public static boolean isEmpty(Timestamp value) {
		return isNull(value) || value.equals(0);
	}
	
}
