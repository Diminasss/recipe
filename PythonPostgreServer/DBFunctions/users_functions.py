from DBFunctions.postgre_sql_connection import cursor
from psycopg2 import OperationalError, IntegrityError


def user_is_in_table(login: str) -> bool:
    """
    Есть ли пользователь в таблице?
    :param login:
    :return:
    """
    try:
        cursor.execute(f"""SELECT EXISTS(SELECT 1 FROM users WHERE login = %s)""", (login,))
        all_users = cursor.fetchall()
        # is_in_column равно False, если пользователя нет в таблице и True, если есть
        is_in_column = all_users[0][0]
        if is_in_column:
            return True
        else:
            return False
    except OperationalError as e:
        print(f"ошибка обнаружения {e}")
        user_is_in_table(login)


# Метод по инициализации пользователя
def user_initialisation(login: str, password: str, nick_name: str, date_of_birth: str = None, recipes_owner: list = None) -> None:
    """
    Для инициализации обязательно ввести логин, пароль, никнейм и дату рождения
    :param login:
    :param password:
    :param nick_name:
    :param date_of_birth:
    :param recipes_owner:
    :return:
    """
    if recipes_owner is None:
        recipes_owner = []
    if user_is_in_table(login):
        pass
    else:
        try:
            cursor.execute("""INSERT INTO users (login, password, nick_name,date_of_birth, recipes_owner) VALUES (%s, %s, %s, %s, %s)""", (login, password, nick_name, date_of_birth, recipes_owner))
            print("Успешная ")
        except IntegrityError as e:
            print("Ошибка при инициализации:", e)


def get_from_postgresql_table(table_name: str, login: str, what_to_get: str) -> any:
    """Функция возвращает None, если не находит данные\n
    Таблицы на выбор:\n
    users\n
    Поля на выбор:\n
    login\n
    password\n
    nick_name\n
    date_of_birth\n
    recipes_owner\n
    :param table_name:
    :param login:
    :param what_to_get:
    :return str | list | int | bool"""
    if user_is_in_table(login):
        try:
            cursor.execute(f"""SELECT {what_to_get} FROM {table_name} WHERE login = %s""", (login,))
            value = cursor.fetchone()[0]
            return value if value is not None else None
        except IntegrityError as e:
            print("Ошибка получения данных при подключении:", e)
            return None
    else:
        return None


def log_in(login: str, password: str) -> bool:
    real_password: str = get_from_postgresql_table("users", login, "password")
    if password == real_password:
        return True
    else:
        return False
