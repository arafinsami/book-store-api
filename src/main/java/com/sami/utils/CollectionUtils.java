package com.sami.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;

public class CollectionUtils {

	public static boolean isEmpty(Collection c) {
		return isNull(c) || c.isEmpty();
	}

	public static boolean isNotEmpty(Collection c) {
		return !isEmpty(c);
	}

	public static <T> List<T> nullSafe(List<T> c) {
		return isNull(c) ? new ArrayList<>() : c;
	}

	public static <T> boolean containsAny(Collection<? extends T> list1, Collection<? super T> list2) {
		return list1.stream().anyMatch(list2::contains);
	}
}