# 权限管理系统

#### 介绍

此系统是使用SpringSecurity+JWT作为安全认证的前后端分离实战项目，其设计是基于RBAC权限设计的，Redis在这个项目中只是起到个缓存的作用。

#### 软件架构
所用技术栈
🐌后端：SpringBoot、Mybatis、SpringSecurity、JWT、Redis
🐌前端：Vue3、Element-Plus


#### 代码拉取后的几点需更改

1.  在WebAppConfig配置类中需更改一下你自身磁盘上对应的头像图片路径（没用热部署，为你更改头像后不重启服务器即可展示图片的更换，最后就配一下）
2.  在application.yml配置文件中需配置自身的Redis服务的主机和密码。
3.  除了开启前后端服务器之外，还需开启你自己的Redis服务。


#### 效果展示

![输入图片说明](https://foruda.gitee.com/images/1689524630166222191/2ba97f70_11633816.png "屏幕截图")

![首页](https://foruda.gitee.com/images/1689524540674209515/7a9166ec_11633816.png "屏幕截图")

![输入图片说明](https://foruda.gitee.com/images/1689524571617044618/03bfaca3_11633816.png "屏幕截图")

![输入图片说明](https://foruda.gitee.com/images/1689524593737762630/45113d12_11633816.png "屏幕截图")

![输入图片说明](https://foruda.gitee.com/images/1689524615070624820/327b9783_11633816.png "屏幕截图")
