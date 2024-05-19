<template>
    <el-dialog v-model="visible" title="修改用户" width="40%">

        <el-form style="padding: 0 30px;">
            <el-form-item label="用户姓名">
                <el-input v-model="data.u_name"/>
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button type="primary" @click="execUpd">确定</el-button>
            <el-button @click="visible = false">取消</el-button>
        </template>
        
    </el-dialog>
</template>
<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { ElMessage } from 'element-plus'

    const visible = ref(false);

    const open = (row)=>{
        data.u_id = row.u_id;
        data.u_name = row.u_name;
        visible.value = true;
    }
    defineExpose({
        open
    })

    const data = reactive({
        u_id: '',
        u_name: '',
    })

    const emit = defineEmits(['upd-ok']);

    const execUpd = async()=> {
        await req.put('/security/user', data);
        emit('upd-ok');
        //数据清空，防止下次添加时还遗留
        data.u_name = '';
        visible.value = false;
        ElMessage.success("用户修改成功!");
    }
    
</script>

<style lang="" scoped>
    
</style>