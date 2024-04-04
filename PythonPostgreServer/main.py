from flask import Flask, wrappers, request, jsonify
from logger import initialize_logger

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
    logger.info(f"Запрос в функциb")
    # Получаем данные из тела запроса в формате JSON
    request_data = request.get_json()

    # Извлекаем значение титла из полученных данных
    login: str = request_data.get('login')
    password: str = request_data.get('password')

    return jsonify({"ok": "ok"}), 200


if __name__ == "__main__":

    app.run(debug=True)
