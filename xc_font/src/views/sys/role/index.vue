<template>
    <div class="app-container">

        <el-row :gutter="20" class="header">
            <el-col :span="7">
                <el-input placeholder="请输入用户名..." v-model="queryForm.query" clearable ></el-input>
            </el-col>
            <el-button type="primary" :icon="Search" @click="initRoleList">搜索</el-button>
            <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue()" v-if="store.HAS_AUTH('system:role:add')">新增</el-button>
            <el-popconfirm title="您确定批量删除这些记录吗？" @confirm="handleDelete(null)">
                <template #reference>
                    <el-button type="danger" :disabled="delBtnStatus" :icon="Delete" v-if="store.HAS_AUTH('system:role:delete')">批量删除</el-button>
                </template>
            </el-popconfirm>
        </el-row>

        <el-table :data="tableData"
                  stripe style="width: 100%"
                  @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" />

            <el-table-column prop="name" label="角色名" width="150" align="center"/>

            <el-table-column prop="code" label="权限字符" width="250" align="center"/>

            <el-table-column prop="createTime" label="创建时间" width="300" align="center"/>
            <el-table-column prop="remark" label="备注"  align="center"/>
            <el-table-column prop="action" label="操作" width="400" fixed="right" align="center">
                <template v-slot="scope" >
                    <el-button  type="primary" :icon="Tools"  @click="handleMenuDialogValue(scope.row.id,scope.row.sysRoleList)" v-if="store.HAS_AUTH('system:role:menu')">分配权限</el-button>
                    <el-button v-if="scope.row.code!='admin' && store.HAS_AUTH('system:role:edit')" type="primary" :icon="Edit" @click="handleDialogValue(scope.row.id)" />

                    <el-popconfirm  v-if="scope.row.code!='admin'" title="您确定要删除这条记录吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button  type="danger" :icon="Delete" v-if="store.HAS_AUTH('system:role:delete')"/>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>


        </el-table>

    <div class="demo-pagination-block">
        <el-pagination
            v-model:current-page="queryForm.pageNum"
            v-model:page-size="queryForm.pageSize"
            :page-sizes="[5,10,15,20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
    </div>

    </div>
    <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :id="id" :dialogTitle="dialogTitle" @initRoleList="initRoleList"></Dialog>
    <MenuDialog v-model="menuDialogVisible" :menuDialogVisible="menuDialogVisible" :id="id" @initRoleList="initRoleList"></MenuDialog>
</template>

<script setup>
import requestUtil, {getServerUrl} from "../../../utils/request.js"
import {ref} from "vue"
import {Delete, DocumentAdd, Edit, Menu, RefreshRight, Search, Tools} from "@element-plus/icons-vue";
import {ElMessage,ElNotification} from 'element-plus'
import Dialog from "./component/Dialog.vue"
import MenuDialog from "./component/MenuDialog.vue"
import {XCStore} from "@/store/XCStore.js";

const store = XCStore()

const delBtnStatus = ref(true) // 删除标识符，true表示不能删除

const multipleSelection=ref([])  // 批量删除id

const tableData = ref({})

const sysRoleList = ref([])

const queryForm = ref({
    query: '',
    pageNum: 1,
    pageSize: 5
})

const total = ref(0)

const dialogVisible=ref(false)

const menuDialogVisible=ref(false)

const dialogTitle=ref('')

const id=ref(-1)

// 获取用户所有信息（分页的用户信息、角色信息...）
const initRoleList = async ()=> {
    const result = await requestUtil.post(`sys/role/list`, queryForm.value)
    const data = result.data
    console.log(data.data)
    if (data.status === 200) {
        tableData.value = data.data.info
        total.value = data.data.total
    }else{
        ElMessage.error(data.data)
    }
}
initRoleList()

// 处理分页布局
const handleSizeChange=(pageSize)=>{
    queryForm.value.pageNum = 1
    queryForm.value.pageSize=pageSize
    initRoleList()
}

const handleCurrentChange = (pageNum)=>{
    queryForm.value.pageNum = pageNum
    initRoleList()
}

/*以roleId标志变量的值来判断是进行用户修改还是添加*/
const handleDialogValue = (roleId)=>{
    console.log(roleId)
    if(roleId){
        id.value = roleId;
        dialogTitle.value = "角色修改"
    }else{
        id.value = -1;
        dialogTitle.value = "角色添加"
    }
    dialogVisible.value = true
}

// 给用户分配好角色
// 且让menu修改标志变量为true
const handleMenuDialogValue = (roleId,roleList)=>{
    console.log("roleId="+roleId)
    id.value=roleId;
    sysRoleList.value=roleList;
    menuDialogVisible.value=true
}

const handleSelectionChange = (selection)=>{
    // 有选择项才有批量删除
    if(selection.length>0) delBtnStatus.value = false;
    else delBtnStatus.value = true
    multipleSelection.value = selection
}
const handleDelete=async (id)=>{
    var ids = []
    if(id){
        ids.push(id)
    }else{
        multipleSelection.value.forEach(row=>{
            if(row.id != 1) ids.push(row.id)
        })
    }
    const res=await requestUtil.post("sys/role/delete",ids)
    if(res.data.status==200){
        ElMessage({
            type: 'success',
            message: res.data.data
        })
        initRoleList();
    }else{
        ElMessage({
            type: 'error',
            message: res.data.data,
        })
    }
}

const handleResetPassword = async (id)=>{
    const res = await requestUtil.get(`sys/role/resetPassword/${id}`)
    const data = res.data
    if(data.status == 200){
        ElMessage.success(data.data)
        initRoleList();
    }else{
        ElMessage.error(data.data)
    }
}

const statusChangeHandle = async (row) => {
    const res = await requestUtil.get(`sys/role/updateStatus/${row.id}/status/${row.status}`)
    const data = res.data
    if(data.status === 200){
        ElMessage.success(data.data)
    }else{
        ElMessage.error(data.data)
        initRoleList();
    }
}


/*
async function test(){
    const result = await requestUtil.post(`sys/role/list`,queryForm.value)
    const data = result.data
    console.log(data)
    if(data.status==200){
        console.log(data.data)
    }
}
*/
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