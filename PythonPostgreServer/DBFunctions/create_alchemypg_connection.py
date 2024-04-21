from sqlalchemy import create_engine, MetaData
from sqlalchemy import Table, Column, Integer, String, ARRAY
from DBFunctions.config import Config

engine = create_engine(
    url=Config.url,
    echo=Config.echo,
    pool_size=Config.pool_size,
    max_overflow=Config.max_overflow
)

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

metadata_obj.create_all(engine)
