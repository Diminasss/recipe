from DBFunctions.postgre_sql_connection import cursor
from psycopg2 import OperationalError, IntegrityError


def user_is_in_table(login: str) -> bool | None:
    """
    Есть ли пользователь в таблице?
    :param login:
    :return:
    """
    try:
        cursor.execute(f"""SELECT EXISTS(SELECT 1 FROM users WHERE login = %s)""", (login,))
        all_users = cursor.fetchall()
        is_in_column = all_users[0][0]
        return is_in_column
    except OperationalError as e:
        print(f"ошибка обнаружения {e}")
        user_is_in_table(login)


# Метод по инициализации пользователя
def user_initialisation(login: str, password: str, nick_name: str, date_of_birth: str = None, recipes_owner: list = None) -> None:
    """
    Для инициализации обязательно ввести логин, пароль и никнейм
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