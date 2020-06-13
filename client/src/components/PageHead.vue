<template>
    <header>
        <div class="title">
            炫·律
        </div>
        <label class="search-box">
            <img src="../assets/search.png" alt="">
            <input type="text" v-on:keypress="searchScore">
        </label>
<!--        <img class="avatar" :src="this.avatarUrl" alt="" v-on:error="setDefaultAvatar($event)">-->
        <img class="avatar" src="../assets/default_avatar.jpg" alt=""
             v-on:mouseenter="showDropList" v-on:mouseleave="hideDropList">
        <button class="create-button" v-on:click="$router.push('/create')">开始创作</button>
        <div class="drop-list" v-on:mouseenter="showDropList" v-on:mouseleave="hideDropList">
            <button class="self-button" v-on:click="toSelfUserPage">adrianyin@yeah.net</button>
            <div class="link" v-on:click="toSelfWorkPage">我的作品</div>
            <div class="link" v-on:click="toSelfCollectPage">个人收藏</div>
            <button v-on:click="logout">退出登录</button>
        </div>
    </header>
</template>

<script>
    // import Url from '@/config/url';
    // import defaultAvatar from '@/assets/default_avatar.jpg';
    import store from '../store';

    export default {
        name: "PageHead",
        props: {
            nickname: String,
            userId: Number
        },
        data() {
            return {
                // 用来判断鼠标是否在下拉列表和头像区域
                inDropList: false
            }
        },
        // computed: {
        //     avatarUrl: function() {
        //         // 服务端图片路径
        //         return Url.apiUrl + '/resources/avatars/avatar_' + this.userId + '.jpg';
        //     }
        // },
        methods: {
            // TODO: 使用服务器头像
            // 若头像文件加载失败，使用默认替代头像
            // setDefaultAvatar(event) {
            //     event.target.src = defaultAvatar.toString();
            // }
            showDropList() {
                this.inDropList = true;
                let dropList = document.getElementsByClassName('drop-list')[0];
                dropList.style.visibility = 'visible';
            },
            hideDropList() {
                this.inDropList = false;
                let dropList = document.getElementsByClassName('drop-list')[0];
                // 延时0.2秒隐藏
                let _this = this;
                setTimeout(function() {
                    if (!_this.inDropList) {
                        dropList.style.visibility = 'hidden';
                    }
                }, 200);
            },
            logout() {
                // 清空token
                store.commit('del_token');
                sessionStorage.removeItem('token');

                this.$router.push('/login');
            },
            // 搜索乐谱，跳转到指定链接
            searchScore(event) {
                if (event.key === 'Enter') {
                    // TODO: 完成搜索乐谱
                    alert(event.target.value);
                }
            },
            toSelfUserPage() {
                this.$router.push({
                    path: '/user',
                    query: {
                        userId: this.userId.toString()
                    }
                });
            },
            toSelfWorkPage() {
                this.$router.push({
                    path: '/work',
                    query: {
                        userId: this.userId.toString()
                    }
                });
            },
            toSelfCollectPage() {
                this.$router.push({
                    path: '/collect',
                    query: {
                        userId: this.userId.toString()
                    }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    header {
        position: fixed;
        top: 0;

        width: 100%;
        height: 80px;
        min-width: 980px;

        background-color: #00000080;
    }

    .title {
        position: absolute;
        top: 50%;
        left: 60px;
        transform: translate(0, -50%);

        font-weight: bold;
        font-size: 35px;
        letter-spacing: 15px;
        color: #fff;
    }

    .search-box {
        position: absolute;
        top: 50%;
        left: 400px;
        transform: translate(0, -50%);

        img {
            position: absolute;
            top: 50%;
            left: 5px;
            transform: translate(0, -50%);
        }

        input {
            padding-left: 25px;
            width: 200px;
            height: 25px;

            border-radius: 20px;
            border-style: solid;
            border-width: 1px;
            border-color: #fff;

            font-size: 17px;
            color: #fff;

            background-color: #00000000;
        }
    }

    .avatar {
        position: absolute;
        top: 50%;
        right: 200px;
        transform: translate(0, -50%);

        height: 56px;
        width: 56px;

        cursor: pointer;
    }

    .create-button {
        position: absolute;
        top: 50%;
        right: 60px;
        transform: translate(0, -50%);

        width: 110px;
        height: 36px;

        border-radius: 20px;
        border: 0;

        font-size: 18px;
        color: #fff;

        background-image: linear-gradient(to right, #7b68ee, #ba55d3);
        cursor: pointer;

        &:hover {
            box-shadow: 3px 3px 5px #888888;
        }

        &:active {
            opacity: 0.5;
        }
    }

    .drop-list {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;
        visibility: hidden;

        position: absolute;
        right: 128px;
        transform: translate(0, 100px);

        width: 200px;
        height: 200px;

        text-align: center;
        font-size: 18px;
        color: #fff;
        letter-spacing: 3px;

        background-color: #00000080;

        /* 向上箭头 */
        &::before {
            content: '';

            position: absolute;
            top: 0;

            border: 15px solid;
            border-color: transparent transparent #00000080;

            transform: translate(0, -30px);
        }

        button {
            box-sizing: border-box;
            padding: 3px;
            width: 130px;
            height: 40px;

            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;

            font-size: 18px;
            color: #fff;
            letter-spacing: 3px;

            background-color: #00000000;
            cursor: pointer;

            &:hover {
                box-shadow: 2px 2px 3px #888888;
            }
        }
        .self-button {
            letter-spacing: 0;
        }

        .link {
            padding: 3px;
            box-sizing: border-box;

            cursor: pointer;

            &:hover {
                border-bottom: 1px solid #fff;
            }
        }
    }
</style>