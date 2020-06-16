<template>
    <div class="finish-dialog">
        <div class="dialog-original-password">
            <label for="original-password">原密码：</label>
            <input id="original-password" type="password" v-model="originalPassword">
        </div>
        <div class="dialog-new-password">
            <label for="new-password">新密码：</label>
            <input id="new-password" type="password" v-model="newPassword">
        </div>
        <div class="dialog-confirm-password">
            <label for="confirm-password">确认密码：</label>
            <input id="confirm-password" type="password" v-model="confirmPassword">
        </div>
        <div class="buttons">
            <button class="button" v-on:click="hideSelf">取消</button>
            <button class="button" v-on:click="changePassword">确认</button>
        </div>
    </div>
</template>

<script>
    import http from "@/utils/http";
    import crypto from "@/utils/crypto";

    export default {
        name: "PasswordDialog",
        props: {
            userId: Number
        },
        data() {
            return {
                originalPassword: '',
                newPassword: '',
                confirmPassword: ''
            }
        },
        methods: {
            hideSelf() {
                let _this = this;
                _this.$parent.hideDialog();
            },
            checkAll() {
                let _this = this;
                if (_this.originalPassword === '') {
                    return {
                        result: false,
                        message: '原密码不能为空!'
                    }
                }
                if (_this.newPassword === '') {
                    return {
                        result: false,
                        message: '新密码不能为空!'
                    }
                }
                // 检验密码格式
                const regex = /^[a-zA-Z0-9_]{6,15}$/
                if (!regex.test(_this.newPassword)) {
                    return {
                        result: false,
                        message: '密码只能包含6-15位的字母、数字、下划线'
                    }
                }
                if (_this.confirmPassword !== _this.newPassword) {
                    return {
                        result: false,
                        message: '两次输入的密码不一致!'
                    }
                }
                return {
                    result: true,
                    message: '验证通过'
                }
            },
            changePassword() {
                let _this = this;
                if (!_this.checkAll().result) {
                    alert(_this.checkAll().message)
                    return false;
                }
                let encodeOriginalPassword = crypto.encrypt(_this.originalPassword);
                let encodeNewPassword = crypto.encrypt(_this.newPassword);
                let requestData = {
                    userId: _this.userId.toString(),
                    originalPassword: encodeOriginalPassword,
                    newPassword: encodeNewPassword
                };
                console.log(requestData);
                http.fetchPost('changepassword', requestData).then((res) => {
                    if (res.data.code === 200) {
                        alert('成功！');
                        _this.$parent.$router.go(0);
                        return true;
                    } else {
                        alert(res.data.message);
                        return false;
                    }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    .finish-dialog {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: flex-end;

        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);

        width: 400px;
        height: 330px;

        padding-left: 0;
        padding-right: 50px;
        text-align: left;
        font-size: 20px;
        color: #000;

        border-radius: 23px;

        background-image: linear-gradient(to top, #9795f0, #fbc8d4);

        .dialog-type {
            font-weight: bold;
            font-size: 25px;
        }

        input {
            border: 0;
            border-bottom: 2px solid #000;

            color: #000;
            font-size: 20px;

            background: 0;
            outline: 0;
        }

        .dialog-original-password {
            input {
                border: 0;
                border-bottom: 2px solid #000;

                color: #000;
                font-size: 20px;

                background: 0;
                outline: 0;
            }
        }

        .buttons {
            position: relative;
            left: 20px;
        }

        .button {
            margin: 0 20px 0 20px;
            width: 150px;
            height: 36px;

            border: 0;
            border-radius: 20px;

            font-size: 20px;
            color: #000;

            background-image: linear-gradient(to bottom, #a8edea, #fed6e3);
            cursor: pointer;

            &:active {
                opacity: 0.5;
            }
        }
    }
</style>