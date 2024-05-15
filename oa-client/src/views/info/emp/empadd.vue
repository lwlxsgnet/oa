<template>
    <el-dialog v-model="visible" title="新增员工" width="40%" >
        <el-form  label-width="70px" style="padding: 0 30px;">

            <el-form-item label="姓名">
                <el-input v-model="data.e_name" placeholder="请输入员工姓名" />
            </el-form-item>

            <el-form-item label="性别">
                <el-select v-model="data.e_sex">
                    <el-option label="男" :value="1" />
                    <el-option label="女" :value="2" />
                </el-select>
            </el-form-item>

            <el-form-item label="出生日期">
                <el-date-picker v-model="data.e_birth" type="date" value-format="YYYY-MM-DD" style="width: 100%;" placeholder="请选择日期"/>
            </el-form-item>

            <el-form-item label="部门">
                <el-select v-model="data.d_id" placeholder="请选择员工所属部门">
                    <el-option v-for="item in depList" :key="item.d_id" :label="item.d_name" :value="item.d_id" />
                </el-select>
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

    const depList = ref([]);

    const open = (_depList)=>{
        depList.value = _depList;
        visible.value = true;
    }

    defineExpose({
        open
    })

    const data = reactive({
        e_name: '',
        e_sex: '',
        e_birth: '',
        d_id: '',
    })

    const emit = defineEmits(['add-ok']);

    const execAdd = async()=> {
        await req.post('/info/emp', data);
        emit('add-ok');
        //数据清空，防止下次添加时还遗留
        data.e_name = '';
        data.e_birth = '';
        data.e_sex = '';
        data.d_id = '';
        visible.value = false;
        ElMessage.success("员工新增成功!");
    }
</script>
<style lang="" scoped>
    
</style>