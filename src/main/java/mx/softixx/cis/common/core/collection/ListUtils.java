package mx.softixx.cis.common.core.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;
import mx.softixx.cis.common.core.data.ValueUtils;
import mx.softixx.cis.common.core.validator.ValidatorUtils;

/**
 * Utility class to operate with a {@code List}
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public final class ListUtils {

	private ListUtils() {
	}

	public static final String DEFAULT_DELIMITER = ",";
	public static final String WHITE_SPACE_DELIMITER = " ";

	/**
	 * Converts a {@code List<String>} to {@code String}. Elements are separated by
	 * the default delimiter ',', in encounter order
	 * 
	 * @param list {@code List<String>}
	 * @return the result String or empty
	 */
	public static String toString(List<String> list) {
		return toString(list, DEFAULT_DELIMITER);
	}

	/**
	 * Converts a {@code List<String>} to {@code String}. Elements are separated by
	 * the specified delimiter, in encounter order
	 * 
	 * @param list      {@code List<String>}
	 * @param delimiter the delimiter to be used between each element
	 * @return the result String or empty
	 */
	public static String toString(List<String> list, String delimiter) {
		if (ValidatorUtils.isNotEmpty(list) && ValidatorUtils.isNotEmpty(delimiter)) {
			return list.stream().collect(Collectors.joining(delimiter));
		}
		return ValueUtils.EMPTY;
	}

	/**
	 * Converts a {@code String} to {@code List<String>}. String are separated by
	 * the default delimiter ','
	 * 
	 * @param source String
	 * @return {@code List<String>} or empty list
	 */
	public static List<String> toList(String source) {
		return toList(source, DEFAULT_DELIMITER);
	}

	/**
	 * Converts a {@code String} to {@code List<String>}. String are separated by
	 * the specified delimiter
	 * 
	 * @param source    String
	 * @param delimiter the delimiter to be used to split the elements
	 * @return {@code List<String>} or empty list
	 */
	public static List<String> toList(String source, String delimiter) {
		if (ValidatorUtils.isNotEmpty(source) && ValidatorUtils.isNotEmpty(delimiter)) {
			return List.of(source.split(Pattern.quote(delimiter)));
		}
		return Collections.emptyList();
	}

	/**
	 * Converts a {@code T[]} to {@code List<T>}
	 * 
	 * @param source {@code T[]} source
	 * @return {@code List<T>} or empty list
	 */
	public static <T> List<T> toList(T[] source) {
		if (ValidatorUtils.isNotEmpty(source)) {
			return new ArrayList<>(Arrays.asList(source));
		}
		return Collections.emptyList();
	}

	/**
	 * Converts a {@code List<T>} to {@code T[]}
	 * 
	 * @param list {@code List<T>} source
	 * @return {@code T[]} or new String[] {}
	 */
	public static <T> String[] toArray(List<T> list) {
		if (ValidatorUtils.isNotEmpty(list)) {
			return list.toArray(new String[0]);
		}
		return new String[] {};
	}

	/**
	 * This method concatenates multiples list
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2); <br>
	 * val l2 = List.of(3, 4); <br>
	 * val l2 = List.of(5, 6); <br>
	 * val result = ListUtils.concat(l1, l2); <br>
	 * //result => [1, 2, 3, 4, 5, 6]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param lists {@code List<T>... to concat}
	 * @return {@code List<T>} or empty list
	 */
	@SafeVarargs
	public static <T> List<T> concat(List<T>... lists) {
		return Stream.of(lists).filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toList());
	}

	/**
	 * This method concatenates two list
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2, 1); <br>
	 * val l2 = List.of(4, 5, 3); <br>
	 * val result = ListUtils.concat(l1, l2); <br>
	 * //result => [1, 2, 1, 4, 5, 3]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param list1 List<T>
	 * @param list2 List<T>
	 * @return {@code List<T>} or empty list
	 */
	public static <T> List<T> concat(List<T> list1, List<T> list2) {
		if (ValidatorUtils.isEmpty(list1) && ValidatorUtils.isEmpty(list2)) {
			return Collections.emptyList();
		}

		if (ValidatorUtils.isEmpty(list2)) {
			return list1;
		}

		if (ValidatorUtils.isEmpty(list1)) {
			return list2;
		}

		return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
	}

	/**
	 * This method merges multiples list and returns a list with unique elements
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2, 1, 4, 5); <br>
	 * val l2 = List.of(1, 1, 3, 4, 1); <br>
	 * val l3 = List.of(1, 2, 3, 5, 1); <br>
	 * val l4 = List.of(6, 1, 3, 4, 1); <br>
	 * val result = ListUtils.merge(l1, l2, l3, l4); <br>
	 * //result => [1, 2, 3, 4, 5, 6]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param lists {@code List<T>... to concat}
	 * @return {@code List<T>} with unique elements or empty list
	 */
	@SafeVarargs
	public static <T> List<T> merge(List<T>... lists) {
		val source = Stream.of(lists).filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toList());

		val set = new HashSet<T>();
		set.addAll(source);

		return new ArrayList<>(set);
	}

	/**
	 * This method merges two list and returns a list with unique elements
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2, 1, 4, 5); <br>
	 * val l2 = List.of(1, 1, 3, 4, 1); <br>
	 * val result = ListUtils.merge(l1, l2); <br>
	 * //result => [1, 2, 3, 4, 5]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param list1 List<T>
	 * @param list2 List<T>
	 * @return {@code List<T>} with unique elements or empty list
	 */
	public static <T> List<T> merge(List<T> list1, List<T> list2) {
		if (ValidatorUtils.isNotEmpty(list1) && ValidatorUtils.isNotEmpty(list2)) {
			val set = new HashSet<T>();
			set.addAll(list1);
			set.addAll(list2);

			return new ArrayList<>(set);
		}
		return Collections.emptyList();
	}

	/**
	 * This method merges two list and returns a list with the intersection values
	 * between them
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2, 1, 4, 5); <br>
	 * val l2 = List.of(1, 1, 3, 4, 1); <br>
	 * val result = ListUtils.intersection(l1, l2); <br>
	 * //result => [1, 4]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param list1 List<T>
	 * @param list2 List<T>
	 * @return {@code List<T>} with the intersection values between the provided lists
	 *         or empty list
	 */
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		if (ValidatorUtils.isNotEmpty(list1) && ValidatorUtils.isNotEmpty(list2)) {
			val result = list1.stream().distinct().filter(list2::contains).collect(Collectors.toSet());
			return new ArrayList<>(result);
		}
		return Collections.emptyList();
	}

	/**
	 * This method returns the values of list1 that do not exist in list2
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2, 1, 4, 5); <br>
	 * val l2 = List.of(1, 1, 3, 4, 1); <br>
	 * val result = ListUtils.difference(l1, l2); <br>
	 * //result => [2, 5]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param list1 List<T>
	 * @param list2 List<T>
	 * @return {@code List<T>} with the values of list1 that do not exist in list2
	 */
	public static <T> List<T> difference(List<T> list1, List<T> list2) {
		if (ValidatorUtils.isNotEmpty(list1) && ValidatorUtils.isNotEmpty(list2)) {
			return list1.stream().filter(element -> !list2.contains(element)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	/**
	 * This method merges two list and returns a list with the different values
	 * between them
	 * <p>
	 * <b>Example</b><br>
	 * val l1 = List.of(1, 2, 1, 4, 5); <br>
	 * val l2 = List.of(1, 1, 3, 4, 1); <br>
	 * val result = ListUtils.difference(l1, l2); <br>
	 * //result => [2, 3, 5]
	 * </p>
	 * 
	 * @param <T>   the type of list elements
	 * @param list1 List<T>
	 * @param list2 List<T>
	 * @return {@code List<T>} with the difference values between the provided lists
	 *         or empty list
	 */
	public static <T> List<T> fullDifference(List<T> list1, List<T> list2) {
		if (ValidatorUtils.isNotEmpty(list1) && ValidatorUtils.isNotEmpty(list2)) {
			val d1 = list1.stream().filter(element -> !list2.contains(element)).collect(Collectors.toList());
			val d2 = list2.stream().filter(element -> !list1.contains(element)).collect(Collectors.toList());
			return merge(d1, d2);
		}
		return Collections.emptyList();
	}
	
	public static <T> boolean hasDuplicateValues(Collection<T> collection) {
		if (collection == null || collection.isEmpty()) {
			return false;
		}
		return collection.stream().distinct().count() < collection.size();
	}

}