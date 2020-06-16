<template>
    <div class="finish-dialog">
        <div class="dialog-nickname">
            <label for="nickname">昵称：</label>
            <input id="nickname" type="text" v-model="user.nickname">
        </div>
        <div class="dialog-note">
            <label for="note">个性签名：</label>
            <textarea name="note" id="note" cols="21" rows="3" v-model="user.note"></textarea>
        </div>
        <div class="dialog-gender">
            <span>性别</span>
            <input id="genderUnknown" type="radio" name="gender" value="unknown" checked>
            <label for="genderUnknown">未知</label>
            <input id="genderFemale" type="radio" name="gender" value="female">
            <label for="genderFemale">女</label>
            <input id="genderMale" type="radio" name="gender" value="male">
            <label for="genderMale">男</label>
        </div>
        <div class="dialog-birthday">
            <label for="birthday">生日：</label>
            <input id="birthday" type="date">
        </div>
        <div class="buttons">
            <button class="button" v-on:click="hideSelf">取消</button>
            <button class="button" v-on:click="changeUserInfo">确认</button>
        </div>
    </div>
</template>

<script>
    import http from "@/utils/http";

    export default {
        name: "UserInfoDialog",
        props: {
            user: Object
        },
        data() {
            return {
                birthdayString: ''
            }
        },
        mounted() {
            let _this = this;

            if (_this.user.gender === 0) {
                document.getElementById('genderUnknown').click();
            } else if (_this.user.gender === 1) {
                document.getElementById('genderMale').click();
            } else if (_this.user.gender === 2) {
                document.getElementById('genderFemale').click();
            }

            // 日期格式化方法
            if (_this.user.birthday !== null) {
                let date = new Date(_this.user.birthday);

                Date.prototype.Format = function (fmt) { // author: meizz
                    let o = {
                        "M+": this.getMonth() + 1, // 月份
                        "d+": this.getDate(), // 日
                        "h+": this.getHours(), // 小时
                        "m+": this.getMinutes(), // 分
                        "s+": this.getSeconds(), // 秒
                        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
                        "S": this.getMilliseconds() // 毫秒
                    };
                    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1,
                        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                    for (let k in o)
                        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1,
                            (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                    return fmt;
                }

                _this.birthdayString = date.Format('yyyy-MM-dd');
                let birthdayElement = document.getElementById('birthday');
                birthdayElement.value = _this.birthdayString;

                birthdayElement.addEventListener('change', function() {
                    _this.birthdayString = birthdayElement.value;
                });
            }
        },
        methods: {
            hideSelf() {
                let _this = this;
                _this.$parent.hideDialog();
            },
            changeUserInfo() {
                let _this = this;
                document.getElementsByName('gender').forEach((element) => {
                    if (element.checked) {
                        if (element.value === 'unknown') {
                            _this.user.gender = 0;
                        } else if (element.value === 'female') {
                            _this.user.gender = 2;
                        } else {
                            _this.user.gender = 1;
                        }
                    }
                });
                let requestData = {
                    id: _this.user.id,
                    nickname: _this.user.nickname,
                    note: _this.user.note,
                    birthday: _this.birthdayString,
                    gender: _this.user.gender
                };
                http.fetchPost('changeuserinfo', requestData).then((res) => {
                    if (res.data.code === 200) {
                        alert('成功！');
                        _this.$parent.$router.go(0);
                        return true;
                    } else {
                        alert('失败！');
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
        align-items: center;

        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);

        width: 400px;
        height: 450px;

        padding-left: 30px;
        padding-right: 30px;
        text-align: left;
        font-size: 20px;
        color: #000;

        border-radius: 23px;

        background-image: linear-gradient(to top, #9795f0, #fbc8d4);

        .dialog-type {
            font-weight: bold;
            font-size: 25px;
        }

        .dialog-nickname {
            input {
                border: 0;
                border-bottom: 2px solid #000;

                color: #000;
                font-size: 20px;

                background: 0;
                outline: 0;
            }
        }

        .dialog-note {
            display: flex;

            textarea {
                padding: 10px;

                border-width: 2px;
                border-radius: 15px;
                border-color: #000;

                font-size: 20px;
                font-weight: bold;
                color: #000;
                line-height: 25px;

                background: 0;
                outline: 0;
            }
        }

        .dialog-gender {
            input {
                margin-left: 20px;
                cursor: pointer;
            }
            label {
                margin-left: 5px;
            }
        }

        .dialog-birthday {
            input {
                padding: 2px;
                font-size: 20px;
                border: 2px solid #000;
                border-radius: 10px;
                background-color: #00000000;
            }
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