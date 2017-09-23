

import java.util.Iterator;


public class MyListImpl implements MyList, ListIterable {
	private int size = 0;
	private Object[] array;
	private boolean process;

	public MyListImpl() {
		array = new Object[1];
	}
	@Override
	public void add(Object e) {
		if (size < array.length) {
			array[array.length - 1] = e;
			size++;
		} else {
			array = expandTheArray(array);
			array[array.length - 1] = e;
			size++;
		}

	}

	public Object[] expandTheArray(Object[] mass) {
		Object[] newMass = new Object[mass.length + 1];
		System.arraycopy(mass, 0, newMass, 0, mass.length);
		return newMass;
	}

	public Object[] narrowTheArray(Object[] mass) {
		Object[] newMass = new Object[mass.length - 1];
		System.arraycopy(mass, 0, newMass, 0, mass.length - 1);
		return newMass;
	}

	@Override
	public void clear() {
		array = new Object[1];
		size = 0;

	}

	@Override
	public boolean remove(Object o) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(o)) {
				deleteByIndex(i);
				return true;
			}
		}
		return false;
	}

	private void deleteByIndex(int indexToDelete) {
		for (int i = indexToDelete; i < array.length - 1; i++) {
			array[i] = array[i + 1];
		}
		size--;
		array = narrowTheArray(array);
	}

	@Override
	public Object[] toArray() {
		return array;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object o) {
		for (Object object : array) {
			if (o.equals(object)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(MyList c) {
		for (int i = 0; i < c.toArray().length; i++) {
			if (!contains(c.toArray()[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<Object> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<Object> {
		protected int currentPosition = -1;
		protected IteratorState state = IteratorState.None;

		public boolean hasNext() {
			return currentPosition + 1 != size() && array[currentPosition + 1] != null;
		}

		public Object next() {
			if (state == IteratorState.PreviousDone) {
				state = IteratorState.NextDone;
				return array[currentPosition];
			}
			Object obj = array[++currentPosition];
			state = IteratorState.NextDone;
			process = true;
			return obj;
		}

		public void remove() {
			if (!process) {
				throw new IllegalStateException();
			}
			MyListImpl.this.remove(array[currentPosition--]);
			process = false;
		}
	}

	@Override
	public ListIterator listIterator() {
		return new ListIteratorImpl();
	}

	private class ListIteratorImpl extends IteratorImpl implements ListIterator {
		
		@Override
		public boolean hasPrevious() {
			return !(currentPosition <= 0);
		}

		@Override
		public Object previous() {
			if (state == IteratorState.NextDone) {
				state = IteratorState.PreviousDone;
				return array[currentPosition];
			}
			Object obj = new Object();
			obj = array[--currentPosition];
			state = IteratorState.PreviousDone;
			process = true;
			return obj;
		}

		@Override
		public void set(Object e) {
			if (process == false) {
				throw new IllegalStateException();
			} else {
				array[currentPosition] = e;
				process = false;
			}
		}

		@Override
		public void remove() {
			if (process = false) {
				throw new IllegalStateException();
			} else {
				deleteByIndex(currentPosition--);
				process = false;
			}
		}
		
	}

	private enum IteratorState {
		None,
		NextDone,
		PreviousDone
	}

	@Override
	public String toString() {
		String str = "[" + array[0];
		for (int i = 1; i < array.length; i++) {
			str += (", " + array[i]);
		}
		str += "]";
		str = str.replace("null", "");
		return str;
	}

}