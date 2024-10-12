# JAVA-learning-practice-project

#### 介绍
中国海洋大学计算机技术与科学中外合作办学java学习中有实践性或教育性的工程或代码

#### 软件说明
使用Java+VScode组合，因而需要下载VScode以及部署Java开发环境


#### 软件安装及环境部署教程

1. 安装JDK（Java Development Kit）
访问 Oracle 或 OpenJDK 网站下载适合你操作系统的JDK版本（建议使用JDK 17或更高）。
按照安装程序进行安装。
2. 配置环境变量
**Windows** ：右键"此电脑" → "属性" → "高级系统设置" → "环境变量"。
在“系统变量”中找到 Path，点击“编辑”，添加JDK的 bin 路径（如 C:\Program Files\Java\jdk-17\bin）。
 **Linux/macOS** ：在终端中编辑 .bashrc 或 .zshrc 文件：bash
  _复制代码_ ：
export JAVA_HOME=/path/to/jdk
export PATH=$JAVA_HOME/bin:$PATH
 _然后执行_ ：
bash
 _复制代码_ ：
source ~/.bashrc  # 或 source ~/.zshrc
3. 安装VS Code
从 VS Code 官网 下载并安装编辑器。
4. 安装Java扩展包
打开VS Code，点击左侧的扩展（Extensions）图标，搜索并安装 "Extension Pack for Java"，该扩展包包含：
Language Support for Java
Debugger for Java
Java Test Runner
Maven for Java
5. 创建Java项目
打开VS Code，点击“文件” → “打开文件夹”，选择你要存放Java项目的文件夹。
在文件夹中创建一个新文件，如 Main.java，输入以下简单代码：
java
复制代码
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
6. 运行Java程序
保存文件后，点击VS Code右上角的 “运行”（Run）按钮，或者按下 Ctrl + F5 来运行程序。
程序的输出会显示在终端窗口中。
7. 调试Java程序（可选）
在代码行号左侧点击设置断点（Breakpoint）。
按 F5 启动调试模式，观察变量和执行流程。
8. 设置自动编译（可选）
如果希望自动编译Java文件，按 Ctrl + Shift + P，搜索并选择 "Java: Clean Java Language Server Workspace"，确保项目环境正常。

#### 使用说明

1.  在VScode中，你有可能想将默认的英文语言转化成汉语，那么请按以下操作进行：
 **扩展安装**  
点击左侧的扩展（Extensions）图标。
在搜索栏中输入 "Chinese (Simplified) Language Pack for Visual Studio Code"。
找到该扩展后，点击“安装”。
安装完成后，按下 Ctrl + Shift + P 打开命令面板。
 **切换语言** 
在命令面板中输入 "Configure Display Language"，并选择此选项。
在列表中选择 "zh-CN"（简体中文）。
 **重启VS Code** 
选择语言后，VS Code 会提示你重启编辑器。
点击重启按钮，重启后VS Code的界面将切换为中文。
2.
3.
#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
