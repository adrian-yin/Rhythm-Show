<template>
    <div class="piano">
        <div class="piano-key white-key"
             v-for="key in whiteKeys"
             :key="key.keyName"
             :data-id="key.id"
             v-on:click.stop="clickPianoKey(key.keyName)"
        >
            <div class="key-tag">
                <div class="key-name">{{key.keyText}}</div>
                <div class="note-name">{{key.name}}</div>
            </div>
        </div>
        <div class="black-keys black-keys1">
            <div class="piano-key black-key"
                 v-for="key in blackKeys1"
                 :key="key.keyName"
                 :data-id="key.id"
                 v-on:click.stop="clickPianoKey(key.keyName)"
            >
                <div class="key-tag">
                    <div class="key-name" v-html="key.keyText"></div>
                </div>
            </div>
        </div>
        <div class="black-keys black-keys2">
            <div class="piano-key black-key"
                 v-for="key in blackKeys2"
                 :key="key.keyName"
                 :data-id="key.id"
                 v-on:click.stop="clickPianoKey(key.keyName)"
            >
                <div class="key-tag">
                    <div class="key-name" v-html="key.keyText"></div>
                </div>
            </div>
        </div>
        <div class="black-keys black-keys3">
            <div class="piano-key black-key"
                 v-for="key in blackKeys3"
                 :key="key.keyName"
                 :data-id="key.id"
                 v-on:click.stop="clickPianoKey(key.keyName)"
            >
                <div class="key-tag">
                    <div class="key-name" v-html="key.keyText"></div>
                </div>
            </div>
        </div>
        <div class="black-keys black-keys4">
            <div class="piano-key black-key"
                 v-for="key in blackKeys4"
                 :key="key.keyName"
                 :data-id="key.id"
                 v-on:click.stop="clickPianoKey(key.keyName)"
            >
                <div class="key-tag">
                    <div class="key-name" v-html="key.keyText"></div>
                </div>
            </div>
        </div>
        <div class="black-keys black-keys5">
            <div class="piano-key black-key"
                 v-for="key in blackKeys5"
                 :key="key.keyName"
                 :data-id="key.id"
                 v-on:click.stop="clickPianoKey(key.keyName)"
            >
                <div class="key-tag">
                    <div class="key-name" v-html="key.keyText"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Keys from '../config/piano_key';
    // 用来播放钢琴声音
    import piano from '../utils/piano'

    export default {
        name: "Create",
        data() {
            return {
                keys: Keys,
                isBlackKey: false,
                lastPressedKey: '',
                lockedKey: []
            }
        },
        // 将钢琴键分类
        computed: {
            whiteKeys: function() {
                return this.keys.filter(item => item.type === 'white');
            },
            blackKeys1: function() {
                return this.keys.filter(item => item.type === 'black' && item.id >= 36 && item.id <= 40);
            },
            blackKeys2: function() {
                return this.keys.filter(item => item.type === 'black' && item.id >= 41 && item.id <= 45);
            },
            blackKeys3: function() {
                return this.keys.filter(item => item.type === 'black' && item.id >= 46 && item.id <= 50);
            },
            blackKeys4: function() {
                return this.keys.filter(item => item.type === 'black' && item.id >= 51 && item.id <= 55);
            },
            blackKeys5: function() {
                return this.keys.filter(item => item.type === 'black' && item.id >= 56 && item.id <= 60);
            }
        },
        created() {
            // 创建键盘输入事件
            this.addKeyBoardEvent();
        },
        destroyed() {
            // 移除键盘输入事件
            let _this = this;
            document.removeEventListener('keydown', _this.keydownFunction);
            document.removeEventListener('keyup', _this.keyupFunction);
        },
        methods: {
            // 播放一个音
            playNote(noteName, duration='2n') {
                // 向父组件传递播放音的音名
                this.$emit('getNote', noteName);
                piano.piano.triggerAttackRelease(noteName, duration);
            },
            // 通过按键名获取对应音符
            getNoteByKeyName(keyName) {
                let target;
                this.keys.forEach(function (item) {
                    if (item.keyName === keyName.toString()) {
                        target = item;
                    }
                });
                return target;
            },
            // 处理点击琴键
            clickPianoKey(keyName) {
                let note = this.getNoteByKeyName(keyName);
                if (note) {
                    this.playNote(note.name);
                }
            },
            keydownFunction(event) {
                let _this = this;
                let pressedKeyName = event.key.toLocaleUpperCase();
                let keyName;
                if (pressedKeyName === "SHIFT") {
                    this.isBlackKey = true;
                }
                if (this.isBlackKey) {
                    keyName = 'b' + pressedKeyName;
                } else {
                    keyName = pressedKeyName;
                }

                // 防止一次按键连续触发
                if (_this.lockedKey.indexOf(keyName) === -1) {
                    // 锁定按键
                    _this.lockedKey.push(keyName);
                    let note = _this.getNoteByKeyName(keyName);
                    if (note) {
                        _this.playNote(note.name);
                        // 修改css样式
                        if (note.type === 'white') {
                            document.querySelector(`[data-id="${note.id}"]`)
                                .classList.add('white-key-active');
                        } else if (note.type === 'black') {
                            document.querySelector(`[data-id="${note.id}"]`)
                                .classList.add('black-key-active');
                        }
                    }
                }
            },
            keyupFunction(event) {
                let _this = this;
                let pressedKeyName = event.key.toLocaleUpperCase();
                let keyName;
                if (this.isBlackKey) {
                    keyName = 'b' + pressedKeyName;
                } else {
                    keyName = pressedKeyName;
                }
                // 取消按键锁定
                delete _this.lockedKey[_this.lockedKey.indexOf(keyName)];
                if (pressedKeyName === "SHIFT") {
                    this.isBlackKey = false;
                }
                let note = _this.getNoteByKeyName(keyName);
                if (note) {
                    if (note.type === 'white') {
                        document.querySelector(`[data-id="${note.id}"]`)
                            .classList.remove('white-key-active');
                    } else if (note.type === 'black') {
                        document.querySelector(`[data-id="${note.id}"]`)
                            .classList.remove('black-key-active');
                    }
                }
            },
            // 添加键盘按下和抬起事件
            addKeyBoardEvent() {
                let _this = this;
                document.addEventListener('keydown', _this.keydownFunction, false);
                document.addEventListener('keyup', _this.keyupFunction, false);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .piano {
        position: relative;

        margin: 20px auto;
        padding: 0;
        width: 1400px;
        height: 280px;
        min-width: 1400px;
        min-height: 280px;
        box-shadow: 5px 5px 20px 5px #888;
        overflow: hidden;

        background: #373737;

        .piano-key {
            cursor: pointer;
        }

        .white-key {
            display: inline-block;
            position: relative;

            margin: 0;
            width: 2.77%;
            height: 100%;
            box-sizing: border-box;

            border-style: solid;
            border-width: 1px;
            border-color: #373737;
            border-radius: 0 0 5px 5px;

            background-image: linear-gradient(white 10%, rgb(251, 251, 251) 92%, rgb(220, 220, 220) 93%, white 97%);

            &:active {
                background-image: linear-gradient(#eee 10%, #ddd 60%, #bbb 93%, #ccc 97%);
            }

            .key-tag {
                position: absolute;
                bottom: 5%;

                width: 100%;

                text-align: center;
                font-size: 14px;
                color: #373737;

                .key-name {
                    margin-bottom: 5px;
                }

                .note-name {
                    font-weight: bold;
                    color: #a7573b;
                }
            }
        }
        .white-key-active {
            background-image: linear-gradient(#eee 10%, #ddd 60%, #bbb 93%, #ccc 97%);
        }

        /* 黑键每五个一组 */
        .black-keys {
            position: absolute;
            top: 0;

            width: 20%;
            height: 0;
        }
        .black-keys1 {
            left: 0;
        }
        .black-keys2 {
            left: 19.5%;
        }
        .black-keys3 {
            left: 39%;
        }
        .black-keys4 {
            left: 58.3%;
        }
        .black-keys5 {
            left: 77.7%;
        }

        .black-key {
            display: inline-block;

            position: absolute;
            top: 0;

            width: 10%;
            height: 196px;
            overflow: hidden;

            border-radius: 0 0 3px 3px;

            background-image: linear-gradient(#000 10%, rgb(86, 86, 86) 85%, #000 90%);

            &:active {
                background-image: linear-gradient(rgb(86, 86, 86) 10%, #000 90%, #222 100%);
            }

            .key-tag {
                position: absolute;
                left: 0;
                bottom: 5%;

                width: 100%;
                overflow: hidden;

                font-size: 14px;
                color: #fff;

                .key-name {
                    width: 100%;
                    text-align: center;
                }
            }
        }
        .black-key-active {
            background-image: linear-gradient(rgb(86, 86, 86) 10%, #000 90%, #222 100%);
        }

        /* 每个黑键在各自组中的偏移 */
        .black-key:nth-child(1) {
            left: 9%;
        }
        .black-key:nth-child(2) {
            left: 23%;
        }
        .black-key:nth-child(3) {
            left: 50%;
        }
        .black-key:nth-child(4) {
            left: 65%;
        }
        .black-key:nth-child(5) {
            left: 79%;
        }
    }
</style>