class Config:
    _username = "postgres"
    _password = "qwerty"
    _host = "127.0.0.1"
    _port = "5432"
    _database_name = "postgres"
    _database_type: str = "postgresql"
    _library: str = "psycopg"
    url: str = f'{_database_type}+{_library}://{_username}:{_password}@{_host}/{_database_name}'
    echo: bool = False
    pool_size: int = 5
    max_overflow: int = 10
