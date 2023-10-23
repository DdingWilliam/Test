<template>
<!--    <el-button type="danger" @click="test">测试</el-button>
    <el-button type="danger" @click="testLogout">测试退出登录</el-button>-->

    <div class="common-layout">
        <el-container class="el-container">
            <el-aside width="200px" class="common-aside"><Menu/></el-aside>
            <el-container>
                <el-header class="common-header"><Header/></el-header>
                <el-main class="common-main">
                    <Tabs/>
                    <router-view/>
                </el-main>
                <el-footer><Footer/></el-footer>
            </el-container>
        </el-container>
    </div>
</template>

<script setup>
import Footer from "./footer/Index.vue"
import Header from "./header/Index.vue"
import Menu from "./menu/Index.vue"
import Tabs from "./tabs/Index.vue"
    import RequestUtil from "../utils/request.js"
    async function test(){
        console.log(666)
        const result = await RequestUtil.get("test/user/list")
        console.log(result)
        /*const data = result.data
        console.log(data)*/
    }

    async  function testLogout(){
        const result = await RequestUtil.get("api/auth/logout")
        console.log(result.data)
    }
</script>

<style scoped>

.el-container {
    height: 100%;
}

.common-layout {
    position: relative;
    width: 100%;
    height: 100%;
}

.common-layout el-container {
    height: 100%;
}

.common-aside {
    background: #a3aa83;
    border-right: 1px solid #ccc;
    height: 100%;
}

.common-header {
    background: #fff;
    border-bottom: 1px solid #ccc;
    padding: 0px;
}

.common-main {
    background: #fff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s ease-in-out;
    padding-top: 0px;
    padding-left: 0px;
    padding-right: 0px;
}

.common-main:hover {
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
}
</style>
