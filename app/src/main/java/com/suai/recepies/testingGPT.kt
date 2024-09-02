//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.suai.recepies.R
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun LogInScreen() {
//    var login by remember { mutableStateOf(TextFieldValue()) }
//    var password by remember { mutableStateOf(TextFieldValue()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0x92DEE853))
//            .padding(16.dp), // Отступ вокруг всего экрана
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.log_in_person), // Замените на ваш ресурс
//            contentDescription = null,
//            modifier = Modifier
//                .size(100.dp)
//                .padding(bottom = 16.dp) // Отступ снизу изображения
//        )
//
//        Text(
//            text = "Вход",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 16.dp) // Отступ снизу текста
//        )
//
//        BasicTextField(
//            value = login,
//            onValueChange = { login = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp), // Отступ снизу поля ввода логина
//            decorationBox = { innerTextField ->
//                TextFieldDefaults.TextFieldDecorationBox(
//                    value = login.text,
//                    innerTextField = innerTextField,
//                    enabled = true,
//                    singleLine = true,
//                    visualTransformation = VisualTransformation.None,
//                    interactionSource = remember { MutableInteractionSource() },
//                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
//                        start = 16.dp,
//                        end = 16.dp
//                    ),
//                    placeholder = {
//                        Text(text = "Введите логин", color = Color.Gray)
//                    }
//                )
//            }
//        )
//
//        BasicTextField(
//            value = password,
//            onValueChange = { password = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp), // Отступ снизу поля ввода пароля
//            decorationBox = { innerTextField ->
//                TextFieldDefaults.TextFieldDecorationBox(
//                    value = password.text,
//                    innerTextField = innerTextField,
//                    enabled = true,
//                    singleLine = true,
//                    visualTransformation = PasswordVisualTransformation(),
//                    interactionSource = remember { MutableInteractionSource() },
//                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
//                        start = 16.dp,
//                        end = 16.dp
//                    ),
//                    placeholder = {
//                        Text(text = "Введите пароль", color = Color.Gray)
//                    }
//                )
//            }
//        )
//
//        Button(
//            onClick = { /* Обработка нажатия кнопки "Войти" */ },
//            modifier = Modifier
//                .width(119.dp)
//                .height(60.dp)
//                .padding(bottom = 16.dp) // Отступ снизу кнопки
//        ) {
//            Text(text = "Войти")
//        }
//
//        Text(
//            text = "Нет аккаунта? Зарегистрироваться",
//            fontSize = 12.sp,
//            modifier = Modifier.padding(top = 8.dp) // Отступ сверху текста
//        )
//    }
//}