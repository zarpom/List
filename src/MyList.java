

import java.util.Iterator;

public interface MyList {
	void add(Object e);

	void clear();

	boolean remove(Object o);

	Object[] toArray();

	int size();

	boolean contains(Object o);

	boolean containsAll(MyList c);

	Iterator<Object> iterator();

	ListIterator listIterator();
   
}