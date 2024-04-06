from flask import Flask, wrappers, request, jsonify
from logger import initialize_logger
from DBFunctions.users_functions import user_is_in_table, log_in

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
    Может отправить в logged_in?: successfully, invalid_password и no_user
    :return:
    """
    logger.info(f"Запрос в функцию {get_person.__name__}")

    request_data = request.get_json()

    login: str = request_data.get('login')
    password: str = request_data.get('password')

    if user_is_in_table(login):
        if log_in(login, password):

            response: dict = {"logged_in?": "success", "login": "", "nickname": "", "date_of_birth": "", "recipes_owner": ""}
            return jsonify(response), 200
        else:
            return jsonify({"logged_in?": "invalid_password", "login": "", "nickname": "", "date_of_birth": "", "recipes_owner": ""}), 200
    return jsonify({"logged_in?": "no_user", "login": "", "nickname": "", "date_of_birth": "", "recipes_owner": ""}), 200


if __name__ == "__main__":
    app.run(debug=False)
