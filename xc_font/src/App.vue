<script setup>
import {useRouter,useRoute} from "vue-router";
import requestUtil from "./utils/request.js"
import {XCStore} from "./store/XCStore.js"
import {watch} from "vue";
import Svglcon from "@/components/SvgIcon.vue";

const store = XCStore()
const router = useRouter()
const route=useRoute();
const whitePath=['/login','/index','/']


watch(route,(to,from)=>{
    console.log("to"+to.name)
    console.log(to.path)

    if (whitePath.indexOf(to.path)===-1) {
        console.log("to.path="+to.path)
        let obj = {
            name: to.name,
            path: to.path
        }
            store.ADD_TABS(store.$state,obj)
    }

},{deep:true,immediate:true})


/*async function handleLogin() {
    const result = await requestUtil.get("test/login")
    const data = result.data
    if(data.status === 200){
        console.log("登录成功!token:" + data.data.token)
        store.SET_TOKEN(store.$state,data.data.token)
        console.log(store.token)
    }else {
        console.log("登录出错! ");
    }
}

async function handleUserList(){
    const result = await requestUtil.get("test/user/list")
    console.log(result)
    const data = result.data
    if(data.status === 200){
        console.log("用户信息：" + data)
    }else{
        console.log("请求用户信息失败！")
    }
}

async function logout(){
    const result = await requestUtil.get("api/auth/logout")
    const data = result.data
    if(data.status === 200){
        console.log(data)
    }else{
        console.log("退出登录失败")
    }
}*/

</script>

<template>
    <router-view/>
<!--    <el-button type="primary" @click="handleLogin">测试登录</el-button>
    <el-button type="danger" @click="handleUserList">测试获取用户请求</el-button>
    <h1>{{store.token}}</h1>-->
<!--    <el-button type="danger" @click="logout">退出登录</el-button>-->
</template>

<style>
/* 重置样式 */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* 设置 html 和 body 高度为 100% */
html,
body {
    height: 100%;
    user-select: none; /* 禁止选择文本 */
    overflow: hidden;
}

/* 设置根元素高度为 100% */
#app {
    height: 100%;
}
</style>