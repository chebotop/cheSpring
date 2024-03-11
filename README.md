* Controllers:
    Используют аннотации Spring, такие как @RestController, @RequestMapping для сопоставления запросов.
    Контроллеры Teacher и Student принимают и возвращают классы DTO (объект передачи данных), такие как TeacherDTO и StudentDTO.
        Они возвращают ResponseEntity, чтобы обернуть HTTP-ответ.
        Коды HttpStatus используются для обозначения успеха или ошибок.
    CourseController имеет методы для обработки CRUD-операций над курсами с помощью связок GET, POST, PUT, DELETE.
        Методы используют такие аннотации, как @GetMapping, @PostMapping, @RequestBody, @PathVariable для сопоставления URL и параметров.
    Контроллеры строятся с помощью инъекции зависимостей с использованием @Autowired или @AllArgsConstructor.

