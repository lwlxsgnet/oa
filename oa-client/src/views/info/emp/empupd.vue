<template>
    <el-dialog v-model="visible" title="员工信息修改" width="40%">
        <el-form label-width="70px" style="padding: 0 30px;">

            <el-form-item label="姓名">
                <el-input v-model="data.e_name"/>
            </el-form-item>

            <el-form-item label="性别">
                <el-select v-model="data.e_sex">
                    <el-option label="男" :value="1" />
                    <el-option label="女" :value="2" />
                </el-select>
            </el-form-item>

            <el-form-item label="出生日期">
                <el-date-picker v-model="data.e_birth" type="date" value-format="YYYY-MM-DD" style="width: 100%;"/>
            </el-form-item>

            <el-form-item label="部门">
                <el-select v-model="data.d_id">
                    <el-option v-for="item in depList" :key="item.d_id" :label="item.d_name" :value="item.d_id" />
                </el-select>
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

    const depList = ref([]);

    const open = (_depList, row)=>{
        // data.e_name = row.e_name;
        // data.e_sex = row.e_sex;
        // data.e_birth = row.e_birth;
        // data.d_id = row.d_id;
        // 循环写法
        for (let prop in data) {
            data[prop] = row[prop];
        }
        depList.value = _depList;
        visible.value = true;
    }

    defineExpose({
        open
    })

    const data = reactive({
        e_id: '',
        e_name: '',
        e_sex: '',
        e_birth: '',
        d_id: '',
    })

    const emit = defineEmits(['upd-ok']);

    const execUpd = async()=> {
        await req.put('/info/emp', data);
        emit('upd-ok');
        // 数据清空，防止下次添加时还遗留
        // data.e_name = '';
        // data.e_sex = '';
        // data.e_birth = '';
        // data.d_id = '';
        // 注释的原因是修改了每次重新加载数据，可是为什么部门修改要清空？？？   猜测是index页引用组件时@upd-ok重新刷新了页面
        visible.value = false;
        ElMessage.success("员工信息修改成功!");
    }
</script>
<style lang="" scoped>
    
</style>