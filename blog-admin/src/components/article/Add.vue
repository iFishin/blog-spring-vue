<!--
 * @Date: 2023-06-23 17:22:17
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-24 21:32:53
 * @FilePath: \blog-admin\src\components\article\Add.vue
 * @Description: https://github.com/iFishin
-->
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/article' }">文章管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加文章</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="70px">
        <el-form-item label="标题" prop="headline">
          <el-input v-model="addForm.headline"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="outline">
          <el-input v-model="addForm.outline"></el-input>
        </el-form-item>
        <el-form-item label="标签" prop="tag_id">
          <el-input v-model="addForm.tag_id"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <textarea v-model="addForm.content"></textarea>
          <div v-html="$options.filters.markdown(addForm.content)"></div>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="addArticle">确 定</el-button>
    </el-card>
  </div>
</template>

<script>
import VueMarkdown from 'vue-markdown';

export default {
  data() {
    return {
      addForm: {
        headline: '',
        outline: '',
        tag_id: '',
        user_id: 12,
        content: ''
      },
      addFormRules: {
        headline: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 5, max: 20, message: '长度在5-20个字符', trigger: 'blur' }
        ],
        tag_id: [
          { required: true, message: '请选择标签', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    addArticle() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) {
          return this.$message.error('请填写必要的信息')
        }
        const { data: res } = await this.$http.post('article', this.addForm)
        if (res.status !== 200) {
          return this.$message.error('提交数据失败')
        }
        this.$message.success('提交数据成功')
        this.$router.push('/article')
      })
    }
  }
}

</script>

<style lang='less' scoped>
.el-button {
  margin-left: 50px;
}

/* 调整markdown编辑器的高度 */
textarea {
  height: 300px;
}

</style>
