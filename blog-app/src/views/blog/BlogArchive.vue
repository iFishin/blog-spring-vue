<template>
  <div v-title :data-title="title">
    <el-container>

      <el-aside class="me-area">
        <ul class="me-month-list">
          <li v-for="a in archives" :key="a.year + a.month" class="me-month-item">
            <el-badge :value="a.count">
              <el-button @click="changeArchive(a.year, a.month)" size="small">{{ a.year + '年' + a.month + '月' }}
              </el-button>
            </el-badge>
          </li>
        </ul>
      </el-aside>


      <el-main class="me-articles">
        <div class="me-month-title">{{ currentArchive }}</div>

        <article-scroll-page v-bind="article"></article-scroll-page>

      </el-main>
    </el-container>
  </div>
</template>

<script>
import ArticleScrollPage from '@/views/common/ArticleScrollPage'
import { listArchives } from '@/api/article'

export default {
  name: "BlogArchive",
  components: {
    ArticleScrollPage
  },
  created() {
    this.listArchives()
  },
  watch: {
    '$route'() {
      if (this.$route.params.year && this.$route.params.month) {
        this.article.query.year = this.$route.params.year
        this.article.query.month = this.$route.params.month
      }
    }
  },
  data() {
    return {
      article: {
        query: {
          month: this.$route.params.month,
          year: this.$route.params.year
        }
      },
      archives: []
    }
  },
  computed: {
    title() {
      return this.currentArchive + ' - 文章归档 - F'
    },
    currentArchive() {
      if (this.article.query.year && this.article.query.month) {
        return `${this.article.query.year}年${this.article.query.month}月`
      }
      return '全部'
    }
  },
  methods: {

    changeArchive(year, month) {
      // this.currentArchive = `${year}年${month}月`
      // this.article.query.year = year
      // this.article.query.month = month
      this.$router.push({ path: `/archives/${year}/${month}` })
    },
    listArchives() {
      listArchives().then((data => {
        this.archives = data.data
      })).catch(error => {
        that.$message({ type: 'error', message: '文章归档加载失败!', showClose: true })
      })
    }
  }
}
</script>


<style scoped>
/* 标题样式 */
.v-title {
  padding: 24px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

/* 容器样式 */
.el-container {
  flex-direction: row;
  align-items: stretch;
  height: 100%;
}

/* 左侧归档区域样式 */
.me-area {
  padding: 5px;
  background-color: #f7f7f7;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  overflow-y: auto;
}

.me-month-list {
  list-style: none;
  margin: 0;
  padding: 0;
  font-size: 16px;
  color: #333;
}
.me-month-item {
  margin-top: 20px;
  display: flex;
  align-items: center;
  cursor: pointer;
  justify-content: center; /* 水平居中 */
  text-align: center; /* 文字水平居中 */
  vertical-align: middle; /* 垂直居中 */
}


.me-month-item:hover {
  color: #198754;
}

.me-month-title {
  font-size: 24px;
  margin-top: 24px;
}

/* 右侧文章列表区域样式 */
.me-articles {
  padding: 40px;
  width: 800px;
  background-color: #fff;
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
  border-left: solid 1px #e9eaec;
  overflow-y: auto;
}

.blog-article-list__item {
  padding: 20px;
  border-bottom: 1px solid #e9eaec;
}

.blog-article-list__link {
  color: #000;
  text-decoration: none;
}

.blog-article-list__title {
  font-size: 24px;
  margin-bottom: 16px;
  line-height: 1.2;
}

.blog-article-list__summary {
  font-size: 16px;
  color: #666;
  margin-bottom: 16px;
  line-height: 1.4;
}

.blog-article-list__date {
  font-size: 14px;
  color: #999;
}
</style>
