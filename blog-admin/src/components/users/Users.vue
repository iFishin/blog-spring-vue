<!--
 * @Date: 2023-06-23 10:21:51
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-24 21:21:06
 * @FilePath: \blog-admin\src\components\users\Users.vue
 * @Description: https://github.com/iFishin
-->
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 输入框 -->
      <el-row :gutter="8">
        <el-col :span="8">
          <el-input v-model="queryInfo.keyword" placeholder="请输入内容" class="input-with-select" clearable
            @clear="getUserList">
            <el-button @click="getUserList" slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="8" v-if="isAdmin">
          <el-button type="primary" style="float:right;" @click="showAddUserDialog">新增用户</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表区 -->
      <el-table :data="userList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="账号" prop="account"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="昵称" prop="nickname"></el-table-column>
        <el-table-column label="手机号码" prop="mobile_phone_number"></el-table-column>
        <el-table-column label="创建时间" prop="create_date"></el-table-column>
        <el-table-column label="状态" prop="status">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status === '1'">正常</el-tag>
            <el-tag type="danger" v-else-if="scope.row.status === '0'">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit"
              @click="showEditUserDialog(scope.row.id)">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteUser(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageIndex" :page-sizes="[10, 20, 50]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>
    <!-- 新增用户对话框 -->
    <el-dialog title="新增用户" :visible.sync="addUserDialogVisible" @close="addUserDialogClosed" width="50%">
      <el-form ref="addFormRef" :model="addForm" :rules="addFormRules" label-width="70px">
        <el-form-item label="账号" prop="account">
          <el-input v-model="addForm.account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="addForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile_phone_number">
          <el-input v-model="addForm.mobile_phone_number"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="addForm.status" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addUserDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 编辑用户对话框 -->
    <el-dialog title="编辑用户" :visible.sync="editUserDialogVisible" @close="editUserDialogClosed" width="50%">
      <el-form ref="editFormRef" :model="editForm" :rules="editFormRules" label-width="70px">
        <el-form-item label="账号" prop="account">
          <el-input v-model="editForm.account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="editForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile_phone_number">
          <el-input v-model="editForm.mobile_phone_number"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="editForm.status" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editUserDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  data() {
    return {
      token: null,
      userList: [], // 用户列表
      total: 0, // 总数
      queryInfo: { // 查询条件
        pageIndex: 1,
        pageSize: 10,
        keyword: ''
      },
      statusOptions: [ // 状态选项
        { label: '正常', value: '1' },
        { label: '禁用', value: '0' }
      ],
      addUserDialogVisible: false, // 新增用户对话框是否可见
      addForm: { // 新增用户表单数据
        account: '',
        password: '',
        email: '',
        nickname: '',
        mobile_phone_number: '',
        status: '1'
      },
      addFormRules: { // 新增用户表单验证规则
        account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }],
        nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
        mobile_phone_number: [{ required: true, message: '请输入手机号码', trigger: 'blur' }, { pattern: /^[1][3,4,5,7,8][0-9]{9}$/, message: '手机号码格式不正确', trigger: ['blur', 'change'] }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }]
      },
      editUserDialogVisible: false, // 编辑用户对话框是否可见
      editForm: {}, // 编辑用户表单数据
      editFormRules: { // 编辑用户表单验证规则
        account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }],
        nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
        mobile_phone_number: [{ required: true, message: '请输入手机号码', trigger: 'blur' },
        { pattern: /^[1][3,4,5,7,8][0-9]{9}/, message: '手机号码格式不正确', trigger: ['blur', 'change'] }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }]
      },
      isAdmin: false // 是否为管理员
    }
  },
  created() {
    this.token = window.sessionStorage.getItem('token')
    // 获取用户列表
    this.getUserList()
  },
  methods: {
    //用户列表
    async getUserList() {
      try {
        const res = await this.$http.get('/users',{
          headers: {
            "Oauth-Token": `${this.token}`
          }
        })
        if (res.code === 0) {
          console.log("ifishin")
          // this.userList = res.data
          this.total = res.data.length
        } else {
          this.message.error(res.msg)
        }
      } catch (error) {
        this.message.error('获取用户列表失败')
      }
    },
    handleSizeChange(val) {
      this.queryInfo.pageSize = val
      this.getUserList()
    },
    handleCurrentChange(val) {
      this.queryInfo.pageIndex = val
      this.getUserList()
    },
    showAddUserDialog() {
      this.addUserDialogVisible = true
    },
    showEditUserDialog(id) {
      // 根据id获取用户信息，并将其赋值给编辑表单数据
      this.editForm = this.userList.find(item => item.id === id)
      this.editUserDialogVisible = true
    },
    addUser() {
      this.refs.addFormRef.validate(async valid => {
        if (valid) {
          try {
            const res = await this.axios.post('/api/user/create', this.addForm)
            if (res.code === 200) {
              this.message.success('新增用户成功')
              this.addUserDialogVisible = false
              this.getUserList()
            } else {
              this.message.error(res.msg)
            }
          } catch (error) {
            console.log(error)
            this.message.error('新增用户失败')
          }
        }
      })
    },
    editUser() {
      this.refs.editFormRef.validate(async valid => {
        if (valid) {
          try {
            const res = await this.axios.post('/api/user/update', this.editForm)
            if (res.code === 200) {
              this.message.success('编辑用户成功')
              this.editUserDialogVisible = false
              this.getUserList()
            } else {
              this.message.error(res.msg)
            }
          } catch (error) {
            console.log(error)
            this.message.error('编辑用户失败')
          }
        }
      })
    },
    async deleteUser(id) {
      try {
        const res = await this.axios.delete(`/api/user/delete/{id}`)
        if (res.code === 200) {
          this.message.success('删除用户成功')
          this.getUserList()
        } else {
          this.message.error(res.msg)
        }
      } catch (error) {
        console.log(error)
        this.$message.error('删除用户失败')
      }
    }
  }
}
</script>
