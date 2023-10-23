<template>
    <el-tabs
        v-model="editableTabsValue"
        type="card"
        class="demo-tabs"
        closable
        @tab-remove="removeTab(editableTabsValue)"
        @tab-click="clickTab"
    >
        <el-tab-pane
            v-for="item in editableTabs"
            :key="item.name"
            :label="item.title"
            :name="item.name"
        >
            {{ item.content }}
        </el-tab-pane>
    </el-tabs>
</template>
<script setup>
import { ref ,watch} from 'vue'
import {XCStore} from "../../store/XCStore.js";
import {useRouter} from "vue-router";

const router = useRouter()
const store = XCStore()
let tabIndex = 2
let editableTabsValue = ref(store.editableTabsValue)
let editableTabs = ref(store.editableTabs)

const removeTab = (targetName) => {

    let activeName = editableTabsValue.value
    let tabs = editableTabs.value

    if(targetName === '/index') return



    if (activeName === targetName) {
        tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
                const nextTab = tabs[index + 1] || tabs[index - 1]
                if (nextTab) {
                    activeName = nextTab.name
                }
            }
        })
    }

    editableTabsValue.value = activeName
    editableTabs.value = tabs.filter((tab) => tab.name !== targetName)

    store.$state.editableTabsValue=editableTabsValue.value
    store.$state.editableTabs=editableTabs.value

    router.push({path:activeName})
}

const clickTab = (target)=>{
    router.push({
        name: target.props.label
    })
}

const refreshTabs = ()=>{
    editableTabsValue.value = store.$state.editableTabsValue
    editableTabs.value = store.$state.editableTabs
}

watch(editableTabsValue,()=>{
    store.$state.editableTabsValue=editableTabsValue.value
},{deep:true,immediate:true})


watch(store.$state,()=>{
    refreshTabs();
},{deep:true,immediate:true})

</script>
<style scoped>
.demo-tabs > .el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
}
</style>
