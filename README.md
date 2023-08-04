# UCProxyCapture
UC 浏览器代理抓取

# 版本变更
- 若无法读写外部存储目录，配置文件将保存在: /storage/emulated/0/Android/data/包名/UCDownloads

# 注意
- 如果开启免流代理选项，无法获取代理信息，请下载 [Releases](https://github.com/unexpecteds/UCProxyCapture/releases) 里对应的浏览器版本
- 1.2 仅支持 UC浏览器 (15.5.1.1242) 和 UC浏览器极速版 (14.0.6.1168)
- 1.1 仅支持 UC浏览器 (15.5.1.1241) 和 UC浏览器极速版 (14.0.6.1168)
- 1.0 仅支持 UC浏览器 (15.3.9.1229) 其他版本自测

# 简介
UC 浏览器自启动后会抓取已开启的免流代理信息，支持联通、电信，文件路径: /storage/emulated/0/UCDownloads

# 使用教程
1.在 Xposed 框架找到模块并开启，打开模块作用域设置，勾选“UC 浏览器”。
2.打开“UC 浏览器”，打开设置→高级设置→浏览器专享流量，填写你的手机号，并点击开启
3.打开“文件管理”，打开路径: /storage/emulated/0/UCDownloads 或 /storage/emulated/0/Android/data/com.UCMobile/files/UCDownloads 可以看到 xxx.ini 文件，那个就是代理信息文件
4.如果嫌以上方法麻烦，打开 Telegram →打开 [Re: Fantasy City](https://t.me/ReFantasyCity) 频道→搜索 “#免流”，有抓取好的配置

**注意:**
- 你必须下载对应版本的“UC 浏览器”
- 电信代理抓取到也没什么用
- 如果抓取到的配置不会使用不要问我！！！

# 停止维护
不再对这个项目进行更新
