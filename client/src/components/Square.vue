<template>
    <div class="main">
        <page-head class="page-head"></page-head>
        <section>
            <div class="choose-area">
                <div class="original">
                    <input type="radio" id="originalAll" name="original" value="all" checked>
                    <label for="originalAll">全部</label>
                    <input type="radio" id="originalTrue" name="original" value="true">
                    <label for="originalTrue">原创</label>
                    <input type="radio" id="originalFalse" name="original" value="false">
                    <label for="originalFalse">非原创</label>
                </div>
                <div class="type">
                    <input type="radio" id="typeAll" name="type" value="all" checked>
                    <label for="typeAll">全部</label>
                    <input type="radio" id="typeRecord" name="type" value="record">
                    <label for="typeRecord">录音</label>
                    <input type="radio" id="typeScore" name="type" value="score">
                    <label for="typeScore">曲谱</label>
                </div>
                <div class="order">
                    <input type="radio" id="orderRecent" name="order" value="recent" checked>
                    <label for="orderRecent">最新发布</label>
                    <input type="radio" id="orderPopular" name="order" value="popular">
                    <label for="orderPopular">获赞最多</label>
                </div>
                <hr class="top-line">
            </div>
            <div class="content-area">
                <div class="share-card" v-for="share in shares" :key="share.id">
                    <div class="title">{{share.name}}</div>
                    <div class="label">{{share.isOriginalName}}·{{share.typeName}}</div>
                    <div class="describe">{{share.describe}}</div>
                    <div class="like">
                        <img src="../assets/like.png" alt=""
                             v-on:click="clickLike($event, share)">
                        <div class="num">{{share.likeNum}}</div>
                    </div>
                    <div class="collect">
                        <img src="../assets/collect.png" alt=""
                             v-on:click="clickCollect($event, share)">
                        <div class="num">{{share.collectNum}}</div>
                    </div>
                    <div class="username" v-on:click="toUserPage">{{share.user.nickname}}</div>
<!--                    添加播放和演奏按钮-->
                    <img class="sound-button" src="../assets/sound.png" alt="播放"
                         v-on:click="playScore(share.musicScore)">
                    <img class="play-button" src="../assets/play.png" alt="演奏"
                         v-if="share.showPlayImg"
                         v-on:click="toPlayPage(share.musicScore)">
                    <hr class="share-line">
                </div>
                <div class="bottom-text" v-if="showBottom">已经到达底部</div>
            </div>
        </section>
    </div>
</template>

<script>
    import $ from 'jquery'
    import http from '@/utils/http';
    import PageHead from '@/components/PageHead';
    import piano from '@/utils/piano';
    import isLike from '@/assets/is_like.png';
    import isCollect from '@/assets/is_collect.png';

    // 控制是否可以继续发送翻页请求
    let canAddPage = true;

    export default {
        name: "Square",
        components: {
             'page-head': PageHead
        },
        data() {
            return {
                shares: [],
                pageNum: 0,
                pageSize: 2,
                types: [0, 1],
                isOriginals: [0, 1],
                sortByString: 'time',
                showBottom: false
            }
        },
        created() {
            this.getShares();
        },
        mounted() {
            let _this = this;
            // 滚动条距底部50px加载下一页
            document.addEventListener('scroll', _this.scrollListener);
            // 添加单选钮监听事件
            _this.addRadioListeners();
        },
        destroyed() {
            let _this = this;
            // 销毁滚动加载事件
            document.removeEventListener('scroll', _this.scrollListener);
        },
        methods: {
            scrollListener() {
                let _this = this;
                let distanceToBottom =  $(document).height() - $(document).scrollTop() - window.innerHeight;
                if (distanceToBottom <= 50 && canAddPage) {
                    canAddPage = false;
                    _this.getShares();
                }
            },
            // 添加筛选单选钮的触发事件
            addRadioListeners() {
                let _this = this;
                $(`input[type=radio][name=original]`).change(function() {
                    canAddPage = true;
                    _this.shares = [];
                    _this.pageNum = 0;
                    _this.showBottom = false;
                    if (this.value === 'all') {
                        _this.isOriginals = [0, 1];
                    } else if (this.value === 'true') {
                        _this.isOriginals = [1];
                    } else if (this.value === 'false') {
                        _this.isOriginals = [0];
                    }
                    _this.getShares();
                });
                $(`input[type=radio][name=type]`).change(function() {
                    canAddPage = true;
                    _this.shares = [];
                    _this.pageNum = 0;
                    _this.showBottom = false;
                    if (this.value === 'all') {
                        _this.types = [0, 1];
                    } else if (this.value === 'score') {
                        _this.types = [1];
                    } else if (this.value === 'record') {
                        _this.types = [0];
                    }
                    _this.getShares();
                });
                $(`input[type=radio][name=order]`).change(function() {
                    canAddPage = true;
                    _this.shares = [];
                    _this.pageNum = 0;
                    _this.showBottom = false;
                    if (this.value === 'recent') {
                        _this.sortByString = 'time';
                    } else if (this.value === 'popular') {
                        _this.sortByString = 'likeNum';
                    }
                    _this.getShares();
                });
            },
            getShares() {
                let _this = this;
                let requestData = {
                    types: _this.types,
                    isOriginals: _this.isOriginals,
                    sortByString: _this.sortByString,
                    pageNum: _this.pageNum,
                    pageSize: _this.pageSize
                }
                http.fetchPost('shares', requestData).then((res) => {
                    if (res.data.code === 200) {
                        let newShares =  res.data.data.content;
                        newShares.forEach((share) => {
                            // 添加类别、原创字符串，以及根据类别判断是否显示演奏图标
                            if (share.type === 0) {
                                share.typeName = '录音';
                                share.showPlayImg = false;
                            } else if (share.type === 1) {
                                share.typeName = '曲谱';
                                share.showPlayImg = true;
                            } else {
                                share.typeName = '未知';
                                share.showPlayImg = true;
                            }
                            if (share.isOriginal === 0) {
                                share.isOriginalName = '非原创';
                            } else if (share.isOriginal === 1) {
                                share.isOriginalName = '原创';
                            } else {
                                share.isOriginalName = '未知';
                            }
                        });
                        _this.shares = _this.shares.concat(newShares);
                        _this.pageNum++;
                        if (JSON.stringify(newShares) === '[]') {
                            _this.showBottom = true;
                        } else {
                            canAddPage = true;
                        }
                        return true;
                    } else {
                        return false;
                    }
                });
            },
            clickLike(event, share) {
                // 播放动画，更换样式
                event.target.src = isLike;
                event.target.style.animationPlayState = "running";

                http.fetchPost('like', {shareId: share.id}).then((res) => {
                    if (res.data.code === 200) {
                        share.likeNum++;
                        return true;
                    } else {
                        alert(res.data.message);
                        return false;
                    }
                });
            },
            clickCollect(event, share) {
                // 播放动画，更换样式
                event.target.src = isCollect;
                event.target.style.animationPlayState = "running";

                http.fetchPost('collect', {shareId: share.id}).then((res) => {
                    if (res.data.code === 200) {
                        share.collectNum++;
                        return true;
                    } else {
                        alert(res.data.message);
                        return false;
                    }
                });
            },
            toUserPage() {
                // TODO: 完成跳转到用户页面
            },
            playScore(musicScore) {
                piano.playByScoreText(musicScore);
            },
            toPlayPage(musicScore) {
                this.$router.push({
                   path: '/play',
                   query: {
                       score: musicScore
                   }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    .main {
        min-width: 980px;
        height: 100%;
    }

    .page-head {
        z-index: 2
    }

    section {
        z-index: 1;

        position: relative;
        top: 100px;

        margin: auto;
        padding: 40px 60px;
        width: 60%;
        min-width: 588px;

        font-size: 20px;
        color: #fff;

        background-color: #00000080;
    }

    .choose-area {
        text-align: left;
        color: #fed6e3;

        .original, .type, .order {
            margin: 0 0 10px 20px;

            input {
                cursor: pointer;
            }

            label {
                margin: 0 20px 0 3px;
                font-weight: bold;
            }
        }

        .top-line {
            background-color: #fed6e3;
            height: 2px;
            margin: 23px 0;
        }
    }

    .content-area {
        padding: 10px 40px;

        .share-card {
            position: relative;

            margin-bottom: 40px;
            height: 216px;

            .title {
                position: absolute;
                top: 0;
                left: 30px;

                font-weight: bold;
                font-size: 26px;
            }

            .label {
                position: absolute;
                left: 300px;
                top: 3px;
                color: #fed6e3;
            }

            .describe {
                // 限制3行，多行溢出显示省略号
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;

                position: absolute;
                left: 5px;
                top: 40px;

                padding: 5px;
                width: 70%;
                height: 96px;
                box-sizing: border-box;
                overflow: hidden;

                text-align: left;
                line-height: 32px;
                text-indent: 2em;
                text-overflow: ellipsis;
            }

            .like, .collect {
                color: #fed6e3;
                text-align: left;

                img {
                    cursor: pointer;

                    animation-name: click-img;
                    animation-duration: 0.5s;
                    animation-play-state: paused;
                }

                .num {
                    position: absolute;
                    left: 22px;
                    top: 0;
                }
            }
            /* 点赞、收藏动画 */
            @keyframes click-img {
                0% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(2);
                }
                100% {
                    transform: scale(1);
                }
            }

            .like {
                position: absolute;
                bottom: 15px;
                left: 80px;
            }
            .collect {
                position: absolute;
                bottom: 15px;
                left: 230px;
            }

            .username {
                position: absolute;
                bottom: 14px;
                right: 150px;

                font-size: 22px;
                color: #FE9200;

                cursor: pointer;

                &:hover {
                    border-bottom: 1px solid #FE9200;
                }
            }

            .share-line {
                position: absolute;
                bottom: 0;

                border-color: #0C92F5;
                width: 100%;
                height: 1px;
                background-color: #0C92F5;
            }

            .sound-button, .play-button {
                cursor: pointer;
                &:active {
                    opacity: 0.5;
                }
            }
            .sound-button {
                position: absolute;
                right: 135px;
                top: 60px;
            }
            .play-button {
                position: absolute;
                right: 50px;
                top: 60px;
            }
        }
    }
</style>