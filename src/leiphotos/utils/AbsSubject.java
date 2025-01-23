package leiphotos.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author antonialopes
 *
 * @param <E>
 * 
 * Represents objects that are being observed by a list of 
 * listeners of events of type E.
 * 
 */
public abstract class AbsSubject<E extends Event> implements Subject<E> {
	
	protected List<Listener<E>> listeners = new ArrayList<>();
	
	protected AbsSubject() {}
	
	/**
	 * Emits a given event to the listeners
	 * 
	 * @param e event that occurred
	 */
	@Override
	public void emitEvent(E e) {
		for (Listener<E> o : listeners) {
			o.processEvent(e);
		}
	}
	
	/**
	 * Registers a new listener
	 * 
	 * @param obs listener to be added 
	 */
	@Override
	public void registerListener(Listener<E> obs) {
		listeners.add(obs);
	}
	
	/**
	 * Removes the registry of the given listener
	 * 
	 * @param obs listener to be removed
	 */
	@Override
	public void unregisterListener(Listener<E> obs) {
		listeners.remove(obs);
	}

}
