// from http://www.vogella.de/articles/JavaAlgorithmsQuicksort/article.html
// modified by soleo shao: add index exchange in the sorting
public class quickSort  {
	private int[] numbers;
	private int number;
	private int[] index;
	public void sort(int[] values, int[] index) {
		// Check for empty or null array
		if (values ==null || values.length==0){
			return;
		}
		this.numbers = values;
		number = values.length;
		this.index = index;
		for(int i = 0; i <values.length; i++ ){
			this.index[i] = i;
		}
		quicksort(0, number - 1);
	}

	private void quicksort(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int pivot = numbers[low + (high-low)/2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (numbers[i] < pivot) {
				i++;
				//index[i] = i;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (numbers[j] > pivot) {
				j--;
				//index[j] = j;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	private void exchange(int i, int j) {
		int temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
		int tmp2 = index[i];
		index[i] = index[j];
		index[j] = tmp2;
	}
}
