<template>
    <div class="finish-dialog">
        <div class="dialog-type">{{dialogType}}</div>
        <div class="dialog-name">
            <label for="work-name">作品名称：</label>
            <input id="work-name" type="text" v-model="name">
        </div>
        <div class="dialog-describe">
            <label for="work-describe">作品描述：</label>
            <textarea name="describe" id="work-describe" cols="21" rows="6" v-model="describe"></textarea>
        </div>
        <div class="dialog-original">
            <span>是否为原创：</span>
            <input id="originalTrue" type="radio" name="original" value="true" checked>
            <label for="originalTrue">是</label>
            <input id="originalFalse" type="radio" name="original" value="false">
            <label for="originalFalse">否</label>
        </div>
        <div class="buttons">
            <button class="button" v-on:click="hideSelf">取消</button>
            <button class="button" v-on:click="addShare">确认</button>
        </div>
    </div>
</template>

<script>
    import http from "@/utils/http";

    export default {
        name: "FinishDialog",
        props: {
            dialogType: String,
            type: Number,
            isPublished: Number,
            musicScore: String
        },
        data() {
            return {
                name: '',
                describe: '',
                isOriginal: 1
            }
        },
        methods: {
            hideSelf() {
                let _this = this;
                _this.$parent.hideDialog();
            },
            checkAll() {
                let _this = this;
                if (_this.name === '') {
                    return {
                        result: false,
                        message: '作品名称不能为空！'
                    }
                }
                if (_this.describe === '') {
                    return {
                        result: false,
                        message: '作品描述不能为空！'
                    }
                }
                return {
                    result: true,
                    message: '验证通过'
                }
            },
            addShare() {
                let _this = this;
                if (_this.checkAll().result) {
                    // 获取原创单选按钮的值
                    document.getElementsByName('original').forEach((element) => {
                        if (element.checked) {
                            if (element.value === 'true') {
                                _this.isOriginal = 1;
                            } else {
                                _this.isOriginal = 0;
                            }
                        }
                    });
                    let requestData = {
                        name: _this.name,
                        describe: _this.describe,
                        type: _this.type,
                        isOriginal: _this.isOriginal,
                        isPublished: _this.isPublished,
                        musicScore: _this.musicScore,
                    };
                    http.fetchPost('addshare', requestData).then((res) => {
                        if (res.data.code === 200) {
                            alert('成功！');
                            if (res.data.data.type === 0) {
                                _this.$parent.saveRecord(res.data.data.id);
                            }
                            _this.$router.push('/square');
                            return true;
                        } else {
                            alert('失败！');
                            return false;
                        }
                    });
                } else {
                    alert(_this.checkAll().message);
                    return false;
                }
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

        .dialog-name {
            input {
                border: 0;
                border-bottom: 2px solid #000;

                color: #000;
                font-size: 20px;

                background: 0;
                outline: 0;
            }
        }

        .dialog-describe {
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

        .dialog-original {
            input {
                margin-left: 20px;
                cursor: pointer;
            }
            label {
                margin-left: 5px;
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