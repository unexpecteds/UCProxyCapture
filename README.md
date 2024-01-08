# UCProxyCapture
UC 浏览器代理抓取

# 简介
UC 浏览器自启动后会抓取已开启的免流代理信息

# 文件路径
- /storage/emulated/0/UCDownloads
- /storage/emulated/0/Download/UCDownloads
- /storage/emulated/0/Android/data/包名/UCDownloads

# 支持浏览器的版本
UC浏览器:
- 16.2.7.1278

UC浏览器极速版:
- 15.5.2.1242(仅电信)

# 使用教程
- 1.在 Xposed 框架找到模块并开启，打开模块作用域设置，勾选“UC 浏览器”。
- 2.打开“UC 浏览器”，打开设置→高级设置→浏览器专享流量，填写你的手机号，并点击开启
- 3.打开“文件管理”，打开配置保存路径: 可以看到 xxx.ini 文件，那个就是代理信息文件

**注意:**
- 现在联通也是动态生成的验证
- 电信代理抓取到也没什么用
- 如果抓取到的配置不会使用不要问我！！！

# 停止维护
不再对这个项目进行更新
