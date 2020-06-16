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
                <div class="select" v-on:click="toWorksPage">作品</div>
                <div v-on:click="toCollectsPage">收藏</div>
            </div>
            <div class="email">邮箱：{{user.email}}</div>
            <div class="birthday">生日：{{user.birthday}}</div>
        </div>
    </div>
</template>

<script>
    import PageHead from "@/components/PageHead";
    import maleImg from "@/assets/male.png";
    import femaleImg from "@/assets/female.png"
    import http from "@/utils/http";

    export default {
        name: "Works",
        components: {
            'page-head': PageHead
        },
        data() {
            return {
                user: null
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
            let userId = this.$route.query.userId;
            this.getUser(userId);
        },
        methods: {
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
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;

        position: absolute;
        top: 450px;
        left: 50%;
        transform: translate(-50%, 0);

        padding: 20px;
        height: 260px;
        width: 80%;

        font-size: 25px;
        color: #fff;

        background-color: #00000080;

        .nav-bar {
            display: flex;
            align-items: center;
            justify-content: space-around;

            width: 60%;

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
</style>