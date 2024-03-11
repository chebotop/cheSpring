* Controllers:
    * Используют аннотации Spring, такие как @RestController, @RequestMapping для сопоставления запросов.
    * Контроллеры Teacher и Student принимают и возвращают классы DTO (объект передачи данных), такие как TeacherDTO и StudentDTO.
        Они возвращают ResponseEntity, чтобы обернуть HTTP-ответ.
        Коды HttpStatus используются для обозначения успеха или ошибок.
    * CourseController имеет методы для обработки CRUD-операций над курсами с помощью связок GET, POST, PUT, DELETE.
        Методы используют такие аннотации, как @GetMapping, @PostMapping, @RequestBody, @PathVariable для сопоставления URL и параметров.
    * Контроллеры строятся с помощью инъекции зависимостей с использованием @Autowired или @AllArgsConstructor.


* Models: 
  * В модели Person используются аннотации Lombok, такие как @Data и @AllArgsConstructor, для автоматической генерации 
      стандартных методов, таких как getters, setters, equals(), hashCode() и toString(), а также конструктора со всеми аргументами. 
      Класс Person определяет поля для хранения информации о человеке, включая уникальные номера паспорта. 
      Аннотация @Column(unique = true) используется для обеспечения уникальности значений в столбцах passportSerial 
      и passportNumber, что предотвращает дублирование данных в базе. Поля firstName, middleName и lastName представляют имя, 
      отчество и фамилию человека соответственно. 



Приложение содержит три объекта, сохраняемые в бд с использованием Hibernate

Обьект называется Person. Его свойства:
1. id Long
2. passportSeria Integer
3. passportNumber Integer
4. lastName String
5. firstName String
6. middleName String
passportSeria and passportNumber should be unique

Object 2 should be named RecordBook (зачетная книжка)
1. id
2. code (recordBook number)
code field should be unique

Object 3 should be named Student
1. id Long
2. recordBook obj
3. person obj
4. group String

После запуска соблюдены условия:
a. создано 10 персон со случайными ФИО
b. создано случайное число (менее 10) зачетных книжек со случайными кодами
c. создано 10 студентов на основе персон (со случайным номером группы), им присвоены зачетные книжки из созданных (но не всем студентам, т.к. число книжек меньше числа студентов)
d. на консоль отображается список студентов, не имеющих зачетной книжки, запрос к бд осуществляется через HQL и Criteria, т.е. список студентов отображается дважды
e. список студентов, у которых фамилия, имя ИЛИ отчество содержит символ 'a'. Also with HQL/Criteria
f. отображается список студентов, не имеющих записной книжки, также с использованием HQL и Criteria (twice  
g. все студенты удаляются, и вместе с тем связанные с ним персоны и зачетные книжки
* выводится информация об удаляемом студенте, с использованием механизма событий Hibernate
h. каждое действие выполняется в отдельной транзакции