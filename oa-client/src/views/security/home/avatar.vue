<template>
    <el-dialog v-model="visible" width="500">
        
    </el-dialog>
</template>
<script setup>
import { ref } from 'vue'

    const visible = ref(false);

    const open = ()=>{
        visible.value = true;
    }

    //对外开放访问接口
    defineExpose({
        open,//对外开放open方法
    });

</script>

<style scoped>
    
</style>