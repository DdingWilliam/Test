<template>
    <el-dropdown class="el-drop">
    <span class="el-dropdown-link">
        <el-avatar :size="40" :src="avatarUrl" />
        &nbsp;&nbsp;{{ userInfo.username }}&nbsp;
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
        <template #dropdown>
            <el-dropdown-menu>
                <el-dropdown-item>
                    <router-link class="myrouter-link" :to="{name:'个人中心'}">个人中心</router-link>
                </el-dropdown-item>
                <el-dropdown-item @click="logout">安全退出</el-dropdown-item>
            </el-dropdown-menu>
        </template>
    </el-dropdown>
</template>

<script setup>
import {XCStore} from "../../../store/XCStore.js";
import { ArrowDown } from '@element-plus/icons-vue'
import RequestUtil, {getServerUrl} from "../../../utils/request.js"
import {useRouter} from "vue-router";
import {ref} from "vue";

const router = useRouter()
const store = XCStore()
const userInfo = ref(store.GET_USER())
const avatarUrl = ref('')
avatarUrl.value = `${getServerUrl()}image/userAvatar/${userInfo.value.avatar}`

const logout = async function(){
    const result = await RequestUtil.get(`api/auth/logout`)
    console.log(result.data)
    if(result.data.status === 200){
        sessionStorage.clear()
        console.log(sessionStorage.getItem("token"))
        console.log(store.GET_TOKEN)
        store.SET_ROUTES_STATE(store.$state,false)
        store.RESET_TABS(store.$state)
        await router.replace(`/login`)
    }
}

</script>

<style scoped>

.el-dropdown-link {
    cursor: pointer;
    color: coral;
    display: flex;
    align-items: center;
}

.myrouter-link{
    text-decoration: none;
    color: var(--el-text-color-regular);
}

.el-drop {
    outline: none;
}

</style>