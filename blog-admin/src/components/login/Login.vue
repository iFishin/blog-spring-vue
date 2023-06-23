<template>
  <div class="login_container">
    <div class="login_box">
      <h2 class="login_title">Blog Admin</h2>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginFormRules"
        class="login_form"
      >
        <!-- 用户名密码 -->
        <el-form-item prop="account">
          <el-input
            v-model="loginForm.account"
            placeholder="account"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            placeholder="Password"
            prefix-icon="el-icon-lock"
            type="password"
            @keyup.enter.native="login"
          ></el-input>
        </el-form-item>

        <!-- 按钮  -->
        <el-form-item class="btns">
          <el-button
            type="primary"
            @click="login"
            @keyup.enter.native="login"
            class="login_btn"
          >
            LOGIN
          </el-button>
          <el-button
            type="info"
            @click="resetLoginForm"
            class="reset_btn"
          >
            RESET
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        account: '',
        password: ''
      },
      loginFormRules: {
        account: [
          { required: true, message: 'Please enter your account', trigger: 'blur' },
          { min: 4, max: 10, message: 'Length should be between 5 and 10 characters', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
          { min: 4, max: 15, message: 'Length should be between 6 and 15 characters', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },
    async login() {
      try {
        await this.$refs.loginFormRef.validate();
        const { data: res } = await this.$http.post('/login', this.loginForm);
        if (res.code !== 0) {
          throw new Error('Login failed');
        }
        console.log(res.data['Oauth-Token']);
        this.$message.success('Logged in successfully!');
        window.sessionStorage.setItem('token', res.data['Oauth-Token']);
        this.$router.push('/home');
      } catch (error) {
        this.$message.error(error.message);
      }
    }
  }
};
</script>

<style lang="less" scoped>
@import url('https://fonts.googleapis.com/css?family=Montserrat&display=swap');

.login_container {
  background: #000027;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login_box {
  width: 400px;
  height: auto;
  background: #1a1a2e;
  border-radius: 10px;
  padding: 30px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login_title {
  font-family: 'Montserrat', sans-serif;
  color: #fff;
  margin-bottom: 50px;
  text-align: center;
  font-size: 36px;
  font-weight: bold;
}

.el-form-item__label {
  color: #fff !important;
  font-size: 18px !important;
}

.el-input__inner {
  background-color: rgba(255, 255, 255, 0.15);
  border-color: transparent !important;
  color: #fff !important;
  font-size: 18px !important;
}

.el-input__prefix {
  color: #fff !important;
}

.el-icon-user,
.el-icon-lock {
  color: #fff !important;
}

.login_btn,
.reset_btn {
  width: 150px;
  height: 40px;
  margin-top: 20px;
  font-size: 18px;
  font-weight: bold;
  text-transform: uppercase;
  border-radius: 10px;
  transition: all 0.3s ease-in-out;
}

.login_btn:hover {
  background-color: #198754;
}

.reset_btn:hover {
  background-color: #dc3545;
}

</style>
