from flask import Flask, wrappers, request, jsonify

app = Flask(__name__)


@app.route("/person", methods=['POST'])
def get_person() -> tuple[wrappers.Response, int]:
    # Получаем данные из тела запроса в формате JSON
    request_data = request.get_json()

    # Извлекаем значение титла из полученных данных
    login: str = request_data.get('login')
    password: str = request_data.get('password')
    return jsonify({"ok": "ok"}), 200


if __name__ == "__main__":
    app.run(debug=True)
    