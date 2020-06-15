<template>
    <div class="main">
        <piano ref="piano" v-on:getNote="addNote"></piano>
        <div class="score-area">
            <div class="note"
                 v-for="note in sortedNotes"
                 :key="note.id"
                 :data-my-note-id="note.id"
                 v-on:click="changeSelected(note.id)">
                <span>{{note.name}}<sup>{{note.duration}}</sup></span>
            </div>
        </div>
        <div class="edit-area">
            <button v-on:click="changeDuration('1')">全音符</button>
            <button v-on:click="changeDuration('2')">二分音符</button>
            <button v-on:click="changeDuration('4')">四分音符</button>
            <button v-on:click="changeDuration('8')">八分音符</button>
            <button v-on:click="changeDuration('16')">十六分音符</button>
            <button v-on:click="changeDuration('32')">三十二分音符</button>
            <button v-on:click="removeNote">删除</button>
            <button v-on:click="addStopNote">休止符</button>
        </div>
        <div class="submit-area">
            <button class="record-button" v-on:click="$router.push('/record')">录音</button>
            <button v-on:click="playScore">播放</button>
            <button v-on:click="saveWork">保存</button>
            <button class="publish-button" v-on:click="publishWork">发布</button>
        </div>
        <finish-dialog
                :dialog-type="'保存'"
                :type="1"
                :is-published="0"
                :music-score="scoreText"
                v-if="showSaveDialog">
        </finish-dialog>
        <finish-dialog
                :dialog-type="'发布'"
                :type="1"
                :is-published="1"
                :music-score="scoreText"
                v-if="showPublishDialog">
        </finish-dialog>
    </div>
</template>

<script>
    import Piano from '@/components/Piano';
    import piano from '../utils/piano'
    import FinishDialog from "@/components/FinishDialog";

    export default {
        name: "Create",
        components: {
            'piano': Piano,
            'finish-dialog': FinishDialog
        },
        data() {
            return {
                notes: [{
                        id: Number.MAX_SAFE_INTEGER,
                        name: '',
                        duration: ''
                }],
                scoreText: '',
                noteId: 1,
                selectedNoteId: 0,
                showSaveDialog: false,
                showPublishDialog: false
            }
        },
        // 对notes按id进行排序
        computed: {
            sortedNotes: function() {
                return this.notes.slice().sort(function(obj1, obj2) {
                    if (obj1.id < obj2.id) {
                        return -1;
                    } else if (obj1.id > obj2.id) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
            }
        },
        mounted() {
            this.changeSelected(Number.MAX_SAFE_INTEGER);
        },
        methods: {
            // 改变选中音符
            changeSelected (noteId) {
                this.selectedNoteId = noteId;
                // 改变选中音符的样式
                let currentSelectedNote = document.getElementsByClassName("selected-note")[0];
                if (currentSelectedNote) {
                    currentSelectedNote.classList.remove('selected-note');
                }
                let newSelectedNote = document.querySelector(`[data-my-note-id="${noteId}"]`);
                newSelectedNote.classList.add('selected-note');
            },
            // 增添新音符
            addNote(noteName) {
                let _this = this;
                // 最后直接插入
                if (_this.selectedNoteId === Number.MAX_SAFE_INTEGER) {
                    this.notes.push({
                        id: _this.noteId,
                        name: noteName,
                        duration: '4'
                    });
                    _this.noteId++;
                    _this.selectedNoteId = Number.MAX_SAFE_INTEGER;
                    // 将音符块滚动条移到最后
                    let scoreArea = document.getElementsByClassName('score-area')[0];
                    // setTimeout保证更新完dom再移动滚动条
                    setTimeout(function () {
                        scoreArea.scrollTo(Number.MAX_SAFE_INTEGER, 0);
                    }, 0);
                } else {
                    // 修改已存在音符的值
                    _this.notes.forEach(note => {
                        if (note.id === _this.selectedNoteId) {
                            note.name = noteName;
                        }
                    });
                }
            },
            // 改变音符时值
            changeDuration(duration) {
                let _this = this;
                if (_this.selectedNoteId !== Number.MAX_SAFE_INTEGER) {
                    _this.notes.forEach(note => {
                        if (note.id === _this.selectedNoteId) {
                            note.duration = duration;
                        }
                    });
                }
            },
            // 添加休止符
            addStopNote() {
                let _this = this;
                if (_this.selectedNoteId !== Number.MAX_SAFE_INTEGER) {
                    _this.notes.forEach(note => {
                        if (note.id === _this.selectedNoteId) {
                            note.name = '0';
                        }
                    });
                } else {
                    _this.addNote('0');
                }
            },
            // 移除音符
            removeNote() {
                let _this = this;
                if (_this.selectedNoteId !== Number.MAX_SAFE_INTEGER) {
                    for (let i = 0; i < _this.notes.length; i++) {
                        if (_this.notes[i].id === _this.selectedNoteId) {
                            _this.notes.splice(i, 1);
                            break;
                        }
                    }
                    // 重新选中最后的空位
                    _this.changeSelected(Number.MAX_SAFE_INTEGER);
                    // 将音符块滚动条移到最后
                    let scoreArea = document.getElementsByClassName('score-area')[0];
                    // setTimeout保证更新完dom再移动滚动条
                    setTimeout(function () {
                        scoreArea.scrollTo(Number.MAX_SAFE_INTEGER, 0);
                    }, 0);
                }
            },
            // 将音符转化为乐谱字符串
            notesToText() {
                let noteArr = [];
                this.sortedNotes.forEach(note => {
                    noteArr.push(note.name + '(' + note.duration + ')');
                });
                // 去除最后的空字符
                noteArr.pop();
                this.scoreText = noteArr.join(' ');
            },
            // 保存
            saveWork() {
                let _this = this;
                // 将音符转成乐谱字符串
                _this.notesToText();
                // 清理键盘监听事件
                document.removeEventListener('keydown', _this.$refs.piano.keydownFunction);
                document.removeEventListener('keyup', _this.$refs.piano.keyupFunction);
                // 隐藏发布窗口
                _this.showPublishDialog = false;
                // 显示保存窗口
                _this.showSaveDialog = true;
            },
            // 发布
            publishWork() {
                let _this = this;
                // 将音符转成乐谱字符串
                _this.notesToText();
                // 清理键盘监听事件
                document.removeEventListener('keydown', _this.$refs.piano.keydownFunction);
                document.removeEventListener('keyup', _this.$refs.piano.keyupFunction);
                // 隐藏保存窗口
                _this.showSaveDialog = false;
                // 显示发布窗口
                _this.showPublishDialog = true;
            },
            // 关闭弹窗
            hideDialog() {
                let _this = this;
                // 恢复监听事件
                document.addEventListener('keydown', _this.$refs.piano.keydownFunction);
                document.addEventListener('keyup', _this.$refs.piano.keyupFunction);
                // 隐藏保存和发布窗口
                _this.showPublishDialog = false;
                _this.showSaveDialog = false;
            },
            // 播放
            playScore() {
                this.notesToText();
                piano.playByScoreText(this.scoreText);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .main {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-around;

        height: 100%;

        background-image: linear-gradient(to top, #9795f0, #fbc8d4);
    }

    .score-area {
        display: flex;
        align-items: center;
        justify-content: left;

        width: 80%;
        height: 130px;
        overflow-x: scroll;

        border-radius: 15px;

        .note {
            position: relative;

            margin-left: 20px;
            width: 90px;
            height: 90px;
            min-width: 90px;
            min-height: 90px;
            overflow: hidden;

            border-radius: 50%;

            background-color: #7b68ee80;
            cursor: pointer;

            span {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-48%, -50%);

                font-size: 31px;
                color: #fff;
            }
        }

        .selected-note {
            background-color: #ff400090;
        }
    }

    .edit-area {
        display: flex;
        align-items: center;
        justify-content: space-between;

        padding: 30px 50px;
        width: 80%;
        height: 8%;

        button {
            width: 140px;
            height: 100%;
            min-width: 140px;
            min-height: 30px;

            border: 0;
            border-radius: 20px;
            box-shadow: 5px 5px 10px #888888;

            font-size: 23px;
            color: #373737;

            background-image: linear-gradient(to bottom, #a8edea, #fed6e3);
            cursor: pointer;

            &:active {
                box-shadow: 10px 10px 20px #888888;
                opacity: 0.5;
            }
        }
    }

    .submit-area {
        display: flex;
        align-items: center;
        justify-content: flex-end;

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
        .record-button {
            position: relative;
            right: 45%;
            font-weight: bold;
        }
        .publish-button {
            background-color: #fed6e3;
            font-weight: bold;
        }
    }
</style>