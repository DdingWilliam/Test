<template>
    <div class="app-container">

        <el-row class="header" style="padding-left: 20px">
            <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue()" v-if="store.HAS_AUTH('system:menu:add')">新增</el-button>
        </el-row>

        <el-table
            :data="tableData"
            style="width: 100%; margin-bottom: 20px"
            row-key="id"
            border
            stripe
            default-expand-all
            :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
            <el-table-column prop="name" label="菜单名称"  width="180" />
            <el-table-column prop="icon" label="图标"  width="70" align="center">
                <template v-slot="scope">
                    <el-icon><svg-icon :name="scope.row.icon" /></el-icon>
                </template>
            </el-table-column>
            <el-table-column prop="orderNum" label="排序"  width="70" align="center"/>
            <el-table-column prop="perms" label="权限标识"  width="200" />
            <el-table-column prop="path" label="组件路径"  width="180" />
            <el-table-column prop="menuType" label="菜单类型"  width="120" align="center">
                <template v-slot="scope">
                    <el-tag size="small" v-if="scope.row.menuType === 'M'" type="danger" effect="dark">目录</el-tag>
                    <el-tag size="small" v-else-if="scope.row.menuType === 'C'" type="success" effect="dark">菜单</el-tag>
                    <el-tag size="small" v-else-if="scope.row.menuType === 'F'" type="warning" effect="dark">按钮</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"  align="center"/>
            <el-table-column prop="action" label="操作" width="300" fixed="right" align="center">
                <template v-slot="scope" >
                    <el-button  type="primary" :icon="Edit" @click="handleDialogValue(scope.row.id)" v-if="store.HAS_AUTH('system:menu:edit')"/>
                    <el-popconfirm   title="您确定要删除这条记录吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button  type="danger" :icon="Delete" v-if="store.HAS_AUTH('system:menu:delete')"/>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

    </div>
    <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :tableData="tableData" :id="id" :dialogTitle="dialogTitle" @initMenuList="initMenuList"></Dialog>
</template>

<script setup>
import requestUtil, {getServerUrl} from "../../../utils/request.js"
import {ref} from "vue"
import {Delete, DocumentAdd, Edit, Menu, RefreshRight, Search, Tools} from "@element-plus/icons-vue";
import {ElMessage,ElNotification} from 'element-plus'
import Dialog from "./component/Dialog.vue"
import {XCStore} from "@/store/XCStore.js";
import {useRouter} from "vue-router";

const router = useRouter()
const store = XCStore()

const tableData = ref({})

const dialogVisible=ref(false)

const dialogTitle=ref('')

const id=ref(-1)

// 获取用户所有信息（分页的用户信息、角色信息...）
const initMenuList = async ()=> {
    const result = await requestUtil.get(`sys/menu/treeList`)
    const data = result.data
    console.log(data.data)
    if (data.status === 200) {
        tableData.value = data.data
        console.log(tableData)
    }else{
        let obj = {
            name: "首页",
            path: "/index"
        }
        store.ADD_TABS(store.$state,obj)
        ElMessage.error(data.data)
        console.log("error:",data.data)
    }
}
initMenuList()



/*以menuId标志变量的值来判断是进行用户修改还是添加*/
const handleDialogValue = (menuId)=>{
    console.log(menuId)
    if(menuId){
        id.value = menuId;
        dialogTitle.value = "菜单修改"
    }else{
        id.value = -1;
        dialogTitle.value = "菜单添加"
    }
    dialogVisible.value = true
}

const handleDelete=async (id)=>{
    const res=await requestUtil.post(`sys/menu/delete/${id}`)
    if(res.data.status==200){
        ElMessage({
            type: 'success',
            message: res.data.data
        })
        initMenuList();
    }else{
        ElMessage({
            type: 'error',
            message: res.data.data,
        })
    }
}

</script>

<style scoped>


.app-container{
    width:100%;
    overflow: hidden;
}

.demo-pagination-block + .demo-pagination-block {
    margin-top: 10px;
}
.demo-pagination-block .demonstration {
    margin-bottom: 16px;
}
.header{
    padding-bottom: 16px;
    box-sizing: border-box;
}

.el-pagination{
    float: right;
    padding: 20px;
    box-sizing: border-box;
}

.el-table__cell{
    word-break: break-word;
    background-color: #f8f8f9 !important;
    color: #515a6e;
    height: 40px;
    font-size: 13px;

}

.el-tag--small {
    margin-left: 5px;
}

</style>