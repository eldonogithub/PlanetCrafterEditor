package nicki0.editor.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Container {
	private long id;
	private List<Long> woIds;
	private int size;
	private List<String> demandGrps;
	private List<String> supplyGrps;
	private long priority;
	
	/**
	 * For new Containers
	 * @param pId
	 * @param pSize
	 * @param pDemandGrps
	 * @param pSupplyGrps
	 */
	public Container(long pId, int pSize, List<String> pDemandGrps, List<String> pSupplyGrps, long pPriority) {
		id = pId;
		woIds = new ArrayList<Long>();
		size = pSize;
		demandGrps = pDemandGrps;
		supplyGrps = pSupplyGrps;
		priority = pPriority;
	}
	public Container(long pId, List<Long> pWoIds, int pSize, List<String> pDemandGrps, List<String> pSupplyGrps, long pPriority) {
		id = pId;
		woIds = pWoIds;
		size = pSize;
		demandGrps = pDemandGrps;
		supplyGrps = pSupplyGrps;
		priority = pPriority;
	}
	@Override
	public String toString() {
		String demandSupplyGrps = "";
		if (!demandGrps.isEmpty() || !supplyGrps.isEmpty()) demandSupplyGrps = ",\"demandGrps\":\"" + demandGrps.stream().collect(Collectors.joining(",", "", "")) + "\",\"supplyGrps\":\"" + supplyGrps.stream().collect(Collectors.joining(",", "", "")) + "\",\"priority\":" + priority;
		return "{\"id\":" + id + ",\"woIds\":\"" + woIds.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",", "", "")) + "\",\"size\":" + size + demandSupplyGrps + "}";
	}
	public long getId() {return id;}
	public List<Long> getWoIds() {return woIds;}
	public int getSize() {return size;}
	
	public void push(Item pItem) {
		woIds.add(pItem.getId());
	}
	public void adaptSize() {
		if (woIds.size() > size) size = woIds.size();
	}
	public void setSize(int pSize) {
		if (pSize < woIds.size()) adaptSize();
		else size = pSize;
	}
	public void setSupplyGrps(List<String> pSupplyGrps) {
		assert pSupplyGrps != null;
		supplyGrps = pSupplyGrps;
	}
	public List<String> getSupplyGrps() {return supplyGrps;}
	public List<String> getDemandGrps() {return demandGrps;}
	public void push(List<Item> pItemList) {
		for (int i = 0; i < pItemList.size(); i++) this.push(pItemList.get(i));
	}
	public long getPriority() {
		return priority;
	}
	/**
	 * ONLY USE FOR INVENTORY TRANSPORT
	 * @param pWoIdsList
	 */
	public void pushWoIds(List<Long> pWoIdsList) {
		woIds = Stream.concat(woIds.stream(), pWoIdsList.stream()).collect(Collectors.toList());
	}
}
