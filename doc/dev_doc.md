<div align="center">
    <h1> 醉生梦死
    <h2> 开发文档
</div>

---

## 一、项目结构
### 1. 项目主体结构
<pre><code>
Forge-EnologistMod-1.19/
├── build.gradle                # Gradle构建脚本，定义依赖、任务等
├── changelog.txt               # 更新日志，记录项目版本及改动历史
├── CREDITS.txt                 # 致谢名单，项目贡献者及相关人员
├── gradle.properties           # Gradle项目配置属性
├── gradlew                     # Unix/Linux 下的Gradle Wrapper启动脚本
├── gradlew.bat                 # Windows 下的Gradle Wrapper启动脚本
├── LICENSE.txt                 # 许可证文件，说明项目开源协议
├── settings.gradle             # Gradle多模块项目配置文件（模块声明）
├── src/                        # 源代码和资源主目录
│   └── main/
│       ├── java/               # Java源码目录，存放游戏核心代码
│       │   └── ...
│       └── resources/          # 资源目录，放贴图、音效、配置等静态文件
│           └── ...
├── doc/                        # 项目文档(设计方案、规划、接口说明等)
│   └── ...                 
├── gradle/                     # Gradle Wrapper相关文件，保证构建环境一致
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── README.md                   # 项目简介(面向用户和开发者)
</code></pre>

### 2. 子模块分结构
<details>
<summary> doc 具体内容 </summary>
<pre><code>
doc/
└── dev_doc.md                  # 开发文档
</code></pre>
</details>

---

## 二、各单位上传说明
### 1. 分组情况：策划组(PlanningGroup)、美工组(ArtGroup)、代码组(CodeGroup).
### 2. 上传教程

> 注：这里基于 `GitHub Desktop` 结合 `Command Line` 命令行工具共同使用

1. 通过 `cmd (win)` / `Terminal (mac)` 执行 ```git clone git@github.com:KunLiangChen/Forge-EnologistMod-1.19.git``` 将项目整体下载到指定文件夹中
2. 进入指定文件夹后执行 `git branch -r` 观察项目分支情况，具体效果如下：
```
(User) git branch -r                
    origin/ArtGroup
    origin/CodeGroup
    origin/HEAD -> origin/master
    origin/PlanningGroup
    origin/master
    origin/图一乐的Java项目
```
3. 根据自身组别进入对应分支，这里以**美工组**为例:
```
(User) git checkout ArtGroup
<<<<<<< Updated upstream

```
=======
    Switched to branch 'ArtGroup'
    Your branch is up to date with 'origin/ArtGroup'.
```
4. 此时你已切入对应分支，各组 **README** 已创建，请基于此进行更新，并在自身分支内创建相应文件夹
- 策划组建议：创建 `plan_resource` 并在其内进行细分
- 美工组建议：创建 `art_resource` 并在其内进行细分
- 代码组建议：创建 `code_resource` 并在其内进行细分
> 注： **master** 主分支只用于给 User 展示并了解本游戏，各单位开发工作请在各分支内完成
5. 上传仓库时，请注意不要讲垃圾文件，如 **mac** 的`.DS_Store`，**win** 的`Thumbs.db` 一同上传，请将垃圾文件丢入 `.gitignore` 进行过滤，具体操作如下：
```
# .DS_Store
.DS_Store
```
6. 上传仓库正确步骤 (`GitHub Desktop` 中有对应 UI 界面方便操作)
```
git add "对应的文件"
git commit -m "对本次上传的说明，建议符合Google规范"
git push origin 指定分支(如ArtGroup)
``` 
> 上传仓库后，请各单位务必上 GitHub 链接查验上传结果，点击 `Switch branches/tags` 即可查看对应分支上传情况；核验无误后，统一汇报上传情况
>>>>>>> Stashed changes
