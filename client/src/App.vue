<template>
    <div id="app">
<!--        <img alt="Vue logo" src="./assets/logo.png">-->
<!--        <HelloWorld msg="Welcome to Your Vue.js App"/>-->
        <router-view></router-view>
    </div>
</template>

<script>
    // import HelloWorld from './components/HelloWorld.vue'

    export default {
        name: 'App'
        // components: {
        //   HelloWorld
        // }
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        height: 100%;
    }

    * {
        margin: 0;
        padding: 0;
    }

    html, body {
        height: 100vh;
        background-image: linear-gradient(to top, #9795f0, #fbc8d4);
        background-repeat: no-repeat;
        background-attachment: fixed;
    }
</style>
