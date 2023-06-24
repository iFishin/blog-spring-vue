<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>标签管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 输入框和添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="showAddDialog">添加标签</el-button>
        </el-col>

        <!-- <el-col :span="8">
          <el-input v-model="queryInfo.keyword" placeholder="请输入id" class="input-with-select" clearable
            @clear="searchTagList">
            <el-button @click="searchTagList" slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col> -->
      </el-row>
      <!-- 用户列表区 -->
      <el-table :data="tagList" border stripe>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="类别" prop="tagname"></el-table-column>
        <el-table-column label="描述" prop="avatar"></el-table-column>
        <el-table-column label="操作" width="240px">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteTag(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageIndex" :page-sizes="[5, 10, 20, 50]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
      <!-- 添加对话框 -->
      <el-dialog title="添加分类" :visible.sync="addDialogVisible" @close="addDialogClosed" width="50%">
        <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="70px">
          <el-form-item label="类别" prop="tagname">
            <el-input v-model="addForm.tagname"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="avatar">
            <el-input v-model="addForm.avatar"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addTag">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 编辑对话框 -->
      <el-dialog title="编辑分类" :visible.sync="editDialogVisible" @close="editDialogClosed" width="50%">
        <el-form ref="editFormRef" :model="editForm" :rules="editFormRules" label-width="70px">
          <el-form-item label="类别" prop="tagname">
            <el-input v-model="editForm.tagname"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="avatar">
            <el-input v-model="editForm.avatar"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editTag">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      token: null,
      queryInfo: {
        keyword: '',
        pageIndex: 1,
        pageSize: 5
      },
      tagList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        tagname: '',
        avatar: '',
      },
      addFormRules: {
        tagname: [{ required: true, message: '请输入类别名称', trigger: 'blur' }],
        avatar: [{ required: true, message: '请输入类别描述', trigger: 'blur' }]
      },
      editForm: {
        id: null,
        tagname: '',
        avatar: ''
      },
      editFormRules: {
        tagname: [{ required: true, message: '请输入类别名称', trigger: 'blur' }],
        avatar: [{ required: true, message: '请输入类别描述', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.token = window.sessionStorage.getItem('token')
    this.getTagList()
  },
  methods: {
    // 获取标签列表
    async getTagList() {
      const { data: res } = await this.$http.get('/tags')
      if (res.code !== 0) return this.$message.error('标签列表获取失败')
      console.log(res)
      this.tagList = res.data
      this.total = res.data.length
    },
    // 搜索标签列表 {先不实现了🤷‍♂️🤷‍♂️}
    async searchTagList() {
      // console.log(this.queryInfo.keyword)
      const { data: res } = await this.$http.get(`/tags/${this.queryInfo.keyword}`)
      if (res.code !== 0) return this.$message.error('标签列表获取失败')
      // console.log(res)
      this.tagList = res.data
      this.total = res.data.length
      console.log(this.tagList)
    },
    // 分页相关
    handleCurrentChange(val) {
      this.queryInfo.pageIndex = val
      this.getTagList()
    },
    handleSizeChange(val) {
      this.queryInfo.pageSize = val
      this.getTagList()
    },
    // 显示添加对话框
    showAddDialog() {
      this.addDialogVisible = true
    },
    // 添加标签
    async addTag() {
      const valid = await this.$refs.addFormRef.validate()
      if (!valid) return
      const { data: res } = await this.$http.post('/tags/create', this.addForm, {
        headers: {
          "Oauth-Token": `${this.token}`
        }
      })
      if (res.code !== 0) return this.$message.error('添加标签失败')
      this.$message.success('添加标签成功')
      this.addDialogVisible = false
      this.getTagList()
    },

    // 关闭添加对话框
    addDialogClosed() {
      this.$refs.addFormRef.clearValidate()
      this.addForm = {
        tagname: '',
        avatar: ''
      }
    },
    // 显示编辑对话框
    showEditDialog(row) {
      this.editForm.id = row.id
      this.editForm.tagname = row.tagname
      this.editForm.avatar = row.avatar
      this.editDialogVisible = true
    },
    // 编辑标签
    async editTag() {
      const valid = await this.$refs.editFormRef.validate()
      if (!valid) return
      const { data: res } = await this.$http.post('/tags/update', this.editForm, {
        headers: {
          "Oauth-Token": `${this.token}`
        }
      })
      if (res.code !== 0) return this.$message.error('编辑标签失败')
      this.$message.success('编辑标签成功')
      this.editDialogVisible = false
      this.getTagList()
    },
    // 关闭编辑对话框
    editDialogClosed() {
      this.$refs.editFormRef.clearValidate()
      this.editForm = {
        id: null,
        tagname: '',
        avatar: ''
      }
    },
    // 删除标签
    async deleteTag(tagId) {
      console.log(tagId)
      const confirmResult = await this.$confirm('是否确定删除该标签？', '提示')
      if (confirmResult !== 'confirm') return
      const { data: res } = await this.$http.get(`/tags/delete/${tagId}`,{
        headers: {
          "Oauth-Token": `${this.token}`
        }
      })
      if (res.code !== 0) return this.$message.error('删除标签失败')
      this.$message.success('删除标签成功')
      this.getTagList()
    }
  }
}
</script>

<style lang='less' scoped>
.input-with-select {
  width: 100%;
  max-width: 400px;
}
</style>
