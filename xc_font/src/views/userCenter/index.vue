<template>
  <div class="app-container">
      <el-row>
          <el-col :span="7">
              <el-card class="box-card">
                  <template v-slot:header>
                      <div class="clearfix">
                          <span>个人信息</span>
                      </div>
                  </template>
                  <div>
                      <div class="text-center">
                          <Avatar :user="user"/>
                      </div>
                      <ul class="list-group list-group-striped">
                          <li class="list-group-item">
                              <svg-icon icon="user" />&nbsp;&nbsp;用户名称
                              <div class="pull-right">{{user.username}}</div>
                          </li>
                          <li class="list-group-item">
                              <svg-icon icon="phone" />&nbsp;&nbsp;手机号码
                              <div class="pull-right">{{user.phonenumber}}</div>
                          </li>
                          <li class="list-group-item">
                              <svg-icon icon="email" />&nbsp;&nbsp;用户邮箱
                              <div class="pull-right">{{user.email}}</div>
                          </li>
                          <li class="list-group-item">
                              &nbsp;&nbsp;所属角色
                              <div class="pull-right">{{user.roles}}</div>
                          </li>
                          <li class="list-group-item">
                              <svg-icon icon="date" />&nbsp;&nbsp;创建日期
                              <div class="pull-right">{{date.toISOString().replace('T', ' ').slice(0, 19)}}</div>
                          </li>
                      </ul>
                  </div>
              </el-card>
          </el-col>
          <el-col :span="17">
              <el-card>
                  <template v-slot:header>
                      <div class="clearfix">
                          <span>基本资料</span>
                      </div>
                  </template>
                  <el-tabs v-model="activeTab">
                      <el-tab-pane label="基本资料" name="userinfo">
                          <UserInfo :user="user"/>
                      </el-tab-pane>
                      <el-tab-pane label="修改密码" name="resetPwd">
                          <ResetPwd :user="user"/>
                      </el-tab-pane>
                  </el-tabs>
              </el-card>
          </el-col>
      </el-row>
  </div>
</template>

<script setup>
import {XCStore} from "../../store/XCStore.js";
import {formatDate} from "../../utils/formatDate.js";
import {ref} from "vue";
import Avatar from "./Avatar.vue"
import ResetPwd from "./ResetPwd.vue"
import UserInfo from "./UserInfo.vue";

const store = XCStore()
const user = ref(store.GET_USER())
const date = new Date(user.value.loginDate)
const activeTab = ref("userinfo");

</script>

<style scoped>
.box-card{
    height:450px;
}
.list-group-item {
    border-bottom: 1px solid #e7eaec;
    border-top: 1px solid #e7eaec;
    margin-bottom: -1px;
    padding: 11px 0;
    font-size: 13px;
}
.pull-right{
    float: right!important;
}
.el-card__body{
    height:230px;
}
</style>