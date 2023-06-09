## Billing Real Time

----------

Сервис, имеющий доступ к базе данных для чтения и записи.
Получает запросы от CRM, взаимодействует с БД и отправляет ответ.

Имеется кеширование запросов к бд на получение данных с помощью Spring Cache. При обновлении данных в бд данные в кеше также обновляются.

### Тарификация:

1. Получает запрос от CRM на тарификацию.
2. Отправляет запрос на генерацию cdr.txt в CDR, получает ответ об успешном или нет создании файла.
3. Если генерация прошла успешно, читает файл и достает от туда только те записи, чьи обладатели являются абонентами нашего оператора.
4. Добавляет к каждой записи тариф.
5. Отправляет обработанные записи в HRS.
6. Получает от HRS (если все хорошо) список отчетов по каждому номеру телефона.
7. Обновляет баланс каждого абонента прошедшего тарификацию: списывает сумму, полученную из отчета.
8. Отправляет ответ в CRM с номерами телдефонов и их новым балансом.


При начальном запуске программы запускается тарификация для января 2010 года, если в базе данных пусто. Дальше тарификация каждый раз производится для + 1 месяца вплоть до сегодняшнего месяца.