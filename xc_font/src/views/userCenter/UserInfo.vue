<template>
    <el-form ref="userRef" :model="form" :rules="rules" label-width="100px" >
        <el-form-item label="手机号码：" prop="phonenumber">
            <el-input v-model="form.phonenumber" maxlength="11" />
        </el-form-item>
        <el-form-item label="用户邮箱：" prop="email">
            <el-input v-model="form.email" maxlength="50" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleSubmit">保存</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import requestUtil from "../../utils/request.js"
import {ElMessage,ElNotification} from "element-plus";
import {defineEmits, defineProps, ref, watch} from "vue";
import {XCStore} from "../../store/XCStore.js";

const store = XCStore()

const props = defineProps({
    user:{
        required:true
    }
})

const form=ref({
    id:-1,
    phonenumber:'',
    email:''
})

const userRef=ref(null)

const rules = ref({
    email: [{ required: true, message: "邮箱地址不能为空", trigger: "blur" }, { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }],
    phonenumber: [{ required: true, message: "手机号码不能为空", trigger: "blur" }, { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }],
});


form.value = props.user

const handleSubmit=()=>{

    userRef.value.validate(async (valid)=>{
        if(valid) {
            let result = await requestUtil.post("sys/user/save", form.value);
            let data = result.data;
            if (data.status == 200) {
                ElMessage.success(data.data)
                store.SET_USER(store.$state,form.value)
                console.log(store.GET_USER())
            }else{
                ElNotification.error(data.data)
            }
        }
    })
}

</script>

<style scoped>

</style>