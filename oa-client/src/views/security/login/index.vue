<template lang="">
    <div style="top: 0; bottom: 0; left: 0; right:0;">
        <div style="position: absolute;top: 0; bottom: 0; left: 0; right:0; margin: auto; height:500px; 
                width: 900px; background-color: rgb(255,255,255); border-radius: 5px;box-shadow: 0 0 5px 1px #DDD; 
                box-sizing: border-box; display: flex;flex-direction: column; align-items: center;">
            <h1 style="font-size: 50px; color: #777; letter-spacing: 5px;">OA云协同</h1>

            <el-form label-width="65px" style="width: 260px;">
                <el-form-item label="username"> 
                    <el-input placeholder="请输入用户名" v-model="data.u_id"/>
                </el-form-item>

                <el-form-item label="password"> 
                    <el-input placeholder="请输入密码" v-model="data.u_pwd" type="password" show-password/>
                </el-form-item>

                <el-form-item label="Verificationcode"> 
                    <el-input style="width: 120px;" v-model="data.u_verify_code"/>
                    <div style="height: 40px; width: 70px; margin-left: 5px;" @click="getVerifyUrl">
                        <el-image :src="u_verify_url" style="height: 100%; border-radius: 5px;" fit="scale-down" ></el-image>
                    </div>
                </el-form-item>

                <el-form-item label="">
                    <el-button type="primary" style="flex: auto; width: auto;" round @click="login">Log in</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { v4 as uuidv4} from 'uuid'
import { apiBaseUrl, localTokenName } from '@/constants'
import req from '@/request' //引入异步访问服务器接口的封装对象
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

    const router = useRouter();//获得路由器

    const u_verify_url = ref('');//验证码的图片地址

    const getVerifyUrl = () => {
        data.u_verify_key = uuidv4();
        u_verify_url.value = apiBaseUrl + "/res/verifycode?u_verify_key=" + data.u_verify_key;
    }
    //登陆数据
    const data = reactive({
        u_id: '',
        u_pwd: '',
        u_verify_code: '',
        u_verify_key: '',
    });
    getVerifyUrl();

    const login = async ()=> {
        const res = await req.post('/security/login', data);
        ElMessage.success("成功登录!");
        const token = res.data; //获取令牌
        localStorage.setItem(localTokenName, token);
        router.push({ path: '/security/home' });
    };

</script>


<style lang="" scoped>
    
</style>