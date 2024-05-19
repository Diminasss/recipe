from sqlalchemy import create_engine, MetaData, func
from sqlalchemy import Table, Column, Integer, String, ARRAY
from sqlalchemy.dialects.postgresql import JSONB
from DBFunctions.config import Config

# engine = create_engine(
#     url=Config.url,
#     echo=Config.echo,
#     pool_size=Config.pool_size,
#     max_overflow=Config.max_overflow
# )

engine = create_engine(url=Config.url, echo=True)

metadata_obj = MetaData()

users_table = Table(
    "users",
    metadata_obj,
    Column("login", String, primary_key=True, nullable=False),
    Column("password", String, nullable=False),
    Column("nick_name", String, nullable=False),
    Column("date_of_birth", String),
    Column("recipes_owner", ARRAY(Integer)),
)

"""
Таблица с рецептами содержит очевидные для рецептов поля. А также поле cooking_time_periods. Это периоды готовки, которые
определяются массивами с словарями внутри, там показаны процессы от нуля до полного приготовления, чтобы человек, 
который готовит один мог видеть, какие процессы сколько времени занимают и как относятся к друг другу.

Отсчёт начинается с нуля.
"""
recipes_table = Table(
    "recipes",
    metadata_obj,
    Column("id", Integer, primary_key=True, nullable=False),
    Column("title", String, nullable=False),
    Column("description", String, nullable=False),
    Column("category", String, nullable=False),
    Column("ingredients", ARRAY(String), nullable=False),
    Column("photo", String, nullable=True),
    Column("cooking_time", String, nullable=False),
    Column("cooking_time_periods", JSONB, nullable=False),
    Column("author_login", String, nullable=False),
    Column("author_nick_name", String, nullable=False),
)

metadata_obj.create_all(engine)
