<template lang="">
    <el-form :inline="true" size="small">

        <el-form-item label="编号">
            <el-input v-model="query.ro_id" />
        </el-form-item>

        <el-form-item label="名称">
            <el-input v-model="query.ro_name" />
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="" @click="getPage(1)">查询</el-button>
            <el-button :icon="Plus" type="primary" @click="roleaddRef.open()">新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel">删除</el-button>
        </el-form-item>

    </el-form>

    <el-table :data="page.list" @selection-change="onSelectionChange">

        <el-table-column type="selection" />

        <el-table-column prop="ro_id" label="编号" width="" />

        <el-table-column prop="ro_name" label="名称" width="" />

        <el-table-column label="权限" width="">

            <template #default="{row}">
                <el-tag v-for="name in row.funNameList" :key="name" style="margin: 5px 5px;" type="">{{ name }}</el-tag>
            </template>

        </el-table-column>

        <el-table-column label="操作" width="200">
            <template #default="{row}">

                <el-button type="primary" size="small" @click="rolefunRef.open(row)">权限</el-button>

                <el-button type="" size="small" @click="roleupdRef.open(row)">修改</el-button>

                <el-button type="danger" size="small" @click="execDel(row.ro_id)">删除</el-button>

            </template>
        </el-table-column>

    </el-table>

    <!-- 分页组件 -->
    <el-pagination :page-sizes="[3, 5, 10, 20, 50, 100]" layout="prev, pager, next, jumper, ->, sizes, total"
        :total="page.total" v-model:page-size="query.pageSize" v-model:current-page="query.pageNum"
        @current-change="getPage" @size-change="getPage(1)" style="margin-top: 10px;" />

    <!-- 新增角色 -->
    <roleadd ref="roleaddRef" @add-ok="getPage(page.last + 1)"></roleadd>
    <!-- 修改角色 -->
    <roleupd ref="roleupdRef" @upd-ok="getPage(page.current)"></roleupd>
    <!-- 修改角色权限 -->
    <rolefun ref="rolefunRef" @exec-ok="getPage(page.current)"></rolefun>

</template>

<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { Search, Plus, Delete } from '@element-plus/icons-vue'
    import { ElMessage, ElMessageBox } from 'element-plus'
    //ref封装整体属性改变替换，reactive封装属性改变

    //定义状态映射 不变化
    const statusMap = {
        0: { text: '未确定', type: 'info' },
        1: { text: '已确定', type: 'primary', statusOpr: '确定' },
        2: { text: '启用', type: 'success', statusOpr: '启用' },
        3: { text: '禁用', type: 'danger', statusOpr: '禁用' },
    }

    //查询条件 ref, reactive和界面联动
    const query = reactive({
        pageNum: 1,
        pageSize: 5,
        ro_id: '',
        ro_name: '',
    })

    //查询结果
    const page = ref({
        total: 0,//总记录数
        list: [],
    })

    const getPage = async (pageNo = 1) => {
        query.pageNum = pageNo;
        const res = await req.get("/security/role", { params: query });
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
        ids.value = selections.map(item => item.ro_id);
    }


    //新增角色
    import roleadd from '@/views/security/role/roleadd.vue'
    const roleaddRef = ref(null);

    //修改角色
    import roleupd from '@/views/security/role/roleupd.vue'
    const roleupdRef = ref(null);

    //单删除
    const execDel = async id => {
        await ElMessageBox.confirm(
            "您确定要删除此角色信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        );
        await req.delete('/security/role/' + id);
        getPage(page.value.current);
        ElMessage.success("成功删除角色!");
    }

    //多删除
    const execMultiDel = async () => {
        if (!ids.value || !ids.value.length) {
            ElMessage.error("请选择至少一条数据!");
            return;
        }
        await ElMessageBox.confirm(
            "您确定要删除选中的角色信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.delete('/security/role', { data: ids.value })
        getPage(page.value.current);
        ElMessage.success("成功删除角色!");
    }

    // 权限
    import rolefun from '@/views/security/role/rolefun.vue'
    const rolefunRef = ref(null);

</script>


<style scoped>

</style>