/* Вставка данных в таблицы */
SET NAMES 'utf8';

/* Вставка в таблицу формирования номера заявок */
INSERT INTO pay_system.numid(id, numC, numB) VALUES
(1, 1, 1);

/* Вставка в таблицу доп. параметров */
INSERT INTO pay_system.params(id, name_params, text_params, value, comments) VALUES
(1, 'PARAMS_ONE_BEZNAL', 'Единовременная комиссия для безнала.', '25', '');