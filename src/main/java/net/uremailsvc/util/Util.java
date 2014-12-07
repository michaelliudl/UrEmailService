package net.uremailsvc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by doliu on 12/3/14.
 */
public class Util {
	private Util() {}

	public static List<String> copyList(List<String> list) {
		if (list == null) return Collections.emptyList();
		List<String> result = new ArrayList<String>(list.size());
		result.addAll(list);
		return Collections.unmodifiableList(result);
	}
}
