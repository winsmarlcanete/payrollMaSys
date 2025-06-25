package Algorithms;

public class QuicksortClass {
    public static void quicksort(Object[][] data, int low, int high, int columnIndex) {
        if (low < high) {
            int pi = partition(data, low, high, columnIndex);
            quicksort(data, low, pi - 1, columnIndex);
            quicksort(data, pi + 1, high, columnIndex);
        }
    }

    private static int partition(Object[][] data, int low, int high, int columnIndex) {
        String pivot = data[high][columnIndex] != null ? data[high][columnIndex].toString() : "";
        int i = low - 1;

        for (int j = low; j < high; j++) {
            String current = data[j][columnIndex] != null ? data[j][columnIndex].toString() : "";
            if (current.compareToIgnoreCase(pivot) <= 0) {
                i++;
                Object[] temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        Object[] temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;

        return i + 1;
    }
}