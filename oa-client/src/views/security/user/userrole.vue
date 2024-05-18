<template>
    <el-dialog v-model="visible" :title="`${currentUser.u_id}(${currentUser.u_name})的角色`" width="50%">

        <div style="display: flex; flex-direction: column; padding: 20px 50px;">

            <div v-for="role in roleList" :key="role.ro_id" 
                 style="box-sizing: border-box; width: 100%;border-bottom: 1px #EEE solid; 
                        padding: 10px;display: flex; justify-content: space-between;">

                <label :style="{color: role.checked?'#409EFF':'black', fontWeight: role.checked?'bolder':'normal'}">
                    {{ role.ro_name }}
                </label>

                <el-switch v-model="role.checked"/>

            </div>

        </div>

        <template #footer>
            <el-button type="primary" @click="execUpdRole">确定</el-button>
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

    const currentUser = ref({});

    const open = async (row)=>{
        currentUser.value = row;

        // 向服务端发出请求，获得当前用户拥有角色编号
        const res = await req.get('/security/user/role/' + row.u_id);
        // 用户所拥有的角色编号
        const currentUserRoleIds = res.data;

        // 遍历所有子角色，如果角色编号在用户拥有的角色编号中存在，则将该角色的checked设置为true,否则为false
        // ref封装的 roleList 要加 .value
        roleList.value.forEach(role => {
            if(currentUserRoleIds.find(roleId => roleId === role.ro_id)) {
                role.checked = true;
            }else{
                role.checked = false;
            }
        })
        visible.value = true;
    }
    
    const roleList = ref([]);//所有角色

    const getRoleList = async () => {
        const res = await req.get('/security/user/role');
        roleList.value = res.data;
    }
    getRoleList();

    const execUpdRole = async ()=> {
        await ElMessageBox.confirm(
            "您确定要更新此用户角色吗?",
            "警告",
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type:'warning'
            }
        )

        const selectedRoleIds = [];//选中角色的id
        roleList.value.forEach(role => {
            role.checked && selectedRoleIds.push(role.ro_id);
        })

        await req.put('/security/user/role', {userId: currentUser.value.u_id, roleIds: selectedRoleIds});
        emit('exec-ok'); //成功后 发射一个事件 主页面接受事件
        visible.value = false;
        ElMessage.success('该用户角色更新成功!');

    }

    defineExpose({
        open
    })

</script>

<style lang="" scoped>
    
</style>