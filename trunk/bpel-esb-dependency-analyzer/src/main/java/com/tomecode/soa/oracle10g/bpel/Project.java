package com.tomecode.soa.oracle10g.bpel;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private final List<Bpel> bpelProcesses;

	public Project() {
		this.bpelProcesses = new ArrayList<Bpel>();
	}

	public final void addBpelProcess(Bpel bpelProcess) {
		this.bpelProcesses.add(bpelProcess);
	}
}
