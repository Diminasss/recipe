from DBFunctions.create_alchemypg_connection import recipes_table, users_table, engine
from DBFunctions.users_alchemy_functions import get_from_postgresql_table
from sqlalchemy import insert, select, func


def max_recipe_id() -> int:
    print("Используется функция для получения максимального")
    statement = select([func.max(recipes_table.c['id'])])
    with engine.connect() as connection:
        max_recipe_id: int = connection.execute(statement).fetchone()[0]
    print(max_recipe_id)
    return max_recipe_id


max_recipe_id: int = max_recipe_id()
print(max_recipe_id)

def add_recipe_to_table(login: str, recipe: dict):
    # author_nick_name = get_from_postgresql_table(users_table, login, "nick_name")
    # print(author_nick_name)
    statement = select(users_table.c["nick_name"], users_table.c["recipes_owner"]).where(users_table.c.login == login)
    with engine.connect() as connection:
        nick_name, recipes = connection.execute(statement).first()
    print(nick_name, recipes)

    # statement = insert(recipes_table).values(
    #     title=recipe['title'],
    #     description=recipe['description'],
    #     category=recipe['category'],
    #     ingredients=recipe['ingredients'],
    #     photo=recipe['photo'],
    #     cooking_time=recipe['cooking_time'],
    #     cooking_time_periods=recipe['cooking_time_periods'],
    #     author_login=login,
    #     author_nick_name=author_nick_name
    # )


# add_recipe_to_table("Test", {})
