<template>
    <el-dialog
            model-value="menuDialogVisible"
            title="分配权限"
            width="30%"
            @close="handleClose"
    >

        <el-form
                ref="formRef"
                :model="form"
                label-width="100px"
        >

            <el-tree
                    ref="treeRef"
                    :data="treeData"
                    :props="defaultProps"
                    show-checkbox
                    :default-expand-all=true
                    node-key="id"
                    :check-strictly=true
            />
        </el-form>

        <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleConfirm">确认</el-button>
        <el-button  @click="handleClose">取消</el-button>
      </span>
        </template>
    </el-dialog>
</template>

<script setup>

import {defineEmits, defineProps, ref, watch} from "vue";
import requestUtil,{getServerUrl} from "../../../../utils/request";
import { ElMessage } from 'element-plus'

const defaultProps = {
    children: 'children',
    label: 'name'
}


// 从父组件传过来的信息参数，子组件接受
const props=defineProps(
    {
        id:{
            type:Number,
            default:-1,
            required:true
        },
        menuDialogVisible:{
            type:Boolean,
            default:false,
            required:true
        }
    }
)


const form=ref({
    id:-1,
    menuList:[],
    checkedMenus:[],
})

const treeData=ref([]);

const formRef=ref(null)

const treeRef=ref(null)

const initFormData=async(id)=>{
    const res=await requestUtil.get("sys/menu/treeList");
    treeData.value = res.data.data
    form.value.id=id;

    const res2 = await requestUtil.get(`sys/menu/menus/${id}`)
    console.log("res2:",res2.data)
    treeRef.value.setCheckedKeys(res2.data.data)
}


watch(
    ()=>props.menuDialogVisible,
    ()=>{
        let id=props.id;
        console.log("id="+id)
        if(id!=-1){
            initFormData(id)
        }
    }
)


const emits=defineEmits(['update:modelValue','initRoleList'])

const handleClose=()=>{
    emits('update:modelValue',false)
}

const handleConfirm=()=>{
    formRef.value.validate(async(valid)=>{
        if(valid){
            const menuIds = treeRef.value.getCheckedKeys();
            console.log(menuIds)
            let result=await requestUtil.post(`sys/menu/updateMenus/${form.value.id}`,menuIds);
            let data=result.data;
            if(data.status===200){
                ElMessage.success(data.data)

                emits("initRoleList")
                handleClose();
            }else{
                ElMessage.error("权限分配失败,请联系管理员！");
            }
        }else{
            console.log("fail")
        }
    })
}

</script>