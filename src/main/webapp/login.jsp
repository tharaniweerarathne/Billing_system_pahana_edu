<%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/4/2025
  Time: 1:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background: url('images/background1.jpg') no-repeat center center/cover;
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            backdrop-filter: blur(3px);
            font-family: 'Poppins', sans-serif;
        }

        .login {
            background: rgba(101, 67, 33, 0.95);
            backdrop-filter: blur(10px);
            border: 5px solid rgba(210, 180, 140, 0.8);
            border-radius: 20px;
            padding: 40px 35px;
            width: 400px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.4),
            0 0 0 1px rgba(255, 255, 255, 0.1) inset;
            position: relative;
            overflow: hidden;
            transform: translateY(0);
            transition: all 0.3s ease;
        }

        .login::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .login:hover {
            transform: translateY(-5px);
            box-shadow: 0 35px 70px rgba(0, 0, 0, 0.5),
            0 0 0 1px rgba(255, 255, 255, 0.2) inset;
        }

        .login:hover::before {
            opacity: 1;
        }

        .logo {
            display: block;
            margin: 0 auto 25px;
            width: 160px;
            height: 80px;
            object-fit: contain;
            border-radius: 15px;
            border: 3px solid rgba(210, 180, 140, 0.8);
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.4);
            transition: transform 0.3s ease;
            background: rgba(245, 230, 211, 0.1);
            padding: 10px;
        }

        .logo:hover {
            transform: scale(1.1) rotate(5deg);
        }

        h2 {
            color: #F5E6D3;
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
            font-weight: 400;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            letter-spacing: 1px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        label {
            color: #D2B48C;
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        input[type="text"],
        input[type="password"] {
            padding: 15px 20px;
            border: 4px solid rgba(210, 180, 140, 0.8);
            border-radius: 12px;
            background: rgba(139, 69, 19, 0.3);
            color: #F5E6D3;
            font-size: 16px;
            font-family: inherit;
            transition: all 0.3s ease;
            backdrop-filter: blur(5px);
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #D2B48C;
            background: rgba(139, 69, 19, 0.5);
            box-shadow: 0 0 0 3px rgba(210, 180, 140, 0.2),
            0 5px 15px rgba(0, 0, 0, 0.3);
            transform: translateY(-2px);
        }

        input[type="text"]::placeholder,
        input[type="password"]::placeholder {
            color: rgba(245, 230, 211, 0.6);
        }

        input[type="submit"] {
            padding: 15px;
            background: #4E1F00;
            color: #F5E6D3;
            border: none;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
            position: relative;
            overflow: hidden;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        input[type="submit"]::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.3), transparent);
            transition: left 0.5s ease;
        }

        input[type="submit"]:hover {
            background: linear-gradient(135deg, #A0522D, #CD853F);
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.4);
        }

        input[type="submit"]:hover::before {
            left: 100%;
        }

        input[type="submit"]:active {
            transform: translateY(0);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
        }


        @media (max-width: 480px) {
            .login {
                width: 90%;
                padding: 30px 25px;
                margin: 20px;
            }

            h2 {
                font-size: 24px;
            }

            .logo {
                width: 60px;
                height: 60px;
            }
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login {
            animation: fadeInUp 0.8s ease-out;
        }


    </style>
</head>
<body>
<div class="login">
    <img src="images/logo.png" alt="Pahana Edu Logo" class="logo">
    <h2>Login</h2>
    <form method="post" action="login">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <input type="submit" value="Login">

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div style="margin: 20px auto; padding: 10px 20px; width: fit-content; max-width: 80%; background-color: #fdecea; color: #d32f2f; border: 1px solid #f5c6cb; border-radius: 8px; text-align: center;">
            <%= error %>
        </div>
        <% } %>
    </form>
</div>


</body>
</html>
