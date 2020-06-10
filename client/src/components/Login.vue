<template>
    <div class="main">
        <div class="login-box">
            <h1>“炫·律”音乐旋律分享</h1>
            <div class="login-form">
                <div class="item">
                    <label for="username"></label>
                    <input type="text" id="username" placeholder="用户名">
                </div>
                <div class="item">
                    <label for="password"></label>
                    <input type="password" id="password" placeholder="密码">
                </div>
            </div>
            <button>登录</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login.vue"
    }
</script>

<style lang="scss" scoped>
    .main {
        display: flex;
        align-items: center;
        justify-content: center;

        height: 100%;
        width: 100%;

        background-image: linear-gradient(to top, #9795f0, #fbc8d4);
    }

    h1 {
        margin-top: 15px;
        color: #fff
    }

    .login-box {
        padding: 10px 20px;
        width: 30%;
        height: 40%;
        min-width: 500px;
        min-height: 300px;

        text-align: center;

        background: #00000080;
    }

    .login-form {
        margin-top: 30px;

        .item input {
            margin: 15px;
            padding: 5px 10px;
            width: 60%;
            height: 16px;

            border: 0;
            border-bottom: 2px solid #ffffff;

            font-size: 18px;
            text-align: center;
            color: #fff;

            background: 0;
            outline: 0;
        }

        .item input::-webkit-input-placeholder {
            text-align: center;
            font-size: 17px;
            color: #fff;
        }
    }

    .login-box button {
        margin: 40px;
        width: 40%;
        height: 14%;

        border: 0;
        border-radius: 20px;

        font-size: 20px;
        color: #fff;

        background: linear-gradient(to right, #7B68EE, #BA55D3);
    }

    .login-box button:hover {
        cursor: pointer;
    }
</style>