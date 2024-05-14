<template lang="">
    <el-dialog v-model="visible" title="修改密码" width="350" style="padding-bottom: 50px;">
        <el-form label-width="80" style="padding: 10px 40px;" :model="data" :rules="rules" ref="formRef">
            <el-form-item label="原密码" prop="old_pwd">
                <el-input v-model="data.old_pwd" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码" prop="new_pwd">
                <el-input v-model="data.new_pwd" type="password" show-password />
            </el-form-item>
            <el-form-item label="确认密码" prop="re_pwd">
                <el-input v-model="data.re_pwd" type="password" show-password />
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button type="primary" @click="checkUpdatePassword">确认</el-button>
            <el-button @click="visible = false">关闭</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import req from '@/request/index.js'
import { localTokenName } from '@/constants'
import { useRouter } from 'vue-router'

    const visible = ref(false);
    
    const open = ()=> {
        visible.value = true;
    }

    defineExpose({
        open,
    })

    const data = reactive({
        old_pwd:'',
        new_pwd: '',
        re_pwd: '',
    })

    //密码检测规则
    const checkRePwd = (rule, value, callback) => {
        if (value !== data.new_pwd)  callback(new error("两次输入的密码不一致!"));
        callback();
    }
    const rules = {
        old_pwd: [
            {required: true, message: "请输入原密码!", trigger: 'blur'}//失去焦点时触发
        ],
        new_pwd: [
            {required: true, message: "请输入新密码!", trigger: 'blur'}//失去焦点时触发
        ],
        re_pwd: [
            {required: true, message: "请输入确认密码!", trigger: 'blur'},//失去焦点时触发
            {validatior: checkRePwd, trigger: 'blur'}
        ],
    }

    //点击确认按钮时校验密码
    const formRef = ref(null);

    const router = useRouter();

    const checkUpdatePassword = async ()=> {
        if (!formRef.value) return;//防止获取密码行为未完成就点击了确认修改
        //检测函数validate fields表示一些信息
        formRef.value.validate(async(valid, fields)=> {
            //检测通过
            if (valid) {
                await req.put("/security/home/pwd", data);
                localStorage.removeItem(localTokenName);
                ElMessage.success("您已成功修改新密码,重新登录吧!");
                router.replace({path: '/security/login'});
            }else{ 
                ElMessage.error('输入错误,请检查!'); 
            }
        })
    }
</script>

<style scoped>
    
</style>