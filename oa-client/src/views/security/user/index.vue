<template lang="">
    <el-form :inline="true" size="small">

        <el-form-item label="账号">
            <el-input v-model="query.u_id" />
        </el-form-item>

        <el-form-item label="姓名">
            <el-input v-model="query.u_name" />
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="" @click="getPage(1)">查询</el-button>
            <el-button :icon="Plus" type="primary" @click="useraddRef.open()">新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel">删除</el-button>
        </el-form-item>

    </el-form>

    <el-table :data="page.list" @selection-change="onSelectionChange">

        <el-table-column type="selection" />

        <el-table-column prop="u_id" label="账号" width="" />

        <el-table-column prop="u_name" label="姓名" width="" />

        <el-table-column label="角色" width="">

            <template #default="{row}">
                <el-tag v-for="name in row.roleNameList" :key="name" style="margin: 5px 5px;" type="success" effect="dark" round>
                    {{ name }}
                </el-tag>
            </template>

        </el-table-column>

        <el-table-column label="操作" width="200">
            <template #default="{row}">

                <el-button type="primary" size="small" @click="userroleRef.open(row)">角色</el-button>

                <el-button type="" size="small" @click="userupdRef.open(row)">修改</el-button>

                <el-button type="danger" size="small" @click="execDel(row.u_id)">删除</el-button>

            </template>
        </el-table-column>

    </el-table>

    <!-- 分页组件 -->
    <el-pagination :page-sizes="[3, 5, 10, 20, 50, 100]" 
                   layout="prev, pager, next, jumper, ->, sizes, total"
                   :total="page.total" 
                   v-model:page-size="query.pageSize" 
                   v-model:current-page="query.pageNum"
                   @current-change="getPage" 
                   @size-change="getPage(1)"
                   style="margin-top: 10px;" />
                  

    <!-- 新增用户 -->
    <useradd ref="useraddRef" @add-ok="getPage(page.last + 1)"></useradd>

    <!-- 修改用户 -->
    <userupd ref="userupdRef" @upd-ok="getPage(page.current)"></userupd>
    
    <!-- 修改用户角色 -->
    <userrole ref="userroleRef" @exec-ok="getPage(page.current)"></userrole>

</template>

<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { Search, Plus, Delete } from '@element-plus/icons-vue'
    import { ElMessage, ElMessageBox } from 'element-plus'
    //ref封装整体属性改变替换，reactive封装属性改变


    //查询条件 ref, reactive和界面联动
    const query = reactive({
        pageNum: 1,
        pageSize: 5,
        u_id: '',
        u_name: '',
    })

    //查询结果
    const page = ref({
        total: 0,//总记录数
        list: [],
    })

    const getPage = async (pageNo = 1) => {
        query.pageNum = pageNo;
        const res = await req.get("/security/user", { params: query });
        page.value = res.data;

        //使用获取的分页数据相关值覆盖 query.pageNum and query.pageSize
        query.pageNum = page.value.current;
        query.pageSize = page.value.pageSize;
    }
    getPage(1);

    //选择数据的编号数组
    const ids = ref([]);

    const onSelectionChange = selections => {
        console.log(selections);
        ids.value = selections.map(item => item.u_id);
    }


    //新增用户
    import useradd from '@/views/security/user/useradd.vue'
    const useraddRef = ref(null);

    //修改用户
    import userupd from '@/views/security/user/userupd.vue'
    const userupdRef = ref(null);

    //单删除
    const execDel = async id => {
        await ElMessageBox.confirm(
            "您确定要删除此用户信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        );
        await req.delete('/security/user/' + id);
        getPage(page.value.current);
        ElMessage.success("成功删除用户!");
    }

    //多删除
    const execMultiDel = async () => {
        if (!ids.value || !ids.value.length) {
            ElMessage.error("请选择至少一条数据!");
            return;
        }
        await ElMessageBox.confirm(
            "您确定要删除选中的用户信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.delete('/security/user', { data: ids.value })
        getPage(page.value.current);
        ElMessage.success("成功删除用户!");
    }

    // 角色
    import userrole from '@/views/security/user/userrole.vue'
    const userroleRef = ref(null);

</script>


<style scoped>

</style>