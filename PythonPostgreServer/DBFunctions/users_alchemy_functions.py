import datetime

from create_alchemypg_connection import engine, users_table
from sqlalchemy import insert, select


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


def user_initialisation(login: str, password: str, nickname: str, date_of_birth: datetime.date = None, recipes_owner: list = None) -> None:
    """
    Для инициализации обязательно ввести логин, пароль, никнейм и дату рождения
    :param login:
    :param password:
    :param nickname:
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
                nickname=nickname,
                date_of_birth=date_of_birth,
                recipes_owner=recipes_owner
            )
            connection.execute(statement)
            connection.commit()


print(user_is_in_table("Dimla"))
