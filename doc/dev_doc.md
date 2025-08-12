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

```