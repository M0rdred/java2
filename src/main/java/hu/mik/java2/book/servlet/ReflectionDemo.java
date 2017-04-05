package hu.mik.java2.book.servlet;

import java.lang.reflect.Field;

import hu.mik.java2.book.bean.Book;

public class ReflectionDemo {

	public static void main(String[] args) {
		try {
			Book book = (Book) Class.forName("hu.mik.java2.book.bean.Book").newInstance();
			System.out.println(book.getId());
			Field id = book.getClass().getDeclaredField("id");
			boolean accessible = id.isAccessible();
			id.setAccessible(true);
			id.set(book, 1500);
			id.setAccessible(accessible);
			System.out.println(book.getId());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
