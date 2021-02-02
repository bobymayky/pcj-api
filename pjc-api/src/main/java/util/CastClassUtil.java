package util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CastClassUtil {


	public static String toString( Object value, String defaultValue ){
		try{
			return toString( value );
		} catch( ClassCastException e ){
			return defaultValue;
		}
	}
	

	public static Date toDate( Object value, Date defaultValue ){
		try{
			return toDate( value );
		} catch( ClassCastException e ){
			return defaultValue;
		}
	}


	public static java.sql.Date toSqlDate( Object value, java.sql.Date defaultValue ){
		try{
			return toSqlDate( value );
		} catch( ClassCastException e ){
			return defaultValue;
		}
	}


	public static Double toDouble( Object value, Double defaultValue ){
		try{
			return toDouble( value );
		} catch( ClassCastException e ){
			return defaultValue;
		}
	}


	public static BigDecimal toBigDecimal( Object value, BigDecimal defaultValue ){
		try{
			return toBigDecimal( value );
		} catch( ClassCastException e ){
			return defaultValue;
		}
	}


	public static Integer toInteger( Object value, Integer defaultValue ){
		try{
			return toInteger( value );
		} catch( ClassCastException | NumberFormatException e ){
			return defaultValue;
		}
	}
	

	public static String toString( Object value ) throws ClassCastException{
		if( value instanceof Integer ){
			return String.valueOf( value );
		} else if( value instanceof Double ){
			return String.valueOf( value );
		} else if( value instanceof Timestamp ){
			return ((Timestamp)value).toString();
		} else if( value instanceof Long ){
			return ((Long)value).toString();
		}
		return (String) value;
	}

	public static boolean toBoolean( Object value ) throws ClassCastException{
		if( !ValidationUtil.isEmpty( value ) ){
			if( value instanceof Integer ){
				return ( value.equals(1) );
			} else if( value instanceof String ){
				value = ((String) value).toLowerCase();
				return ( value.equals( "1" ) || value.equals( "t" ) || value.equals( "true" ) );
			}
		} else {
			return false;
		}
		return (Boolean) value;
	}
	
	public static Date toDate( Object value ) throws ClassCastException{
		if( value instanceof Timestamp ){
			return new Date( ((Timestamp)value).getTime() );
		} else if( value instanceof java.sql.Date ){
			return new Date( ((java.sql.Date)value).getTime() );
		} else if( value instanceof Calendar ){
			return new Date( ((Calendar)value).getTimeInMillis() );
		} else if( value instanceof Integer ){
			return new Date( (Integer)value );
		} else if( value instanceof Long ){
			return new Date( (Long)value );
		}		
		return (Date) value;
	}
	
	public static java.sql.Date toSqlDate( Object value ) throws ClassCastException{
		if( value instanceof Timestamp ){
			return new java.sql.Date( ((Timestamp)value).getTime() );
		} else if( value instanceof java.sql.Date ){
			return new java.sql.Date( ((java.util.Date)value).getTime() );
		} else if( value instanceof Calendar ){
			return new java.sql.Date( ((Calendar)value).getTimeInMillis() );
		} else if( value instanceof Integer ){
			return new java.sql.Date( (Integer)value );
		} else if( value instanceof Long ){
			return new java.sql.Date( (Long)value );
		}  else if( value instanceof String ){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
			try{
				return new java.sql.Date(dateFormat.parse( (String) value ).getTime());
			} catch( ParseException e ){
				throw new ClassCastException();
			}
		}
		return (java.sql.Date) value;
	}
	
	public static Timestamp toTimestamp( Object value ) throws ClassCastException{
		if( value instanceof Date ){
			return new Timestamp( ((Date)value).getTime() );
		} else if( value instanceof java.sql.Date ){
			return new Timestamp( ((java.sql.Date)value).getTime() );
		} else if( value instanceof Integer ){
			return new Timestamp( (Integer)value );
		} else if( value instanceof Long ){
			return new Timestamp( (Long)value );
		}	
		return (Timestamp) value;
	}

	public static Time toTime( Object value ) throws ClassCastException{
		return (Time) value;
	}


	public static Double toDouble( Object value ) throws ClassCastException{
		if ( value instanceof BigInteger ){
			return ( (BigInteger) value ).doubleValue();
		} else if ( value instanceof Long ){
			return ( (Long) value ).doubleValue();
		} else if ( value instanceof String ){
			return Double.parseDouble( (String) value );
		} else if ( value instanceof BigDecimal ){
			return ( (BigDecimal) value ).doubleValue();
		} else if ( value instanceof Integer ){
			return ( (Integer) value ).doubleValue();
		}
		return (Double) value;
	}
	

	public static BigDecimal toBigDecimal( Object value ) throws ClassCastException{
		if ( value instanceof String ){
			return new BigDecimal( (String) value );
		} else if ( value instanceof Double ){
			return new BigDecimal( (Double) value );
		}
		return (BigDecimal) value;
	}
	

	public static Integer toInteger( Object value ) throws ClassCastException{
		if( ValidationUtil.isNull( value ) ){
			return null;
		} else {
			if ( value instanceof BigInteger ){
				return ( (BigInteger) value ).intValue();
			} else if ( value instanceof Long ){
				return ( (Long) value ).intValue();
			} else if ( value instanceof Double ){
				return ((Double) value).intValue();
			} else if ( value instanceof String ){
				return Integer.parseInt( (String) value );
			}
		}
		return (Integer) value;
	}


	public static Long toLong( Object value ) throws ClassCastException{
		if ( value instanceof String ){
			return Long.parseLong( (String) value );
		}
		return (Long) value;
	}


	public static <T extends Enum<T>> T toEnum(Class<T> c, Object value) {
	    if( c != null && value != null && value instanceof String ) {
	        try {
	            return Enum.valueOf(c, ((String)value).trim().toUpperCase());
	        } catch(IllegalArgumentException ex) {
	        }
	    }
	    return null;
	}
	
	

	public static BigInteger toBigInteger( Object value ) throws ClassCastException{
		if( ValidationUtil.isNull( value ) ){
			return null;
		} else {
			if ( value instanceof Long ){
				return BigInteger.valueOf(( (Long) value ).intValue());
			} else if ( value instanceof Integer ){
				return BigInteger.valueOf(((Integer) value).intValue());
			} else if ( value instanceof Double ){
				return BigInteger.valueOf(((Double) value).intValue());
			} else if ( value instanceof String ){
				return BigInteger.valueOf(Integer.parseInt( (String) value ));
			}
		}
		return (BigInteger) value;
	}
	
}
