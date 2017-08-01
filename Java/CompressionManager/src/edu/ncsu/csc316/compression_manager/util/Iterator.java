package edu.ncsu.csc316.compression_manager.util;

public interface Iterator<E> {
	public boolean hasNext();
	public boolean hasPrevious();
	public E next();
	public E previous();
	public void add(E data);
	public int remove(E data);
}
