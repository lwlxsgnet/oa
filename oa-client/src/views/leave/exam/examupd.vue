<template>
    <el-dialog v-model="visible" :title="`${data.e_name}的请假申请单`" width="40%">
        <el-form style="padding: 15px 100px;" label-width="80px">
            <el-form-item label="状态 :" style="color: #337ecc; font-weight: bold;">
                {{ statusMap[data.l_status] }}
            </el-form-item>

            <el-form-item label="所属部门 :" style="color: #337ecc; font-weight: bold;">
                {{ data.d_name }}
            </el-form-item>

            <el-form-item label="起止时间 :" style="color: #337ecc; font-weight: bold;">
                {{ data.l_start }} ~ {{ data.l_end }}
            </el-form-item>

            <el-form-item label="事由 :" style="color: #337ecc; font-weight: bold;">
                {{ data.l_cause }}
            </el-form-item>

            <el-form-item label="提交时间 :" style="color: #337ecc; font-weight: bold;">
                {{ data.l_submit_date }}
            </el-form-item>

            <el-form-item label="审批意见 :" style="font-weight: bold;" v-if="data.l_status === 1">
                <el-input v-model="data.l_sp_advice" type="textarea" />
            </el-form-item>

            <template v-else>
                <el-form-item label="审批人 :" style="color: #337ecc; font-weight: bold;">
                    {{ data.l_spr }}
                </el-form-item>
                <el-form-item label="审批时间 :" style="color: #337ecc; font-weight: bold;">
                    {{ data.l_sp_date }}
                </el-form-item>
                <el-form-item label="审批意见 :" style="color: #337ecc; font-weight: bold;">
                    {{ data.l_sp_advice }}
                </el-form-item>
                <el-form-item label="销假时间 :" style="color: #337ecc; font-weight: bold;">
                    {{ data.l_back_date }}
                </el-form-item>
            </template>

        </el-form>

        <template #footer>
            <div style="text-align: center;">
                <el-button type="success" @click="execPass" v-if="data.l_status === 1">同意</el-button>
                <el-button type="danger" @click="execReject" v-if="data.l_status === 1">驳回</el-button>
                <el-button @click="visible = false" type="info">关闭</el-button>
            </div>
        </template>

    </el-dialog>
</template>


<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { ElMessage } from 'element-plus'

    const statusMap = {
        0: '未提交',
        1: '已提交',
        2: '已驳回',
        3: '已批准',
        4: '已销假',
    }

    const visible = ref(false);

    // 修改请假申请单的时候需要传入当前行数据
    const open = (row)=>{
        for (let prop in data) data[prop] = row[prop];
        visible.value = true;
    }
    defineExpose({
        open
    })

    const data = reactive({
        l_id: '',
        d_name:'',
        e_name:'',
        l_start: '',
        l_end: '',
        l_cause: '',
        l_submit_date:'',
        l_spr:'',
        l_sp_date:'',
        l_sp_advice:'',
        l_back_date:'',
        l_status:'',
    })

    const emit = defineEmits(['ok']);

    //同意请假申请
    const execPass = async()=> {
        await req.put('/leave/exam/pass', data);
        emit('ok');
        visible.value = false;
        ElMessage.success("已同意请假申请!");
    }

    //同意请假申请
    const execReject = async()=> {
        await req.put('/leave/exam/reject', data);
        emit('ok');
        visible.value = false;
        ElMessage.success("已驳回请假申请!");
    }


</script>



<style lang="" scoped>
    
</style>