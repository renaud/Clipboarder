package com.oslutions.clipboarder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ClipboarderTest {

	@Test
	public void testUUID() throws Exception {
		UUID randomUUID = UUID.randomUUID();
		System.out.println(randomUUID);
	}

	@Test
	public void testSimple() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("hello");
		list.add("world");

		String uuid = Clipboard.put(list);

		List<String> serList = (List<String>) Clipboard.get(uuid);

		assertEquals(serList.get(0), list.get(0));
	}
}
