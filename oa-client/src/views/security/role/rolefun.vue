<template>
    <el-dialog v-model="visible" :title="`No.${currentRole.ro_id} (${currentRole.ro_name}) 权限`" width="50%">

        <div>
            <el-card v-for="mainFun in funList" :key="mainFun.menuId" style="margin: 6px 0;">

                <template #header>
                    <div style="font-weight: bolder;">{{ mainFun.menuName }}</div>
                </template>

                <div style="display: flex;">
                    <div v-for="subFun in mainFun.children" :key="subFun.menuId">
                        <el-switch v-model="subFun.checked" style="margin: 0 10px;"/> <!--虽然没有checked属性，但是JS没有默认为false-->
                        <label>{{ subFun.menuName }}</label>
                    </div>
                </div>

            </el-card>
        </div>

        <template #footer>
            <el-button type="primary" @click="execUpdFun">确定</el-button>
            <el-button @click="visible = false">取消</el-button>
        </template>

    </el-dialog>
</template>

<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { ElMessage, ElMessageBox } from 'element-plus'

    const emit = defineEmits(['exec-ok']);

    const visible = ref(false);

    const currentRole = ref({});

    const open = async (row)=>{
        currentRole.value = row;

        // 向服务端发出请求，获得当前角色拥有权限编号
        const res = await req.get('/security/role/fun/' + row.ro_id);
        // 角色所拥有的权限编号
        const currentRoleFunIds = res.data;

        // 遍历所有子权限，如果权限编号在角色拥有的权限编号中存在，则将该权限的checked设置为true,否则为false
        // ref封装的 funList 要加 .value
        funList.value.forEach(main => {
            main.children.forEach(sub => {
                if(currentRoleFunIds.find(funId => funId === sub.menuId)) {
                    sub.checked = true;
                }else{
                    sub.checked = false;
                }
            })
        })

        visible.value = true;
    }
    
    const funList = ref([]);//所有权限

    const getFunList = async () => {
        const res = await req.get('/security/role/fun');
        funList.value = res.data;
    }
    getFunList();

    const execUpdFun = async ()=> {
        await ElMessageBox.confirm(
            "您确定要更新此角色权限吗?",
            "警告",
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'warning'
            }
        )

        const selectedFunIds = [];//选中权限的id
        funList.value.forEach(main => {
            main.children.forEach(sub => {
                sub.checked && selectedFunIds.push(sub.menuId);
            })
        })

        await req.put('/security/role/fun', {roleId: currentRole.value.ro_id, funIds: selectedFunIds});
        emit('exec-ok'); //成功后 发射一个事件 主页面接受事件
        visible.value = false;
        ElMessage.success('权限更新成功!');

    }

    defineExpose({
        open
    })

</script>

<style lang="" scoped>
    
</style>