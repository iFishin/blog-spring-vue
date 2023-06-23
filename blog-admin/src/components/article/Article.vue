<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 输入框 -->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="goAddpage">添加文章</el-button>
        </el-col>
        <el-col :span="8">
          <el-input
            v-model="queryInfo.keyword"
            placeholder="请输入内容"
            class="input-with-select"
            clearable
            @clear="getArticleList">
            <el-button @click="getArticleList" slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
      </el-row>
      <el-table :data="articleList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="标题" prop="headline"></el-table-column>
        <el-table-column label="作者" prop="tag_id"></el-table-column>
        <el-table-column label="标签" prop="tag_id"></el-table-column>
        <el-table-column label="状态" prop="status">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status === '1'">未锁定</el-tag>
            <el-tag type="danger" v-else-if="scope.row.status === '0'">锁定</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeUserById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageIndex"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryInfo: {
        keyword: '',
        pageIndex: 1,
        pageSize: 5
      },
      total: 0,
      articleList: []
    }
  },
  created () {
    this.getArticleList()
  },
  methods: {
    // 获取文章信息
    async getArticleList () {
      const { data: res } = await this.$http.get('article', { params: this.queryInfo })
      if (res.status !== 200) return this.$message.error('用户列表获取失败')
      this.articleList = res.data.data
      this.total = res.data.totalCount
    },
    // 监听页码的变化
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getArticleList()
    },
    // 监听页码值的变化
    handleCurrentChange (newPage) {
      this.queryInfo.pageIndex = newPage
      this.getArticleList()
    },
    async removeUserById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => {
        return err
      })
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消')
        return
      }
      const { data: res } = await this.$http.delete('article/' + id)
      if (res.status !== 200) {
        this.$message.error('删除文章失败')
      }
      this.$message.success('删除文章成功')
      this.getArticleList()
    },
    // 添加文章页面
    goAddpage () {
      this.$router.push('article/add')
    }
  }
}

</script>

<style lang='less' scoped>

</style>
