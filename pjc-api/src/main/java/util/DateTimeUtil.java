package util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {
	
	public final static int MILLISECOND = 1;
	public final static int SECOND = 2;
	public final static int MINUTE = 3;
	public final static int HOUR = 4;
	public final static int DAY = 5;
	public final static int MONTH = 6;
	public final static int YEAR = 7;
	public final static int WEEKDAY = 8;

	public static Date getCurrentDate(){
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	public static Time getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return new Time( calendar.getTimeInMillis() );
	}
	
	public static Time resetSeconds( Time time ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis( time.getTime() );
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return new Time(calendar.getTimeInMillis());
	}
	
	public static Timestamp resetHours( Timestamp date ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis( date.getTime() );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return new Timestamp(calendar.getTimeInMillis());
	}
	
	public static TimeZone getDefaultTimeZone(){
		return TimeZone.getDefault();
	}
	
	public static void setDefaultTimeZone( TimeZone timeZone ){
		TimeZone.setDefault( timeZone );
	}
	
	public static int getTimeZoneOffset( TimeZone timeZone ){
		int serverOffset = timeZone.getRawOffset();
		return ((serverOffset/1000)/60)/60;		
	}
	
	public static int calculateTimeZoneOffsetDifference( int timeZone1, int timeZone2 ){
		int timeZoneDifference = 0;
		if( timeZone1 > timeZone2 ){
			timeZoneDifference = timeZone2 - timeZone1;
		} else {
			timeZoneDifference = timeZone1 - timeZone2;
		}
		return ( timeZoneDifference < 0 ) ? timeZoneDifference * (-1) : timeZoneDifference;
	}
	
	public static int getDefaultTimeZoneOffset(){
		return getTimeZoneOffset( getDefaultTimeZone() );		
	}
	
	public static boolean inDaylightTime( TimeZone timeZone, Date date ){
		return timeZone.inDaylightTime( date );
	}
	
	public static boolean inDaylightTime( Date date ){
		return getDefaultTimeZone().inDaylightTime( date );
	}
	
	public static java.sql.Date synchronizeDate( java.sql.Date unsynchronizedDate, int localOffset ){
		return synchronizeDate( getDefaultTimeZone(), unsynchronizedDate, localOffset );
	}
	
	public static Date synchronizeDate( Date unsynchronizedDate, int localOffset ){
		return synchronizeDate( getDefaultTimeZone(), unsynchronizedDate, localOffset );
	}
	
	public static Timestamp synchronizeDate( Timestamp unsynchronizedDate, int localOffset ){
		return synchronizeDate( getDefaultTimeZone(), unsynchronizedDate, localOffset );
	}
	
	public static java.sql.Date synchronizeDate( TimeZone timeZone, java.sql.Date unsynchronizedDate, int localOffset ){
		int serverOffset = getTimeZoneOffset( timeZone );
		int timezoneDifference = calculateTimeZoneOffsetDifference( serverOffset, localOffset );
		if( timezoneDifference != 0 ){
			java.sql.Date newValue = new java.sql.Date( unsynchronizedDate.getTime() );
			java.sql.Date synchronizedDate = new java.sql.Date( DateTimeUtil.dateIncrement( newValue, timezoneDifference, DateTimeUtil.HOUR ).getTime() );
			if( inDaylightTime( timeZone, synchronizedDate )  ) {
				synchronizedDate = new java.sql.Date( DateTimeUtil.dateIncrement( synchronizedDate, 1, DateTimeUtil.HOUR ).getTime() );	
			}
			return synchronizedDate;
		} else {
			return unsynchronizedDate;
		}
	}
	
	public static Date synchronizeDate( TimeZone timeZone, Date unsynchronizedDate, int localOffset ){
		int serverOffset = getTimeZoneOffset( timeZone );
		int timezoneDifference = calculateTimeZoneOffsetDifference( serverOffset, localOffset );
		if( timezoneDifference != 0 ){
			Date newValue = new Date( unsynchronizedDate.getTime() );
			Date synchronizedDate = new Date( DateTimeUtil.dateIncrement( newValue, timezoneDifference, DateTimeUtil.HOUR ).getTime() );
			if( inDaylightTime( timeZone, synchronizedDate ) ) {
				synchronizedDate = new Date( DateTimeUtil.dateIncrement( synchronizedDate, 1, DateTimeUtil.HOUR ).getTime() );	
			}
			return synchronizedDate;
		} else {
			return unsynchronizedDate;
		}
	}
	
	public static Timestamp synchronizeDate( TimeZone timeZone, Timestamp unsynchronizedDate, int localOffset ){
		int serverOffset = getTimeZoneOffset( timeZone );
		int timezoneDifference = calculateTimeZoneOffsetDifference( serverOffset, localOffset );
		Timestamp newValue = new Timestamp( unsynchronizedDate.getTime() );
		Timestamp synchronizedDate = new Timestamp( DateTimeUtil.dateIncrement( newValue, timezoneDifference, DateTimeUtil.HOUR ).getTime() );
		if( timezoneDifference != 0 ){
			if( inDaylightTime( timeZone, synchronizedDate )  ) {
				synchronizedDate = new Timestamp( DateTimeUtil.dateIncrement( synchronizedDate, 1, DateTimeUtil.HOUR ).getTime() );	
			}
			return synchronizedDate;
		} else {
			return unsynchronizedDate;
		}
	}
	
	public static Timestamp getCurrentDateTime(){
		Calendar calendar = Calendar.getInstance();
	    return new Timestamp( calendar.getTimeInMillis() );
	}
	
	public static int countWeekendDays( Date date1, Date date2 ){		
		int result = 0;
		if( getDatePart( date1, DateTimeUtil.WEEKDAY ).equals( Calendar.SATURDAY ) || getDatePart( date1, DateTimeUtil.WEEKDAY ).equals( Calendar.SUNDAY ) ){
			result = result+1;
		}
		int dateDifference = Long.valueOf( getDifference( date2, date1, DateTimeUtil.DAY ) ).intValue();
		if( dateDifference > 0 ){
			int counter = 1;
			Date referenceDate = null;
			while( counter <= dateDifference ){
				referenceDate = dateIncrement(date1, counter, DateTimeUtil.DAY );
				if( getDatePart( referenceDate, DateTimeUtil.WEEKDAY ).equals( Calendar.SATURDAY ) || getDatePart( referenceDate, DateTimeUtil.WEEKDAY ).equals( Calendar.SUNDAY ) ){
					result++;
				}
				counter++;
			}
		}
		return result;
	}
	
	public static Timestamp timestampDecrement( Timestamp dateTime, Integer decrementValue, int datePart ){
		return new Timestamp( dateDecrement( dateTime, decrementValue, datePart ).getTime() );
	}
	
	public static Timestamp timestampIncrement( Timestamp dateTime, Integer incrementValue, int datePart ){
		return new Timestamp( dateIncrement( dateTime, incrementValue, datePart ).getTime() );
	}	
	
	public static Date dateDecrement( Date date, Integer decrementValue, int datePart ){
		return dateIncrement( date, (decrementValue * -1), datePart );
	}
	
	public static Calendar toCalendar( Date date ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static Date dateIncrement( Date date, Integer incrementValue, int datePart ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch ( datePart ) {
			case MILLISECOND:
				calendar.add( Calendar.MILLISECOND, incrementValue );
				break;
			case SECOND:
				calendar.add( Calendar.SECOND, incrementValue );
				break;
			case MINUTE:
				calendar.add( Calendar.MINUTE, incrementValue );
				break;
			case HOUR:
				calendar.add( Calendar.HOUR, incrementValue );
				break;
			case DAY:
				calendar.add( Calendar.DATE, incrementValue );
				break;
			case MONTH:
				calendar.add( Calendar.MONTH, incrementValue );
				break;
			case YEAR:
				calendar.add( Calendar.YEAR, incrementValue );
				break;
			case WEEKDAY:
				break;
			default:
				break;
		}
		return calendar.getTime();
	}
	
	public static long getDifference( Date date1, Date date2, int datePart ){
		Calendar calendar = Calendar.getInstance();
		switch (datePart) {		
			case MILLISECOND:
				return date1.getTime()-date2.getTime();
			case SECOND:
				return (date1.getTime()-date2.getTime())/1000;
			case MINUTE:
				return (date1.getTime()-date2.getTime())/(1000*60);
			case HOUR:
				return (date1.getTime()-date2.getTime())/(1000*3600);
			case DAY:
				return (date1.getTime()-date2.getTime())/(3600000*24);
			case MONTH:				
				calendar.setTime(date1);
				int month1 = calendar.get(Calendar.MONTH);
				calendar.setTime(date2);
				int month2 = calendar.get(Calendar.MONTH);
				long diffYear = getDifference( date1, date2, DateTimeUtil.YEAR );
				return ( (diffYear * 12) + (month2 - month1) );
			case YEAR:
				calendar.setTime(date1);
				int year1 = calendar.get(Calendar.YEAR);
				calendar.setTime(date2);
				int year2 = calendar.get(Calendar.YEAR);
				return (year2 - year1);
			default:
				break;
		}
		return 0;
	}
	
	public static Object getDatePart( Date date, int datePart ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( ( ValidationUtil.isEmpty(date) ? getCurrentDateTime() : date ) );
		switch (datePart) {
			case MILLISECOND:
				return calendar.getTimeInMillis();
			case SECOND:
				return calendar.get( Calendar.SECOND );
			case MINUTE:
				return calendar.get( Calendar.MINUTE );
			case HOUR:
				return calendar.get( Calendar.HOUR );
			case DAY:
				return calendar.get( Calendar.DAY_OF_MONTH );
			case MONTH:
				return calendar.get( Calendar.MONTH );
			case YEAR:
				return calendar.get( Calendar.YEAR );
			case WEEKDAY:
				return calendar.get( Calendar.DAY_OF_WEEK );				
		}
		return null;
	}
	
	public static String format( Date date, String format ){
		return new SimpleDateFormat( format, new Locale( "pt", "BR" ) ).format(date);
	}
	
	public static long getAge( Date date, Date baseDate ) {
		long age = getDifference( date, baseDate, DateTimeUtil.YEAR );
		Date newDate = dateIncrement( date, (int) age, DateTimeUtil.YEAR );
		if ( baseDate.before(newDate) ) {
			age--;
		}
		return age;
	}

	public static long getAge(Date date) {
		return getAge( date, getCurrentDate() );
	}
	
	public static boolean dateEquals( Date date1, Date date2 ){
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis( date1.getTime() );
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis( date2.getTime() );
		if( calendar1.get( Calendar.DAY_OF_MONTH ) == calendar2.get( Calendar.DAY_OF_MONTH ) ){
			if( calendar1.get( Calendar.MONTH ) == calendar2.get( Calendar.MONTH ) ){
				if( calendar1.get( Calendar.YEAR ) == calendar2.get( Calendar.YEAR ) ){
					return true;
				}
			}
		}
		return false;		
	}
		
}