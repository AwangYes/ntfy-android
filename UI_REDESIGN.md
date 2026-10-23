# ntfy Android — iOS 风格 UI 重构

本次重构保留原有 Activity / RecyclerView / PreferenceFragment 架构和全部业务逻辑，只替换视觉系统与页面层级，避免引入 Compose 或大规模导航重写。

## 设计系统

- **背景/表面**：iOS grouped background `#F2F2F7`（深色 `#000000`），卡片 `#FFFFFF`（深色 `#1C1C1E`）。
- **强调色**：iOS system blue `#007AFF`（深色 `#0A84FF`）；错误/未读 badge 使用 system red。
- **文字**：系统 `sans-serif` / `sans-serif-medium`；34sp 大标题、17sp 行标题、16sp 正文、13sp footnote、12sp caption。
- **形状**：卡片 18dp、输入框/按钮 14dp、弹层 28dp、badge 全圆角；卡片无阴影，使用背景色差和 0.5dp hairline 表达层级。
- **组件**：Material3 主题属性统一映射到 iOS token；为 Button、Card、TextInputLayout、AlertDialog、Preference 增加 iOS 样式覆盖。

主要 token 位于：

- `app/src/main/res/values/colors.xml`
- `app/src/main/res/values-night/colors.xml`
- `app/src/main/res/values/themes.xml`
- `app/src/main/res/values/dimens.xml`

## 页面调整

- **订阅首页**：34sp 大标题、52dp 紧凑导航栏、独立圆角订阅卡片、44dp 图标 tile、红色未读 badge、iOS 空态卡片、圆形 FAB、inset Banner；右上角更多按钮改为 iOS 底部操作表（action sheet），不再使用 Android 锚定式 overflow popup。
- **消息详情**：紧凑导航标题、18dp 圆角通知卡片、17sp 标题/15sp 正文、12dp 图片圆角、iOS composer 输入条。
- **设置**：顶层大标题、嵌套页紧凑标题、所有 Preference 行统一使用 `preference_ios.xml` 圆角卡布局、蓝色 accent、iOS 绿色 switch、13sp footnote summary。
- **新建订阅 / 发布 / 用户 / 证书 / 连接错误等全屏弹层**：grouped background、居中标题、蓝色文字操作、14dp 表单圆角、移除历史负 margin 和 Holo 颜色。
- **分享页**：重写为内容卡 + 目标卡的分组表单，TextInputLayout 包裹正文和 topic，保留原有 `share_*` View ID 与上传/校验逻辑。
- **深色模式**：完整的 night 色板、系统栏图标明暗适配、卡片/输入/弹层表面色独立定义。

动态取色开关已从设置页移除，应用固定使用 iOS 设计色板，避免 Material You 壁纸色破坏跨页面一致性。

## 构建与产物

所有编译和 Android Lint 均在 GitHub Actions 中执行：

- Workflow：`.github/workflows/android-build.yml`
- 触发：push 到 `main` / `feature/**`、pull request、手动 `workflow_dispatch`
- 命令：`./gradlew :app:assembleFdroidDebug :app:lintFdroidDebug`
- 产物：`ntfy-ios-ui-fdroid-debug`（APK）与 lint reports

本仓库不要求在本地运行 Gradle。
