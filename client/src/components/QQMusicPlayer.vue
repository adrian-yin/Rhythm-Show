<template>
    <div class="qq-music-player">
        <audio class="player" controls></audio>
    </div>
</template>

<script>
    export default {
        name: "QQMusicPlayer",
        mounted() {

        },
        methods: {

        }
    }
</script>

<style lang="scss" scoped>
    .player {
        width: 300px;
        height: 35px;
    }

    /* 控制音乐播放组件深层背景色 */
    audio::-webkit-media-controls-panel {
        background: #C6ADE2;
    }
</style>