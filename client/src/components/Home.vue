<template>
    <div class="main">
        <img class="title" src="../assets/title.png" alt="炫·律">
        <div class="entry">
            <button class="register-button" v-on:click="$router.push('/register')">注册</button>
            <button class="login-button" v-on:click="$router.push( '/login')">登录</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Main"
    }
</script>

<style lang="scss" scoped>
    .main {
        height: 100%;
    }

    .title {
        margin: 120px auto;
        height: 27%;
        width: 40%;
    }

    .entry {
        display: flex;
        align-items: center;
        justify-content: space-around;

        margin: 20px auto;
        width: 30%;
        height: 20%;

        button {
            width: 35%;
            height: 28%;
            box-shadow: 5px 5px 10px #888888;

            border: 0;
            border-radius: 20px;

            font-weight: bold;
            font-size: 21px;
            color: #fff;

            cursor: pointer;

            &:active {
                opacity: 0.5;
            }

            &:hover {
                box-shadow: 10px 10px 15px #888888;
            }
        }
        .register-button {
            background-image: linear-gradient(to right, #7B68EEcc, #BA55D3cc);
        }
        .login-button {
            background-image: linear-gradient(to right, #7B68EEcc, #BA55D3cc);
        }
    }
</style>