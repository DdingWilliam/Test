<template>
    <el-form
        ref="formRef"
        :model="form"
        label-width="100px"
        style="text-align: center;padding-bottom:10px"
    >
        <el-upload
            :headers="headers"
            class="avatar-uploader"
            :action="getServerUrl()+'sys/user/uploadImage'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>

        <el-button @click="handleConfirm" >确认更换</el-button>

    </el-form>
</template>

<script setup>
import { ref , defineProps} from 'vue'
import { ElMessage ,ElNotification} from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import {XCStore} from "../../store/XCStore.js"
import {getServerUrl} from "../../utils/request.js";
import requestUtil from "../../utils/request.js"

const imageUrl = ref('')
const store = XCStore()
const user = store.GET_USER()

const props = defineProps(
    {
        user:{
            type:Object,
            default:()=>{},
            required:true
        }
    }
)

imageUrl.value = `${getServerUrl()}image/userAvatar/${user.avatar}`

const headers=ref({
    token:store.GET_TOKEN()
})

const form=ref({
    id:-1,
    avatar:''
})
form.value = props.user;
const formRef=ref(null)

const handleAvatarSuccess=(res)=>{
    console.log(res)
    imageUrl.value = getServerUrl()+res.data.src
    form.value.avatar = res.data.title;
}

const beforeAvatarUpload = (file)=>{
    console.log(file.type)
    const isJPG = file.type === 'image/jpeg'
    const isPNG = file.type === 'image/png'
    const isLt2M = file.size / 1024 / 1024 <2
    if (!isJPG && !isPNG) {
        ElMessage.error('图片必须是jpg格式或者png格式')
    }
    if (!isLt2M) {
        ElMessage.error('图片大小不能超过2M!')
    }
}
const handleConfirm=async()=>{

    let result=await requestUtil.post("sys/user/updateAvatar",form.value);
    let data=result.data;
    if(data.status==200){
        ElMessage.success(data.data)
        store.SET_USER(store.$state,form.value)
    }else{
        ElMessage.error(data.data);
    }

}

</script>

<style scoped>
.avatar-uploader .avatar {
    width: 120px;
    height: 120px;
    display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    text-align: center;
}
.avatar {
    width: 80px;
    height: 80px;
    display: block;
}
</style>
