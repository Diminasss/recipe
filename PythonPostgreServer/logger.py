import logging


def initialize_logger(name):
    # Настройка логера с указанным именем
    logger = logging.getLogger(name)
    logger.setLevel(logging.DEBUG)  # Устанавливаем уровень логирования

    # Проверяем, не были ли уже добавлены обработчики (чтобы избежать дублирования)
    if not logger.handlers:
        # Создание форматтера с именем модуля
        formatter = logging.Formatter('\033[11m%(message)s - %(asctime)s - %(name)s - %(levelname)s\033[0m')

        # Создание обработчика, который будет выводить логи в консоль
        console_handler = logging.StreamHandler()
        console_handler.setFormatter(formatter)
        console_handler.setLevel(logging.DEBUG)  # Устанавливаем уровень логирования для консоли

        # Добавление обработчика к логеру
        logger.addHandler(console_handler)

    logger.info("Запущен логгер")

    return logger
