import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import "./router/permission.js"
import router from "./router/index.js"
import { createPinia } from 'pinia'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 国际化中文
import I18n from "./utils/i18n.js";
import 'virtual:svg-icons-register'
import SvgIcon from "./components/SvgIcon.vue"


const pinia = createPinia()


const app = createApp(App)
    .use(ElementPlus)
    .use(router)
    .use(pinia)
    .use(I18n)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.component("svg-icon",SvgIcon)

app.mount('#app')