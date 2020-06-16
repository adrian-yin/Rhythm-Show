<template>
    <div class="main">
        <page-head class="page-head"></page-head>
        <div class="user-background">
            <img src="../assets/default_avatar.jpg" alt="">
        </div>
        <div class="nickname">{{user.nickname}}</div>
        <div class="brief-info">
            <img :src="genderImg" alt="" class="sex">
            <div class="note">{{user.note}}</div>
        </div>
        <div class="info-area">
            <div class="nav-bar">
                <div v-on:click="toUserPage">信息</div>
                <div v-on:click="toWorksPage" class="select" >作品</div>
                <div v-on:click="toCollectsPage">收藏</div>
            </div>
            <div class="content-area">
                <div class="share-card" v-for="share in collects" :key="share.id">
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
                    <div class="delete" v-if="isSelf">
                        <img src="../assets/delete.png" alt=""
                             v-on:click="deleteWork(share.id)">
                    </div>
                    <div class="username" v-on:click="toSelectUserPage(share.user.id)">{{share.user.nickname}}</div>
                    <img class="sound-button" src="../assets/sound.png" alt="播放"
                         v-on:click="playMusic(share)">
                    <img class="play-button" src="../assets/play.png" alt="演奏"
                         v-if="share.showPlayImg"
                         v-on:click="toPlayPage(share.musicScore)">
                    <hr class="share-line">
                </div>
                <div class="bottom-text">已经到达底部</div>
            </div>
        </div>
        <audio hidden></audio>
    </div>
</template>

<script>
    import PageHead from "@/components/PageHead";
    import maleImg from "@/assets/male.png";
    import femaleImg from "@/assets/female.png"
    import http from "@/utils/http";
    import isLike from "@/assets/is_like.png";
    import isCollect from "@/assets/is_collect.png";
    import piano from "@/utils/piano";
    import Config from "@/config/url";

    export default {
        name: "Works",
        components: {
            'page-head': PageHead
        },
        data() {
            return {
                user: null,
                isSelf: false,
                collects:[]
            }
        },
        computed: {
            'genderImg': function() {
                let _this = this;
                if (_this.user.gender === 1) {
                    return maleImg;
                } else if (_this.user.gender === 2) {
                    return femaleImg;
                } else {
                    return null;
                }
            }
        },
        created() {
            let _this = this;
            // 获取用户
            let userId = _this.$route.query.userId;
            http.fetchGet('getuser?userId=' + userId, {}).then((res) => {
                if (res.data.code === 200) {
                    _this.user = res.data.data;

                    // 获取作品
                    http.fetchGet('getworks?userId=' + userId, {}).then((res1) => {
                        if (res1.data.code === 200) {
                            console.log(res1);
                            _this.collects = res1.data.data;
                            _this.collects.forEach((share) => {
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
                            })
                            return true;
                        } else {
                            return false;
                        }
                    })

                    return true;
                } else {
                    return false;
                }
            });
            // 判断是否为当前用户
            http.fetchGet('currentuser', {}). then((res) => {
                if (res.data.code === 200) {
                    _this.isSelf = (res.data.data.id.toString() === userId);
                    return true;
                } else {
                    return false;
                }
            });
            // 获取收藏

        },
        methods: {
            deleteWork(shareId) {
                let _this = this
                if (confirm("确认要删除吗？") === true) {
                    http.fetchGet('deletework?shareId=' + shareId, {}).then((res) => {
                        if (res.data.code === 200) {
                            alert('成功');
                            _this.$router.go(0);
                            return true;
                        } else {
                            alert('失败');
                            return false;
                        }
                    });
                }
            },
            toPlayPage(musicScore) {
                this.$router.push({
                    path: '/play',
                    query: {
                        score: musicScore
                    }
                });
            },
            playMusic(share) {
                // let _this = this;
                if (share.type === 1) {
                    // 播放曲谱
                    piano.playByScoreText(share.musicScore);
                } else {
                    // 播放wav
                    let audio = document.getElementsByTagName('audio')[0];
                    audio.src = `${Config.apiUrl}/${Config.apiPrefix}/getrecord?shareId=` + share.id;
                    audio.load();
                    audio.play();
                }
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
            getUser(userId) {
                let _this = this;
                http.fetchGet('getuser?userId=' + userId, {}).then((res) => {
                    if (res.data.code === 200) {
                        _this.user = res.data.data;
                        return true;
                    } else {
                        return false;
                    }
                });
            },
            toSelectUserPage(userId) {
                let _this = this;
                _this.$router.push({
                    path: '/user',
                    query: {
                        userId: userId
                    }
                });
            },
            toUserPage() {
                let _this = this;
                _this.$router.push({
                    path: '/user',
                    query: {
                        userId: _this.user.id
                    }
                });
            },
            toCollectsPage() {
                let _this = this;
                _this.$router.push({
                    path: '/collects',
                    query: {
                        userId: _this.user.id
                    }
                });
            },
            toWorksPage() {
                let _this = this;
                _this.$router.push({
                    path: '/works',
                    query: {
                        userId: _this.user.id
                    }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    .main {
        height: 100%;
        width: 100%;
        min-width: 980px;
    }

    .page-head {
        z-index: 1;
    }

    .user-background {
        position: absolute;
        top: 80px;

        width: 100%;
        height: 250px;

        background-image: linear-gradient(to top, #fed6e3, #fff);

        img {
            position: absolute;
            top: 100%;
            left: 50%;
            transform: translate(-50%, -50%);

            width: 80px;
            height: 80px;

            border-radius: 50%;
        }
    }

    .nickname {
        position: absolute;
        left: 50%;
        top: 180px;
        transform: translate(-50%, 0);

        width: 980px;

        color: #7B05FF;
        font-weight: bold;
        font-size: 50px;
        text-align: center;
        text-shadow: 10px 10px 20px #888888;
    }

    .brief-info {
        display: flex;
        justify-content: center;
        align-items: center;

        position: absolute;
        top: 390px;
        left: 50%;
        transform: translate(-50%, 0);

        width: 13%;

        .note {
            margin-left: 15px;

            font-weight: bold;
            font-size: 20px;
            color: #fff;
            text-align: center;
        }
    }

    .info-area {

        position: absolute;
        top: 450px;
        left: 50%;
        transform: translate(-50%, 0);

        padding: 20px;
        width: 60%;

        font-size: 20px;
        color: #fff;

        background-color: #00000080;

        .nav-bar {
            display: flex;
            align-items: center;
            justify-content: space-around;

            position: absolute;
            left: 50%;
            transform: translate(-50%, 0);

            width: 60%;

            font-size: 25px;
            font-weight: bold;
            color: #FCDC00;

            div {
                padding: 5px 10px 5px 10px;
                cursor: pointer;
            }

            .select {
                border-radius: 20px;
                background-color: #9795F0;
            }
        }
    }

    .content-area {
        padding: 10px 40px;
        position: relative;
        top: 100px;

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
                left: 500px;
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

            .like, .collect, .delete {
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
            .delete {
                position: absolute;
                bottom: 15px;
                left: 400px;
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