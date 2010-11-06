package com.oslutions.clipboarder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StringUtils {

	public static Object deserialize(String name) throws IOException,
			ClassNotFoundException {
		return deserialize(new File(name));
	}

	public static Object deserialize(File file) throws IOException,
			ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		Object o = in.readObject();
		in.close();
		fileIn.close();
		return o;
	}

	public static void serialize(Object o, File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(o);
		out.close();
		fileOut.close();
	}

	public static void serialize(Object o, String name) throws IOException {
		serialize(o, new File(name));
	}
}
