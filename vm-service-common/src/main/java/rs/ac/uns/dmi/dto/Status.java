package rs.ac.uns.dmi.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Use for create radnom Status by Virtual Machine
 * 
 * @author Bojan
 *
 */

public enum Status {
	ON, OFF;

	public static final List<Status> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();

	public static Status generatedRandom() {
		return VALUES.get(new Random().nextInt(SIZE));

	}
}
