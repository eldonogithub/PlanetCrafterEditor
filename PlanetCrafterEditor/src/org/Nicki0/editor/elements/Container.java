package org.Nicki0.editor.elements;

import java.util.List;
import java.util.stream.Collectors;

public class Container {
	private long id;
	private List<Long> woIds;
	private int size;
	
	public Container() {}
	public Container(long pId, List<Long> pWoIds, int pSize) {
		id = pId;
		woIds = pWoIds;
		size = pSize;
	}
	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"woIds\":\"" + woIds.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", "")) + "\",\"size\":" + size + "}";
	}
	public long getId() {return id;}
	public List<Long> getWoIds() {return woIds;}
	public int getSize() {return size;}
	
	public void push(Item pItem) {
		woIds.add(pItem.getId());
		if (woIds.size() >= size) size += 1; // Annahme: woIds.size() immer kleiner oder gleich size
	}
	public void push(List<Item> pItemList) {
		for (int i = 0; i < pItemList.size(); i++) this.push(pItemList.get(i));
	}
}
