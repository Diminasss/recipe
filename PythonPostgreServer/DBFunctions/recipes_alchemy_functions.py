from DBFunctions.create_alchemypg_connection import recipes_table, users_table, engine
from DBFunctions.users_alchemy_functions import edit_sql_table, user_is_in_table, get_from_postgresql_table
from sqlalchemy import select, func, insert
from logger import initialize_logger

logger = initialize_logger(__name__)


def add_recipe_to_table(login: str, recipe: dict) -> dict:
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

    if user_is_in_table(login):
        # Выясняем ID для следующего рецепта
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
            photo=recipe['photo'],
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

# import base64
# add_recipe_to_table("login", {"title": "Сырники", "description": "Взять творог, яйца, соль и сахар, положить на сковороду и зажарить! И потом мука.", "category": "Выпечка", "photo": base64.b64encode(open("../Sirniki.jpg", "rb").read()).decode("utf-8")})
def recipe_is_in_table(recipe_id: int) -> bool:
    """
    Есть ли рецепт в таблице?
    :param recipe_id:
    :return:
    """
    with engine.connect() as connection:
        statement = select(recipes_table).where(recipes_table.c.id == recipe_id)
        result = connection.execute(statement)
    return bool(result.fetchone())


def get_all_recipes_from_user(login: str) -> dict:
    if user_is_in_table(login):
        recipes = get_from_postgresql_table(users_table, login, "recipes_owner")
        statement = select(recipes_table).where(recipes_table.c.id.in_(recipes))
        with engine.connect() as connection:
            result = connection.execute(statement)
            rows = result.fetchall()
            columns = result.keys()

        if len(rows) == 0:
            return {"result": "recipe_is_not_in_table"}
        else:
            result_dicts = []
            for row in rows:
                result_dicts.append(dict(zip(columns, row)))
            return {"result": result_dicts}
    else:
        return {"result": "user_is_not_in_table"}


def get_six_random_recipes_from_table() -> dict[str, str] | dict[str, list[dict[str, str]]]:
    statement = select(recipes_table).order_by(func.random()).limit(6)
    with engine.connect() as connection:
        result = connection.execute(statement)
        rows = result.fetchall()
        columns = result.keys()
    if len(rows) == 0:
        return {"result": "recipe_is_not_in_table"}
    else:
        result_dicts = [dict(zip(columns, row)) for row in rows]

        return {"result": result_dicts}
