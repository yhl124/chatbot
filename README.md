# chatbot
kosa final project

###테스트용 플라스크 코드
```
from flask import Flask, jsonify, render_template, request, make_response
import time

app = Flask(__name__)

@app.route('/botresponse', methods=['POST'])
def rest_api_test1():
    # 입력된 텍스트를 받음
    user_input = request.json.get('message')
    user_id = request.json.get('userId')
    print("Received message:", user_input)
    print("user id : ", user_id)

    # 고정된 응답 텍스트
    response_text = "This is a fixed response from the chatbot."

    time.sleep(3)

    # JSON 형식으로 응답 반환
    return jsonify({"bot_response": response_text, "userId" : user_id})


if __name__ == '__main__':
    app.debug = True
    app.run(host='127.0.0.1', port='5000')
```
