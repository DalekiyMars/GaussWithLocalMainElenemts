//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static double[] solve(double[][] A, double[] b) {
        int n = A.length; // размерность системы
        double[] x = new double[n]; // вектор решения
        double[][] Ab = augment(A, b); // расширенная матрица системы

        // Прямой ход
        for (int k = 0; k < n - 1; k++) {           // Выбор ведущего элемента в k-м столбце

            int max = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(Ab[i][k]) > Math.abs(Ab[max][k])) {
                    max = i;
                }
            }

            if (max != k) {                         // Перестановка строк, если нужно
                swapRows(Ab, k, max);
            }

            for (int i = k + 1; i < n; i++) {       // Обнуление элементов под ведущим элементом
                double m = Ab[i][k] / Ab[k][k];
                for (int j = k; j < n + 1; j++) {
                    Ab[i][j] = Ab[i][j] - m * Ab[k][j];
                }
            }
        }

        // Обратный ход
        for (int k = n - 1; k >= 0; k--) {
            x[k] = Ab[k][n] / Ab[k][k]; // нахождение k-й переменной
            for (int i = 0; i < k; i++) {
                Ab[i][n] = Ab[i][n] - Ab[i][k] * x[k]; // подстановка в предыдущие уравнения
            }
        }
        System.out.println("Треугольная матрица:");
        printMatrix(Ab);
        return x;
    }

    public static double[][] augment(double[][] A, double[] b) {
        int n = A.length;
        double[][] Ab = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j < n) {
                    Ab[i][j] = A[i][j];
                } else {
                    Ab[i][j] = b[i];
                }
            }
        }
        return Ab;
    }

    public static void swapRows(double[][] Ab, int i, int j) {
        int n = Ab[0].length;
        for (int k = 0; k < n; k++) {
            double temp = Ab[i][k];
            Ab[i][k] = Ab[j][k];
            Ab[j][k] = temp;
        }
    }

    // Метод для вывода матрицы на экран
    public static void printMatrix(double[][] A) {
        int n = A[0].length; // количество столбцов матрицы
        for (double[] doubles : A) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%.8f\t", doubles[j]); // выводим элемент с двумя знаками после запятой
            }
            System.out.println(); // переходим на новую строку
        }
    }

    // Метод для вывода вектора на экран
    public static void printVector(double[] x) {
        int n = x.length; // размерность вектора
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %.15f\n", i+1,x[i]); // выводим элемент с двумя знаками после запятой
        }
        System.out.println(); // переходим на новую строку
    }


    public static void main(String[] args) {
        double[][] A = {{1.00, 0.42, 0.54},
                        {0.42, 1.00, 0.32},
                        {0.66, 0.44, 0.22}};

        double[] b =    {0.3,
                        0.5,
                        0.7,
                        0.9};

        double[] x = solve(A, b);

        System.out.println("\nРешение системы:");
        printVector(x);
    }
}