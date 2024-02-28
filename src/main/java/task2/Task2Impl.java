package task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <h1>Задание №2</h1>
 * Реализуйте интерфейс {@link IElementNumberAssigner}.
 *
 * <p>Помимо качества кода, мы будем обращать внимание на оптимальность предложенного алгоритма по времени работы
 * с учетом скорости выполнения операции присвоения номера:
 * большим плюсом (хотя это и не обязательно) будет оценка числа операций, доказательство оптимальности
 * или указание области, в которой алгоритм будет оптимальным.</p>
 */
public enum Task2Impl implements IElementNumberAssigner {
    /* реализован enum-based Singleton, в расчете на исполнение инструкции о том,
     * что надо предусмотреть работу из нескольких потоков */
    INSTANCE;

    /**
     * Создает список для тестирования работы реализации присвоения номеров
     * @return объект List
     */
    public List<IElement> createElements() {
        ElementExampleImpl.Context context = new ElementExampleImpl.Context();
        List<IElement> elements = new ArrayList<>();
        final Random random = new Random();
        List<Integer> shuffledNumbers = Stream.generate(() -> random.nextInt(200))
                .distinct()
                .limit(100).distinct().collect(Collectors.toList());
        Collections.shuffle(shuffledNumbers, random);
        for (Integer number : shuffledNumbers) {
            elements.add(new ElementExampleImpl(context, number));
        }

        return elements;
    }

    /* Тест работы алгоритма. Выполняется N использований setupNumber (операция которая должна выполняться как можно реже) */
    public static void main(String[] args) throws InterruptedException {
        List<IElement> elements = Task2Impl.INSTANCE.createElements();
        System.out.println("Вводные данные(количество элементов: " + elements.size() + "): ");
        for (IElement element : elements) {
            System.out.print("ID" + element.getId() + ", Number:" + element.getNumber() + " ");
        }
        System.out.println();

        long start = System.nanoTime();

        // Execute the assignment
        Task2Impl.INSTANCE.assignNumbers(elements);


        // Check time of process
        long end = System.nanoTime();
        long durationMs = (end - start) / 1000000;

        // Check result
        System.out.println("\nПосле работы алгоритма: ");
        for (IElement element : elements) {
            System.out.println("ID: " + element.getId() + ", Number: " + element.getNumber());
        }
        System.out.println("\nВремя работы (+ засыпание 10мс на каждый setupNumber): " + durationMs + " мс");
    }

    /**
     * Назначает элементам списка {@code elements} уникальные номера в порядке возрастания, начиная с наименьшего доступного номера.
     * Для каждого элемента в списке проверяется его текущий номер. Если текущий номер не соответствует целевому номеру,
     * определенному на основе отсортированного списка номеров, метод выполняет переназначение номеров.
     * Процесс включает в себя временное "освобождение" номера элемента путем установки его в значение {@code freeNumber} (-1),
     * и последующее назначение ему нового целевого номера. Это минимизирует количество операций назначения номера,
     * одновременно обеспечивая, что каждый элемент получает уникальный номер в соответствии с его позицией в отсортированном списке.
     *
     * В процессе назначения используется один внутренний цикл для перебора элементов и возможный второй цикл
     * для поиска элемента, который уже имеет целевой номер, для его временного освобождения.
     *
     * Важно: Метод предполагает, что все элементы могут быть временно "освобождены" путем назначения им номера {@code freeNumber} (-1),
     * и что для всех элементов впоследствии будет назначен корректный целевой номер.
     * @param elements Список элементов {@link IElement}, которым необходимо переназначить правильные номера.
     * @throws InterruptedException {@code Thread.sleep()} в методе {@code setupNumber}) */
    @Override
    public void assignNumbers(final List<IElement> elements) throws InterruptedException {
        int freeNumber = -1;
        // чтобы назначать нужные номера элементам, создал отсортированный список номеров
        List<Integer> targetNums = elements.stream()
                .map(IElement::getNumber)
                .sorted()
                .toList();

        // цикл проходит через все элементы
        for (int i = 0; i < elements.size(); i++) {
            IElement element = elements.get(i);
            // предварительное назначение переменных в каждом цикле
            int targetNumber = targetNums.get(i); // целевой номер текущего итерируемого элемента
            int currentNumber = element.getNumber(); // номер текущего освобожденного элемента(которому назначается -1)

            if (currentNumber != targetNumber) {
                element.setupNumber(freeNumber);
                // целевой элемент, предназначенный для освобожденного номера ищем через еще один цикл
                for (IElement e : elements) {
                    if (e.getNumber() == targetNumber) {
                        e.setupNumber(currentNumber);
                    }
                }
                element.setupNumber(targetNumber);
            }
        }

        System.out.println("\nКоличество операций setupNumbers: " + ((ElementExampleImpl)elements.getFirst()).getContext().getOperationCount());

    }
}            
