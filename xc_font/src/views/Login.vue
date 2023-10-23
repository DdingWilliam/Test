<template>
    <el-main>
        <div class="box">

            <div class="login-form">
                <div class="pre-box"  style="background-color: white;">
<!--                    <div class="img-box">-->
<!--                        <el-image-->
<!--                                src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F29%2F20180829115629_VY2rG.thumb.1000_0.gif&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1691115137&t=e77e0636a5d93fc30ad241c49b64dc21"-->
<!--                                alt="背景图片"/>-->
<!--                    </div>-->
                </div>

                <div class="title-box">
                    <h1>欢迎登录</h1>
                </div>

                <el-form class="w-[250px]" :rules="rules" ref="formRef" :model="formData">
<!--                    <el-form-item prop="userType">  &lt;!&ndash;prop：父组件向子组件传递数据的属性&ndash;&gt;-->
<!--                        <div class="input-box">-->
<!--                            <el-input name="usertype" v-model="formData.usertype" disabled>-->
<!--                            </el-input>-->
<!--                        </div>-->
<!--                    </el-form-item>-->
                    <el-form-item prop="userName">
                        <div class="input-box">
                            <el-input name="username" v-model="formData.username" placeholder="请输入用户名">
                                <template #prefix>
                                    <el-icon>
                                        <User/>
                                    </el-icon>
                                    <!--                                    <svg-icon name="user"/>-->
                                </template>
                            </el-input>
                        </div>
                    </el-form-item>
                    <el-form-item prop="password">
                        <div class="input-box">
                            <el-input name="password" type="password" show-password v-model="formData.password"
                                      placeholder="请输入密码">
                                <template #prefix>
                                    <el-icon>
                                        <lock/>
                                    </el-icon>
                                    <!--                                    <svg-icon name="lock" />-->
                                </template>
                            </el-input>
                        </div>
                    </el-form-item>
                    <el-form-item label="验证码" prop="code" >
                        <div class="input-box">
                            <el-input v-model="code" maxlength="5" style="width: 150px; float: left"></el-input>
                            <el-image :src="captchaData.captchaImg" class="captchaImg" @click="getCaptcha" style="padding-left: 5px;padding-bottom: 8px"></el-image>
                        </div>
                    </el-form-item>
                    <!--                    <el-checkbox name="rememberMe" v-model="formData.rememberMe"  style="margin:0px 245px 0px; color: white">记住密码</el-checkbox>-->
                    <el-form-item>
                        <div class="btn-box">
                            <el-button round class="w-[250px]" color="#6ee7b7" type="primary" @click.prevent="submit">
                                登录
                            </el-button>
                        </div>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </el-main>
</template>

<script setup>
import {ElMessage, ElNotification} from 'element-plus'
import {XCStore} from "../store/XCStore.js";
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import RequestUtil from "../utils/request.js"
import {Lock} from "@element-plus/icons-vue";
import SvgIcon from "@/components/SvgIcon.vue";

const router = useRouter()
const store = XCStore()

const captchaData = ref({})

const code = ref('')

const formData = ref({
    usertype: '普通用户',
    username: '',
    password: '',
    rememberMe: false
})

const rules = {
    username: [
        {required: true, message: '用户名不能为空', trigger: 'change'},
        {min: 1, max: 6, message: '长度小于7', trigger: 'blur'},
    ],
    password: [{
        required: true,
        message: '密码不能为空',
        trigger: 'change',
    },
    ],
}
const formRef = ref(null);

function submit() {
    formRef.value.validate(async (valid) => {
        if (valid) {
            try {
                let result = await RequestUtil.post(`api/auth/login?code=${code.value}&uuid=${captchaData.value.uuid}`, formData.value);
                /*const captchaRes = await RequestUtil.get(`/captcha`);
                captchaData.value = captchaRes.data.data*/
                let data = result.data
                if (data.status === 200) {
                    const resultObj = data.data;
                    console.log(resultObj)
                    sessionStorage.clear()
                    store.SET_TOKEN(store.$state, resultObj.jwt)
                    store.SET_MENUS(store.$state, resultObj.menus)
                    store.SET_USER(store.$state, resultObj.user)
                    store.SET_AUTH(store.$state, resultObj.authority)
                    /*console.log(store.GET_TOKEN())*/
                    console.log(store.GET_AUTH())
                    console.log(store.HAS_AUTH("system:user:add"))
                    await router.replace(`/`)
                } else {
                    ElMessage.error(data)
                }
            } catch (err) {
                console.log("error :" + err)
                ElMessage.error("服务器出错，请联系管理员")
            }
        } else {
            console.log("验证失败")
        }
    })
}

const getCaptcha = async () => {
    const captchaRes = await RequestUtil.get(`/captcha`);
    console.log(captchaRes.data)
    if (captchaRes.data.status === 200) {
        captchaData.value = captchaRes.data.data
        console.log(captchaData)
    }
}
getCaptcha()
</script>



<style scoped>
.btn-box p:hover {
    cursor: pointer;
    color: #c45656;
    text-decoration: underline;
}

/*.img-box {*/
/*    height: 550px;*/
/*    width: 800px;*/
/*    border-radius: 4px;*/
/*}*/

/*.img-box .el-image {*/
/*    height: 550px;*/
/*    border-radius: 4px;*/
/*    width: 800px;*/
/*}*/

.pre-box {
    position: absolute;
    width: 800px;
    height: 100%;
    left: 0;
    top: 0;
    border-radius: 4px;
    box-shadow: 4px 4px 3px rgba(0, 0, 0, -6);
}

.pre-box h1 {
    margin-top: 150px;
    text-align: center;
    letter-spacing: 5px;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, -6);
}

.pre-box p {
    margin: 20px 0;
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-weight: bold;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, -6);
}

.btn-box {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    flex: 1;
    line-height: 32px;
    position: relative;
    font-size: var(--font-size);
    min-width: 0;
    justify-content: center;
}

.btn-box p {
    cursor: pointer;
    height: 30px;
    font-size: 14px;
    line-height: 30px;
}

.input-box {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    flex: 1;
    line-height: 32px;
    position: relative;
    font-size: var(--font-size);
    min-width: 0;
    justify-content: center;
}

.box {
    height: 550px;
    width: 800px;
    background-color: #f1f5f8;
    margin: auto;
    border-radius: 9px;
    border: 1px solid rgba(255, 255, 255, -6);
    box-shadow: 4px 4px 3px rgba(0, 0, 0, -6);
    display: flex;
    position: relative;
}

.login-form, .registry-form {
    flex: 1;
    height: 100%;
}

.title-box {
    height: 200px;
    line-height: 300px;
    position: relative;
}

.title-box h1 {
    text-align: center;
    letter-spacing: 5px;
}

.el-input {
    outline: none;
    width: 40%;
    height: 40px;
    margin-bottom: 7px;
    text-indent: 4px;
    border: 1px solid #c6e2ff;
    border-radius: 4px;
}

.el-input:focus {
    color: #d9ecff;
}

.el-input:focus::placeholder {
    opacity: 0;
}

.el-main {
    height: 100vh;
    width: 100%;
    background: linear-gradient(to right, rgb(247, 209, 215), rgb(191, 227, 241));
    box-sizing: border-box;
    padding-top: 70px;
}
</style>