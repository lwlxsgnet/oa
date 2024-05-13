<template>
    <!-- 外层容器 -->
    <el-container style="position: fixed; left: 0; right: 0; top: 0; bottom: 0;">
        <!-- 侧边栏 -->
        <el-aside width="200px"
            style="background-color: rgb(247,248,250); border-right: thin solid rgb(228, 225, 225);">

            <!--头像 -->
            <div style="display: flex; justify-content: center; margin-top: 20px;">
                <el-avatar src="/public/avatar.jpeg" :size="150"></el-avatar>
            </div>
            <!-- 菜单 -->
            <el-menu active-text-color="rgb(212,226,255)" background-color="rgb(247,248,250)" style="margin-top: 30px;">
                <el-sub-menu>
                    <template #title>
                        <span>安全管理</span>
                    </template>
                    <el-menu-item>
                        用户管理
                    </el-menu-item>
                    <el-menu-item>
                        角色管理
                    </el-menu-item>
                    <el-menu-item>
                        密码重置
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>

        <!-- 内层容器 -->
        <el-container style="position: absolute; left: 200px; right: 0; top: 0; bottom: 0;">

            <!-- 头部 -->
            <el-header height="50px" style="padding: 0 5px; background-color: rgb(243,246,244);">

                <div style="font-family: 等线; font-size: 24px; font-weight: bold; line-height: 50px; text-align: center;">
                    OA云协同系统
                </div>

                <div style="position: absolute; right: 100px; top: 2.5%;">
                    <el-dropdown>
                        <span style="border: none; outline: none; cursor: pointer;">
                            当前用户：
                            <el-icon class="el-icon-right">
                                <CaretBottom />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-item>修改头像</el-dropdown-item>
                            <el-dropdown-item>修改密码</el-dropdown-item>
                            <el-dropdown-item>Log out</el-dropdown-item>
                        </template>
                    </el-dropdown>
                </div>

            </el-header>

            <!-- 主体 -->
            <el-main style="padding: 0 3px; position: relative; background-color: rgb(255,255,255);">
                Main
            </el-main>

            <!-- 底部 -->
            <el-footer height="30px"
                style="padding: 0 5px; background-color: rgb(247,248,250); 
                        font-size: 13px; line-height: 30px; text-align: center; border-top: thin solid rgb(212, 209, 209);">
                Copyright &copy; lwlxsgnet
            </el-footer>

        </el-container>
    </el-container>
</template>


<script setup>
import { CaretBottom } from '@element-plus/icons-vue'
</script>


<style scoped>

</style>