package com.tomecode.soa.dependency.analyzer.view.visual;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Frastia Tomas
 * 
 */
public final class BackForwardManager {

	private final Hashtable<Integer, List<Object>> enteredProjects;

	private int currentLevel;

	/**
	 * Constructor
	 */
	public BackForwardManager() {
		enteredProjects = new Hashtable<Integer, List<Object>>();
		currentLevel = 0;
	}

	public final boolean isEnableBack() {
		int level = currentLevel;
		level--;
		return enteredProjects.containsKey(level);
	}

	public final boolean isEnableForward() {
		return enteredProjects.size() - 1 >= currentLevel;
	}

	public final List<Object> back() {
		currentLevel--;
		return enteredProjects.get(currentLevel);
	}

	public final List<Object> forward() {
		if (currentLevel == 0) {
			currentLevel = 1;
		}
		currentLevel++;
		// }
		return enteredProjects.get(currentLevel);
	}

	public final void newEnter(List<Object> cells) {
		List<Object> newCells = new ArrayList<Object>();
		for (Object cell : cells) {
			newCells.add(cell);
		}
		currentLevel++;
		enteredProjects.put(currentLevel, newCells);
	}

	public final void reset() {
		currentLevel = 0;
		enteredProjects.clear();
	}
}
