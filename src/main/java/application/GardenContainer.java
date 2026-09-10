/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Generic container for storing garden items
 */

package application;
import java.util.*;

public class GardenContainer<T> {
	private List<T> items;
	
	public GardenContainer() {
		items = new ArrayList<>();
	}
	
	public void add(T item) {
		items.add(item);
	}
	
	public void remove(T item) {
		items.remove(item);
	}
	
	public List<T> getAll() {
		return new ArrayList<>(items);
	}
	
	public int size() {
		return items.size();
	}
	
	// Bounded generic method: returns items of Vegetable subclasses' types
	public <U extends Vegetable> List<U> getByType(Class<U> type) {
		List<U> result = new ArrayList<>();
		for (T item : items) {
			if (type.isInstance(item)) {
				result.add(type.cast(item));
			}
		}
		return result;
	}
	
	// Wildcard: prints all the names of all the vegetables
	public static void printAllNames(GardenContainer<? extends Vegetable> container) {
		for (Vegetable veg : container.getAll()) {
			System.out.println(veg.getName());
		}
	}
	
}
