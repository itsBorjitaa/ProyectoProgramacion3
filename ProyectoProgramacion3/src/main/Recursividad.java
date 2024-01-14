package main;

public class Recursividad {
    public static void main(String[] args) {
        double[] gastos = {45.2, 12.5, 78.0, 34.8, 21.3};
        
        System.out.println("Array de gastos original:");
        imprimirArray(gastos);

        ordenarGastos(gastos, 0, gastos.length - 1);

        System.out.println("\nArray de gastos ordenado de mayor a menor:");
        imprimirArray(gastos);

        // Crear una copia invertida para mostrar el orden de mayor a menor
        double[] gastosInvertidos = new double[gastos.length];
        for (int i = 0; i < gastos.length; i++) {
            gastosInvertidos[i] = gastos[gastos.length - 1 - i];
        }

        System.out.println("\nArray de gastos ordenado de menor a mayor:");
        imprimirArray(gastosInvertidos);
    }

    // Función recursiva para ordenar un array de gastos
    public static void ordenarGastos(double[] gastos, int inicio, int fin) {
        if (inicio < fin) {
            // Obtener la posición
            int indicePivote = particion(gastos, inicio, fin);

            // Llamadas recursivas para ordenar
            ordenarGastos(gastos, inicio, indicePivote - 1);
            ordenarGastos(gastos, indicePivote + 1, fin);
        }
    }

    // Función para realizar la partición del array y devolver la posición
    private static int particion(double[] gastos, int inicio, int fin) {
        double pivote = gastos[fin];
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            // Cambiar la comparación para ordenar de mayor a menor
            if (gastos[j] >= pivote) {
                i++;
                intercambiar(gastos, i, j);
            }
        }

        intercambiar(gastos, i + 1, fin);

        return i + 1;
    }

    // Función para intercambiar dos elementos en un array
    private static void intercambiar(double[] gastos, int i, int j) {
        double temp = gastos[i];
        gastos[i] = gastos[j];
        gastos[j] = temp;
    }

    // Función para imprimir un array de gastos
    private static void imprimirArray(double[] gastos) {
        for (double gasto : gastos) {
            System.out.print(gasto + " ");
        }
        System.out.println();
    }
}
