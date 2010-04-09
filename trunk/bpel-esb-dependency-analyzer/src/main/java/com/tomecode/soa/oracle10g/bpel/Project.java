package com.tomecode.soa.oracle10g.bpel;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private final List<BpelProcess> bpelProcesses;

	public Project() {
		this.bpelProcesses = new ArrayList<BpelProcess>();
	}

	public final void addBpelProcess(BpelProcess bpelProcess) {
		this.bpelProcesses.add(bpelProcess);
	}
}
