<template lang="">

    <el-form :inline="true" size="small">
        <el-form-item label="编号">
            <el-input v-model="query.d_id" />
        </el-form-item>

        <el-form-item label="名称">
            <el-input v-model="query.d_name" />
        </el-form-item>

        <el-form-item label="备注">
            <el-input v-model="query.d_remark" />
        </el-form-item>

        <el-form-item label="状态">
            <el-select v-model="query.d_status" style="width: 70px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option v-for="(item, status) in statusMap" :key="status" :label="item.text" :value="status" />
            </el-select>
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="" @click="getPage(1)">查询</el-button>
            <el-button :icon="Plus" type="primary" @click="depaddRef.open()">新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel">删除</el-button>
        </el-form-item>

    </el-form>

    <el-table :data="page.list" @selection-change="onSelectionChange">
        <el-table-column type="selection"/>
        <el-table-column prop="d_id" label="编号"/>
        <el-table-column prop="d_name" label="名称"/>
        <el-table-column label="备注">
            <template #default="{row}">
                <span v-if="row.d_remark?.length <= 15">{{ row.d_remark }}</span>
                <span v-else-if="!row.d_remark?.length">null</span>
                <span v-else>
                    <el-popover effect="dark" palcement="top-start" :width="270" :content="row.d_remark" trigger="hover">
                        <template #reference>
                            {{ row.d_remark?.slice(0,15) }}...
                        </template>
                    </el-popover>
                </span>
            </template>
        </el-table-column>
        <el-table-column label="状态">
            <template #default="{row}">
                <el-tag :type="statusMap[row.d_status].type">{{ statusMap[row.d_status].text }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
            <template #default="{row}">
                <el-button v-if="row.d_status === 0" type="" size="small" @click="depupdRef.open(row)">修改</el-button>
                <el-button v-if="row.d_status === 0" type="danger" size="small" @click="execDel(row.d_id)">删除</el-button>
                <el-button v-if="row.d_status === 0" type="primary" size="small" @click="changeStatus(row.d_id, 1)">确定</el-button>
                <el-button v-if="row.d_status === 1 || row.d_status === 3" type="success" size="small" @click="changeStatus(row.d_id, 2)">启用</el-button>
                <el-button v-if="row.d_status === 2" type="info" size="small" @click="changeStatus(row.d_id, 3)">禁用</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination :page-sizes="[3, 5, 10, 20, 50, 100]" layout="prev, pager, next, jumper, ->, sizes, total"
                   :total="page.total" v-model:page-size="query.pageSize" v-model:current-page="query.pageNum" 
                   @current-change="getPage" @size-change="getPage(1)" style="margin-top: 10px;"/>

    <!-- 新增部门 -->
    <depadd ref="depaddRef" @add-ok="getPage(page.last + 1)"></depadd>
    <!-- 修改部门 -->
    <depupd ref="depupdRef" @upd-ok="getPage(page.current)"></depupd>
    
</template> 

<script setup>
    import { ref, reactive } from 'vue'
    import req from '@/request/index.js'
    import { Search, Plus, Delete } from '@element-plus/icons-vue'
    import { ElMessage, ElMessageBox } from 'element-plus'
    //ref封装整体属性改变替换，reactive封装属性改变

    //定义状态映射 不变化
    const statusMap = {
        0: {text: '未确定', type: 'info'},
        1: {text: '已确定', type: 'primary', statusOpr: '确定'},
        2: {text: '启用', type: 'success', statusOpr: '启用'},
        3: {text: '禁用', type: 'danger', statusOpr: '禁用'},
    }

    //查询条件 ref, reactive和界面联动
    const query = reactive({
        pageNum: 1,
        pageSize: 5,
        d_id: '',
        d_name: '',
        d_remark: '',
        d_status: '',
    })

    //查询结果
    const page = ref({
        total: 0,//总记录数
        list: [],
    })

    const getPage = async (pageNo = 1)=>{
        query.pageNum = pageNo;
        const res = await req.get("/info/dep", {params: query});
        page.value = res.data;

        //使用获取的分页数据相关值覆盖 query.pageNum and query.pageSize
        query.pageNum = page.value.current;
        query.pageSize = page.value.pageSize;
    }
    getPage(1);

    //选择数据的编号数组
    const ids = ref([]);

    const onSelectionChange = selections =>{
        console.log(selections);
        ids.value = selections.map(item=>item.d_id);
    }

    
     //新增部门
     import depadd from '@/views/info/dep/depadd.vue'
    const depaddRef = ref(null);

    //修改部门
    import depupd from '@/views/info/dep/depupd.vue'
    const depupdRef = ref(null);

    //单删除
    const execDel = async id=> {
        await ElMessageBox.confirm(
            "您确定要删除此部门信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        );
        await req.delete('/info/dep/' + id);
        getPage(page.value.current);
        ElMessage.success("成功删除部门!");
    }

    //多删除
    const execMultiDel = async ()=>{
        if(!ids.value || !ids.value.length){
            ElMessage.error("请选择至少一条数据!");
            return;
        }
        await ElMessageBox.confirm(
            "您确定要删除选中的部门信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.delete('/info/dep', {data: ids.value})
        getPage(page.value.current);
        ElMessage.success("成功删除部门!");
    }

    //修改部门状态
    const changeStatus = async(id,status)=>{
        await ElMessageBox.confirm(
            "您选择要"+ statusMap[status].statusOpr +"部门信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.put('/info/dep/' + id + '/' + status);
        getPage(page.value.current);
        ElMessage.success(statusMap[status].statusOpr + "部门信息成功!");
    }
    
</script>


<style scoped>
    
</style>