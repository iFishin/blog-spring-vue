<template>
  <div class="timeline">
    <h2 class="title">操作记录</h2>
    <el-pagination :current-page.sync="currentPage" :page-size="pageSize" :total="totalRecords" @current-change="handlePageChange"/>
    <el-timeline>
      <el-timeline-item v-for="(item, index) in displayedRecords" :key="index" :timestamp="item.time" placement="top"
        :hide-timestamp="false">
        <el-card>
          <h4>{{ item.title }}</h4>
          <p>操作人：{{ item.user }} 提交于 {{ item.time }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-pagination :current-page.sync="currentPage" :page-size="pageSize" :total="totalRecords" @current-change="handlePageChange"/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      records: [],
      currentPage: 1,
      pageSize: 10
    };
  },
  mounted() {
    this.fetchRecords();
  },
  computed: {
    totalRecords() {
      return this.records.length;
    },
    displayedRecords() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.records.slice(startIndex, endIndex);
    }
  },
  methods: {
    fetchRecords() {
      this.$http.get('/logs')
        .then(response => {
          const data = response.data;
          // 将后端返回的数据转换为前端所需的格式
          const convertedData = data.map(item => ({
            time: new Date(item.createDate).toLocaleString(),
            title: item.operation,
            user: item.nickname || '匿名用户'
          }));
          this.records = convertedData.reverse(); // 倒转输出，显示最新数据
        })
        .catch(error => {
          console.error(error);
        });
    },
    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    }
  }
};
</script>

<style lang='less' scoped>
.timeline {
  max-width: 800px;
  margin: 50px auto;
  padding: 0 20px;

  .title {
    font-size: 24px;
    text-align: center;
    margin-bottom: 40px;
  }

  .el-timeline-item__timestamp {
    color: #999;
  }

  h4 {
    margin-top: 0;
    margin-bottom: 10px;
    font-size: 18px;
    font-weight: bold;
  }

  p {
    margin: 0;
    font-size: 16px;
    line-height: 1.5;
  }
}
</style>
