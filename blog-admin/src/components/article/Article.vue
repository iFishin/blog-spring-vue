<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- </el-row> -->
      <el-table :data="getPageData()" border stripe>
        <el-table-column label="id" prop="id" type="index"></el-table-column>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="作者" prop="author.nickname"></el-table-column>
        <el-table-column label="标签">
          <template slot-scope="scope">
            <div v-for="(tag, index) in scope.row.tags" :key="index"
              :style="{ backgroundColor: '#f2f2f2', padding: '5px', margin: '5px', display: 'inline-block', borderRadius: '5px' }">
              {{ tag.tagname }}
            </div>
          </template>
        </el-table-column><el-table-column label="访问量" prop="viewCounts"></el-table-column>
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeArticle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageIndex" :page-sizes="[5, 10, 20, 50]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>

    <!-- 添加/编辑文章弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false">
      <el-form :model="formData" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="formData.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="formData.author" placeholder="请输入作者"></el-input>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="formData.tags" multiple placeholder="请选择标签">
            <el-option v-for="(tag, index) in tagList" :key="index" :label="tag.tagname" :value="tag.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input v-model="formData.summary" placeholder="请输入摘要"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <quill-editor v-model="formData.content"></quill-editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

export default {
  components: {
    quillEditor
  },
  data() {
    return {
      queryInfo: {
        keyword: '',
        pageIndex: 1,
        pageSize: 5
      },
      total: 0,
      articleList: [],
      dialogTitle: '',
      dialogVisible: false,
      formData: {
        id: null,
        title: '',
        author: '',
        tags: [],
        content: ''
      },
      tagList: [],
    }
  },
  created() {
    this.token = window.sessionStorage.getItem('token')
    this.getArticleList()
    this.getTagList()
  },
  methods: {
    // 获取文章信息
    async getArticleList() {
      const { data: res } = await this.$http.get(`/articles`)
      if (res.code !== 0) return this.message.error('文章列表获取失败')
      this.articleList = res.data
      console.log(this.articleList)
      this.total = res.data.length
    },
    // 获取标签列表
    async getTagList() {
      const { data: res } = await this.$http.get(`/tags`)
      if (res.code !== 0) return this.message.error('标签列表获取失败')
      this.tagList = res.data
    },
    // 监听页码的变化
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize
      this.getArticleList()
    },
    // 监听页码值的变化
    handleCurrentChange(newPage) {
      this.queryInfo.pageIndex = newPage
      this.getArticleList()
    },
    getPageData() {
      const start = (this.queryInfo.pageIndex - 1) * this.queryInfo.pageSize;
      return this.articleList.slice(start, start + this.queryInfo.pageSize);
    },
    async removeArticle(id) {
      const confirmResult = await this.confirm('此操作将永久删除该文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      if (confirmResult !== 'confirm') {
        this.message.info('已取消')
        return
      }
      const { data: res } = await this.http.delete(`/articles/{id}`)
      if (res.code !== 0) {
        this.message.error('删除文章失败')
      }
      this.message.success('删除文章成功')
      this.getArticleList()
    },
    // 打开添加文章弹窗
    handleAdd() {
      this.dialogTitle = '添加文章'
      this.dialogVisible = true
      this.formData = {
        id: null,
        title: '',
        author: '',
        tags: [],
        content: ''
      }
    },
    // 打开编辑文章弹窗
    handleEdit(row) {
      this.dialogTitle = '编辑文章'
      this.dialogVisible = true
      this.formData = {
        id: row.id,
        title: row.title,
        author: row.author.nickname,
        tags: row.tags.map(tag => tag.id), // 修改此行
        summary: row.summary,
        content: row.content
      }
    },

    // 编辑文章处理
    async handleSubmit() {
      try {
        const { data: res } = await this.$http.put(`/articles/update/${this.formData.id}`, this.formData)
        if (res.code !== 0) return this.message.error('编辑文章失败')
        this.message.success('编辑文章成功')
        this.getArticleList()
        this.dialogVisible = false
      } catch (error) {
        console.log(error)
        this.message.error('编辑文章异常')
      }
    }


  }
}

</script>

<style lang='less' scoped>
.dialog-footer {
  text-align: center;
}
</style>
