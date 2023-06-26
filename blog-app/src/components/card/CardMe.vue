<!--
 * @Date: 2023-06-22 10:17:02
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-22 20:50:33
 * @FilePath: \blog-app\src\components\card\CardMe.vue
 * @Description: https://github.com/iFishin
-->
<template>
  <el-card>
    <h1 class="me-author-name">Fish</h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i> &nbsp;广西#柳州</span>
      <span><i class="me-icon-job"></i> &nbsp;🎣</span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(qq)" :title="qq.title" class="me-icon-QQ"></i>
      <i @click="goToGithub" :title="github.title" class="me-icon-github"></i>
    </div>
  </el-card>

</template>

<script>
export default {
  data() {
    return {
      qq: {
        title: 'QQ',
        message: '2079797279'
      },
      github: {
        title: 'GitHub',
        url: 'https://github.com/ifishin'
      }
    }
  },
  methods: {
    showTool(tool) {
      this.$message({
        duration: 0,
        showClose: true,
        dangerouslyUseHTMLString: true,
        message: '<strong>' + tool.message + '</strong>'
      });
    },
    goToGithub() {
      window.open(this.github.url, '_blank');
    }
  }
}
</script>


<style scoped>
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #198754;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
