import psycopg2
import logging
from config import username, password, host, port, database_name


# Инициализация БД
def database_connection() -> psycopg2.connect:
    try:
        connection = psycopg2.connect(user=username, password=password, host=host, port=port, database=database_name)
        connection.autocommit = True
        cursor = connection.cursor()
        logging.info("Подключение к базе данных успешно")

        cursor.execute("""CREATE TABLE IF NOT EXISTS users(
        login varchar(100) PRIMARY KEY,
        password varchar(20) NOT NULL,
        nick_name varchar(100) NOT NULL,
        date_of_birth date NOT NULL""")

        logging.info("Таблицы созданы/подключены успешно")
        return cursor
    except psycopg2.OperationalError as e:
        print("Ошибка при подключении к базе данных:", e)


cursor = database_connection()









