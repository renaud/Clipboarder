package com.oslutions.clipboarder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author ren
 * 
 */
public class Clipboard {

	public static final String SER_PATH = "data/clipboarder/";

	private static void init() throws IOException {
		File file = new File(SER_PATH);
		if (!file.exists()) {
			file.mkdirs();
			System.out.println("file://" + file.getAbsolutePath());
		}
	}

	/**
	 * @param o
	 *            object
	 * @return a uuid to later retrieve this object
	 */
	public static String put(Object o) {
		String uuid = UUID.randomUUID().toString();
		return put(o, uuid, false);
	}

	/**
	 * @param o
	 *            object
	 * @param name_or_path
	 *            a unique name or path for the stored object
	 * @return a uuid to later retrieve this object
	 */
	public static String put(Object o, String name_or_path) {
		return put(o, name_or_path, false);
	}

	/**
	 * @param o
	 *            object
	 * @param name_or_path
	 *            a unique name or path for the stored object
	 * @param overwrite
	 *            if an object with this name already exists
	 * @return a uuid to later retrieve this object
	 */
	public static String put(Object o, String name_or_path, boolean overwrite) {
		return put(o, name_or_path, overwrite, true);
	}

	/**
	 * @param o
	 *            object
	 * @param name_or_path
	 *            a unique name or path for the stored object
	 * @param overwrite
	 *            if an object with this name already exists
	 * @param writeHtml
	 *            whether to write this object as html
	 * @return a uuid to later retrieve this object
	 */
	public static String put(Object o, String name_or_path, boolean overwrite,
			boolean writeHtml) {

		try {
			init();
			File file = new File(SER_PATH + name_or_path + ".ser");
			if (file.exists() && !overwrite) {
				System.out.println("file already exists: "
						+ file.getAbsolutePath());
				return null;// TODO exception?
			}

			file.getParentFile().mkdirs();

			StringUtils.serialize(o, file);

			if (writeHtml) {
				String html = ReflectionToStringBuilder.toString(o,
						ToStringStyle.MULTI_LINE_STYLE, true, true);

				FileWriter outFile = new FileWriter(SER_PATH + name_or_path
						+ ".html");
				PrintWriter out = new PrintWriter(outFile);
				out.println("<html><body><pre>");
				out.println(html);
				out.print("</pre></body></html>");
				out.close();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name_or_path;
	}

	public static boolean exists(String name_or_path_or_uuid) {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(SER_PATH + name_or_path_or_uuid + ".ser");
		return file.exists();
	}

	public static Object get(String name_or_path_or_uuid) {
		File file = new File(SER_PATH + name_or_path_or_uuid + ".ser");
		if (!file.exists()) {
			System.out.println("file does not exists: "
					+ file.getAbsolutePath());
			return null;// TODO exception?
		}

		try {
			return StringUtils.deserialize(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean delete(String name_or_path_or_uuid) {
		File file = new File(SER_PATH + name_or_path_or_uuid + ".ser");
		return file.delete();
	}
}
