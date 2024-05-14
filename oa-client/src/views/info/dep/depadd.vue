<template>
    <el-dialog v-model="visible" title="新增部门" width="40%">
        <el-form style="padding: 0 30px;">
            <el-form-item label="名称">
                <el-input v-model="data.d_name"/>
            </el-form-item>

            <el-form-item label="备注">
                <el-input v-model="data.d_remark" type="textarea"/>
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button type="primary" @click="execAdd">确定</el-button>
            <el-button @click="visible = false">取消</el-button>
        </template>
    </el-dialog>
</template>
<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { ElMessage } from 'element-plus'

    const visible = ref(false);

    const open = ()=>{
        visible.value = true;
    }
    defineExpose({
        open
    })

    const data = reactive({
        d_name: '',
        d_remark: '',
    })

    const emit = defineEmits(['add-ok']);

    const execAdd = async()=> {
        await req.post('/info/dep', data);
        emit('add-ok');
        //数据清空，防止下次添加时还遗留
        data.d_name = '';
        data.d_remark = '';
        visible.value = false;
        ElMessage.success("部门新增成功!");
    }
</script>
<style lang="" scoped>
    
</style>