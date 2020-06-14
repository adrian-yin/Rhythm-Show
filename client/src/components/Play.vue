<template>
    <div class="main">
        <piano class="piano"></piano>
        <p class="score-area">
            {{score}}
        </p>
    </div>
</template>

<script>
    import Piano from "@/components/Piano";

    export default {
        name: "Play",
        components: {
            piano: Piano
        },
        created() {
            if (this.$route.query.score) {
                this.score = this.$route.query.score;
            }
        },
        data() {
            return {
                score: '无选中乐谱'
            }
        }
    }
</script>

<style lang="scss" scoped>
    .main {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
        height: 100%;
    }

    .piano {
        margin-top: 30px;
    }

    .score-area {
        // 限制6行，多行溢出显示省略号
        display: -webkit-box;
        -webkit-line-clamp: 6;
        -webkit-box-orient: vertical;

        margin-top: 30px;
        padding: 10px 30px;
        height: 300px;
        width: 80%;
        overflow: hidden;

        font-size: 30px;
        color: #fff;
        text-align: center;
        line-height: 50px;
        word-break: break-word;
        text-overflow: ellipsis;

        background-color: #00000080;
    }
</style>