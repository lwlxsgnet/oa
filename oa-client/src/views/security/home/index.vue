<template>
    <!-- 外层容器 -->
    <el-container style="position: fixed; left: 0; right: 0; top: 0; bottom: 0;">
        <!-- 侧边栏 -->
        <el-aside width="200px" style="background-color: rgb(247,248,250); border-right: thin solid rgb(228, 225, 225);">

            <!--头像 -->
            <div style="display: flex; justify-content: center; margin-top: 20px;">
                <el-avatar :src="avatarUrl" :size="150"></el-avatar>
            </div>

            <!-- 菜单 -->
            <el-menu background-color="rgb(247,248,250)" style="margin-top: 30px; border: none;" @select="goUrl">
                <el-sub-menu v-for="main in mainMenuList" :key="main.menuId" :index="main.menuId + '' ">
                    <template #title>
                        <span>
                            {{ main.menuName }}
                        </span>
                    </template>
                    <el-menu-item v-for="sub in main.children" :key="sub.menuId" :index="sub.menuUrl">
                        {{ sub.menuName }}
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>

        <!-- 内层容器 -->
        <el-container style="position: absolute; left: 200px; right: 0; top: 0; bottom: 0;">

            <!-- 头部 -->
            <el-header height="50px" 
                style="padding: 0 5px; background-color: rgb(243,246,244);border-bottom: thin solid rgb(228, 225, 225);">

                <div
                    style="font-family: 等线; font-size: 24px; font-weight: bold; line-height: 50px; text-align: center;">
                    OA云协同
                </div>

                <div style="position: absolute; right: 100px; top: 2.5%;">
                    <el-dropdown @command="handleCommand">
                        <span style="border: none; outline: none; cursor: pointer;">
                            [{{ currentUser.userId }}]:[{{ currentUser.userName }}]
                            <el-icon class="el-icon-right">
                                <CaretBottom />
                            </el-icon>
                        </span>
                        <template #dropdown>
                                <!-- 不弹出对话框更新头像 省了一个vue
                                <el-dropdown-item>
                                    <el-upload :on-change="onChange" :show-file-list="false" :auto-upload="false" 
                                                class="avatar-uploader"> 
                                            更换头像
                                    </el-upload>
                                </el-dropdown-item> -->  
                            <el-dropdown-item command="avatar">修改头像</el-dropdown-item>
                            <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
                            <el-dropdown-item command="exit">Log out</el-dropdown-item>
                        </template>
                    </el-dropdown>
                </div>

            </el-header>

            <!-- 主体 -->
            <el-main style="padding: 0 3px; position: relative; background-color: rgb(255,255,255);">

                <el-breadcrumb :separator-icon="ArrowRight" style="height: 26px; line-height: 26px;">

                    <el-breadcrumb-item to="/welcome">
                        首页
                    </el-breadcrumb-item>

                    <el-breadcrumb-item v-for="item in route.meta.position" :key="item">
                        {{ item }}
                    </el-breadcrumb-item>
                </el-breadcrumb>

                <div style="position: absolute; left: 3px; right: 3px; top: 26px; bottom: 0; padding-top: 25px; box-sizing: border-box;">
                    <router-view>
                    </router-view>
                </div>
            </el-main>

            <!-- 底部 -->
            <el-footer height="30px"
                style="padding: 0 5px; background-color: rgb(247,248,250); 
                        font-size: 13px; line-height: 30px; text-align: center; border-top: thin solid rgb(212, 209, 209);">
                Copyright &copy; lwlxsgnet
            </el-footer>

        </el-container>
    </el-container>

    <avatar ref="avatarRef" @update-ok="getAvatar"></avatar>
    <pwd ref="pwdRef"></pwd>
</template>


<script setup>
import { CaretBottom, ArrowRight } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useRouter,useRoute} from 'vue-router'
import { apiBaseUrl, localTokenName } from '@/constants'
import { ElMessage } from 'element-plus'
import req from '@/request/index.js'

//引入自定义组件
import avatar from '@/views/security/home/avatar.vue' 
    const avatarRef = ref(null);//avatarRef变量就代表了页面上以标签所示的avatar组件对象

import pwd from "@/views/security/home/pwd.vue"
    const pwdRef = ref(null);//pwdRef变量就代表了页面上以标签所示的pwd组件对象

    const router = useRouter();//路由器

    const route = useRoute();//获得当前路由

    const mainMenuList = ref([]);
    const getMainMenuList = async () => {
        const res = await req.get("/security/home/menus");
        mainMenuList.value = res.data;
    }
    getMainMenuList();

    const goUrl = url => {
        router.push({ path: url });
    }

    const currentUser = ref({});

    const getCurrentUser = async()=> {
        const res = await req.get('/security/home/currentuser');
        currentUser.value = res.data;
        getAvatar(); //获得userid后调用，不能同时请求userid和请求头像
    }

    getCurrentUser();

    const handleCommand = command => {
        switch (command) {
            case 'avatar':
                avatarRef.value.open(currentUser.value);//调用avatar组件对象的open方法
                break;
            case 'pwd':
                pwdRef.value.open(currentUser.value);
                break;
            case 'exit':
                //console.log("点击了退出系统!");
                localStorage.removeItem(localTokenName);
                ElMessage.success("您已成功退出系统!");
                router.replace('/security/login');
                break;
        }
    }

    const avatarUrl = ref('');//当前头像Url

    const getAvatar = async()=>{
        try{
            const blob = await req.get('/user/avatars/' + currentUser.value.userId, {
                responseType: 'blob'
            });
            avatarUrl.value = URL.createObjectURL(blob);
        }catch(err){
            avatarUrl.value = '/avatar.jpeg'
        }
    }

</script>


<style scoped>
</style>