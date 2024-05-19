<template lang="">

    <el-form :inline="true" size="small">

        <el-form-item label="编号">
            <el-input v-model="query.l_id" />
        </el-form-item>

        <el-form-item label="事由">
            <el-input v-model="query.l_cause" />
        </el-form-item>

        <el-form-item label="状态">
            <el-select v-model="query.l_status" style="width: 80px;">
                <el-option label="全部" value="" />
                <el-option v-for="(name, status) in statusMap" :label="name" :value="status" />
            </el-select>
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="" @click="getPage(1)">查询</el-button>
            <el-button :icon="Plus" type="primary" @click="leaveaddRef.open()">新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel">删除</el-button>
        </el-form-item>
    </el-form>
    {{ ids }}
    <oa-table :page="page" :query="query" :getPage="getPage">

        <el-table :data="page.list" @selection-change="onSelectionChange">

            <el-table-column type="selection" />
            <el-table-column prop="l_id" label="编号" width="" />
            <el-table-column prop="e_name" label="申请人" width="" />
            <el-table-column label="起止时间" width="300">
                <template #default="{row}">
                    {{ row.l_start }} ~ {{ row.l_end }}
                </template>
            </el-table-column>

            <el-table-column prop="l_cause" label="事由"/>
            <el-table-column prop="l_submit_date" label="提交时间" width=""/>
            <el-table-column prop="l_status_name" label="状态" width="" />

            <el-table-column label="操作" fixed="right" width="">
                <template #default="{row}">
                    <template v-if="row.l_status === 0">    <!--未提交时显示 -->
                        <el-tooltip content="修改" effect="light">
                            <el-button :icon="Edit" type="primary" size="small" circle  @click="leaveupdRef.open(row)" ></el-button>
                        </el-tooltip>
                        <el-tooltip content="删除" effect="light">
                            <el-button :icon="Delete" type="danger" size="small" circle @click="execDel(row.l_id)"></el-button>
                        </el-tooltip>
                        <el-tooltip content="提交" effect="light">
                            <el-button :icon="Finished" type="success" size="small" circle @click="execSubmit(row.l_id)"></el-button>
                        </el-tooltip>
                    </template>
                    <template v-if="row.l_status === 3">     <!-- 请假已批准时显示 -->
                        <el-tooltip content="销假" effect="light">
                            <el-button :icon="Back" type="primary" size="small" circle></el-button>
                        </el-tooltip>
                    </template>
                </template>
            </el-table-column>

        </el-table>

    </oa-table>

    <!-- 新增请假申请 按时间倒序排列 获取第一页  -->
    <leaveadd ref="leaveaddRef" @ok="getPage(1)"></leaveadd>

    <!-- 修改请假申请 按时间倒序排列 获取第一页  -->
    <leaveupd ref="leaveupdRef" @ok="getPage(page.current )"></leaveupd>

</template>

<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import OaTable from '@/components/OaTable.vue'
    import { Edit, Delete, Finished, Back, Search, Plus } from '@element-plus/icons-vue'
    import { ElMessage, ElMessageBox } from 'element-plus'

    const statusMap = {
        0: '未提交',
        1: '已提交',
        2: '已驳回',
        3: '已批准',
        4: '已销假',
    }

    const query = reactive({
        pageNum: 1,
        pageSize: 5,
        l_id: '',
        l_cause: '',
        l_status: '',
    })

    const page = ref({
        total: 0,
        list: [],
    })

    const getPage = async (pageNo=1) => {
        query.pageNum = pageNo;
        const res = await req.get('/leave/apprev', {params: query});
        page.value = res.data;

        //使用获取的分页数据相关值覆盖 query.pageNum 和 query.pageSize
        query.pageNum = page.value.current;
        query.pageSize = page.value.pageSize;
    }
    getPage(1);


    const ids = ref([]);

    const onSelectionChange = selections => {
        ids.value = selections.map(item=>item.l_id);
    }

    //新增请假申请
    import leaveadd from '@/views/leave/apprev/leaveadd.vue'
    const leaveaddRef = ref(null);

    //修改请假申请
    import leaveupd from '@/views/leave/apprev/leaveupd.vue'
    const leaveupdRef = ref(null);

    //单删除
    const execDel = async id=> {
        await ElMessageBox.confirm(
            "您确定要删除该请假申请单吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        );
        await req.delete('/leave/apprev/' + id);
        getPage(page.value.current);
        ElMessage.success("成功删除请假申请单!");
    }

    //多删除
    const execMultiDel = async ()=>{
        if(!ids.value || !ids.value.length){
            ElMessage.error("请选择至少一条数据!");
            return;
        }
        await ElMessageBox.confirm(
            "您确定要删除选中的请假申请单吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.delete('/leave/apprev', {data: ids.value})
        getPage(page.value.current);
        ElMessage.success("成功删除请假申请单!");
    }

    //提交
    const execSubmit = async id=>{
        await ElMessageBox.confirm(
            "您确定要提交该请假申请单吗?一旦提交将禁止修改",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        );
        await req.put('/leave/apprev/submit/' + id);
        getPage(page.value.current);
        ElMessage.success("成功提交请假申请单!");
    }

</script>


<style lang="" scoped>
    
</style>