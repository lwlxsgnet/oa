<template lang="">

    <el-form :inline="true" size="small">

        <el-form-item label="工号">
            <el-input v-model="query.e_id" style="width: 80px;"/>
        </el-form-item>

        <el-form-item label="姓名">
            <el-input v-model="query.e_name" style="width: 80px;"/>
        </el-form-item>

        <el-form-item label="性别">
            <el-select v-model="query.e_sex" style="width: 70px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
            </el-select>
        </el-form-item>

        <el-form-item label="出生日期" >
            <el-row style="width: 100px;">
                <el-date-picker v-model="query.e_birth_start" type="date" value-format="YYYY-MM-DD" placeholder="开始日期"/>
            </el-row>
            <el-row style="text-align: center; margin: 0 6px; ">至</el-row>
            <el-row style="width: 100px;">
                <el-date-picker v-model="query.e_birth_end" type="date" value="YYYY-MM-DD" placeholder="截至日期"/>
            </el-row>
        </el-form-item>

        <el-form-item label="部门">
            <el-select v-model="query.d_id" style="width: 100px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option v-for="item in depList" :key="item.d_id" :label="item.d_name" :value="item.d_id" />
            </el-select>
        </el-form-item>

        <el-form-item label="状态">
            <el-select v-model="query.e_status" style="width: 70px;" @change="getPage(1)">
                <el-option label="全部" value="" />
                <el-option v-for="(item, status) in statusMap" :key="status" :label="item.text" :value="status" />
            </el-select>
        </el-form-item>

        <el-form-item>
            <el-button :icon="Search" type="" @click="getPage(1)">查询</el-button>
            <el-button :icon="Plus" type="primary" @click="empaddRef.open(depList)">新增</el-button>
            <el-button :icon="Delete" type="danger" @click="execMultiDel">删除</el-button>
        </el-form-item>

    </el-form>

    <el-table :data="page.list" @selection-change="onSelectionChange">
        <el-table-column type="selection"/>

        <el-table-column label="工号" width="180"> <!-- 后期处理员工生成账号时，取消了 prop = 'e_id' -->
            <template #default="{row}">
                {{ row.e_id }}
                <span v-if="row.account" style="color: brown;">(登录账号)</span>
            </template>
        </el-table-column>

        <el-table-column prop="e_name" label="姓名" width="200">
            <template #default="{row}">
                {{ row.e_name }}
                <span v-if="row.leader" style="color: teal;">(部门领导)</span>
            </template>
        </el-table-column>

        <el-table-column prop="e_sex" label="性别" width="120">
            <template #default="{row}">
                {{ row.e_sex == 1? "男":"女" }}
            </template>
        </el-table-column>

        <el-table-column prop="e_birth" label="出生日期"/>

        <el-table-column prop="d_name" label="部门"/>

        <el-table-column label="状态">
            <template #default="{row}">
                <el-tag :type="statusMap[row.e_status].type" round>{{ row.e_status_name }}</el-tag>
            </template>
        </el-table-column>

        <el-table-column label="操作" width="250">
            <template #default="{row}">
                <el-button v-if="row.e_status === 0" type="" size="small" @click="empupdRef.open(depList, row)" round>修改</el-button>
                <el-button v-if="row.e_status === 0" type="danger" size="small" @click="execDel(row.e_id)" round>删除</el-button>
                <el-button v-if="row.e_status === 0" type="primary" size="small" @click="changeStatus(row.e_id, 1)" round>确定</el-button>
                <el-button v-if="row.e_status === 1 || row.e_status === 3" type="success" size="small" @click="changeStatus(row.e_id, 2)" round>启用</el-button>
                <el-button v-if="row.e_status === 2" type="info" size="small" @click="changeStatus(row.e_id, 3)" round>禁用</el-button>
                <el-button v-if="row.e_status > 0 && !row.account" type="primary" size="small" @click="createUser(row.e_id)" round>生成账号</el-button>
                <el-button v-if="row.e_status > 0 && row.account" type="warning" size="small" @click="cancelUser(row.e_id)" round>撤销账号</el-button>
                <el-button v-if="row.e_status === 2 && !row.leader" type="" size="small" @click="setLeader(row.e_id)" round>设置领导</el-button>
                
            </template> 
        </el-table-column>

    </el-table>

    <!-- 分页组件 -->
    <el-pagination :page-sizes="[5, 10, 20, 50, 100, 200, 500]" 
                   layout="prev, pager, next, jumper, ->, sizes, total"
                   :total="page.total" 
                   v-model:page-size="query.pageSize" 
                   v-model:current-page="query.pageNum" 
                   @current-change="getPage" 
                   @size-change="getPage(1)" 
                   style="margin-top: 10px;"/>


    <!-- 新增员工 -->
    <empadd ref="empaddRef" @add-ok="getPage(page.last + 1)"></empadd>

    <!-- 修改员工 -->
    <empupd ref="empupdRef" @upd-ok="getPage(page.current)"></empupd>
    
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
        pageSize: 10,
        e_id: '',
        e_name: '',
        e_sex: '',
        e_birth_start: '',
        e_birth_end: '',
        e_status: '',
        d_id: '',
    })

    //查询结果
    const page = ref({
        total: 0,//总记录数
        list: [],
    })

    const getPage = async (pageNo = 1)=>{
        query.pageNum = pageNo;
        const res = await req.get("/info/emp", {params: query});
        page.value = res.data;

        //使用获取的分页数据相关值覆盖 query.pageNum and query.pageSize
        query.pageNum = page.value.current;
        query.pageSize = page.value.pageSize;
    }
    getPage(1);

    const depList = ref([]);
    const getDepList = async ()=>{
        const res = await req.get('/info/emp/dep');
        depList.value = res.data;
    }
    getDepList();


    //选择数据的编号数组
    const ids = ref([]);

    const onSelectionChange = selections =>{
        console.log(selections);
        ids.value = selections.map(item=>item.e_id);
    }

    //新增员工
    import empadd from '@/views/info/emp/empadd.vue'
    const empaddRef = ref(null);

    //修改员工
    import empupd from '@/views/info/emp/empupd.vue'
    const empupdRef = ref(null);

    //单删除员工
    const execDel = async id=> {
        await ElMessageBox.confirm(
            "您确定要删除此员工信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        );
        await req.delete('/info/emp/' + id);
        getPage(page.value.current);
        ElMessage.success("成功删除员工!");
    }

    //多删除员工
    const execMultiDel = async ()=>{
        if(!ids.value || !ids.value.length){
            ElMessage.error("请选择至少一条数据!");
            return;
        }
        await ElMessageBox.confirm(
            "您确定要删除选中的员工信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.delete('/info/emp', {data: ids.value})
        getPage(page.value.current);
        ElMessage.success("成功删除员工!");
    }

    //修改员工状态
    const changeStatus = async(id, status)=>{
        await ElMessageBox.confirm(
            "您选择要"+ statusMap[status].statusOpr +"员工信息吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.put('/info/emp/' + id + '/' + status);
        getPage(page.value.current);
        ElMessage.success(statusMap[status].statusOpr + "员工信息成功!");
    }
    
    // 生成账号
    const createUser = async empId =>{
        await ElMessageBox.confirm(
            "您确定要为工号为"+ empId +"的员工生成账号吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.post('info/emp/user', {empId}); //提交JSNON数据，empId为名，empId.value为值
        getPage(page.value.current);
        ElMessage.success("成功生成员工账号");
    }

    // 撤销账号
    const cancelUser = async empId =>{
        await ElMessageBox.confirm(
            "您确定要撤销工号为"+ empId +"的员工账号吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.delete('info/emp/user', {data: { empId }}); //注意和生成账号的区别，传输数据方式不一样
        getPage(page.value.current);
        ElMessage.success("成功撤销员工账号");
    }

    // 设置为领导账号
    const setLeader = async empId =>{
        await ElMessageBox.confirm(
            "您确定要将工号"+ empId +"的员工设置为部门领导吗?",
            "警告",
            {
                cancelButtonText: '取消',
                confirmButtonText: '确定',
                type: 'warning'
            }
        )
        await req.put('info/emp/leader', { empId }); //与前两个对比，put post直接传， delete需要另外的格式
        getPage(page.value.current);
        ElMessage.success("成功设置该账号为部门领导");
    }

  

</script>


<style scoped>
    
</style>