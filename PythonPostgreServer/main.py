from flask import Flask, wrappers, request, jsonify
from logger import initialize_logger
from DBFunctions.users_alchemy_functions import (user_is_in_table, log_in, get_all_user_information_excluding_password,
                                                 delete_user, user_initialisation)

app = Flask(__name__)
logger = initialize_logger(__name__)


# Обработчик для CORS
@app.after_request
def after_request(response) -> wrappers.Response:
    response.headers.add('Access-Control-Allow-Origin', '*')
    response.headers.add('Access-Control-Allow-Headers', 'Content-Type')
    response.headers.add('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
    return response


@app.route("/person", methods=['POST'])
def get_person() -> tuple[wrappers.Response, int]:
    """
    Может отправить в result: successfully, invalid_password и no_user.

    Функция определяет, зарегистрирован ли пользователь. Если пользователь не зарегистрирован, то возвращает json с
    "no_user", если пользователь ввёл неверный пароль, то "invalid_password", если пользователь ввёл верный пароль и при
    этом он есть в базе, то функция отправляет "successfully" и полную информацию о пользователе.

    :return:
    """
    logger.info(f"Запрос в функцию {get_person.__name__}")

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


@app.route("/delete_person", methods=['POST'])
def delete_person() -> tuple[wrappers.Response, int]:
    logger.info(f"Произведение удаления пользователя в {delete_person.__name__}")
    request_data = request.get_json()

    login: str = str(request_data.get('login'))
    result = delete_user(login)
    return jsonify(result), 200


@app.route("/register", methods=['POST'])
def register() -> tuple[wrappers.Response, int]:
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


if __name__ == "__main__":
    app.run(debug=False)
