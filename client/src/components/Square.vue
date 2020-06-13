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
<!--                        TODO: 添加图标-->
                        <div class="num">{{share.likeNum}}</div>
                    </div>
                    <div class="like">
                        <!--                        TODO: 添加赞图标-->
                        <div class="num">{{share.collectNum}}</div>
                    </div>
                    <div class="username">{{share.userNickname}}</div>
<!--                    添加播放和演奏按钮-->
                    <hr class="share-line">
                </div>
            </div>
        </section>
    </div>
</template>

<script>
    import PageHead from "@/components/PageHead";

    export default {
        name: "Square",
        components: {
             'page-head': PageHead
        },
        data() {
            return {
                shares: [
                    {
                        id: 1,
                        userId: 6,
                        userNickname: '八零一五',
                        name: '致爱丽丝',
                        describe: '简单的描述',
                        type: 1,
                        typeName: '曲谱',
                        isOriginal: 0,
                        isOriginalName: '非原创',
                        likeNum: 23,
                        collectNum: 8,
                        musicScore: 'C3(8) B4(2) F#3(4)'
                    },
                    {
                        id: 2,
                        userId: 4,
                        userNickname: '八零',
                        name: '致丽',
                        describe: '简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发简单的描述还有阿萨的佛教离开仔细检查v可立即执行啊手动阀沙发',
                        type: 0,
                        typeName: '录音',
                        isOriginal: 1,
                        isOriginalName: '原创',
                        likeNum: 108,
                        collectNum: 5,
                        musicScore: 'D5(16) B3(4) 0(4) F#3(4) A3(8)'
                    },
                ]
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
                color: #fed6e3;
                cursor: pointer;
            }

            label {
                margin: 0 20px 0 3px;
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

            height: 230px;

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
            }

            .describe {
                position: absolute;
                left: 5px;
                top: 40px;

                padding: 5px;
                width: 70%;
                height: 160px;
                box-sizing: border-box;
                overflow: hidden;

                text-align: left;
                line-height: 32px;
                text-indent: 2em;
            }
        }
    }
</style>