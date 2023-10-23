import { createI18n } from 'vue-i18n';
import zhCN from 'element-plus/es/locale/lang/zh-cn';

const messages = {
    'zh-cn': {
        el: zhCN,
        // 在此处添加其他的中文翻译
    },
};

const i18n = createI18n({
    locale: 'zh-cn', // 设置默认语言为中文
    messages, // 引入语言包
});

export default i18n;