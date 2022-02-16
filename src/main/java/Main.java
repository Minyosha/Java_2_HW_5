// Закомментированный код оставлен для проверок.
// При разбивке массива для второго метода значения будут различны, тк формула будет давать разный результат
// из-за смещения на h для массива a2.
// Есть сомнения в правильности кода, тк при размере массива в 100 миллионов элементов многопоток выполняется
// за 39 мс, а в 1 поток эта операция занимает 81862 мс. Слишком большая разница в производительности (2000 раз).

public class Main {

    public static void main(String[] args) {
        final int size = 10000000;
        final int h = size / 2;
        float[] original = new float[size];
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        // Заполнение массива единицами
        for (int i = 0; i < original.length; i++) {
            original[i] = 1;
        }

        // Копирование массива
        System.arraycopy(original, 0, arr, 0, size);

        // Печать массива
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }

        // Временная метка начала расчетов для первого способа
        long a = System.currentTimeMillis();
//        System.out.println(a);

        // Изменение значений в массиве
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        // Печать массива
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }

        // Вывод времени выполнения задачи в консоль
        System.out.println("Время на выполнение в один поток:");
        System.out.println(System.currentTimeMillis() - a);

        // Деление на два массива
//        float[] a1 = new float[h];
//        float[] a2 = new float[h];
//        System.arraycopy(arr, 0, a1, 0, h);
//        System.arraycopy(arr, h, a2, 0, h);

        // Печать разделенных массивов
//        for (int i = 0; i < a1.length; i++) {
//            System.out.println(a1[i]);
//        }
//        System.out.println("Разбивка");
//
//        for (int i = 0; i < a2.length; i++) {
//            System.out.println(a2[i]);
//        }
//
//        for (int i = 0; i < original.length; i++) {
//            System.out.println(original[i]);
//        }

        // Метод 2: Временная метка начала расчетов, разделение на два потока,
        // вывод затраченного времени в консоль.

        // Временная метка начала расчетов для второго способа
        long b = System.currentTimeMillis();

        Thread thread1 = new Thread(() -> {
            System.arraycopy(original, 0, a1, 0, h);
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
//            for (int i = 0; i < a1.length; i++) {
//                System.out.println(a1[i]);
//            }
            System.arraycopy(a1, 0, arr, 0, h);
        });

        Thread thread2 = new Thread(() -> {
            System.arraycopy(original, h, a2, 0, h);
            for (int i = 0; i < a2.length; i++) {

                // Для одинакового результата в обоих массивах необходимо заменить "i" на "i + h" в математической формуле ниже.
                // Оригинал закомментирован:
//                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                a2[i] = (float) (a2[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
            }
//            for (int i = 0; i < a2.length; i++) {
//                System.out.println(a2[i]);
//            }
            System.arraycopy(a2, 0, arr, h, h);
        });



        thread1.start();
        thread2.start();
//        System.arraycopy(a1, 0, arr, 0, h);
//        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Время на выполнение в два потока:");
        System.out.println(System.currentTimeMillis() - b);

//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(arr[i]);
//        }

    }
}
