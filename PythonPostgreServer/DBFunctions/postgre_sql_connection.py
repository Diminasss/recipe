import psycopg2
from DBFunctions.config import username, password, host, port, database_name
from logger import initialize_logger

logger = initialize_logger(__name__)


# Инициализация БД
def database_connection() -> psycopg2.connect:
    try:

        logger.info('Подключение базы данных')
        connection = psycopg2.connect(user=username, password=password, host=host, port=port, database=database_name)
        connection.autocommit = True
        cursor = connection.cursor()
        logger.info("База данных успешно подключена")

        logger.info("Создание/подключение таблицы users")
        cursor.execute("""CREATE TABLE IF NOT EXISTS users(
        login varchar(100) PRIMARY KEY,
        password varchar(20) NOT NULL,
        nick_name varchar(100),
        date_of_birth date,
        recipes_owner integer ARRAY)""")
        logger.info("Таблица users успешно создана/подключена")

        return cursor

    except psycopg2.OperationalError as e:
        logger.exception(f"Ошибка базы данных при создании таблицы {e}")


cursor = database_connection()
