我们欢迎您参与 pi-cloud 项目，请您仔细阅读本文档，以了解如何最好地提交参与贡献。

# 代码规范

我们希望您能与我们共同遵循同一套开发规范，以保证代码质量。为了降低学习成本，pi-cloud 采用了知名度较高的 [Java 开发手册](https://github.com/alibaba/p3c) 作为准则。作为要求，您需要在你的 IDE 中安装 [Java开发规约IDE插件](https://github.com/alibaba/p3c/tree/master/idea-plugin)。

# Fork 仓库

fork 仓库是非常简单的，进到[仓库](https://gitee.com/linjiabin100/pi-cloud)页面，然后找到右上角的 fork 按钮，点击后选择 fork 到的命名空间，再点击确认，等待系统在后台完成仓库克隆操作，就完成了 fork 操作。

![image-20221108150529816](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/fork.png)

你需要克隆你自己仓库中的项目到本地，然后进行修改。

# 向所有新类添加 Apache 许可证头文件

```java
/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ...;
```

根据需要更新 Apache 许可证头文件。

# 对新添加的公共 API 标记作者和时间

如：

```java
/**
 * @author ZnPi
 * @date 2022-09-04
 */
```

# 提交你的 Pull Request

1. 开发者 Fork 目标仓库，在对应分支上修改后，推送到自己 Fork 的仓库里，从自己仓库中点击 “Pull Request”。

![image-20221108163745318](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/folk%20request.png)

2. 填入Pull Request的说明，点击“创建”，就可以提交一个Pull Request，**目标分支请选择 `dev`**：

![image-20221108165552270](https://gitee.com/linjiabin100/pi-cloud-resource/raw/master/imgs/create%20pull%20request.png)

在说明中，请解释你的用例，是什么导致您提交了这个更改?