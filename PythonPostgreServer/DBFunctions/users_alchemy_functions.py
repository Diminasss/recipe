import datetime
from create_alchemypg_connection import engine, users_table
from sqlalchemy import insert, select, Table


def user_is_in_table(login: str) -> bool:
    """
    Есть ли пользователь в таблице?
    :param login:
    :return:
    """
    with engine.connect() as connection:
        statement = select(users_table).where(users_table.c.login == login)
        result = connection.execute(statement)
        return bool(result.fetchone())


def user_initialisation(login: str, password: str, nick_name: str, date_of_birth: datetime.date = None, recipes_owner: list = None) -> None:
    """
    Для инициализации обязательно ввести логин, пароль, никнейм и дату рождения
    :param login:
    :param password:
    :param nick_name:
    :param date_of_birth:
    :param recipes_owner:
    :return:
    """
    if user_is_in_table(login):  # Подписать условие
        pass
    else:
        with engine.connect() as connection:
            if recipes_owner is None:
                recipes_owner: list[int] = []
            statement = insert(users_table).values(
                login=login,
                password=password,
                nick_name=nick_name,
                date_of_birth=date_of_birth,
                recipes_owner=recipes_owner
            )
            connection.execute(statement)
            connection.commit()


def get_from_postgresql_table(table: Table, login: str, column_name: str) -> any:
    """Функция возвращает None, если не находит данные\n
    Таблицы на выбор:\n
    users\n
    Поля на выбор:\n
    login\n
    password\n
    nick_name\n
    date_of_birth\n
    recipes_owner\n
    :param table:
    :param login:
    :param column_name:
    :return str | list | int | bool"""
    if user_is_in_table(login):
        with engine.connect() as connection:
            statement = select(table.c[column_name]).where(table.c.login == login)
            result = connection.execute(statement)
            print("")
            print(result)
            print("")
            data = result.scalar()
            return data
    else:
        return "Not found"
