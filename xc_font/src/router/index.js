import {createRouter, createWebHistory} from "vue-router";
import Index from "../layout/Index.vue";
import Login from "../views/Login.vue"
import index from "../views/index/index.vue"

const routes =  [
    {
        path: "/",
        component: Index,
        redirect: '/index',
        children: [
            {
                path : '/index',
                name : '首页',
                component : index
            },
            {
                path: '/userCenter',
                name: '个人中心',
                component: () => import('../views/userCenter/index.vue')
            },
            {
                path:'/sys/user',
                component: ()=>import('@/views/sys/user/index.vue')
            }
        ]
    },
    {
        path: `/login`,
        component: Login
    }
]

const router = createRouter({
        history: createWebHistory(),
        routes
    }
)

export default router