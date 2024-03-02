package task1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Реализация сортировщика списка строк по заданному столбцу.
 * Класс реализует интерфейс {@link IStringRowsListSorter}, предоставляя функциональность для сортировки списков строковых массивов.
 * String = new String("Когда я начинал это писать, только Бог и я понимали, что я делаю.
 * Сейчас остался только Бог © ");
 */
public class Task1Impl implements IStringRowsListSorter {

    /**
     * Единственный экземпляр класса для реализации паттерна Singleton.
     * Доступ к экземпляру класса возможен через метод {@link #getInstance()}.
     */
    public static final IStringRowsListSorter sorterInstance = new Task1Impl();

    /**
     * Приватный конструктор для предотвращения создания экземпляра класса извне.
     * Используется в реализации паттерна Singleton.
     */
    private Task1Impl() {
    }

    /**
     * Предоставляет доступ к единственному экземпляру класса.
     * @return единственный экземпляр класса {@link Task1Impl}.
     */
    public static IStringRowsListSorter getInstance() {
        return sorterInstance;
    }

    /**
     * Шаблон для разбиения строки на подстроки, содержащие либо только цифры, либо только нецифровые символы.
     * Используется для улучшения производительности разбиения строки на подстроки.
     */
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\d+|\\D+");

    public static void main(String[] args) throws SQLException {
    // try-catch process case where is the query to database. It's commented for simply example
        try {
        // Пример вводных данных
     List<String[]> rows = Task1RealDataTest.fetchData();

        /* Сортировка по второму аргументу в методе sort, т.е. столбцу.
         * 0 - по фамилиям, 1 - по зарплате,
         * 2 - по отделу, где строка содержит и цифровые и нецифровые символы
         * (пример сортировки с разбиением на подстроки) */
        Task1Impl.sorterInstance.sort(rows, 2);
        for (String[] row : rows) {
            System.out.println(Arrays.toString(row));
        }
        }  catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Сортирует список строковых массивов по заданному столбцу.
     * Сортировка учитывает числовые и текстовые подстроки, обеспечивая естественный порядок сортировки.
     * @param rows Список строковых массивов для сортировки.
     * @param columnIndex Индекс столбца, по которому производится сортировка.
     */
    @Override
    public void sort(List<String[]> rows, int columnIndex) {
        rows.sort((o1, o2) -> {
            // Обработка null
            int nullOrEmpty = compareNullOrEmpty(o1[columnIndex], o2[columnIndex]);
            if (nullOrEmpty != 0) return nullOrEmpty;

            // Разбиение строки на подстроки
            List<String> substrings1 = splitString(o1[columnIndex]);
            List<String> substrings2 = splitString(o2[columnIndex]);

            // Сравнение подстрок
            for (int i = 0; i < Math.min(substrings1.size(), substrings2.size()); i++) {
                int result = getResult(substrings1, i, substrings2);

                if (result != 0) {
                    return result;
                }
            }
            // Если все подстроки совпадают, то сравниваем длины
            return Integer.compare(substrings1.size(), substrings2.size());
        });
    }

    private int compareNullOrEmpty(String s1, String s2) {
        if (s1 == null || s1.isEmpty()) {
            return s2 == null || s2.isEmpty() ? 0 : -1;
        }   else if (s2 == null || s2.isEmpty()) {
            return 1;
        }
        return 0;
    }


    private static int getResult(List<String> substrings1, int i, List<String> substrings2) {
        String sub1 = substrings1.get(i);
        String sub2 = substrings2.get(i);
        boolean isDigits1 = sub1.matches("\\d+");
        boolean isDigits2 = sub2.matches("\\d+");

        int result;
        if (isDigits1 && isDigits2) {
            // Сравнение числовых подстрок как чисел
            result = Integer.compare(Integer.parseInt(sub1), Integer.parseInt(sub2));
        } else {
            // Сравнение остальных подстрок как строк
            result = sub1.compareTo(sub2);
        }
        return result;
    }

    /**
     * Разбивает строку на подстроки, используя предварительно скомпилированный шаблон {@link #SPLIT_PATTERN}.
     * @param str Строка для разбиения на подстроки.
     * @return Список подстрок, полученных в результате разбиения исходной строки.
     */
    private List<String> splitString(String str) {
        List<String> parts = new ArrayList<>();
        if (str == null) {
            return parts;
        }
        Matcher matcher = SPLIT_PATTERN.matcher(str);
        while (matcher.find()) {
            parts.add(matcher.group());
        }
        return parts;

    }
}
