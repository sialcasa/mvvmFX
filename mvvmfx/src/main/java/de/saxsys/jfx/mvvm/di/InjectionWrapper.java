package de.saxsys.jfx.mvvm.di;

/**
 * Wrapper to encapsulate functionality provided by the dependeny injection framework.
 * It is used to separate the code of the mvvm framework from the used DI-Framework.
 * 
 * @author manuel.mauky
 *
 */
public interface InjectionWrapper {
	
	/**
	 * Returns an instance of the given class type. 
	 */
	<T> T getInstance(Class<T> type);

}
