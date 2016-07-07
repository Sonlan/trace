package org.biac.trace.utils;

import org.biac.trace.entity.Label;

import java.util.Comparator;


public class LabelComparator implements Comparator<Label> {

	public int compare(Label o1, Label o2) {
		if(o1.getWashRemain()==o2.getWashRemain()) return TimeRevert.toLong(o1.getAliveTime()).compareTo(TimeRevert.toLong(o2.getAliveTime()));
		else return o1.getWashRemain()-o2.getWashRemain();
	}

}
