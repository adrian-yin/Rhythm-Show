<template>
    <div class="main">
        <div class="login-box">
            <h1>“炫·律”音乐旋律分享</h1>
            <div class="login-form">
                <div class="item">
                    <img src="../assets/mail.png" alt="">
                    <label>
                        <input type="text" placeholder="邮箱" v-model="email">
                    </label>
                </div>
                <div class="item">
                    <img src="../assets/key.png" alt="">
                    <label>
                        <input type="password" placeholder="密码" v-model="password">
                    </label>
                </div>
            </div>
            <button v-on:click="loginSubmit">登录</button>
        </div>
    </div>
</template>

<script>
    import http from '../utils/http';
    import crypto from '../utils/crypto'

    export default {
        name: "Login.vue",
        data() {
            return {
                email: '',
                password: ''
            }
        },
        methods: {
            loginSubmit() {
                let _this = this;
                // 验证输入
                let checkResult = _this.checkInput();
                if (checkResult.result) {
                    let email = _this.email;
                    let password = _this.password;
                    // 加密密码
                    password = crypto.encrypt(password);
                    let requestData = {
                        email: email,
                        password: password
                    };
                    http.fetchPost('login', requestData).then((res) => {
                        if (res.data.code === 200) {
                            // 管理员跳转至后台登录页面
                            if (res.data.data.role === '1') {
                                window.location = 'http://127.0.0.1:8083/AdminLogin.aspx';
                            } else {
                                // 保存token
                                this.$store.commit('set_token', res.data.data.token);
                                sessionStorage.setItem('token', res.data.data.token);
                                // 跳转
                                this.$router.push('/square');
                            }
                            return true;
                        } else {
                            alert(res.data.message);
                            return false;
                        }
                    });
                } else {
                    alert(checkResult.message);
                    return false;
                }
            },
            // 输入校验函数
            checkInput() {
                let _this = this;
                if (_this.email === '') {
                    return {
                        result: false,
                        message: "邮箱不能为空"
                    }
                }
                if (_this.password === '') {
                    return {
                        result: false,
                        message: "密码不能为空"
                    }
                }
                return {
                    result: true,
                    message: "验证通过"
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    .main {
        display: flex;
        align-items: center;
        justify-content: center;

        height: 100%;
        width: 100%;
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

        .item {
            position: relative;

            img {
                position: relative;
                top: 3px;
            }

            input {
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

                &::-webkit-input-placeholder {
                    text-align: center;
                    font-size: 17px;
                    color: #fff;
                }
            }
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

        &:active {
            opacity: 0.5;
        }
    }

    .login-box button:hover {
        cursor: pointer;
    }
</style>