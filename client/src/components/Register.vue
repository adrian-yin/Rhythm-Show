<template>
    <div class="main">
        <div class="register-box">
            <h1>“炫·律”音乐旋律分享</h1>
            <div class="register-form">
                <div class="item">
                    <label>
                        <input type="text" placeholder="邮箱" v-model="email">
                    </label>
                </div>
                <div class="item">
                    <label>
                        <input class="verify-code-input" type="text" placeholder="验证码" v-model="verifyCode">
                        <button class="verify-code-button" v-on:click="getVerifyCode">获取验证码</button>
                    </label>
                </div>
                <div class="item">
                    <label>
                        <input type="password" placeholder="密码" v-model="password">
                    </label>
                </div>
                <div class="item">
                    <label>
                        <input type="password" placeholder="确认密码" v-model="confirmPassword">
                    </label>
                </div>
            </div>
            <button class="submit-button" v-on:click="registerSubmit">注册</button>
        </div>
    </div>
</template>

<script>
    import http from '@/utils/http';
    import crypto from "@/utils/crypto";

    export default {
        name: "Register",
        data() {
            return {
                email: '',
                verifyCode: '',
                password: '',
                confirmPassword: ''
            }
        },
        methods: {
            getVerifyCode() {
                let _this = this;
                if (_this.checkEmail().result) {
                    // 禁用按钮60s
                    _this.countSecond();
                    let requestData = {
                        email: _this.email
                    }
                    http.fetchPost('verify', requestData).then((res) => {
                        if (res.data.code === 200) {
                            return true;
                        } else {
                            alert('发送失败');
                            return false;
                        }
                    });
                } else {
                    alert(_this.checkEmail().message);
                    return false;
                }
            },
            countSecond() {
                let verifyCodeButton = document.getElementsByClassName('verify-code-button')[0];
                let second = 60;
                verifyCodeButton.disabled = true;
                verifyCodeButton.classList.add('disable-button');
                verifyCodeButton.innerHTML = second + 's';
                let countInterval = setInterval(function() {
                    if (second === 0) {
                        clearInterval(countInterval);
                        verifyCodeButton.innerHTML = '获取验证码';
                        verifyCodeButton.disabled = false;
                        verifyCodeButton.classList.remove('disable-button');
                    } else {
                        second--;
                        verifyCodeButton.innerHTML = second + 's';
                    }
                }, 1000);
            },
            registerSubmit() {
                let _this = this;
                if (_this.checkAll()) {
                    // 加密密码
                    let cryptoPassword = crypto.encrypt(_this.password);
                    let requestData = {
                        email: _this.email,
                        verifyCode: _this.verifyCode,
                        password: cryptoPassword
                    };
                    http.fetchPost('register', requestData).then((res) => {
                        if (res.data.code === 200) {
                            alert('注册成功！');
                            this.$router.push('/login');
                            return true;
                        } else {
                            alert(res.data.message);
                            return false;
                        }
                    });
                } else {
                    return false;
                }
            },
            checkEmail() {
                let _this = this;

                if (_this.email === '') {
                    return {
                        result: false,
                        message: '邮箱不能为空'
                    }
                }

                // 检验邮箱格式
                const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                if (!regex.test(_this.email)) {
                    return {
                        result: false,
                        message: '邮箱格式不正确'
                    }
                }

                return {
                    result: true,
                    message: '验证通过'
                }
            },
            checkVerifyCode() {
                let _this = this;

                if (_this.verifyCode === '') {
                    return {
                        result: false,
                        message: '验证码不能为空'
                    }
                }

                // 检查验证码是否为6位数字
                const regex = /^[0-9]{6}$/;
                if (!regex.test(_this.verifyCode)) {
                    return {
                        result: false,
                        message: '验证码错误'
                    }
                }

                return {
                    result: true,
                    message: '验证通过'
                }
            },
            checkPassword() {
                let _this = this;
                if (_this.password === '') {
                    return {
                        result: false,
                        message: '密码不能为空'
                    }
                }

                // 检验密码格式
                const regex = /^[a-zA-Z0-9_]{6,15}$/
                if (!regex.test(_this.password)) {
                    return {
                        result: false,
                        message: '密码只能包含6-15位的字母、数字、下划线'
                    }
                }

                return {
                    result: true,
                    message: '验证通过'
                }
            },
            checkConfirmPassword() {
                let _this = this;
                if (_this.confirmPassword !== _this.password) {
                    return {
                        result: false,
                        message: '两次输入的密码不一致！'
                    }
                }
                return {
                    result: true,
                    message: '验证通过'
                }
            },
            checkAll() {
                let _this = this;
                if (!_this.checkEmail().result) {
                    alert(_this.checkEmail().message);
                    return false;
                }
                if (!_this.checkVerifyCode().result) {
                    alert(_this.checkVerifyCode().message);
                    return false;
                }
                if (!_this.checkPassword().result) {
                    alert(_this.checkPassword().message);
                    return false;
                }
                if (!_this.checkConfirmPassword().result) {
                    alert(_this.checkConfirmPassword().message);
                    return false;
                }
                return true;
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

        background-image: linear-gradient(to top, #9795f0, #fbc8d4);
    }

    h1 {
        margin-top: 15px;
        color: #fff
    }

    .register-box {
        padding: 10px 20px;
        width: 30%;
        height: 60%;
        min-width: 500px;
        min-height: 430px;

        text-align: center;

        background: #00000080;
    }

    .register-form {
        margin-top: 30px;

        .item {
            position: relative;

            input {
                margin: 18px;
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

            .verify-code-input{
                width: 38%;
                margin-right: 2%;
            }
            .verify-code-button {
                width: 20%;
                height: 30px;
                margin-right: 18px;

                border-radius: 10px;
                border: 0;

                font-size: 15px;
                color: #fff;

                background-image: linear-gradient(to left, #7B68EEe0, #BA55D3e0);
                cursor: pointer;

                &:active {
                    opacity: 0.5;
                }
            }
            .disable-button {
                background-image: none;
                background-color: #888888;
                cursor: default;
            }
        }
    }

    .register-box .submit-button {
        margin: 40px;
        width: 40%;
        height: 9%;

        border: 0;
        border-radius: 20px;

        font-size: 20px;
        color: #fff;

        background-image: linear-gradient(to right, #7B68EE, #BA55D3);
        cursor: pointer;

        &:active {
            opacity: 0.5;
        }
    }
</style>