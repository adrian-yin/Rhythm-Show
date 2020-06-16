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
                <div class="select" v-on:click="toUserPage">信息</div>
                <div v-on:click="toWorksPage">作品</div>
                <div v-on:click="toCollectsPage">收藏</div>
            </div>
            <button class="change-password" v-if="isSelf" v-on:click="changePassword">修改密码</button>
            <button class="edit-info" v-if="isSelf" v-on:click="changeUserInfo">编辑资料</button>
            <div class="email">邮箱：{{user.email}}</div>
            <div class="birthday">生日：{{birthdayText}}</div>
        </div>
        <user-info-dialog v-if="showUserInfoDialog" :user="user"></user-info-dialog>
        <password-dialog v-if="showPasswordDialog" :user-id="user.id"></password-dialog>
    </div>
</template>

<script>
    import PageHead from "@/components/PageHead";
    import maleImg from "@/assets/male.png";
    import femaleImg from "@/assets/female.png"
    import http from "@/utils/http";
    import UserInfoDialog from "@/components/UserInfoDialog";
    import PasswordDialog from "@/components/PasswordDialog";

    export default {
        name: "User",
        components: {
            'page-head': PageHead,
            'user-info-dialog': UserInfoDialog,
            'password-dialog': PasswordDialog
        },
        data() {
            return {
                user: null,
                isSelf: false,
                showUserInfoDialog: false,
                showPasswordDialog: false
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
            },
            'birthdayText': function() {
                let _this = this;
                if (_this.user.birthday === null) {
                    return '未知';
                } else {
                    let date = new Date(_this.user.birthday);
                    return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
                }
            }
        },
        created() {
            let _this = this;
            let userId = _this.$route.query.userId;
            this.getUser(userId);
            // 判断是否为当前用户
            http.fetchGet('currentuser', {}). then((res) => {
               if (res.data.code === 200) {
                   _this.isSelf = (res.data.data.id.toString() === userId);
                   return true;
               } else {
                   return false;
               }
            });
        },
        methods: {
            changeUserInfo() {
                this.showPasswordDialog = false;
                this.showUserInfoDialog = true;
            },
            changePassword() {
                this.showPasswordDialog = true;
                this.showUserInfoDialog = false;
            },
            hideDialog() {
                this.showPasswordDialog = false;
                this.showUserInfoDialog = false;
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
        height: 260px;
        width: 60%;

        font-size: 25px;
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

        .email {
            position: absolute;
            top: 110px;
            left: 50%;
            transform: translate(-50%, 0);
        }

        .birthday {
            position: absolute;
            top: 190px;
            left: 50%;
            transform: translate(-50%, 0);
        }

        button {
            height: 30px;
            width: 80px;

            border: 0;
            border-radius: 15px;

            font-size: 16px;

            background-color: #a8edea;
            cursor: pointer;

            &:hover {
                box-shadow: 3px 3px 5px #888888;
            }

            &:active {
                opacity: 0.5;
            }
        }
        .change-password {
            position: absolute;
            left: 3%;
            top: 80px;
        }
        .edit-info {
            position: absolute;
            left: 3%;
            top: 25px;
        }
    }
</style>