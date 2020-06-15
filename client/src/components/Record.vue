<template>
    <div class="main">
        <div class="control-area">
            <button class="start-button" v-on:click="startRecord">开始</button>
            <button class="pause-button" v-on:click="pauseRecord">暂停</button>
            <button class="resume-button" v-on:click="resumeRecord">继续</button>
            <button class="stop-button" v-on:click="stopRecord">停止</button>
        </div>
        <div class="submit-area">
            <button v-on:click="playRecord">播放</button>
            <button v-on:click="saveWork">保存</button>
            <button class="publish-button" v-on:click="publishWork">发布</button>
        </div>
        <div class="record-animation"></div>
        <finish-dialog
                class="finish-dialog"
                :dialog-type="'保存'"
                :type="0"
                :is-published="0"
                :music-score="''"
                v-if="showSaveDialog">
        </finish-dialog>
        <finish-dialog
                class="finish-dialog"
                :dialog-type="'发布'"
                :type="0"
                :is-published="1"
                :music-score="''"
                v-if="showPublishDialog">
        </finish-dialog>
    </div>
</template>

<script>
    import http from "@/utils/http";
    import Recorder from 'js-audio-recorder';
    import FinishDialog from "@/components/FinishDialog";

    export default {
        name: "Record",
        components: {
            'finish-dialog': FinishDialog
        },
        data() {
            return {
                showSaveDialog: false,
                showPublishDialog: false,
                recorder: null,
                hasRecorder: false,
                isFinished: false,
                isStart: false,
                isPaused: false
            }
        },
        created() {
            // 初始化录音对象
            this.recorder = new Recorder();
        },
        destroyed() {
            // 销毁录音释放资源
            this.recorder.destroy();
            this.recorder = null;
        },
        methods: {
            // 保存录音
            saveRecord(shareId) {
                let _this = this;
                let formData = new FormData();
                formData.append("shareId", shareId);
                formData.append("wavBlob", _this.recorder.getWAVBlob());
                http.fetchPost('saverecord', formData).then((res) => {
                    return res.data.code === 200;
                });
            },
            // 开始录音
            startRecord() {
                let _this = this;
                if (_this.hasRecorder) {
                    if (confirm('开始录音将会丢失之前的录音，确认继续吗？') === false) {
                        return
                    } else {
                        _this.recorder.destroy();
                        _this.recorder = new Recorder();
                    }
                }
                _this.recorder.start();
                _this.isStart = true;
                _this.isFinished = false;
                _this.hasRecorder = false;
                _this.isPaused = false;
                let recordAnimation = document.getElementsByClassName('record-animation')[0]
                recordAnimation.style.animationPlayState = "running";
            },
            // 暂停录音
            pauseRecord() {
                let _this = this;
                if (!_this.isStart) {
                    return;
                }
                _this.recorder.pause();
                _this.isStart = false;
                _this.isFinished = false;
                _this.hasRecorder = true;
                _this.isPaused = true;
                let recordAnimation = document.getElementsByClassName('record-animation')[0]
                recordAnimation.style.animationPlayState = "paused";
            },
            // 继续录音
            resumeRecord() {
                let _this = this;
                if (!_this.isPaused) {
                    return;
                }
                _this.recorder.resume();
                _this.isPaused = false;
                _this.isStart = true;
                _this.isFinished = false;
                _this.hasRecorder = true;
                let recordAnimation = document.getElementsByClassName('record-animation')[0]
                recordAnimation.style.animationPlayState = "running";
            },
            // 停止录音
            stopRecord() {
                let _this = this;
                _this.pauseRecord();
                if (!_this.hasRecorder) {
                    return;
                }
                if (confirm("确认停止录音吗？") === true) {
                    _this.recorder.stop();
                    _this.isFinished = true;
                    _this.isStart = false;
                    _this.isPaused = false;
                    _this.hasRecorder = true;
                    alert("录音结束");
                    let recordAnimation = document.getElementsByClassName('record-animation')[0]
                    recordAnimation.style.animationPlayState = 'paused';
                } else {
                    _this.isFinished = false;
                    _this.isPaused = true;
                    _this.isStart = false;
                    _this.hasRecorder = true;
                }
            },
            // 播放录音
            playRecord() {
                let _this = this;
                _this.pauseRecord();
                if (_this.isFinished) {
                    _this.recorder.play();
                } else {
                    alert("录音未结束，播放失败");
                }
            },
            // 保存
            saveWork() {
                let _this = this;
                _this.pauseRecord();
                if (_this.isFinished) {
                    // 隐藏发布窗口
                    _this.showPublishDialog = false;
                    // 显示保存窗口
                    _this.showSaveDialog = true;
                } else {
                    alert("录音未结束，保存失败");
                }
            },
            // 发布
            publishWork() {
                let _this = this;
                if (_this.isFinished) {
                    // 显示发布窗口
                    _this.showPublishDialog = true;
                    // 隐藏保存窗口
                    _this.showSaveDialog = false;
                } else {
                    alert("录音未结束，发布失败");
                }
            },
            // 关闭弹窗
            hideDialog() {
                let _this = this;
                // 隐藏保存和发布窗口
                _this.showPublishDialog = false;
                _this.showSaveDialog = false;
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
    }

    .control-area {
        z-index: 1;

        display: flex;
        align-items: center;
        justify-content: space-around;

        margin: 20px auto;
        width: 60%;
        height: 20%;

        button {
            width: 20%;
            height: 28%;
            box-shadow: 5px 5px 10px #888888;

            border: 0;
            border-radius: 20px;

            font-weight: bold;
            font-size: 21px;
            color: #fff;

            cursor: pointer;
            background-image: linear-gradient(to right, #7B68EEcc, #BA55D3cc);

            &:active {
                opacity: 0.5;
            }

            &:hover {
                box-shadow: 10px 10px 15px #888888;
            }
        }
    }

    .submit-area {
        z-index: 1;

        display: flex;
        align-items: center;
        justify-content: flex-end;

        position: fixed;
        right: 30px;
        bottom: 20px;

        width: 80%;
        height: 6%;

        button {
            margin: 0 20px 40px 20px;
            height: 90%;
            width: 10%;
            min-width: 80px;
            min-height: 40px;

            border-radius: 15px;
            border: 0;

            font-size: 20px;

            background-color: #a8edea;
            cursor: pointer;

            &:active {
                opacity: 0.5;
            }
        }
        .publish-button {
            background-color: #fed6e3;
            font-weight: bold;
        }
    }

    .record-animation {
        z-index: 0;

        position: absolute;
        top: 50%;
        left: 50%;

        width: 0;
        height: 0;

        border-color: #9795F0;
        border-style: solid;
        border-width: 10px;
        border-radius: 50%;

        animation-name: record;
        animation-duration: 1s;
        animation-iteration-count: infinite;
        animation-play-state: paused;
        transform: translate(-50%, -50%);

        /* 录音动画 */
        @keyframes record {
            0% {
                width: 0;
                height: 0;
                opacity: 1;
            }
            100% {
                width: 600px;
                height: 600px;
                opacity: 0;
            }
        }
    }

    .finish-dialog {
        z-index: 2;
    }
</style>