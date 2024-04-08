from flask import Flask, wrappers, request, jsonify
from logger import initialize_logger
from DBFunctions.users_alchemy_functions import user_is_in_table, log_in, get_all_user_information_excluding_password

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
    Может отправить в logged_in?: successfully, invalid_password и no_user.

    Функция определяет, зарегистрирован ли пользователь. Если пользователь не зарегистрирован, то возвращает json с
    "no_user", если пользователь ввёл неверный пароль, то "invalid_password", если пользователь ввёл верный пароль и при
    этом он есть в базе, то функция отправляет "successfully" и полную информацию о пользователе.

    :return:
    """
    logger.info(f"Запрос в функцию {get_person.__name__}")

    request_data = request.get_json()

    login: str = request_data.get('login')
    password: str = request_data.get('password')

    if user_is_in_table(login):
        if log_in(login, password):
            user_information: dict[str, any] = get_all_user_information_excluding_password(login)
            user_information['logged_in?'] = "successfully"
            return jsonify(user_information), 200
        else:
            return jsonify({"logged_in?": "invalid_password"}), 200
    return jsonify({"logged_in?": "no_user"}), 200


if __name__ == "__main__":
    app.run(debug=False)