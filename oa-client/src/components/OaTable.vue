<template>

    <slot>

    </slot>

     <!-- 分页组件 -->
    <el-pagination :page-sizes="[5, 10, 20, 50, 100, 200, 500]" 
                   layout="prev, pager, next, jumper, ->, sizes, total"
                   :total="page.total" 
                   v-model:page-size="query.pageSize" 
                   v-model:current-page="query.pageNum" 
                   @current-change="getPage" 
                   @size-change="getPage(1)" 
                   style="margin-top: 10px;"/>
                   
</template>

<script setup>
    const props = defineProps({
        query:{
            type: Object,
            required: true,
        },
        page:{
            type: Object,
            required: true,
        },
        getPage:{
            type: Function,
            required: true,
        }
    })

</script>

<style scoped></style>