from flask import Flask, wrappers, request, jsonify
from logger import initialize_logger
from DBFunctions.users_alchemy_functions import (user_is_in_table, log_in, get_all_user_information_excluding_password,
                                                 delete_user, user_initialisation, edit_sql_table)
from DBFunctions.create_alchemypg_connection import users_table

from DBFunctions.recipes_alchemy_functions import add_recipe_to_table, get_all_recipes_from_user

app = Flask(__name__)
logger = initialize_logger(__name__)


# Обработчик для CORS
@app.after_request
def after_request(response) -> wrappers.Response:
    response.headers.add('Access-Control-Allow-Origin', '*')
    response.headers.add('Access-Control-Allow-Headers', 'Content-Type')
    response.headers.add('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
    return response


# API входа в аккаунт
@app.route("/log_in", methods=['POST'])
def log_in_account() -> tuple[wrappers.Response, int]:
    """
    Может отправить в result: successfully, invalid_password и no_user.

    Функция определяет, зарегистрирован ли пользователь. Если пользователь не зарегистрирован, то возвращает json с
    "no_user", если пользователь ввёл неверный пароль, то "invalid_password", если пользователь ввёл верный пароль и при
    этом он есть в базе, то функция отправляет "successfully" и полную информацию о пользователе.

    :return:
    """
    logger.info(f"Запрос в функцию {log_in.__name__}")

    request_data = request.get_json()

    login: str = str(request_data.get('login'))
    password: str = str(request_data.get('password'))

    if user_is_in_table(login):
        if log_in(login, password):
            user_information: dict[str, any] = get_all_user_information_excluding_password(login)
            user_information['result'] = "successfully"
            return jsonify(user_information), 200
        else:
            return jsonify({"result": "invalid_password"}), 200
    return jsonify({"result": "no_user"}), 200


# API удаления аккаунта
@app.route("/delete_person", methods=['POST'])  # Добавить удаление рецептов вместе с аккаунтом
def delete_person() -> tuple[wrappers.Response, int]:
    """
    Ответ successfully deleted, not_person метод для удаления аккаунта
    :return:
    """
    logger.info(f"Произведение удаления пользователя в {delete_person.__name__}")
    request_data = request.get_json()

    login: str = str(request_data.get('login'))
    result = delete_user(login)
    return jsonify(result), 200


# API регистрация
@app.route("/register", methods=['POST'])
def register() -> tuple[wrappers.Response, int]:
    """
    Метод для регистрации пользователя
    Ответами могут быть user_is_already_registered, data_base_error, login_password_and_nick_name_are_necessary, successfully
    :return:
    """
    logger.info(f"Производится регистрация пользователя в {register.__name__}")
    request_data: dict = request.get_json()

    login: str = request_data.get('login')
    password: str = request_data.get('password')
    nick_name: str = request_data.get('nick_name')
    date_of_birth: str = request_data.get('date_of_birth')

    if login is not None and password is not None and nick_name is not None:
        if user_is_in_table(login):
            return jsonify({"result": "user_is_already_registered"}), 200
        else:
            user_initialisation(login, password, nick_name, date_of_birth=date_of_birth)
            if user_is_in_table(login):
                return jsonify({"result": "successfully"}), 200
            else:
                return jsonify({"result": "data_base_error"}), 200
    else:
        return jsonify({"result": "login_password_and_nick_name_are_necessary"}), 200


# API добавления дня рождения
@app.route("/add_birthday", methods=['POST'])
def add_birthday() -> tuple[wrappers.Response, int]:
    logger.info(f"Добавление даты в {add_birthday.__name__}")

    request_data: dict = request.get_json()
    login: str = request_data.get('login')
    date_of_birth: str = request_data.get('date_of_birth')

    response = edit_sql_table(users_table, login, "date_of_birth", date_of_birth)

    return jsonify(response), 200


# API добавления рецепта
@app.route("/add_recipe", methods=['POST'])
def add_recipe() -> tuple[wrappers.Response, int]:
    logger.info(f"Добавление рецепта в {add_recipe.__name__}")

    request_data: dict = request.get_json()
    login: str = request_data.get('login')
    recipe: dict = request_data.get('recipe')  # состав рецепта смотреть в add_recipe_to_table

    response = add_recipe_to_table(login, recipe)

    return jsonify(response), 200


# API получения всех рецептов пользователя
@app.route("/get_users_recipes", methods=['POST'])
def get_users_recipes() -> tuple[wrappers.Response, int]:
    logger.info(f"Получение всех рецептов пользователя в {get_users_recipes.__name__}")

    request_data: dict = request.get_json()
    login: str = request_data.get('login')

    response = get_all_recipes_from_user(login)
    return jsonify(response), 200


@app.route("/test", methods=['POST'])
def test():
    request_data: dict = request.get_json()
    test = request_data.get('test')
    login = request_data.get('login')
    print(test)
    print(login)
    return jsonify({"response": "OTVET"}), 200


if __name__ == "__main__":
    app.run(debug=False)
