import requests
import unittest
from DBFunctions.users_alchemy_functions import user_initialisation, user_is_in_table

if not user_is_in_table("Test"):
    user_initialisation("Test", "test_password", "TestNickname", "21-10-2004", [1, 2])


class TestPostgreServer(unittest.TestCase):

    # Неверный логин
    def test_UserInitialisationWithNoUser(self) -> None:
        a = requests.post('http://127.0.0.1:5000/person', json={'login': 'jdbkjdbkldjb', 'password': '<PASSWORD>'})

        self.assertEqual(len(a.json()), 1)
        self.assertEqual(a.status_code, 200)
        self.assertEqual(a.json()['logged_in?'], 'no_user')

    # Верный логин, но неверный пароль
    def test_UserInitialisationWithInvalidPassword(self) -> None:
        a = requests.post('http://127.0.0.1:5000/person', json={'login': 'Test', 'password': '<PASSWORD>'})

        self.assertEqual(len(a.json()), 1)
        self.assertEqual(a.status_code, 200)
        self.assertEqual(a.json()['logged_in?'], 'invalid_password')

    # Верный логин и верный пароль
    def test_UserInitialisationWithSuccessfullyIn(self) -> None:
        a = requests.post('http://127.0.0.1:5000/person', json={'login': 'Test', 'password': 'test_password'})

        self.assertEqual(len(a.json()), 5)
        self.assertEqual(a.status_code, 200)

        self.assertEqual(a.json()['logged_in?'], 'successfully')
        self.assertEqual(a.json()['date_of_birth'], "21-10-2004")
        self.assertEqual(a.json()['login'], 'Test')
        self.assertEqual(a.json()['nick_name'], 'TestNickname')
        self.assertEqual(a.json()['recipes_owner'], [1, 2])


if __name__ == '__main__':
    unittest.main()