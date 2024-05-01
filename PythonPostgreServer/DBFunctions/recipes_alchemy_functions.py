from DBFunctions.create_alchemypg_connection import recipes_table, users_table, engine
from DBFunctions.users_alchemy_functions import edit_sql_table, user_is_in_table
from sqlalchemy import select, func, insert
from logger import initialize_logger

logger = initialize_logger(__name__)


def max_recipe_idf() -> int:
    def count_recipe_lines() -> int:
        logger.info(f"Для подсчёта количества строк рецептов используется подфункция {count_recipe_lines.__name__}")
        statement = select(func.count(recipes_table.c['id']))
        with engine.connect() as connection:
            result = connection.execute(statement).scalar()
        return result
    if count_recipe_lines() > 0:
        logger.info(f"Для получения максимального id рецепта используется функция {max_recipe_idf.__name__}")
        statement = select(func.max(recipes_table.c['id']))

        with engine.connect() as connection:
            max_recipe_id: int = connection.execute(statement).scalar()

        return max_recipe_id
    else:
        return 0


max_recipe_id: int = max_recipe_idf()


def add_recipe_to_table(login: str, recipe: dict) -> dict:
    if user_is_in_table(login):
        # Выясняем ID для следующего рецепта
        global max_recipe_id
        max_recipe_id += 1

        # Выясняем никнейм пользователя и рецепты, которыми он владеет
        statement = select(users_table.c["nick_name"], users_table.c["recipes_owner"]).where(users_table.c.login == login)
        with engine.connect() as connection:
            nick_name, recipes = connection.execute(statement).first()

        # Добавляем новый рецепт в таблицу рецептов (СОСТАВ РЕЦЕПТА ЗДЕСЬ, БОЛЕЕ ПОДРОБНЫЙ СОСТАВ ВЫ МОЖЕТЕ УВИДЕТЬ В
        # ФАЙЛЕ create_alchemypg_connection.py)
        statement = insert(recipes_table).values(
            id=max_recipe_id,
            title=recipe['title'],
            description=recipe['description'],
            category=recipe['category'],
            ingredients=recipe['ingredients'],
            photo=recipe['photo'],
            cooking_time=recipe['cooking_time'],
            cooking_time_periods=recipe['cooking_time_periods'],
            author_login=login,
            author_nick_name=nick_name
        )
        with engine.connect() as connection:
            connection.execute(statement)
            connection.commit()

        # Добавляем право собственности на рецепт в таблицу users
        recipes.append(max_recipe_id)
        edit_sql_table(users_table, login, "recipes_owner", recipes)

        return {"result": "success", "id": max_recipe_id}
    else:
        return {"result": "user_is_not_in_table"}
