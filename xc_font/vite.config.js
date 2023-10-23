import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { join, resolve } from "path";
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'



// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
      vue(),
      createSvgIconsPlugin({
          iconDirs: [resolve(process.cwd(), 'src/assets/svg')],
          symbolId: 'icon-[dir]-[name]',
      }),
  ],
    resolve: {
        alias: {
            '@': join(__dirname, 'src'),
        }
    },
})
