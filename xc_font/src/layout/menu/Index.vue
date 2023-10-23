<template>
    <el-row class="tac">
        <el-col>
            <h5
                class="mb-2"
                align="center"
                style="margin-top: 10px"
            >XC-权限管理系统</h5>
            <el-menu
                active-text-color="#ffd04b"
                background-color="#a3aa83"
                class="el-menu-vertical-demo"
                router
                :default-active="activeIndex"
                text-color="#fff"
            >
                <el-menu-item index="/index">
                    <el-icon><home-filled /></el-icon>
                    <span>首页</span>
                </el-menu-item>

                <el-sub-menu v-for="(item,index) in menus" :index="item.path" v-key="index" >
                    <template #title>
                        <el-icon><svg-icon :name="item.icon" /></el-icon>
                        <span>{{item.name}}</span>
                    </template>
                    <el-menu-item
                        v-for="(itemChild,index) in item.children"
                                  :index="itemChild.path"
                        v-key="index"
                        @click="openTab(itemChild)"
                    >
                        <el-icon><svg-icon :name="itemChild.icon" /></el-icon>
                        <span>{{itemChild.name}}</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-col>
    </el-row>
</template>

<script setup>
import {HomeFilled,User,Tickets,Goods,DocumentAdd,Management,Setting,Edit,SwitchButton,Promotion} from '@element-plus/icons-vue'
import {XCStore} from "../../store/XCStore.js";
import {ref, watch} from "vue";

const store = XCStore()
const menus = store.GET_MENUS()

const openTab=(item)=>{
    store.ADD_TABS(store.$state,item)
}

const activeIndex = ref('/index')

watch(store.$state,()=>{
    activeIndex.value = store.$state.editableTabsValue
},{
    deep:true,
    immediate:true
})
</script>
