package de.saxsys.mvvmfx.contacts.util;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class is used to have a single clock that is used to get time instances. 
 * When somewhere an instance of {@link java.time.LocalDate} or {@link java.time.LocalDateTime} with the current time
 * is needed you should get it by using the 'now' method with the Clock param:
 *  
 *  <pre>
 *      LocalDate now = LocalDate.now(CentralClock.getClock());
 *      
 *  </pre>
 *  
 *  This can help to improve the testablility of the code because in the
 *  test you can define a fixed clock (see {#setFixedClock}).
 * 
 */
public class CentralClock {
	
	private static Clock clock = Clock.systemUTC();
	
	public static Clock getClock(){
		return clock;
	}
	
	public static void setClock(Clock clock){
		CentralClock.clock = clock;
	}

	/**
	 * This method is used to set the clock to a fixed time. This is useful for
	 * tests. This way it's possible to create date/time instances with a predictable value for your tests.
	 */
	public static void setFixedClock(ZonedDateTime zonedDateTime){
		CentralClock.clock = Clock.fixed(zonedDateTime.toInstant(), ZoneId.systemDefault());
	}
	
}
