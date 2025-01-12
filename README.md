# Automated
Automated自动化工具模板
# 项目结构

* ├── main
* │   ├── java
* │   │   └── cn.kutori
* │   │       ├── common
* │   │       ├── config
* │   │       ├── enumPojo
* │   │       ├── utils
* │   │       └── application
* │   └── resources
* ├── test
* ├── target
* └── pom.xml


# 使用 jpackage 打包 Java 项目为独立运行的 .exe 文件

以下是通过 `jpackage` 工具将 Java 项目打包成 `.exe` 可执行文件的命令与步骤。

## 命令

```bash
jpackage --input target \
         --name YourAppName \
         --main-jar your-app-1.0.0.jar \
         --main-class cn.kutori.application.main \
         --type exe \
         --output dist \
         --runtime-image path/to/custom-runtime
```

