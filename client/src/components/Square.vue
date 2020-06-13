<template>
    <div class="main">
        <page-head></page-head>
    </div>
</template>

<script>
    import PageHead from "@/components/PageHead";

    export default {
        name: "Square",
        components: {
             'page-head': PageHead
        }
    }
</script>

<style scoped>
    .main {
        height: 100%;
        background-image: linear-gradient(to top, #9795f0, #fbc8d4);
    }
</style>